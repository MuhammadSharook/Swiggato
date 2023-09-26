package com.example.Swiggato.controller;

import com.example.Swiggato.dto.request.DeliveryPartnerRequest;
import com.example.Swiggato.service.DeliveryPartnerService;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/partner")
public class DeliveryPartnerController {

    final DeliveryPartnerService deliveryPartnerService;

    @Autowired
    public DeliveryPartnerController(DeliveryPartnerService deliveryPartnerService) {
        this.deliveryPartnerService = deliveryPartnerService;
    }

    @PostMapping("/add")
    public ResponseEntity addDeliveryPartner(@RequestBody DeliveryPartnerRequest deliveryPartnerRequest){

        String message = deliveryPartnerService.addPartner(deliveryPartnerRequest);
        return new ResponseEntity(message, HttpStatus.CREATED);
    }

    // give delivery partner with highest number of deliveries

    // send an email to all the partners who have done less than 10 deliveries.


}
