package com.example.Swiggato.transformer;

import com.example.Swiggato.dto.request.RestaurantRequest;
import com.example.Swiggato.dto.response.FoodResponse;
import com.example.Swiggato.dto.response.RestaurantResponse;
import com.example.Swiggato.model.Restaurant;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RestaurantTransformer {

    public static Restaurant RestaurantRequestToRestaurant(RestaurantRequest restaurantRequest){
         return Restaurant.builder()
                 .name(restaurantRequest.getName())
                 .contactNumber(restaurantRequest.getContactNumber())
                 .location(restaurantRequest.getLocation())
                 .restrauntCategory(restaurantRequest.getRestrauntCategory())
                 .opened(true)
                 .availableFoodItems(new ArrayList<>())
                 .orders(new ArrayList<>())
                 .build();
    }

    public static RestaurantResponse RestaurantToRestaurantResponse(Restaurant restaurant){

        List<FoodResponse> menu = restaurant.getAvailableFoodItems()
                .stream()
                .map(foodItem -> FoodItemTransformer.FoodItemToFoodResponse(foodItem))
                .collect(Collectors.toList());

        return RestaurantResponse.builder()
                .name(restaurant.getName())
                .contactNumber(restaurant.getContactNumber())
                .location(restaurant.getLocation())
                .opened(restaurant.isOpened())
                .menu(menu)
                .build();
    }
}
