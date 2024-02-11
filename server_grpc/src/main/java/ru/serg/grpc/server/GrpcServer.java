package ru.serg.grpc.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import ru.serg.grpc.server.controllers.CurrencyRate;

import java.io.IOException;

public class GrpcServer {
    private static final int port = 8081;

    static public void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(port).addService(new CurrencyRate()).build();

        System.out.println("Starting grpc server on " + port + " port.");
        server.start();
        System.out.println("Server started!");
        server.awaitTermination();
    }
}
