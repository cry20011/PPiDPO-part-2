package ru.serg.client;


import org.apache.zookeeper.KeeperException;
import ru.serg.client.currency_listeners.CurrencyListener;
import ru.serg.client.currency_listeners.impl.GrpcCurrencyListener;
import ru.serg.client.service_registry.ClientServiceRegistry;
import ru.serg.client.service_registry.ZookeeperClientServiceRegistry;

import java.io.IOException;

public class Client {
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ClientServiceRegistry registry = new ZookeeperClientServiceRegistry("localhost:2181", "/services");
        String restService = registry.getService("restService");
        String grpcService = registry.getService("grpcService");


//        CurrencyListener usdRubRestListener = new RestCurrencyListener("http://" + restService + "/currency/USDRUB", 5000);
//        new Thread(usdRubRestListener::Listen).start();

        String[] split = grpcService.split(":");
        CurrencyListener usdRubGrpcListener = new GrpcCurrencyListener(split[0], Integer.parseInt(split[1]), 5000);
        new Thread(usdRubGrpcListener::Listen).start();
    }
}
