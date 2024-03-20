package ru.serg.grpc.server.controllers;

import io.grpc.stub.StreamObserver;
import ru.serg.grpc.CurrencyResponse;
import ru.serg.grpc.CurrencyServiceGrpc;

import java.util.Random;

public class CurrencyRate extends CurrencyServiceGrpc.CurrencyServiceImplBase {
    Random random = new Random();

    @Override
    public void currencyUSDRUB(com.google.protobuf.Empty request, StreamObserver<CurrencyResponse> responseObserver) {
        CurrencyResponse currencyResponse = CurrencyResponse
                .newBuilder()
                .setValue(random.nextFloat() * 20 + 80)
                .build();

        responseObserver.onNext(currencyResponse);
        responseObserver.onCompleted();
    }
}
