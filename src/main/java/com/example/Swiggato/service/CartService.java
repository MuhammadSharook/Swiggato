package com.example.Swiggato.service;

import com.example.Swiggato.dto.request.FoodRequest;
import com.example.Swiggato.dto.response.CartStatusResponse;
import com.example.Swiggato.dto.response.FoodResponse;
import com.example.Swiggato.exception.CustomerNotFoundException;
import com.example.Swiggato.exception.MenuItemNotFoundException;
import com.example.Swiggato.model.Cart;
import com.example.Swiggato.model.Customer;
import com.example.Swiggato.model.FoodItem;
import com.example.Swiggato.model.MenuItem;
import com.example.Swiggato.repository.CartRepository;
import com.example.Swiggato.repository.CustomerRepository;
import com.example.Swiggato.repository.FoodRepo;
import com.example.Swiggato.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    final CustomerRepository customerRepository;
    final MenuRepository menuRepository;
    final CartRepository cartRepository;
    final FoodRepo foodRepo;

    @Autowired
    public CartService(CustomerRepository customerRepository,
                       MenuRepository menuRepository,
                       CartRepository cartRepository,
                       FoodRepo foodRepo) {
        this.customerRepository = customerRepository;
        this.menuRepository = menuRepository;
        this.cartRepository = cartRepository;
        this.foodRepo = foodRepo;
    }

    public CartStatusResponse addFoodItemToCart(FoodRequest foodRequest) {

        Customer customer = customerRepository.findByMobileNo(foodRequest.getCustomerMobile());
        if(customer==null){
            throw new CustomerNotFoundException("Customer doesn't exisit");
        }

        Optional<MenuItem> menuItemOptional = menuRepository.findById(foodRequest.getMenuItemId());
        if(menuItemOptional.isEmpty()){
            throw new MenuItemNotFoundException("Item not available in the restaurant!!!");
        }

        MenuItem menuItem = menuItemOptional.get();
        if(!menuItem.getRestaurant().isOpened() || !menuItem.isAvailable()){
            throw new MenuItemNotFoundException("Given dish is out of stock for now!!!");
        }

        // ready to add item to cart
        FoodItem foodItem = FoodItem.builder()
                .menuItem(menuItem)
                .cart(customer.getCart())
                .requiredQuantity(foodRequest.getRequiredQuantity())
                .totalCost(foodRequest.getRequiredQuantity()*menuItem.getPrice())
                .build();

        Cart cart = customer.getCart();
        FoodItem savedFoodItem = foodRepo.save(foodItem);

        double cartTotal = 0;
        for(FoodItem food: cart.getFoodItems()){
            cartTotal += food.getRequiredQuantity()*food.getMenuItem().getPrice();
        }
        cart.getFoodItems().add(savedFoodItem);
        cart.setCartTotal(cartTotal);
        menuItem.getFoodItems().add(savedFoodItem);

        // save
        Cart savedCart = cartRepository.save(cart);
        MenuItem savedMenuItem = menuRepository.save(menuItem);

        // prepare
        List<FoodResponse> foodResponseList = new ArrayList<>();
        for(FoodItem food: cart.getFoodItems()){
            FoodResponse foodResponse = FoodResponse.builder()
                    .dishName(food.getMenuItem().getDishName())
                    .price(food.getMenuItem().getPrice())
                    .category(food.getMenuItem().getCategory())
                    .veg(food.getMenuItem().isVeg())
                    .quantityAdded(food.getRequiredQuantity())
                    .build();

            foodResponseList.add(foodResponse);
        }

        return CartStatusResponse.builder()
                .customerName(savedCart.getCustomer().getName())
                .customerMobile(savedCart.getCustomer().getMobileNo())
                .customerAddress(savedCart.getCustomer().getAddress())
                .foodList(foodResponseList)
                .restaurantName(savedMenuItem.getRestaurant().getName())
                .cartTotal(cartTotal)
                .build();

    }
}
