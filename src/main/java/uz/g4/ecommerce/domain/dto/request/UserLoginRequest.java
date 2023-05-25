package uz.g4.ecommerce.domain.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserLoginRequest {
    @Pattern(regexp = "^[A-Za-z]+$", message = "name is not valid")
    private String username;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$)", message = "password is not valid")
    private String password;
}
