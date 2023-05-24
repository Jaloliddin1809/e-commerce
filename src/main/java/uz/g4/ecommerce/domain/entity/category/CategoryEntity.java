package uz.g4.ecommerce.domain.entity.category;

import jakarta.persistence.*;
import lombok.*;
import uz.g4.ecommerce.domain.entity.BaseEntity;
import uz.g4.ecommerce.domain.entity.product.ProductEntity;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "category")
@Builder
public class CategoryEntity extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private CategoryType type;
    @OneToMany(mappedBy = "parent")
    private Set<CategoryEntity> children;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private CategoryEntity parent;
    @OneToMany(mappedBy = "category")
    private List<ProductEntity> products;
}
