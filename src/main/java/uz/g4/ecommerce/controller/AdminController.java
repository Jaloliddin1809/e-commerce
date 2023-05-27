package uz.g4.ecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AdminController {
    @GetMapping("/dashboard")
    public String DashBoardPage() {
        return "dashboard";
    }

}
