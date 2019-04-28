package com.lambdaschool.coffeebean.Stripe;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class StripeService
{

//    @Value("sk_test_iXtYHR0NqelPYcrdrLB5Sj8O")
//    String secretKey;

    @PostConstruct
    public void init()
    {
        Stripe.apiKey = "sk_test_iXtYHR0NqelPYcrdrLB5Sj8O";
    }

    public Charge charge(ChargeRequest chargeRequest)
            throws StripeException
    {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", chargeRequest.getAmount());
        chargeParams.put("currency", chargeRequest.getCurrency());
        chargeParams.put("description", chargeRequest.getDescription());
        chargeParams.put("source", chargeRequest.getStripeToken());
        chargeParams.put("receipt_email", chargeRequest.getStripeEmail());
        return Charge.create(chargeParams);
    }
}