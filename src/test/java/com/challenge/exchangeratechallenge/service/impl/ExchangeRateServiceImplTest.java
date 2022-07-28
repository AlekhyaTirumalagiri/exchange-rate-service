package com.challenge.exchangeratechallenge.service.impl;

import com.challenge.exchangeratechallenge.model.ExchangeRates;
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
public class ExchangeRateServiceImplTest {

    @InjectMocks
    private ExchangeRateServiceImpl exchangeRateService;

    private List<String> toDateList = Arrays.asList("INR", "USA");

    @Before
    public void setUp() {
        exchangeRateService = new ExchangeRateServiceImpl(new RestTemplate());
        ReflectionTestUtils.setField(exchangeRateService, "exchangeURL", "https://api.exchangerate.host");
    }

    @Test
    public void fectAllExchangeRatesForGivenCurrency_Success() {
        ExchangeRates actual = exchangeRateService.getAllExchangeRatesForGivenCurrency("GBP");
        Assert.assertEquals("true", actual.getSuccess());
        Assert.assertEquals("GBP", actual.getBase());
        Assert.assertNotNull(actual.getRates());
    }

    @Test
    public void fectAllExchangeRatesForGivenCurrency_after_passing_wrong_information_one() {
        ExchangeRates actual = exchangeRateService.getAllExchangeRatesForGivenCurrency("123");
        Assert.assertEquals("false", actual.getSuccess());
        Assert.assertNull(actual.getRates());
        Assert.assertEquals("Please provide valid details", actual.getErrorMessage());
    }

    @Test
    public void fectAllExchangeRatesForGivenCurrency_Even_after_passing_wrong_information_two() {
        ExchangeRates actual = exchangeRateService.getAllExchangeRatesForGivenCurrency("AAA");
        Assert.assertEquals("true", actual.getSuccess());
        Assert.assertNotNull(actual.getRates());
    }

    @Test
    public void fectAllExchangeRatesForGivenCurrency_Exception() {
        ReflectionTestUtils.setField(exchangeRateService, "exchangeURL", "https://api.xchangerate.host");
        ExchangeRates actual = exchangeRateService.getAllExchangeRatesForGivenCurrency("GBP");
        Assert.assertEquals("false", actual.getSuccess());
        Assert.assertEquals(null, actual.getBase());
        Assert.assertNull(actual.getRates());
    }

    @Test
    public void fectValueFromCurrencyAToB_Success() {
        List<ExchangeRates> actual = exchangeRateService.getConversionValue("GBP", Arrays.asList("INR"), 10);
        Assert.assertEquals("true", actual.get(0).getSuccess());
        Assert.assertNotNull(actual.get(0).getResult());
        Assert.assertNotNull(actual.get(0).getQuery());
    }

    @Test
    public void fectValueFromCurrencyAToB_after_passing_wrong_information_one() {
        List<ExchangeRates> actual = exchangeRateService.getConversionValue("123", Arrays.asList("INR"), 10);
        Assert.assertEquals("false", actual.get(0).getSuccess());
        Assert.assertNull(actual.get(0).getResult());
        Assert.assertNull(actual.get(0).getQuery());
    }

    @Test
    public void fectValueFromCurrencyAToB_Even_after_passing_wrong_information_two() {
        List<ExchangeRates> actual = exchangeRateService.getConversionValue("AAA", Arrays.asList("INR"), 10);
        Assert.assertEquals("true", actual.get(0).getSuccess());
        Assert.assertNotNull(actual.get(0).getResult());
        Assert.assertNotNull(actual.get(0).getQuery());
    }

    @Test
    public void fectValueFromCurrencyAToB_Exception() {
        ReflectionTestUtils.setField(exchangeRateService, "exchangeURL", "https://api.xchangerate.host");
        List<ExchangeRates> actual = exchangeRateService.getConversionValue("GBP", Arrays.asList("INR"), 10);
        Assert.assertEquals("false", actual.get(0).getSuccess());
        Assert.assertNull(actual.get(0).getResult());
        Assert.assertNull(actual.get(0).getQuery());
        Assert.assertNotNull(actual.get(0).getErrorMessage());
    }

    @Test
    public void fectValueFromCurrencyAToB_Exception_One() {
        List<ExchangeRates> actual = exchangeRateService.getConversionValue("123", Arrays.asList("INR"), 10);
        Assert.assertEquals("false", actual.get(0).getSuccess());
        Assert.assertEquals("Please provide valid details", actual.get(0).getErrorMessage());
        Assert.assertNotNull(actual.get(0).getErrorMessage());
    }

    @Test
    public void fectValueFromAtoListOfCcy_Success() {
        List<ExchangeRates> actual = exchangeRateService.getConversionValue("GBP", toDateList, 10);
        Assert.assertEquals("true", actual.get(0).getSuccess());
        Assert.assertNotNull(actual.get(0).getResult());
        Assert.assertNotNull(actual.get(0).getQuery());
    }

    @Test
    public void fectValueFromAtoListOfCcy_after_passing_wrong_information_one() {
        List<ExchangeRates> actual = exchangeRateService.getConversionValue("123", toDateList, 10);
        Assert.assertEquals("false", actual.get(0).getSuccess());
        Assert.assertNull(actual.get(0).getResult());
        Assert.assertNull(actual.get(0).getQuery());
    }

    @Test
    public void fectValueFromAtoListOfCcy_Even_after_passing_wrong_information_two() {
        List<ExchangeRates> actual = exchangeRateService.getConversionValue("AAA", toDateList, 10);
        Assert.assertEquals("true", actual.get(0).getSuccess());
        Assert.assertNotNull(actual.get(0).getResult());
        Assert.assertNotNull(actual.get(0).getQuery());
    }

    @Test
    public void fectValueFromAtoListOfCcy_Exception() {
        ReflectionTestUtils.setField(exchangeRateService, "exchangeURL", "https://api.xchangerate.host");
        List<ExchangeRates> actual = exchangeRateService.getConversionValue("GBP", toDateList, 10);
        Assert.assertEquals("false", actual.get(0).getSuccess());
        Assert.assertNull(actual.get(0).getResult());
        Assert.assertNull(actual.get(0).getQuery());
        Assert.assertNotNull(actual.get(0).getErrorMessage());
    }

}
