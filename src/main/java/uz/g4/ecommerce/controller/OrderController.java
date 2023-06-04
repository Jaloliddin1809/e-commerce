package uz.g4.ecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uz.g4.ecommerce.domain.dto.request.OrderRequest;
import uz.g4.ecommerce.domain.dto.request.ProductRequest;
import uz.g4.ecommerce.domain.dto.request.UserRequest;
import uz.g4.ecommerce.domain.dto.response.BaseResponse;
import uz.g4.ecommerce.domain.dto.response.CategoryResponse;
import uz.g4.ecommerce.domain.dto.response.OrderResponse;
import uz.g4.ecommerce.domain.dto.response.UserResponse;
import uz.g4.ecommerce.domain.entity.order.OrderEntity;
import uz.g4.ecommerce.domain.entity.product.ProductEntity;
import uz.g4.ecommerce.domain.entity.user.UserEntity;
import uz.g4.ecommerce.service.order.OrderService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard/orders")
public class OrderController {
    private final OrderService orderService;
    @PostMapping("/add")
    public String updateEmployee(@ModelAttribute OrderRequest userRequest, Model model) {
        BaseResponse<OrderResponse> response = orderService.create(userRequest);
        model.addAttribute("response", response.getMessage());
        return "redirect:/dashboard/orders";
    }
    @GetMapping
    public String getList(Model model) {
        model.addAttribute("response", orderService.findAll());
        return "order";
    }

    @PostMapping("/delete")
    public String deleteWorker(@RequestParam("id") UUID id) {
        orderService.delete(id);
        return "redirect:/dashboard/orders";
    }
    @PostMapping("/update")
    public String updateUser(@RequestParam("id") UUID id, @ModelAttribute OrderRequest orderRequest) {
        orderService.update(orderRequest, id);
        return "redirect:/dashboard/orders";


        //    @PostMapping("/add")
//    public String updateEmployee(@ModelAttribute ProductRequest productRequest) {
//        orderService.create(productRequest);
//
//        return "redirect:/dashboard/products";
//    }

//    @PostMapping("/delete")
//    public String deleteWorker(@RequestParam("id") UUID id) {
//        productService.delete(id);
//        return "redirect:/dashboard/products";
//    }
//    @PostMapping("/update")
//    public String updateUser(@RequestParam("id") UUID id, @ModelAttribute ProductRequest productRequest) {
//        productService.update(productRequest, id);
//        return "redirect:/dashboard/products";
//    }
    }
}
