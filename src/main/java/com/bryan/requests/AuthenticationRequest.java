package com.bryan.requests;

import org.springframework.lang.NonNull;

public record AuthenticationRequest(@NonNull String email, @NonNull String password) {
    
}
