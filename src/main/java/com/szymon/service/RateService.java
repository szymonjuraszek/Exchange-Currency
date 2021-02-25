package com.szymon.service;

import com.szymon.model.Money;
import com.szymon.model.Rate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RateService {

    private final Logger logger = LoggerFactory.getLogger(RateService.class);

    public Money exchangeCurrency(Rate rateFrom, Rate rateTo, double amount) {

        double exchangedAmount = (rateFrom.getBid() * amount) / rateTo.getAsk();

        logger.info("Amount: " + amount + " FROM " + rateFrom.getCode() + " TO " + rateTo.getCode() + " give: " + exchangedAmount);

        return new Money(rateTo.getCode(), exchangedAmount);
    }
}
