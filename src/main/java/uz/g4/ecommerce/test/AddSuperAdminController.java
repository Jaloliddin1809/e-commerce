package uz.g4.ecommerce.test;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.g4.ecommerce.domain.dto.request.UserRequest;
import uz.g4.ecommerce.domain.dto.response.BaseResponse;
import uz.g4.ecommerce.domain.dto.response.UserResponse;
import uz.g4.ecommerce.domain.entity.user.Permission;
import uz.g4.ecommerce.domain.entity.user.Role;
import uz.g4.ecommerce.service.category.CategoryService;
import uz.g4.ecommerce.service.product.ProductService;
import uz.g4.ecommerce.service.user.UserService;

import java.util.Set;

@RestController
@RequestMapping("/auth/v1/api")
@RequiredArgsConstructor
public class AddSuperAdminController {
    private final UserService userService;
    private final CategoryService categoryService;
    private final ProductService productService;

    @GetMapping("/super-admin")
    public BaseResponse<UserResponse> addSuperAdmin() {
        Set<Permission> perm = Set.of(Permission.ADD, Permission.EDIT, Permission.GET, Permission.DELETE);
        return userService.create(
                new UserRequest("Jaloliddin", "Accountant", "123", Set.of(Role.ACCOUNTANT), perm));
    }
//
//    @GetMapping("/add_category")
//    public BaseResponse<CategoryResponse> addCategory(@RequestBody CategoryRequest categoryRequest) {
//
//        return categoryService.create(categoryRequest);
//    }
}
