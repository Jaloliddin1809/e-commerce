package uz.g4.ecommerce.domain.dto.response;

import lombok.*;
import uz.g4.ecommerce.domain.entity.category.CategoryEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponse {
    private String type;
    private CategoryEntity parentId;
    private UUID id;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String lastModifiedBy;
    private String createdBy;
}
