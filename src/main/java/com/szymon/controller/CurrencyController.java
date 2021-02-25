package com.szymon.controller;

import com.szymon.model.Money;
import com.szymon.model.Rate;
import com.szymon.service.RateMapperService;
import com.szymon.service.RateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

import static com.szymon.config.WebConstants.GET_SPECIFIC_CURRENCY_RATE;
import static com.szymon.config.WebConstants.GET_TABLE_C_FROM_NBP;

@RestController
public class CurrencyController {

    private final Logger logger = LoggerFactory.getLogger(CurrencyController.class);

    private final RateMapperService rateMapperService;

    private final WebClient webClient;

    private final RateService rateService;

    public CurrencyController(
            RateMapperService rateMapperService,
            WebClient webClient,
            RateService rateService
    ) {
        this.rateMapperService = rateMapperService;
        this.webClient = webClient;
        this.rateService = rateService;
    }

    @GetMapping("/currencies")
    public List<String> getAllAvailableCurrencies() {
        return rateMapperService.mapJsonFromTableCToRates(getCurrenciesRate()).stream()
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
        return rateMapperService.mapJsonFromTableCToRates(getCurrenciesRate())
                .stream()
                .filter(rate -> codes.contains(rate.getCode()))
                .collect(Collectors.toList());
    }

    private String getCurrencyRate(String currencyCode) {
        return webClient.get()
                .uri(GET_SPECIFIC_CURRENCY_RATE + currencyCode)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private String getCurrenciesRate() {
        return webClient.get()
                .uri(GET_TABLE_C_FROM_NBP)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

}