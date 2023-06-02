package uz.g4.ecommerce.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.g4.ecommerce.domain.entity.order.OrderStatus;
import uz.g4.ecommerce.domain.entity.product.ProductEntity;
import uz.g4.ecommerce.domain.entity.user.UserEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private UserEntity user;
    private OrderStatus orderState;
    private Integer amount;
    private ProductEntity product;
    private UUID id;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
