package com.utp.Aychow.Ventas_MS.controller;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.utp.Aychow.Ventas_MS.entity.Venta;
import com.utp.Aychow.Ventas_MS.request.VentaRequest;
import com.utp.Aychow.Ventas_MS.service.PaypalService;
import com.utp.Aychow.Ventas_MS.service.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/paypal")
@RequiredArgsConstructor
public class PayPalController {

    private final PaypalService paypalService;
    private final VentaService ventaService;

    @PostMapping("/create-payment")
    public ResponseEntity<String> createPayment(@RequestBody VentaRequest ventaRequest) {
        try {
            String cancelUrl = "http://localhost:8089/checkout";
            String successUrl = "http://localhost:8089/success";
            Payment payment = paypalService.createPayment(
                    (double) ventaRequest.getTotal(),
                    "USD",
                    "paypal",
                    "sale",
                    "Compra en AyChow",
                    cancelUrl,
                    successUrl
            );

            return ResponseEntity.ok(payment.getLinks()
                    .stream()
                    .filter(link -> link.getRel().equals("approval_url"))
                    .findFirst()
                    .get()
                    .getHref());
        } catch (PayPalRESTException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el pago.");
        }
    }

    @PostMapping("/execute-payment")
    public ResponseEntity<String> executePayment(@RequestParam String paymentId, @RequestParam String payerId, @RequestBody VentaRequest ventaRequest) {
        try {
            // Ejecutar el pago con PayPal
            Payment payment = paypalService.executePayment(paymentId, payerId);

            if (payment.getState().equals("approved")) {
                Venta venta = ventaService.generarVenta(ventaRequest);
                return ResponseEntity.ok("Pago realizado con éxito. Venta creada con ID: " + venta.getIdVenta());
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al confirmar el pago.");
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al ejecutar el pago.");
        }
    }
}

