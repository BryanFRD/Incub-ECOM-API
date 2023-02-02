package com.bryan.controllers;

import com.bryan.models.ProductType;
import com.bryan.requests.ProductTypeRequest;
import com.bryan.services.ProductTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("v1/producttype")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ProductTypeController {
    
    private final ProductTypeService productTypeService;
    
    @GetMapping
    public List<ProductType> getProductTypes(){
        return productTypeService.getProductTypes();
    }
    
    @PostMapping
    public void addProductType(@RequestBody List<ProductTypeRequest> productTypeRequests){
        List<ProductType> productTypes = new ArrayList<>();
        
        productTypeRequests.forEach(pt -> {
            ProductType productType = new ProductType();
            productType.setName(pt.name());
            productTypes.add(productType);
        });
        
        productTypeService.addProduct(productTypes);
    }
    
}
