package com.bryan.models;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    private String name;
    private Float price;
    private String src;
    private String alt;
    
    public Product(){}
    
    public Product(UUID id, String name, float price, String src, String alt){
        this.id = id;
        this.name = name;
        this.price = price;
        this.src = src;
        this.alt = alt;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSrc() {
        return src;
    }

    public String getAlt() {
        return alt;
    }

    public float getPrice() {
        return price;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Float.compare(product.price, price) == 0 && Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(src, product.src) && Objects.equals(alt, product.alt);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, name, src, alt, price);
    }
    
}
