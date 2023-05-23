package uz.g4.ecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import uz.g4.ecommerce.service.user.UserService;

@Controller
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    @GetMapping("/dashboard")
    public String DashBoardPage() {
        return "dashboard";
    }

}
