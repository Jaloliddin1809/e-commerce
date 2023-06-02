package uz.g4.ecommerce.repository.order;

import jakarta.persistence.NamedNativeQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.g4.ecommerce.domain.dto.response.OrderResponse;
import uz.g4.ecommerce.domain.entity.order.OrderEntity;

import java.util.List;
import java.util.UUID;
@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
    @Query(value = "select u from orders u where u.orderState <> 'IN_CART'")
    List<OrderEntity> findAllByStateNotInCart();
}
