package com.example.Swiggato.repository;

import com.example.Swiggato.model.DeliveryPartner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryPartnerRepo extends JpaRepository<DeliveryPartner,Integer> {
    String findRandomPartner = "select * from delivery_partner Order By RANDOM() LIMIT 1";

    @Query(value = findRandomPartner,nativeQuery = true)
    DeliveryPartner findRandomDeliveryPartner();
}
