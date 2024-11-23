package com.utp.Aychow.Ventas_MS.controller;

import com.paypal.api.payments.Payment;
import com.utp.Aychow.Ventas_MS.entity.Venta;
import com.utp.Aychow.Ventas_MS.service.PaypalService;
import com.utp.Aychow.Ventas_MS.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
@RestController
@RequestMapping
public class SuccessController {

    @Autowired
    private PaypalService paypalService;

    @GetMapping("/success")
    public ResponseEntity<Void> handleSuccess(@RequestParam String paymentId, @RequestParam String PayerID) {
        try {
            paypalService.executePayment(paymentId, PayerID);

            String redirectUrl = "http://localhost:8089/success?paymentId=" + paymentId + "&PayerID=" + PayerID;
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(redirectUrl));
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}


