package com.hotelManagment.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    //FAKE payment as it is not real time
    @PostMapping("/pay")
    public String fakePayment(@RequestBody String payload) {
        return "Payment Successful";
    }
}
