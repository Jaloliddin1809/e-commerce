package uz.g4.ecommerce.domain.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import uz.g4.ecommerce.domain.entity.user.UserEntity;

@Getter
@Setter
@Builder
public class HistoryRequest {
    private String productName;
    private Double price;
    private Integer amount;
    private Double totalPrice;
    private UserEntity user;
}
