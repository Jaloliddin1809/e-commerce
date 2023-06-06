package uz.g4.ecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.g4.ecommerce.domain.dto.request.UserRequest;
import uz.g4.ecommerce.domain.dto.response.BaseResponse;
import uz.g4.ecommerce.domain.dto.response.UserResponse;
import uz.g4.ecommerce.domain.entity.user.UserEntity;
import uz.g4.ecommerce.service.user.UserService;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard/employees")
public class EmployeeController {
    private final UserService userService;
    @PostMapping("/add")
    public String updateEmployee(@ModelAttribute UserRequest userRequest, Model model) {
        BaseResponse<UserResponse> response = userService.create(userRequest);
        model.addAttribute("response", response.getMessage());
        System.out.println(response.getMessage());
        return "redirect:/dashboard/employees";
    }
    @GetMapping
    public String getList(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("response", userService.findAll(authentication.getName()));
        return "employees";
    }

    @PostMapping("/delete")
    public String deleteWorker(@RequestParam("id") UUID id) {
        userService.delete(id);
        return "redirect:/dashboard/employees";
    }
    @GetMapping("/get-one")
    @ResponseBody
    public Optional<UserEntity> showEditModal(@RequestParam("id") UUID id) {
        return userService.getOneUser(id);
    }
    @PostMapping("/update")
    public String updateUser(@RequestParam("id") UUID id, @ModelAttribute UserRequest userRequest) {
        userService.update(userRequest, id);
        return "redirect:/dashboard/employees";


//     @GetMapping("/employees")
//     public String findByPage(Model model) {
//         model.addAttribute("users", userService.findByPage(Optional.of(0), Optional.of(500)));
//         return "employees";
//     }
//     @GetMapping("/employees/delete/{id}")
//     public String delete(
//             @PathVariable(value = "id")UUID id
//     ){
//         userService.delete(id);
//         return "employees";
//     }
    }
}
