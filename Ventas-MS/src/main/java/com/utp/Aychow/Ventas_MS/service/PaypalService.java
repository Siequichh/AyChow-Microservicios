package com.utp.Aychow.Ventas_MS.service;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaypalService {

    private static final String PAYPAL_MODE = "sandbox";
    private static final String CLIENT_ID = "AQHAR08imHLDsL7mVTJhPirB_nWth5WKSTkuHReVykWvcN6rEgvkIRImuSrq_s_FLMNlxIecys-fTZow";
    private static final String CLIENT_SECRET = "EKul2QFlppBUzqe8puiyvCBdOaEG3-WHrX03gXDceYT0sIHH9rqjGO1G-3mBqyKJCkqvBXNL_bKfXlaM";

    public Payment createPayment(Double total, String currency, String method, String intent,
                                 String description, String cancelUrl, String successUrl) throws PayPalRESTException {
        Amount amount = new Amount();
        amount.setCurrency(currency);
        amount.setTotal(String.format("%.2f", total));

        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(method.toString());

        Payment payment = new Payment();
        payment.setIntent(intent.toString());
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);

        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, PAYPAL_MODE);
        return payment.create(apiContext);
    }

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);
        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, PAYPAL_MODE);
        return payment.execute(apiContext, paymentExecution);
    }
}

