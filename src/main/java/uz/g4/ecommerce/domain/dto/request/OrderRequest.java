package uz.g4.ecommerce.domain.dto.request;

import lombok.*;
import uz.g4.ecommerce.domain.entity.order.OrderStatus;
import uz.g4.ecommerce.domain.entity.product.ProductEntity;
import uz.g4.ecommerce.domain.entity.user.UserEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {
   private UserEntity user;
   private Integer amount;
   private OrderStatus orderState;
   private ProductEntity product;
}
