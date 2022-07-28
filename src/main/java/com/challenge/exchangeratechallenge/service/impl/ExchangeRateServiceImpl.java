package com.challenge.exchangeratechallenge.service.impl;

import com.challenge.exchangeratechallenge.model.ExchangeRates;
import com.challenge.exchangeratechallenge.service.ExchangeRateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created By Alekhya Tirumalagiri
 */
@Service
@Slf4j
public class ExchangeRateServiceImpl implements ExchangeRateService {

    @Value("${application.url}")
    private String exchangeURL;

    @Autowired
    private RestTemplate restTemplate;

    private final Pattern pattern = Pattern.compile("[^a-z]", Pattern.CASE_INSENSITIVE);

    public ExchangeRateServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ExchangeRates getAllExchangeRatesForGivenCurrency(String currency) {
        ExchangeRates exchangeRates = new ExchangeRates();
        try {
            log.info("Inside getAllExchangeRatesForGivenCurrency method in ExchangeRateServiceImpl");
            if (StringUtils.isEmpty(currency) || pattern.matcher(currency).find()) {
                exchangeRates.setSuccess("false");
                exchangeRates.setErrorMessage("Please provide valid details");
                return exchangeRates;
            }
            exchangeRates = restTemplate.getForObject(exchangeURL + "/latest?base=" + currency, ExchangeRates.class);
        } catch (Exception e) {
            log.error("Exception occured while processing data for currency {}", currency, e);
            exchangeRates.setSuccess("false");
            exchangeRates.setErrorMessage(e.getMessage());
        }
        return exchangeRates;
    }

    public List<ExchangeRates> getConversionValue(String from, List<String> toList, Integer amount) {
        log.info("Inside getValueFromAtoListOfCcy method in ExchangeRateServiceImpl");
        List<ExchangeRates> exchangeRatesList = new ArrayList<>();
        try {
            log.info("Inside getConversionValue method in ExchangeRateServiceImpl");
            if (StringUtils.isEmpty(from) || CollectionUtils.isEmpty(toList)
                    || pattern.matcher(from).find() || toList.stream().anyMatch(e -> pattern.matcher(e).find())) {
                constructErrorMessage("Please provide valid details", exchangeRatesList);
                return exchangeRatesList;
            }
            for (String toDate : toList) {
                ExchangeRates exchangeRates = restTemplate.getForObject(exchangeURL + "/convert?from=" + from + "&to=" + toDate + "&amount=" + amount, ExchangeRates.class);
                exchangeRatesList.add(exchangeRates);
            }
        } catch (Exception e) {
            log.error("Exception occured while processing data in getExchangeRateFromCurrencyAToB -> {}", e);
            constructErrorMessage(e.getMessage(), exchangeRatesList);
        }
        return exchangeRatesList;
    }

    public void constructErrorMessage(String errorMessage, List<ExchangeRates> exchangeRatesList) {
        ExchangeRates exchangeRates = new ExchangeRates();
        exchangeRates.setSuccess("false");
        exchangeRates.setErrorMessage(errorMessage);
        exchangeRatesList.add(exchangeRates);
    }

}
