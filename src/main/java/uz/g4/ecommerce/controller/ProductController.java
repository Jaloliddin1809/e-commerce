package uz.g4.ecommerce.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uz.g4.ecommerce.domain.dto.request.ProductRequest;
import uz.g4.ecommerce.domain.dto.request.UserLoginRequest;
import uz.g4.ecommerce.domain.dto.request.UserRequest;
import uz.g4.ecommerce.domain.dto.response.BaseResponse;
import uz.g4.ecommerce.domain.dto.response.CategoryResponse;
import uz.g4.ecommerce.domain.dto.response.ProductResponse;
import uz.g4.ecommerce.domain.entity.category.CategoryEntity;
import uz.g4.ecommerce.domain.entity.product.ProductEntity;
import uz.g4.ecommerce.domain.entity.user.UserEntity;
import uz.g4.ecommerce.service.category.CategoryService;
import uz.g4.ecommerce.service.product.ProductService;
import uz.g4.ecommerce.service.user.UserService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard/products")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping("/get-one")
    @ResponseBody
    public Optional<ProductEntity> showEditModal(@RequestParam("id") UUID productId) {
        return productService.getOneProduct(productId);
    }

    @PostMapping("/add")
    public String updateEmployee(@ModelAttribute ProductRequest productRequest) {
        productService.create(productRequest);

        return "redirect:/dashboard/products";
    }
    @GetMapping
    public String getList(Model model) {
        model.addAttribute("response", productService.findAll());
        return "product";
    }

    @PostMapping("/delete")
    public String deleteWorker(@RequestParam("id") UUID id) {
        productService.delete(id);
        return "redirect:/dashboard/products";
    }
    @PostMapping("/update")
    public String updateUser(@RequestParam("id") UUID id, @ModelAttribute ProductRequest productRequest) {
        productService.update(productRequest, id);
        return "redirect:/dashboard/products";
    }


    @GetMapping("/get-categories")
    public String getAllCategories(Model model) {
        BaseResponse<List<CategoryResponse>> categories = categoryService.findAllChildCategories();
        model.addAttribute("categories", categories.getData());
    return "product";
    }
}