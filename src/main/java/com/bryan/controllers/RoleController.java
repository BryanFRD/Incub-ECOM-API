package com.bryan.controllers;

import com.bryan.models.Role;
import com.bryan.requests.RoleRequest;
import com.bryan.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("v1/role")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class RoleController {
    
    private final RoleService roleService;
    
    @GetMapping
    public List<Role> getRoles(){
        return roleService.getRoles();
    }
    
    @PostMapping
    public void addRole(@RequestBody List<RoleRequest> roleRequest){
        List<Role> roles = new ArrayList<>();
        
        roleRequest.forEach(r -> {
            Role role = new Role();
            role.setAuthority(r.authority());
            
            roles.add(role);
        });
        
        roleService.addRole(roles);
    }
    
}
