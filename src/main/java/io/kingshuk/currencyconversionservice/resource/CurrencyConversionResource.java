package io.kingshuk.currencyconversionservice.resource;

import io.kingshuk.currencyconversionservice.model.CurrencyConversionBean;
import io.kingshuk.currencyconversionservice.model.ExchangeValue;
import io.kingshuk.currencyconversionservice.proxy.CurrencyExchangeServiceProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@RestController
public class CurrencyConversionResource {

    private static Logger logger = LoggerFactory.getLogger(CurrencyConversionResource.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CurrencyExchangeServiceProxy currencyExchangeServiceProxy;

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrency(@PathVariable("from") String from,
                                                  @PathVariable("to") String to,
                                                  @PathVariable("quantity") String quantity)
    {
        ExchangeValue exchangeValue = restTemplate.getForObject("http://CURRENCY-EXCHANGE-SERVICE/currency-exchange/from/"+from+"/to/"+ to, ExchangeValue.class);
        logger.info("--CurrencyConversionResource.convertCurrency(): exchange value={}",exchangeValue);
        CurrencyConversionBean currencyConversionBean = new CurrencyConversionBean();
        currencyConversionBean.setFromCurrency(exchangeValue.getFromCurrency());
        currencyConversionBean.setToCurrency(exchangeValue.getToCurrency());
        currencyConversionBean.setQuantity(BigDecimal.valueOf(Long.parseLong(quantity)));
        currencyConversionBean.setConversionMultiple(exchangeValue.getConversionMultiple());
        currencyConversionBean.setPort(Integer.parseInt(exchangeValue.getPort()));
        BigDecimal conversionMultiple = exchangeValue.getConversionMultiple();
        BigDecimal conversionValue = conversionMultiple.multiply(BigDecimal.valueOf(Long.parseLong(quantity)));
        currencyConversionBean.setConversionValue(conversionValue);

        return currencyConversionBean;
    }

    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrencyWithFeign(@PathVariable("from") String from,
                                                  @PathVariable("to") String to,
                                                  @PathVariable("quantity") String quantity)
    {
        ExchangeValue exchangeValue = currencyExchangeServiceProxy.retrieveExchangeValue(from,to);
        logger.info("--CurrencyConversionResource.convertCurrencyWithFeign(): exchange value={}",exchangeValue);
        CurrencyConversionBean currencyConversionBean = new CurrencyConversionBean();
        currencyConversionBean.setFromCurrency(exchangeValue.getFromCurrency());
        currencyConversionBean.setToCurrency(exchangeValue.getToCurrency());
        currencyConversionBean.setQuantity(BigDecimal.valueOf(Long.parseLong(quantity)));
        currencyConversionBean.setConversionMultiple(exchangeValue.getConversionMultiple());
        currencyConversionBean.setPort(Integer.parseInt(exchangeValue.getPort()));
        BigDecimal conversionMultiple = exchangeValue.getConversionMultiple();
        BigDecimal conversionValue = conversionMultiple.multiply(BigDecimal.valueOf(Long.parseLong(quantity)));
        currencyConversionBean.setConversionValue(conversionValue);

        return currencyConversionBean;
    }
}
