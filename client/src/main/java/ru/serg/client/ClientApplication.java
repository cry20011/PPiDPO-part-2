package ru.serg.client;

import ru.serg.client.currency_listeners.CurrencyListener;
import ru.serg.client.currency_listeners.impl.RestCurrencyListener;

public class ClientApplication {
    public static void main(String[] args) {
        CurrencyListener usdRubListener = new RestCurrencyListener("http://localhost:8080/currency/USDRUB", 5000);
        usdRubListener.Listen();
    }

}
