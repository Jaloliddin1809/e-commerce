package uz.g4.ecommerce.test;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.g4.ecommerce.domain.dto.request.UserRequest;
import uz.g4.ecommerce.domain.dto.response.BaseResponse;
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
    public BaseResponse<UserResponse> addSuperAdmin() {
        Set<Permission> perm = Set.of(Permission.ADD, Permission.EDIT, Permission.GET, Permission.DELETE);
//        int count=0;
//        while (count<25){
//            UserRequest user = new UserRequest();
//            if(count<5){
//                user.setName("Sardor");
//                user.setUsername("Alimov_Sardor"+ count);
//                user.setPassword("123");
//                user.setRoles(Set.of(Role.ADMIN));
//                user.setPermissions(Set.of(Permission.ADD));
//            }if(5<=count&&count<10){
//                user.setName("Malika");
//                user.setUsername("Alimova_Malika"+ count);
//                user.setPassword("123");
//                user.setRoles(Set.of(Role.ACCOUNTANT));
//                user.setPermissions(Set.of(Permission.EDIT));
//            }if(10<=count&&count<15){
//                user.setName("Lola");
//                user.setUsername("Lola_Hamidova"+ count);
//                user.setPassword("123");
//                user.setRoles(Set.of(Role.ADMIN));
//                user.setPermissions(Set.of(Permission.EDIT));
//            }if(15<=count&&count<20){
//                user.setName("Jorabek");
//                user.setUsername("Jorabek_Komilov"+ count);
//                user.setPassword("123");
//                user.setRoles(Set.of(Role.ADMIN));
//                user.setPermissions(Set.of(Permission.DELETE));
//            }if(20<=count&&count<25){
//                user.setName("Feruza");
//                user.setUsername("Feruza_Fazilova"+ count);
//                user.setPassword("123");
//                user.setRoles(Set.of(Role.ACCOUNTANT));
//                user.setPermissions(Set.of(Permission.EDIT));
//            }
//            userService.create(user);
//            count++;
//        }
        return userService.create(
                new UserRequest("Khamroz", "admin", "1", Set.of(Role.ADMIN, Role.SUPER_ADMIN), perm));
    }
}
