package uz.g4.ecommerce.domain.entity.cart;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
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
@Entity(name = "carts")
@Builder
public class CartEntity extends BaseEntity {
    private String name;
    @OneToOne
    @JoinColumn(name = "owner_id")
    private UserEntity owner;
    private Double totalPrice;
    @OneToMany(mappedBy = "cart")
    private List<ProductEntity> products;
}
