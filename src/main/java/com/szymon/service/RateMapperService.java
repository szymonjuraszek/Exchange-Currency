package com.szymon.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.szymon.model.Rate;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RateMapperService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    public Rate mapJsonFromSpecificCurrencyToRate(String specificJson) {
        JsonNode jsonNode = objectMapper.readTree(specificJson);

        return new Rate(
                objectMapper.readValue(jsonNode.get("currency").toString(), String.class),
                objectMapper.readValue(jsonNode.get("code").toString(), String.class),
                objectMapper.readValue(jsonNode.get("rates").get(0).get("bid").toString(), Double.class),
                objectMapper.readValue(jsonNode.get("rates").get(0).get("ask").toString(), Double.class));
    }

    @SneakyThrows
    public List<Rate> mapJsonFromTableCToRates(String specificJson) {
        JsonNode jsonNode = objectMapper.readTree(specificJson);

        return objectMapper.readValue(jsonNode.get(0).get("rates").toString(), new TypeReference<>() {
        });
    }
}
