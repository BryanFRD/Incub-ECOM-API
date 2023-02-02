package com.bryan.responses;

import com.bryan.models.Product;

import java.util.List;

public record ProductResponse(List<Product> products, long total, int totalPages) {
}
