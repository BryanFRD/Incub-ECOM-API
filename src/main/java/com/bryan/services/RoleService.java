package com.bryan.services;

import com.bryan.models.Role;
import com.bryan.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    
    private final RoleRepository roleRepository;
    
    public List<Role> getRoles(){
        return roleRepository.findAll();
    }
    
    public void addRole(List<Role> roles){
        roleRepository.saveAll(roles);
    }
    
}
