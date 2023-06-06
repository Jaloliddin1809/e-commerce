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
    @Column(unique = true)
    private String type;
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CategoryEntity> children;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private CategoryEntity parent;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductEntity> products;
}
