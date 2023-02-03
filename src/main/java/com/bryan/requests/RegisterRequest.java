package com.bryan.requests;

import lombok.NonNull;

public record RegisterRequest(@NonNull String firstname, @NonNull String lastname, @NonNull String email, @NonNull String password){
    
}
