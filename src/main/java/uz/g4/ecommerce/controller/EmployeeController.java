package uz.g4.ecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uz.g4.ecommerce.domain.entity.user.UserEntity;
import uz.g4.ecommerce.service.user.UserService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class EmployeeController {
    private final UserService userService;

    @GetMapping("/employees")
    public String findByPage(Model model) {
        model.addAttribute("users", userService.findByPage(Optional.of(0), Optional.of(500)));
        return "employees";
    }
    @GetMapping("/employees/delete/{id}")
    public String delete(
            @PathVariable(value = "id")UUID id
    ){
        userService.delete(id);
        return "employees";
    }

}
