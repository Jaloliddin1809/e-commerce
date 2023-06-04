package uz.g4.ecommerce.test;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.g4.ecommerce.domain.dto.request.UserRequest;
import uz.g4.ecommerce.domain.dto.response.UserResponse;
import uz.g4.ecommerce.domain.entity.user.Permission;
import uz.g4.ecommerce.domain.entity.user.Role;
import uz.g4.ecommerce.service.user.UserService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/auth/v1/api")
@RequiredArgsConstructor
public class AddSuperAdminController {
    private final UserService userService;

    @GetMapping("/super-admin")
    public void addSuperAdmin() {
        Set<Permission> perm = Set.of(Permission.ADD, Permission.EDIT, Permission.GET, Permission.DELETE);
        return userService.create(
                 new UserRequest("Name", "ADMIN", "123", Set.of(Role.SUPER_ADMIN),
                         perm, null, null, null));
        for(int i=0;i<300;i++) {
            userService.create(
                    new UserRequest(i+"name"+i, "username"+i, "1", Set.of(Role.ADMIN, Role.SUPER_ADMIN), perm));
        }
    }
}
