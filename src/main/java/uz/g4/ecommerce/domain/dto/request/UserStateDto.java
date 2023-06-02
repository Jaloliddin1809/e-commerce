package uz.g4.ecommerce.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import uz.g4.ecommerce.domain.entity.user.UserState;

@Getter
@AllArgsConstructor
public class UserStateDto {
   private Long chatId;
   private UserState state;
}
