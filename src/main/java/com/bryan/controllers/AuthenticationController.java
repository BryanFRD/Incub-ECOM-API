package com.bryan.controllers;

import com.bryan.requests.AuthenticationRequest;
import com.bryan.requests.RegisterRequest;
import com.bryan.responses.AuthenticationResponse;
import com.bryan.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AuthenticationController {
    
    private final AuthenticationService authenticationService;
    
    @PostMapping("register")
    public void register(@RequestBody RegisterRequest request){
        authenticationService.register(request);
    }
    
    @PostMapping("authenticate")
    public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest request){
        return authenticationService.authenticate(request);
    }
    
    @GetMapping("renew")
    public AuthenticationResponse renew(){
        return authenticationService.renew();
    }
    
    @GetMapping("logout")
    public void logout(){
        authenticationService.logout();
    }
    
}
