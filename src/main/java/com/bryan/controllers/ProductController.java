package com.bryan.controllers;

import com.bryan.models.Product;
import com.bryan.repositories.ProductRepository;
import com.bryan.requests.ProductRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/product")
public class ProductController {
    
    private ProductRepository productRepository;
    
    public ProductController(ProductRepository productRepository){
        this.productRepository = productRepository;
    }
    
    @GetMapping
    public List<Product> getProducts(){
        return productRepository.findAll();
    }

    @PostMapping
    public void addProduct(@RequestBody ProductRequest request){
        Product product = new Product();

        product.setName(request.name());
        product.setPrice(request.price());
        product.setSrc(request.src());
        product.setAlt(request.alt());

        productRepository.save(product);
    }
    
}
