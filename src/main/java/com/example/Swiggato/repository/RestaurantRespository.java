package com.example.Swiggato.repository;

import com.example.Swiggato.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRespository extends JpaRepository<Restaurant,Integer> {
}
