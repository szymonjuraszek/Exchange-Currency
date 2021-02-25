package com.szymon.model;

import lombok.Data;

@Data
public class Rate {

    private String currency;

    private String code;

    private double bid;

    private double ask;

    public Rate() {
    }

    public Rate(String currency, String code, double bid, double ask) {
        this.currency = currency;
        this.code = code;
        this.bid = bid;
        this.ask = ask;
    }
}
