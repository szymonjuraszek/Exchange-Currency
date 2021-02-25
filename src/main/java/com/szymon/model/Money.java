package com.szymon.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Money {

    private String currencyCode;

    private double amount;

    public Money(String currencyCode, double amount) {
        this.currencyCode = currencyCode;
        this.amount = amount;
    }
}
