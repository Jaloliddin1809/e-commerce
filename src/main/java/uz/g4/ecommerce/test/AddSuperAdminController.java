package uz.g4.ecommerce.test;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.g4.ecommerce.domain.dto.request.UserRequest;
import uz.g4.ecommerce.domain.dto.response.UserResponse;
import uz.g4.ecommerce.domain.entity.user.Permission;
import uz.g4.ecommerce.domain.entity.user.Role;
import uz.g4.ecommerce.service.category.CategoryService;
import uz.g4.ecommerce.service.product.ProductService;
import uz.g4.ecommerce.service.user.UserService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/auth/v1/api")
@RequiredArgsConstructor
public class AddSuperAdminController {
    private final UserService userService;
    private final CategoryService categoryService;
    private final ProductService productService;

    @GetMapping("/super-admin")
    public void addSuperAdmin() {
        Set<Permission> perm = Set.of(Permission.ADD, Permission.EDIT, Permission.GET, Permission.DELETE);
        for(int i=0;i<300;i++) {
            userService.create(
                    new UserRequest(i+"name"+i, "username"+i, "1", Set.of(Role.ADMIN, Role.SUPER_ADMIN), perm));
        }
    }

    @GetMapping("/search")
    public List<UserResponse> search(){
        return userService.search("name");
    }
//
//    @GetMapping("/add_category")
//    public BaseResponse<CategoryResponse> addCategory(@RequestBody CategoryRequest categoryRequest) {
//
//        return categoryService.create(categoryRequest);
//    }
}
