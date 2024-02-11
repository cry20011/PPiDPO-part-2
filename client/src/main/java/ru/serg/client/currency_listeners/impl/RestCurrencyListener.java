package ru.serg.client.currency_listeners.impl;


import org.springframework.web.client.RestTemplate;
import ru.serg.client.currency_listeners.CurrencyListener;

public class RestCurrencyListener implements CurrencyListener {
    RestTemplate restTemplate = new RestTemplate();
    private final String url;
    private final int pollMillisInterval;

    public RestCurrencyListener(String url, int pollMillisInterval) {
        this.url = url;
        this.pollMillisInterval = pollMillisInterval;
    }

    @Override
    public void Listen() {
        while (true) {
            System.out.println(restTemplate.getForObject(url, String.class));
            try {
                Thread.sleep(pollMillisInterval);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
