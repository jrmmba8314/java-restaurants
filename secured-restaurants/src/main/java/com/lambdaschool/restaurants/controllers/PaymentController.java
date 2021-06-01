package com.lambdaschool.restaurants.controllers;

import com.lambdaschool.restaurants.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The entry point for clients to access payment data
 */
@RestController
@RequestMapping("/payments")
public class PaymentController
{
    /**
     * Using the Payment service to process payment data
     */
    @Autowired
    private PaymentService paymentService;
}
