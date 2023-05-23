package uz.g4.ecommerce.domain.dto.request;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import uz.g4.ecommerce.domain.entity.user.Permission;
import uz.g4.ecommerce.domain.entity.user.Role;

import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {
    private String name;
    @Column(unique = true, nullable = false)
    private String username;  // Admin username or users phone number
    @Column(nullable = false)
    private String password;
    private Set<Role> roles;
    private Set<Permission> permissions;

}
