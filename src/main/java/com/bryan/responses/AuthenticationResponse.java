package com.bryan.responses;

import com.bryan.models.Role;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.UUID;

public record AuthenticationResponse(UUID id, String firstname, String lastname, String email, Collection<? extends GrantedAuthority> authorities) {
}
