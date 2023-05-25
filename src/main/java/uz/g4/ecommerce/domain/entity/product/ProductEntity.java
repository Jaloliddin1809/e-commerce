package uz.g4.ecommerce.domain.entity.product;

import jakarta.persistence.*;
import lombok.*;
import uz.g4.ecommerce.domain.entity.BaseEntity;
import uz.g4.ecommerce.domain.entity.category.CategoryEntity;
import uz.g4.ecommerce.domain.entity.order.OrderEntity;

import java.util.List;

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
    @OneToMany(mappedBy = "product")
    private List<OrderEntity> order;
}
