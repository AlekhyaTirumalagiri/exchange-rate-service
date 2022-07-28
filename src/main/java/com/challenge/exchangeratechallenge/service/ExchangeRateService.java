package com.challenge.exchangeratechallenge.service;

import com.challenge.exchangeratechallenge.model.ExchangeRates;

import java.util.List;
/**
 * Created By Alekhya Tirumalagiri
 */
public interface ExchangeRateService {

    ExchangeRates getAllExchangeRatesForGivenCurrency(String currency);

    List<ExchangeRates> getConversionValue(String from, List<String> toList, Integer amount);
}
