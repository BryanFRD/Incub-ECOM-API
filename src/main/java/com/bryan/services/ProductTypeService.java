package com.bryan.services;

import com.bryan.models.ProductType;
import com.bryan.repositories.ProductTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductTypeService {
    
    private final ProductTypeRepository productTypeRepository;
    
    public List<ProductType> getProductTypes(){
        return productTypeRepository.findAll();
    }
    
    public void addProduct(List<ProductType> productTypes){
        productTypeRepository.saveAll(productTypes);
    }
    
}
