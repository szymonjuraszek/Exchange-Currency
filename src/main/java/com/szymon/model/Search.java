package com.szymon.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Search {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    List<String> codes;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    List<Rate> rates;
}
