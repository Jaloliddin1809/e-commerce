package uz.g4.ecommerce.domain.dto.response;

import lombok.*;
import uz.g4.ecommerce.domain.entity.user.UserEntity;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
public class HistoryResponse {
    private String productName;
    private Double price;
    private Integer amount;
    private Double totalPrice;
    private UserEntity user;
    private UUID id;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
