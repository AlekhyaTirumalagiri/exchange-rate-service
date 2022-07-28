package com.challenge.exchangeratechallenge.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
/**
 * Created By Alekhya Tirumalagiri
 */
@Getter
@Setter
public class ExchangeRates {
    @JsonView({Views.OnlyRates.class,
                Views.ConversionValue.class})
    public String success;
    public String base;
    public String date;
    @JsonView(Views.OnlyRates.class)
    public Map<String, String> rates;
    public String historical;
    @JsonView({Views.ConversionValue.class})
    public String result;
    @JsonView(Views.ConversionValue.class)
    public Map<String, String> query;
    public Map<String, String> info;
    @JsonView({Views.OnlyRates.class,
            Views.ConversionValue.class})
    public String errorMessage;
}
