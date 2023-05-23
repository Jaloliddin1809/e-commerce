package uz.g4.ecommerce.test;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.g4.ecommerce.domain.dto.request.UserRequest;
import uz.g4.ecommerce.domain.dto.response.BaseResponse;
import uz.g4.ecommerce.domain.dto.response.UserResponse;
import uz.g4.ecommerce.domain.entity.user.Permission;
import uz.g4.ecommerce.domain.entity.user.Role;
import uz.g4.ecommerce.service.user.UserService;
import java.util.Set;

@RestController
@RequestMapping("auth/v1/api")
@RequiredArgsConstructor
public class AddSuperAdminController {
//   http://localhost:8080/auth/v1/api/super-admin
    private final UserService userService;

    @GetMapping("/super-admin")
    public BaseResponse<UserResponse> addSuperAdmin() {
        Set<Permission> perm = Set.of(Permission.ADD, Permission.EDIT, Permission.GET,Permission.DELETE);
        return userService.create(
                new UserRequest("Khamroz", "admin", "123", Set.of(Role.SUPER_ADMIN), perm));
    }
}
