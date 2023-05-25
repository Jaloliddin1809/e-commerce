package uz.g4.ecommerce.domain.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    @Pattern(regexp = "^[A-Za-z]+$", message = "name is not valid")
    private String name;
    private Double price;
    private Integer amount;
}
