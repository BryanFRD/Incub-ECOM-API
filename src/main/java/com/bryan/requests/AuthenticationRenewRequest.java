package com.bryan.requests;

import lombok.NonNull;

public record AuthenticationRenewRequest(@NonNull String token) {
    
}
