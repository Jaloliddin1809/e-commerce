package uz.g4.ecommerce.domain.entity.history;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import uz.g4.ecommerce.domain.entity.BaseEntity;
import uz.g4.ecommerce.domain.entity.user.UserEntity;

@Entity(name = "histories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoryEntity extends BaseEntity {
    private String productName;
    private Double price;
    private Integer amount;
    private Double totalPrice;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
