package uz.g4.ecommerce.domain.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import uz.g4.ecommerce.domain.entity.user.Permission;
import uz.g4.ecommerce.domain.entity.user.Role;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {
    @Pattern(regexp = "^[A-Za-z]+$", message = "name is not valid")
    private String name;
    @Pattern(regexp = "^[A-Za-z]+$", message = "username is not valid")
    private String username;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$)", message = "password is not valid")
    @Length(min = 8, max = 20, message = "must have between 8 and 20 characters")
    private String password;
    private Set<Role> roles;
    private Set<Permission> permissions;

    public UserRequest(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }
}
