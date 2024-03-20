package ru.serg.server.service_registry;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ZookeeperServerServiceRegistry implements ServerServiceRegistry {

    private final String servicesPath;
    private final ZooKeeper zooKeeper;
    private final List<String> services = new ArrayList<>();

    public ZookeeperServerServiceRegistry(String server, String servicesPath) throws IOException, InterruptedException {
        this.servicesPath = servicesPath;

        Object lock = new Object();

        Watcher connectionWatcher = we -> {
            if (we.getState() == Watcher.Event.KeeperState.SyncConnected) {
                System.out.println("Connected to Zookeeper in " + Thread.currentThread().getName());
                synchronized (lock) {
                    lock.notifyAll();
                }
            }
        };

        int sessionTimeout = 2000;

        synchronized (lock) {
            zooKeeper = new ZooKeeper(server, sessionTimeout, connectionWatcher);
            lock.wait();
        }
    }

    @Override
    public void addService(String name, String url) {
        String servicePath = servicesPath + "/" + name;
        List<ACL> acls = ZooDefs.Ids.OPEN_ACL_UNSAFE;
        try {
            if (zooKeeper.exists(servicesPath, false) == null) {
                zooKeeper.create(servicesPath, url.getBytes(), acls, CreateMode.PERSISTENT);
            }

            if (zooKeeper.exists(servicePath, false) == null) {
                zooKeeper.create(servicePath, url.getBytes(), acls, CreateMode.PERSISTENT);
                services.add(servicePath);
            }
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        for (String service: services) {
            try {
                zooKeeper.delete(service, -1);
            } catch (InterruptedException | KeeperException e) {
                e.printStackTrace();
            }
        }

        try {
            zooKeeper.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
