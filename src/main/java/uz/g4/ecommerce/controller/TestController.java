package uz.g4.ecommerce.controller;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uz.g4.ecommerce.domain.dto.request.UserRequest;
import uz.g4.ecommerce.domain.entity.user.UserEntity;
import uz.g4.ecommerce.service.user.UserService;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/test/employees")
public class TestController {
    private final UserService userService;

    @GetMapping("/get-one/{id}")
    @ResponseBody
    public Optional<UserEntity> showEditModal(@PathVariable("id") UUID id) {
        return userService.getOneUser(id);
    }

//    @RequestMapping(value = "/update", method = {RequestMethod.POST, RequestMethod.GET})
    @PostMapping("/update")
    public String updateUser(@RequestParam("id") UUID id, @ModelAttribute UserRequest userRequest) {
        userService.update(userRequest, id);
        return "redirect:/dashboard/employees";
    }
}
