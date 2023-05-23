package uz.g4.ecommerce.domain.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserLoginRequest {
    private String username;  //email,username or phone number
    private String password;
}
