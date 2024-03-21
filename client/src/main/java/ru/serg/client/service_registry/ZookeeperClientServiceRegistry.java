package ru.serg.client.service_registry;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

public class ZookeeperClientServiceRegistry implements ClientServiceRegistry {
    private final String servicesPath;
    private final ZooKeeper zooKeeper;

    public ZookeeperClientServiceRegistry(String server, String servicesPath) throws IOException, InterruptedException {
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
    public String getService(String name) throws InterruptedException, KeeperException {
        String servicePath = servicesPath + "/" + name;
        byte[] data = zooKeeper.getData(servicePath, null, null);
        return new String(data);
    }

    @Override
    public void close() {
        try {
            zooKeeper.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
