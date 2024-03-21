package ru.serg.client.service_registry;

import org.apache.zookeeper.KeeperException;

public interface ClientServiceRegistry {
    String getService(String name) throws InterruptedException, KeeperException;
    void close();
}
