package com.bryan.repositories;

import com.bryan.models.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProductTypeRepository extends JpaRepository<ProductType, UUID> {
    
    Optional<ProductType> findByName(String name);
    
}
