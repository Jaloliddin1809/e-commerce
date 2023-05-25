package uz.g4.ecommerce.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.g4.ecommerce.domain.entity.order.OrderStatus;
import uz.g4.ecommerce.domain.entity.product.ProductEntity;
import uz.g4.ecommerce.domain.entity.user.UserEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
   private UserEntity userId;
   private OrderStatus orderStatus;
   private ProductEntity productId;
}
