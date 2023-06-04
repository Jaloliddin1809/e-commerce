package uz.g4.ecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uz.g4.ecommerce.domain.dto.request.CategoryRequest;
import uz.g4.ecommerce.domain.dto.response.BaseResponse;
import uz.g4.ecommerce.domain.dto.response.CategoryResponse;
import uz.g4.ecommerce.domain.dto.response.UserResponse;
import uz.g4.ecommerce.service.category.CategoryService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dashboard/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public String login(Model model) {
        BaseResponse<List<CategoryResponse>> all = categoryService.findAll();
        model.addAttribute("categories",all.getData());
        return "categories";
    }
    @PostMapping("/add")
    public String addCategory(@ModelAttribute CategoryRequest categoryRequest, Model model) {
        BaseResponse<CategoryResponse> response = categoryService.create(categoryRequest);
        model.addAttribute("categories", response.getMessage());
        return "redirect:/dashboard/categories";
    }
    @PostMapping("/delete")
    public String deleteWorker(@RequestParam("id") UUID id) {
        categoryService.delete(id);
        return "redirect:/dashboard/categories";
    }
    @PostMapping("/update")
    public String updateCategory(@RequestParam("id") UUID id, @ModelAttribute CategoryRequest categoryRequest) {
        categoryService.update(categoryRequest, id);
        return "redirect:/dashboard/categories";
    }
}
