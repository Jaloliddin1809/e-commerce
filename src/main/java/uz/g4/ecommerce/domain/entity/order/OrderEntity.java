package uz.g4.ecommerce.domain.entity.order;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;
import uz.g4.ecommerce.domain.entity.BaseEntity;
import uz.g4.ecommerce.domain.entity.product.ProductEntity;
import uz.g4.ecommerce.domain.entity.user.UserEntity;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "orders")
@Builder
public class OrderEntity extends BaseEntity {
    @OneToOne
    private UserEntity user;
    private OrderState orderState;
    @OneToMany(mappedBy = "order")
    private List<ProductEntity> products;
}
