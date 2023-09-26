package com.example.Swiggato.controller;

import com.example.Swiggato.dto.request.MenuRequest;
import com.example.Swiggato.dto.request.RestaurantRequest;
import com.example.Swiggato.dto.response.RestaurantResponse;
import com.example.Swiggato.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    final RestaurantService restaurantService;

    /**
     * Constructor Injection
     * @param restaurantService  --> bean of restaurant Service
     */
    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping("/add")
    public ResponseEntity addRestaurant(@RequestBody RestaurantRequest restaurantRequest){
        RestaurantResponse restaurantResponse = restaurantService.addRestaurant(restaurantRequest);
        return new ResponseEntity(restaurantResponse, HttpStatus.CREATED);
    }


    @PutMapping("/update/status")
    public ResponseEntity changeOpenedStatus(@RequestParam int id){
        String message = restaurantService.changeOpenedStatus(id);
        return new ResponseEntity(message,HttpStatus.ACCEPTED);
    }


    @PostMapping("/add/menu")
    public ResponseEntity addMenuItemtToRestaurant(@RequestBody MenuRequest menuRequest){
        RestaurantResponse restaurantResponse = restaurantService.addMenuItemtToRestaurant(menuRequest);
        return new ResponseEntity(restaurantResponse,HttpStatus.CREATED);
    }

    // get menu of a restaurant

    // give all the restauratns who have served more than 'x' number of orders

    // give the restaurants which have maximum number of items in their menu and which are opened also
}
