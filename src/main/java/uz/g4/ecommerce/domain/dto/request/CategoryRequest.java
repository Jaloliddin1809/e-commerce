package uz.g4.ecommerce.domain.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryRequest {
    @Pattern(regexp = "^[A-Za-z]+$", message = "category type is not valid")
    private String type;
    private UUID parentId;
}
