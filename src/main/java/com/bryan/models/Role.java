package com.bryan.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"authority"}))
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;
    
    @Column(nullable = false)
    private String authority;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id) && Objects.equals(authority, role.authority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, authority);
    }
    
}
