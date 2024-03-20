package ru.serg.grpc.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import ru.serg.grpc.server.controllers.CurrencyRate;
import ru.serg.grpc.server.service_registry.ServerServiceRegistry;
import ru.serg.grpc.server.service_registry.ZookeeperServerServiceRegistry;

import java.io.IOException;

public class GrpcServer {
    private static final int port = 8081;

    static public void main(String[] args) throws IOException, InterruptedException {
        ServerServiceRegistry registry = new ZookeeperServerServiceRegistry("localhost:2181", "/services");
        registry.addService("grpcService", "localhost:8081");

        Server server = ServerBuilder.forPort(port).addService(new CurrencyRate()).build();

        System.out.println("Starting grpc server on " + port + " port.");
        server.start();
        System.out.println("Server started!");
        server.awaitTermination();
    }
}
