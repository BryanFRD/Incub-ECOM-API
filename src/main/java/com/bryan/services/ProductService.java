package com.bryan.services;

import com.bryan.models.Product;
import com.bryan.models.ProductType;
import com.bryan.repositories.ProductRepository;
import com.bryan.repositories.ProductTypeRepository;
import com.bryan.requests.ProductRequest;
import com.bryan.responses.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    
    private final ProductRepository productRepository;
    private final ProductTypeRepository productTypeRepository;

    public ProductResponse getProducts(Pageable pageable, String type, String search) {
        Product product = new Product();
        product.setName(search);
        
        
        if(!type.isEmpty() || !type.isBlank()){
            ProductType productType = new ProductType();
            productType.setId(UUID.fromString(type));
            product.setProductType(productType);
        }
        
        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("name", new ExampleMatcher.GenericPropertyMatcher().ignoreCase().contains());
        
        Page<Product> page = productRepository.findAll(Example.of(product, matcher), pageable);
        
        return new ProductResponse(page.toList(), page.getTotalElements(), page.getTotalPages());
    }
    
    public void addProduct(List<ProductRequest> request){
        List<Product> products = new ArrayList<>();
        
        request.forEach(productRequest -> {
            Optional<ProductType> productType = productTypeRepository.findByName(productRequest.productType());
            
            if(productType.isEmpty()){
                return;
            }

            Product product = new Product();

            product.setName(productRequest.name());
            product.setPrice(productRequest.price());
            product.setSrc(productRequest.src());
            product.setAlt(productRequest.alt());
            product.setProductType(productType.get());
            
            products.add(product);
        });
        
        productRepository.saveAll(products);
    }
    
}
