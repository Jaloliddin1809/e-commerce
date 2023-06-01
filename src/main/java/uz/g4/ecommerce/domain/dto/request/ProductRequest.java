package uz.g4.ecommerce.domain.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.g4.ecommerce.domain.entity.category.CategoryEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    @NotEmpty
    @NotNull
    @Pattern(regexp = "^[A-Za-z]+$", message = "name is not valid")
    private String name;
    @NotNull(message = "{price.notnull}")
    @NotEmpty(message = "{price.not empty}")
    private Double price;   
    private Integer amount;
    private CategoryEntity category;

}
