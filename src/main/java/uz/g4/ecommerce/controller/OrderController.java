package uz.g4.ecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uz.g4.ecommerce.domain.dto.request.ProductRequest;
import uz.g4.ecommerce.domain.dto.response.BaseResponse;
import uz.g4.ecommerce.domain.dto.response.CategoryResponse;
import uz.g4.ecommerce.domain.entity.order.OrderEntity;
import uz.g4.ecommerce.domain.entity.product.ProductEntity;
import uz.g4.ecommerce.service.order.OrderService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard/orders")
public class OrderController {
    private final OrderService orderService;
    @GetMapping
    public String getList(Model model) {
        model.addAttribute("orders", orderService.findAllStateNotInCart());
        return "order";
    }

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
