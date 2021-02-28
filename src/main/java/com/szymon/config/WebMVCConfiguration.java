package com.szymon.config;

import com.szymon.controller.CurrencyRequestInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMVCConfiguration implements WebMvcConfigurer {

    private final CurrencyRequestInterceptor currencyRequestInterceptor;

    public WebMVCConfiguration(CurrencyRequestInterceptor currencyRequestInterceptor) {
        this.currencyRequestInterceptor = currencyRequestInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(currencyRequestInterceptor)
                .addPathPatterns("/currencies/**");
    }
}
