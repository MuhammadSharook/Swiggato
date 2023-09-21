package com.example.Swiggato.service;

import com.example.Swiggato.dto.request.FoodRequest;
import com.example.Swiggato.dto.request.RestaurantRequest;
import com.example.Swiggato.dto.response.RestaurantResponse;
import com.example.Swiggato.exception.RestaurantNotFoundException;
import com.example.Swiggato.model.FoodItem;
import com.example.Swiggato.model.Restaurant;
import com.example.Swiggato.repository.RestaurantRespository;
import com.example.Swiggato.transformer.FoodItemTransformer;
import com.example.Swiggato.transformer.RestaurantTransformer;
import com.example.Swiggato.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RestaurantService {

    final RestaurantRespository restaurantRespository;
    final ValidationUtils validationUtils;

    @Autowired
    public RestaurantService(RestaurantRespository restaurantRespository,
                             ValidationUtils validationUtils) {
        this.restaurantRespository = restaurantRespository;
        this.validationUtils = validationUtils;
    }

    public RestaurantResponse addRestaurant(RestaurantRequest restaurantRequest) {

        // dto -> model
        Restaurant restaurant = RestaurantTransformer.RestaurantRequestToRestaurant(restaurantRequest);
        //persist/save the model in db
        Restaurant savedRestaurant = restaurantRespository.save(restaurant);
        // prepare response dto from model
        return RestaurantTransformer.RestaurantToRestaurantResponse(savedRestaurant);
    }

    public String changeOpenedStatus(int id) {

        //check whether id is valid or not
        if(!validationUtils.validateRestaurantId(id)){
            throw new RestaurantNotFoundException("Restaurant doesn't exist!!");
        }

        Restaurant restaurant = restaurantRespository.findById(id).get();
        restaurant.setOpened(!restaurant.isOpened());
        restaurantRespository.save(restaurant);

        if(restaurant.isOpened()){
            return "Restaurant is opened now!!!";
        }

        return "Restaurant is closed";
    }

    public RestaurantResponse addFoodItemtToRestaurant(FoodRequest foodRequest) {

        // check reataurant is valid or not
        if(!validationUtils.validateRestaurantId(foodRequest.getRestaurantId())){
            throw new RestaurantNotFoundException("Restaurant doesn't exist!!");
        }

        Restaurant restaurant = restaurantRespository.findById(foodRequest.getRestaurantId()).get();
        // make food entity
        FoodItem foodItem = FoodItemTransformer.FoodRequestToFoodItem(foodRequest);
        foodItem.setRestaurant(restaurant);

        restaurant.getAvailableFoodItems().add(foodItem);

        // save rest and food
        Restaurant savedRestaurant = restaurantRespository.save(restaurant);

        // prepare response
        return RestaurantTransformer.RestaurantToRestaurantResponse(savedRestaurant);

    }
}
