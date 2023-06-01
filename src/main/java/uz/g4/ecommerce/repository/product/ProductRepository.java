package uz.g4.ecommerce.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.g4.ecommerce.domain.entity.product.ProductEntity;

import java.util.List;
import java.util.UUID;
@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
    List<ProductEntity> getProductEntitiesByCategory_Id(UUID categoryId);
}
