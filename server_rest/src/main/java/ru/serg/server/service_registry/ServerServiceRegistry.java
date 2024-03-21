package ru.serg.server.service_registry;

public interface ServerServiceRegistry {
    void addService(String name, String url);
    void close();
}
