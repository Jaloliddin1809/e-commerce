package uz.g4.ecommerce.repository.order;

import jakarta.persistence.NamedNativeQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.g4.ecommerce.domain.dto.response.OrderResponse;
import uz.g4.ecommerce.domain.entity.order.OrderEntity;

import java.util.List;
import java.util.UUID;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.g4.ecommerce.domain.entity.order.OrderEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.g4.ecommerce.domain.entity.order.OrderEntity;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
    @Query(value = "select u from orders u where u.orderState <> 'IN_CART'")
    List<OrderEntity> findAllByStateNotInCart();

    @Query("select o from orders o where o.user.chatId = :chatId")
    List<OrderEntity> findByChatId(@Param("chatId") Long chatId);

    Optional<OrderEntity> findOrderEntityByUser_IdAndProduct_Id (UUID userId, UUID productId);

    @Modifying
    @Transactional
    @Query("update orders o set o.amount = :amount where o.user.id = :userId and o.product.id = :productId")
    void updateAmount(@Param("amount") Integer amount, UUID userId, UUID productId);

    @Modifying
    @Transactional
    @Query("update orders o set o.amount = o.amount + 1 where o.id = :id")
    void plusOrderAmount(@Param("id") UUID uuid);

    @Modifying
    @Transactional
    @Query("update orders o set o.amount = o.amount - 1 where o.id = :id")
    void minusOrderAmount(@Param("id") UUID uuid);
}
