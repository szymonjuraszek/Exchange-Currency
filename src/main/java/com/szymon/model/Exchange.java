package com.szymon.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Exchange {

    private double amount;

    private String from;

    private String to;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private double exchangedValue;

    public Exchange() {
    }

    public Exchange(double amount, String from, String to, double exchangedValue) {
        this.amount = amount;
        this.from = from;
        this.to = to;
        this.exchangedValue = exchangedValue;
    }
}
