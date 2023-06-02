package uz.g4.ecommerce.domain.entity.order;

import jakarta.persistence.*;
import lombok.*;
import uz.g4.ecommerce.domain.entity.BaseEntity;
import uz.g4.ecommerce.domain.entity.product.ProductEntity;
import uz.g4.ecommerce.domain.entity.user.UserEntity;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "orders")
@Builder
public class OrderEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    private Integer amount;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderState;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;
}
