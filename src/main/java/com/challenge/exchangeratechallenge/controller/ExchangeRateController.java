package com.challenge.exchangeratechallenge.controller;

import com.challenge.exchangeratechallenge.model.ExchangeRates;
import com.challenge.exchangeratechallenge.model.Views;
import com.challenge.exchangeratechallenge.service.ExchangeRateService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 * Created By Alekhya Tirumalagiri
 */
@RestController
@Slf4j
@RequestMapping("/exchange")
public class ExchangeRateController {

    @Autowired
    private ExchangeRateService exchangeRateService;

    public ExchangeRateController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    /**
     * @param currency
     * @return
     */
    @GetMapping(value = "/getAllExchangeRates")
    @JsonView(Views.OnlyRates.class)
    public ExchangeRates getAllExchangeRatesForGivenCurrency(@RequestParam("ccy") String currency) {
        log.info("Inside getAllExchangeRatesForGivenCurrency method in ExchangeRateController");
        return exchangeRateService.getAllExchangeRatesForGivenCurrency(currency);
    }

    @GetMapping(value = "/getConversionValue")
    @JsonView(Views.ConversionValue.class)
    public List<ExchangeRates> getConversionValue(@RequestParam("from") String from,
                                                  @RequestParam("to") List<String> toList,
                                                  @RequestParam(value = "amount", required = false, defaultValue = "1") Integer amount) {
        log.info("Inside getValueFromAtoListOfCcy method in ExchangeRateController");
        return exchangeRateService.getConversionValue(from, toList, amount);
    }

}
