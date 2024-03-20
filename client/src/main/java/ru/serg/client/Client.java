package ru.serg.client;


import ru.serg.client.currency_listeners.CurrencyListener;
import ru.serg.client.currency_listeners.impl.GrpcCurrencyListener;
import ru.serg.client.currency_listeners.impl.RestCurrencyListener;

public class Client {
    public static void main(String[] args) {
//        CurrencyListener usdRubRestListener = new RestCurrencyListener("http://localhost:8080/currency/USDRUB", 5000);
//        usdRubRestListener.Listen();

        CurrencyListener usdRubGrpcListener = new GrpcCurrencyListener("localhost", 8081, 5000);
        usdRubGrpcListener.Listen();
    }

}
