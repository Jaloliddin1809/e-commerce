package uz.g4.ecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uz.g4.ecommerce.domain.dto.request.UserLoginRequest;
import uz.g4.ecommerce.service.user.UserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    @GetMapping("/login")
    public ModelAndView login(ModelAndView view) {
        view.setViewName("index");
        return view;
    }

    @PostMapping("/login")
    public ModelAndView login(@RequestBody UserLoginRequest userLoginRequest,
                              BindingResult result, ModelAndView view) {
        if (result.hasErrors()) {
            List<ObjectError> allErrors = result.getAllErrors();
            StringBuilder sb = new StringBuilder();
            allErrors.forEach((error) -> sb.append(error.getDefaultMessage()).append("\n"));
            view.addObject("message", sb.toString());
            view.setViewName("index");
            return view;
        }
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getName());
        view.addObject("user", auth.getName());
        userService.login(userLoginRequest);
        view.setViewName("dashboard");
        return view;
    }
}
