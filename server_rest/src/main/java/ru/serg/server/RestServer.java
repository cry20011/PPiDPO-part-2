package ru.serg.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.serg.server.service_registry.ServerServiceRegistry;
import ru.serg.server.service_registry.ZookeeperServerServiceRegistry;

import java.io.IOException;
import java.util.Collections;

@SpringBootApplication
public class RestServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerServiceRegistry registry = new ZookeeperServerServiceRegistry("localhost:2181", "/services");
        registry.addService("restService", "localhost:8080");

        SpringApplication app = new SpringApplication(RestServer.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8080"));
        app.run(args);
    }
}
