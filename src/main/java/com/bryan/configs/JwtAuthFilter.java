package com.bryan.configs;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    
    private final UserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;
    private final CookieUtils cookieUtils;
    
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        final Optional<Cookie> authCookie = cookieUtils.getCookie("authToken");
        final String userId;
        final String jwtToken;
        
        if(authCookie.isEmpty()){
            filterChain.doFilter(request, response);
            return;
        }
        
        jwtToken = authCookie.get().getValue();
        userId = jwtUtils.extractUsername(jwtToken);
        
        if(userId != null && SecurityContextHolder.getContext().getAuthentication() == null){
            final UserDetails userDetails;
            
            try {
                userDetails = userDetailsService.loadUserByUsername(userId);
            } catch (Exception ignored){
                cookieUtils.removeCookie("authToken");
                filterChain.doFilter(request, response);
                return;
            }
            
            if(jwtUtils.isTokenValid(jwtToken, userDetails)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        
        filterChain.doFilter(request, response);
    }
}
