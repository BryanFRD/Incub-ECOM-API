package com.bryan.models;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
public class Product {
    
    @Id
    @SequenceGenerator(name = "product_id_sequence", sequenceName = "product_id_sequence")
    @GeneratedValue(strategy = GenerationType.UUID, generator = "product_id_sequence")
    private final UUID id;
    private final String name;
    private final Float price;
    private final String src;
    private final String alt;
    
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
