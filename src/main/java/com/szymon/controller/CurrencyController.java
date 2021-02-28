package com.szymon.controller;

import com.szymon.cache.LogCacheManager;
import com.szymon.entity.Log;
import com.szymon.model.Money;
import com.szymon.model.Rate;
import com.szymon.service.RateMapperService;
import com.szymon.service.RateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

import static com.szymon.config.WebConstants.*;

@RestController
public class CurrencyController {

    private final Logger logger = LoggerFactory.getLogger(CurrencyController.class);

    private final RateMapperService rateMapperService;

    private final LogCacheManager logCacheManager;

    private final RestTemplate restTemplate;

    private final RateService rateService;

    public CurrencyController(
            RateMapperService rateMapperService,
            LogCacheManager logCacheManager,
            RestTemplate restTemplate, RateService rateService
    ) {
        this.rateMapperService = rateMapperService;
        this.logCacheManager = logCacheManager;
        this.restTemplate = restTemplate;
        this.rateService = rateService;
    }

    @GetMapping("/currencies")
    public List<String> getAllAvailableCurrencies() {
        List<Rate> rates = rateMapperService.mapJsonFromTableCToRates(getCurrenciesRate());

        logCacheManager.add(new Log("Response OK"));

        return rates.stream()
                .map(Rate::getCode)
                .collect(Collectors.toList());
    }

    @GetMapping("/currencies/exchange")
    public Money getExchangeValue(
            @RequestParam("amount") int amount,
            @RequestParam("from") String from,
            @RequestParam("to") String to
    ) {

        Rate rateFrom = this.rateMapperService.mapJsonFromSpecificCurrencyToRate(getCurrencyRate(from));

        Rate rateTo = this.rateMapperService.mapJsonFromSpecificCurrencyToRate(getCurrencyRate(to));

        logger.info("Rate From: " + rateFrom);
        logger.info("Rate To: " + rateTo);

        return rateService.exchangeCurrency(rateFrom, rateTo, amount);
    }

    @GetMapping("/currencies/rates")
    public List<Rate> getRatesForSpecificCurrencies(@RequestBody List<String> codes) {

        List<Rate> rates = rateMapperService.mapJsonFromTableCToRates(getCurrenciesRate());

        return rates.stream()
                .filter(rate -> codes.contains(rate.getCode()))
                .collect(Collectors.toList());
    }

    private String getCurrencyRate(String currencyCode) {
        return restTemplate.getForObject(GET_SPECIFIC_CURRENCY_RATE + currencyCode, String.class);
    }

    private String getCurrenciesRate() {
        return restTemplate.getForObject(GET_TABLE_C_FROM_NBP, String.class);
    }

}
