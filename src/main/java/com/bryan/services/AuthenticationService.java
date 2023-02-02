package com.bryan.services;

import com.bryan.configs.CookieUtils;
import com.bryan.configs.JwtUtils;
import com.bryan.models.Account;
import com.bryan.models.Role;
import com.bryan.repositories.AccountRepository;
import com.bryan.repositories.RoleRepository;
import com.bryan.requests.AuthenticationRequest;
import com.bryan.requests.RegisterRequest;
import com.bryan.responses.AuthenticationResponse;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    
    private final AccountRepository accountRepository;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final CookieUtils cookieUtils;
    private final RoleRepository roleRepository;
    
    public void register(RegisterRequest request){
        final Account account = new Account();
        account.setFirstname(request.firstname());
        account.setLastname(request.lastname());
        account.setEmail(request.email());
        
        Role role = roleRepository.findByAuthority("USER").orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        account.setAuthorities(List.of(role));
        
        String encodedPassword = passwordEncoder.encode(request.password());
        account.setPassword(encodedPassword);
        
        try {
            accountRepository.save(account);
        } catch (Exception exception){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }
    
    public AuthenticationResponse authenticate(AuthenticationRequest request){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        } catch (Exception ignored){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        
        final Account account = accountRepository.findByEmail(request.email()).orElseThrow(() -> {
            logout();
            return new ResponseStatusException(HttpStatus.NOT_FOUND);
        });
        final String token = jwtUtils.generateToken(account);
        
        cookieUtils.addCookie(cookieUtils.createAuthCookie(token));
        
        return new AuthenticationResponse(account.getId(), account.getFirstname(), account.getLastname(), account.getEmail(), account.getAuthorities());
    }
    
    public AuthenticationResponse renew(){
        final Cookie cookieToken = cookieUtils.getCookie("authToken").orElseThrow(() -> {
            logout();
            return new ResponseStatusException(HttpStatus.NOT_FOUND);
        });
        final String token = cookieToken.getValue();
        
        final UUID userId = UUID.fromString(jwtUtils.extractUsername(token));
        final Account account = accountRepository.findById(userId).orElseThrow(() -> {
            logout();
            return new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        });
        
        if(!jwtUtils.isTokenValid(token, account)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        
        final String newToken = jwtUtils.generateToken(account);

        cookieUtils.addCookie(cookieUtils.createAuthCookie(newToken));
        
        return new AuthenticationResponse(account.getId(), account.getFirstname(), account.getLastname(), account.getEmail(), account.getAuthorities());
    }
    
    public void logout(){
        cookieUtils.removeCookie("authToken");
    }
    
}
