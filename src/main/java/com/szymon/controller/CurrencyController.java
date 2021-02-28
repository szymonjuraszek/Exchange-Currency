package com.szymon.controller;

import com.szymon.cache.LogCacheManager;
import com.szymon.entity.Log;
import com.szymon.model.Exchange;
import com.szymon.model.Rate;
import com.szymon.model.Search;
import com.szymon.service.RateMapperService;
import com.szymon.service.RateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

import static com.szymon.config.WebConstants.*;

@RestController
public class CurrencyController {

    private final Logger logger = LoggerFactory.getLogger(CurrencyController.class);

    private final RateMapperService rateMapperService;

    private final RestTemplate restTemplate;

    private final RateService rateService;

    public CurrencyController(
            RateMapperService rateMapperService,
            RestTemplate restTemplate,
            RateService rateService
    ) {
        this.rateMapperService = rateMapperService;
        this.restTemplate = restTemplate;
        this.rateService = rateService;
    }

    @GetMapping("/currencies")
    public List<String> getAllAvailableCurrencies() {
        List<Rate> rates = rateMapperService.mapJsonFromTableCToRates(getCurrenciesRate());

        return rates.stream()
                .map(Rate::getCode)
                .collect(Collectors.toList());
    }

    @PostMapping("/currencies/exchanges")
    public Exchange createExchangedValue(@RequestBody Exchange exchange) {

        Rate rateFrom = this.rateMapperService.mapJsonFromSpecificCurrencyToRate(getCurrencyRate(exchange.getFrom()));

        Rate rateTo = this.rateMapperService.mapJsonFromSpecificCurrencyToRate(getCurrencyRate(exchange.getTo()));

        logger.info("Rate From: " + rateFrom);
        logger.info("Rate To: " + rateTo);

        return rateService.exchangeCurrency(rateFrom, rateTo, exchange.getAmount());
    }

    @PostMapping("/currencies/searches")
    public Search createSearchRatesForSpecificCurrencies(@RequestBody Search search) {

        List<Rate> rates = rateMapperService.mapJsonFromTableCToRates(getCurrenciesRate());

        search.setRates(rates.stream()
                .filter(rate -> search.getCodes().contains(rate.getCode()))
                .collect(Collectors.toList()));

        return search;
    }

    private String getCurrencyRate(String currencyCode) {
        return restTemplate.getForObject(GET_SPECIFIC_CURRENCY_RATE + currencyCode, String.class);
    }

    private String getCurrenciesRate() {
        return restTemplate.getForObject(GET_TABLE_C_FROM_NBP, String.class);
    }

}
