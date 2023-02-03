package com.bryan.controllers;

import com.bryan.models.Product;
import com.bryan.models.ProductType;
import com.bryan.repositories.ProductRepository;
import com.bryan.repositories.ProductTypeRepository;
import com.bryan.requests.ProductRequest;
import com.bryan.responses.ProductResponse;
import com.bryan.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/product")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ProductController {
    
    private final ProductService productService;
    
    @GetMapping
    public ProductResponse getProducts(
            @RequestParam(name = "page", defaultValue = "0") int page, 
            @RequestParam(name = "size", defaultValue = "5") int size,
            @RequestParam(name = "type", defaultValue = "") String type,
            @RequestParam(name = "search", defaultValue = "") String search){
        return productService.getProducts(PageRequest.of(Math.max(page, 0), size), type, search);
    }
    
    @PostMapping
    public void addProduct(@RequestBody List<ProductRequest> request){
        productService.addProduct(request);
    }
    
}
