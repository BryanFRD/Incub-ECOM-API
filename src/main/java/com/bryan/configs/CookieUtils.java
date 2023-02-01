package com.bryan.configs;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class CookieUtils {
    
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    
    public Optional<Cookie> getCookie(String key){
        if(request.getCookies() == null){
            return Optional.empty();
        }
        
        return Arrays.stream(request.getCookies())
                .filter(c -> key.equals(c.getName()))
                .findAny();
    }
    
    public void addCookie(Cookie cookie){
        response.addCookie(cookie);
    }
    
    public void removeCookie(String key){
        Cookie cookie = new Cookie(key, "");
        cookie.setMaxAge(0);
        
        response.addCookie(cookie);
    }
    
    public Cookie createAuthCookie(String token){
        Cookie cookie = new Cookie("authToken", token);
        cookie.setMaxAge((int) TimeUnit.HOURS.toSeconds(24));
        cookie.setSecure(false);
        cookie.setHttpOnly(false);
        
        return cookie;
    }
    
}
