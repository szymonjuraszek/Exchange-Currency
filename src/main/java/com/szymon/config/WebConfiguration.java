package com.szymon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import static com.szymon.config.WebConstants.BASE_URL_FOR_NBP_CURRENCY_EXCHANGE_API;

@Configuration
public class WebConfiguration {

    @Bean
    public WebClient getWebClient() {
        return WebClient.create(BASE_URL_FOR_NBP_CURRENCY_EXCHANGE_API);
    }
}
