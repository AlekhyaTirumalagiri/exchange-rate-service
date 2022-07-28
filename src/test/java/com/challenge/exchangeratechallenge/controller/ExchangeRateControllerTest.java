package com.challenge.exchangeratechallenge.controller;

import com.challenge.exchangeratechallenge.model.ExchangeRates;
import com.challenge.exchangeratechallenge.service.ExchangeRateService;
import com.challenge.exchangeratechallenge.service.impl.ExchangeRateServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
/**
 * Created By Alekhya Tirumalagiri
 */
@SpringBootTest
public class ExchangeRateControllerTest {

    @InjectMocks
    private ExchangeRateController exchangeRateController;
    @InjectMocks
    ExchangeRateService exchangeRateService;

    private List<String> toDateList = Arrays.asList("INR","USA");

    @Before
    public void setUp() {
        exchangeRateService = new ExchangeRateServiceImpl(new RestTemplate());
        exchangeRateController = new ExchangeRateController(exchangeRateService);
        ReflectionTestUtils.setField(exchangeRateService, "exchangeURL", "https://api.exchangerate.host");
    }

    @Test
    public void fectAllExchangeRatesForGivenCurrency_Success() {
        ExchangeRates actual = exchangeRateController.getAllExchangeRatesForGivenCurrency("GBP");
        Assert.assertEquals("true", actual.getSuccess());
        Assert.assertEquals("GBP", actual.getBase());
        Assert.assertNotNull(actual.getRates());
    }

    @Test
    public void fectAllExchangeRatesForGivenCurrency_after_passing_wrong_information_one() {
        ExchangeRates actual = exchangeRateController.getAllExchangeRatesForGivenCurrency("123");
        Assert.assertEquals("false", actual.getSuccess());
        Assert.assertNull(actual.getRates());
        Assert.assertEquals("Please provide valid details", actual.getErrorMessage());
    }

    @Test
    public void fectAllExchangeRatesForGivenCurrency_Even_after_passing_wrong_information_two() {
        ExchangeRates actual = exchangeRateController.getAllExchangeRatesForGivenCurrency("AAA");
        Assert.assertEquals("true", actual.getSuccess());
        Assert.assertNotNull(actual.getRates());
    }

    @Test
    public void fectAllExchangeRatesForGivenCurrency_Exception() {
        ReflectionTestUtils.setField(exchangeRateService, "exchangeURL", "https://api.xchangerate.host");
        ExchangeRates actual = exchangeRateController.getAllExchangeRatesForGivenCurrency("GBP");
        Assert.assertEquals("false", actual.getSuccess());
        Assert.assertEquals(null, actual.getBase());
        Assert.assertNull(actual.getRates());
    }

    @Test
    public void fectExchangeRateFromCurrencyAToB_Success() {
        List<ExchangeRates> actual = exchangeRateController.getConversionValue("GBP", Arrays.asList("INR"), 1);
        Assert.assertEquals("true", actual.get(0).getSuccess());
        Assert.assertNotNull(actual.get(0).getResult());
        Assert.assertNotNull(actual.get(0).getQuery());
    }

    @Test
    public void fectExchangeRateFromCurrencyAToB_after_passing_wrong_information_one() {
        List<ExchangeRates> actual = exchangeRateController.getConversionValue("123", Arrays.asList("INR"), 1);
        Assert.assertEquals("false", actual.get(0).getSuccess());
        Assert.assertEquals("Please provide valid details", actual.get(0).getErrorMessage());
    }

    @Test
    public void fectValueFromCurrencyAToB_Even_after_passing_wrong_information_two() {
        List<ExchangeRates> actual = exchangeRateController.getConversionValue("AAA", Arrays.asList("123"), 1);
        Assert.assertEquals("false", actual.get(0).getSuccess());
        Assert.assertEquals("Please provide valid details", actual.get(0).getErrorMessage());
    }

    @Test
    public void fectValueFromCurrencyAToB_Even_after_passing_wrong_information_three() {
        List<ExchangeRates> actual = exchangeRateController.getConversionValue("USA", Arrays.asList("123"), 1);
        Assert.assertEquals("false", actual.get(0).getSuccess());
        Assert.assertEquals("Please provide valid details", actual.get(0).getErrorMessage());
    }

    @Test
    public void fectValueFromAtoListOfCcy_Success() {
        List<ExchangeRates> actual = exchangeRateController.getConversionValue("GBP", toDateList,10);
        Assert.assertEquals("true", actual.get(0).getSuccess());
        Assert.assertNotNull(actual.get(0).getResult());
        Assert.assertNotNull(actual.get(0).getQuery());
    }

    @Test
    public void fectValueFromAtoListOfCcy_Even_after_passing_wrong_information_one() {
        List<ExchangeRates> actual = exchangeRateController.getConversionValue("123", toDateList, 10);
        Assert.assertEquals("false", actual.get(0).getSuccess());
        Assert.assertEquals("Please provide valid details", actual.get(0).getErrorMessage());
    }

    @Test
    public void fectValueFromAtoListOfCcy_Even_after_passing_wrong_information_two() {
        List<ExchangeRates> actual = exchangeRateController.getConversionValue("AAA", toDateList, 10);
        Assert.assertEquals("true", actual.get(0).getSuccess());
        Assert.assertNotNull(actual.get(0).getResult());
        Assert.assertNotNull(actual.get(0).getQuery());
    }

    @Test
    public void fectValueFromAtoListOfCcy_Exception() {
        ReflectionTestUtils.setField(exchangeRateService, "exchangeURL", "https://api.xchangerate.host");
        List<ExchangeRates> actual = exchangeRateController.getConversionValue("GBP", toDateList, 10);
        Assert.assertEquals("false", actual.get(0).getSuccess());
        Assert.assertNull(actual.get(0).getResult());
        Assert.assertNull(actual.get(0).getQuery());
    }
}
