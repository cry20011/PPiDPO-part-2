package ru.serg.client.currency_listeners.impl;

import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import ru.serg.grpc.CurrencyServiceGrpc;
import ru.serg.client.currency_listeners.CurrencyListener;

public class GrpcCurrencyListener implements CurrencyListener {
    private final int pollMillisInterval;
    private final ManagedChannel grpcChannel;
    private final CurrencyServiceGrpc.CurrencyServiceBlockingStub currencyService;

    public GrpcCurrencyListener(String host, int port, int pollMillisInterval) {
        this.grpcChannel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        this.currencyService = CurrencyServiceGrpc.newBlockingStub(grpcChannel);
        this.pollMillisInterval = pollMillisInterval;
    }

    @Override
    public void Listen() {
        while (true) {
            System.out.println(this.currencyService.currencyUSDRUB(Empty.newBuilder().build()).getValue());
            try {
                Thread.sleep(pollMillisInterval);
            } catch (InterruptedException e) {
                grpcChannel.shutdown();
                throw new RuntimeException(e);
            }
        }
    }
}
