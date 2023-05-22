package uz.g4.ecommerce.domain.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserLoginDto {
    private String username;  //email,username or phone number
    private String password;
}
