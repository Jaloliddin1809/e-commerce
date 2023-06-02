package uz.g4.ecommerce.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.g4.ecommerce.domain.dto.response.BaseResponse;
import uz.g4.ecommerce.domain.dto.response.ProductResponse;
import uz.g4.ecommerce.domain.entity.product.ProductEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
//    Optional<ProductEntity> findById(UUID id);
    List<ProductEntity> getProductEntitiesByCategory_Id(UUID categoryId);
    Boolean existsByName(String name);
}
