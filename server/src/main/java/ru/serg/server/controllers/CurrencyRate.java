package ru.serg.server.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/currency")
public class CurrencyRate {
    Random random = new Random();

    @ResponseBody
    @GetMapping("/USDRUB")
    public float currencyRate() {
        return random.nextFloat(80, 100);
    }
}