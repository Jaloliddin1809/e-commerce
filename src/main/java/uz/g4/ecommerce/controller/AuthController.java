package uz.g4.ecommerce.controller;

import lombok.RequiredArgsConstructor;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.g4.ecommerce.domain.dto.request.UserLoginRequest;




import uz.g4.ecommerce.service.user.UserService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class AuthController {
    private final UserService userService;
    @GetMapping
    public String login() {
        return "index";
    }
    @PostMapping
    public String login(@RequestBody UserLoginDto userLoginDto) {
        userService.login(userLoginDto);
        return "/dashboard";
    }
}
