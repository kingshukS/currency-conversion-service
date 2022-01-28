package io.kingshuk.currencyconversionservice.proxy;

import io.kingshuk.currencyconversionservice.model.ExchangeValue;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "currency-exchange-service",  url = "http://localhost:8000")
//@FeignClient(name = "CURRENCY-EXCHANGE-SERVICE")
//@LoadBalancerClient(name = "CURRENCY-EXCHANGE-SERVICE", configuration= LoadBalancerConfiguration.class)
//@RibbonClient(name = "currency-exchange-service")
@FeignClient(name = "zuul-api-gateway-server")
public interface CurrencyExchangeServiceProxy {

    @GetMapping("currency-exchange-service/currency-exchange/from/{fromCurr}/to/{toCurr}")
    ExchangeValue retrieveExchangeValue(@PathVariable("fromCurr") String fromCurr, @PathVariable("toCurr") String toCurr);
}
