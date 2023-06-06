package uz.g4.ecommerce.domain.dto.response;

import lombok.*;
import uz.g4.ecommerce.domain.entity.user.Permission;
import uz.g4.ecommerce.domain.entity.user.Role;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserResponse {
    private UUID id;
    private String name;
    private String username;
    private String password;
    private Double balance;
    private Set<Role> roles;
    private Set<Permission> permissions;
    private Boolean enabled = true;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String lastModifiedBy;
    private String createdBy;

    public UserResponse(UUID id, String name, String username, String password, Double balance, Set<Role> roles, Set<Permission> permissions, Boolean enabled, LocalDateTime createdDate, LocalDateTime updatedDate, String lastModifiedBy) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.roles = roles;
        this.permissions = permissions;
        this.enabled = enabled;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.lastModifiedBy = lastModifiedBy;
    }
}
