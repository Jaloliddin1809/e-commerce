package uz.g4.ecommerce.domain.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {
    @Pattern(regexp = "^[A-Za-z]+$", message = "category type is not valid")
    private String type;
    private UUID parentId;
}
