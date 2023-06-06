package uz.g4.ecommerce.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.g4.ecommerce.domain.entity.category.CategoryEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private UUID id;
    private String name;
    private Double price;
    private Integer amount;
    private CategoryEntity category;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String lastModifiedBy;
    private String createdBy;
}
