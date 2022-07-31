package com.challenge.exchangeratechallenge.service.impl;

import com.challenge.exchangeratechallenge.model.ExchangeRates;
import com.challenge.exchangeratechallenge.service.ExchangeRateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
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

    @Cacheable(value = "all_exchange_rates", key = "#currency")
    public ExchangeRates getAllExchangeRatesForGivenCurrency(String currency) {
        ExchangeRates exchangeRates;
        try {
            log.info("Inside getAllExchangeRatesForGivenCurrency method in ExchangeRateServiceImpl");
            if (StringUtils.isEmpty(currency) || pattern.matcher(currency).find()) {
                return new ExchangeRates("false", "Please provide valid details");
            }
            exchangeRates = restTemplate.getForObject(exchangeURL + "/latest?base=" + currency, ExchangeRates.class);
        } catch (Exception e) {
            log.error("Exception occured while processing data for currency {}", currency, e);
            exchangeRates = new ExchangeRates("false", e.getMessage());
        }
        return exchangeRates;
    }

    @Cacheable(cacheNames = "conversion_values")
    public List<ExchangeRates> getConversionValue(String from, List<String> toList, Integer amount) {
        log.info("Inside getValueFromAtoListOfCcy method in ExchangeRateServiceImpl");
        List<ExchangeRates> exchangeRatesList = new ArrayList<>();
        try {
            if (StringUtils.isEmpty(from) || CollectionUtils.isEmpty(toList)
                    || pattern.matcher(from).find() || toList.stream().anyMatch(e -> pattern.matcher(e).find())) {
                exchangeRatesList.add(new ExchangeRates("false", "Please provide valid details"));
                return exchangeRatesList;
            }
            for (String toDate : toList) {
                ExchangeRates exchangeRates = restTemplate.getForObject(exchangeURL + "/convert?from=" + from + "&to=" + toDate + "&amount=" + amount, ExchangeRates.class);
                exchangeRatesList.add(exchangeRates);
            }
        } catch (Exception e) {
            log.error("Exception occured while processing data in getExchangeRateFromCurrencyAToB -> {}", e);
            exchangeRatesList.add(new ExchangeRates("false", e.getMessage()));
        }
        return exchangeRatesList;
    }


}
