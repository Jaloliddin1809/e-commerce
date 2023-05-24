package uz.g4.ecommerce.domain.entity.product;

import jakarta.persistence.*;
import lombok.*;
import uz.g4.ecommerce.domain.entity.BaseEntity;
import uz.g4.ecommerce.domain.entity.cart.CartEntity;
import uz.g4.ecommerce.domain.entity.category.CategoryEntity;
import uz.g4.ecommerce.domain.entity.order.OrderEntity;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "products")
@Builder
public class ProductEntity extends BaseEntity {
    private String name;
    private Double price;
    private Integer amount;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private CartEntity cart;
}
