package com.bryan.requests;

import com.bryan.models.ProductType;
import lombok.NonNull;

public record ProductRequest(@NonNull String name, float price, String src, String alt, @NonNull String productType) {
    
}
