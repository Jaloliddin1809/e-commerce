package uz.g4.ecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uz.g4.ecommerce.domain.dto.request.CategoryRequest;
import uz.g4.ecommerce.domain.dto.response.BaseResponse;
import uz.g4.ecommerce.domain.dto.response.CategoryResponse;
import uz.g4.ecommerce.domain.dto.response.UserResponse;
import uz.g4.ecommerce.service.category.CategoryService;
import uz.g4.ecommerce.service.user.UserService;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final UserService userService;

    @GetMapping
    public String getList(Model model, String keyword) {
        model.addAttribute("userPermission", userService.getAllPermissionsByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        if (Objects.nonNull(keyword)) {
            model.addAttribute("categories", categoryService.findByKeyword(keyword).getData());
        } else {
            List<CategoryResponse> all = categoryService.getParentCategories();
            model.addAttribute("categories",all);
        }
        return "categories";
    }
    @PostMapping("/add")
    public String addCategory(@ModelAttribute CategoryRequest categoryRequest, Model model) {
        BaseResponse<CategoryResponse> response = categoryService.create(categoryRequest);
        model.addAttribute("categories", response.getMessage());
        return "redirect:/dashboard/categories";
    }
    @PostMapping("/delete")
    public String deleteCategory(@RequestParam("id") UUID id) {
        categoryService.delete(id);
        return "redirect:/dashboard/categories";
    }
    @PostMapping("/update")
    public String updateCategory(@RequestParam("id") UUID id, @ModelAttribute CategoryRequest categoryRequest) {
        categoryService.update(categoryRequest, id);
        return "redirect:/dashboard/categories";
    }

    @GetMapping("/children/{categoryId}")
    public String categoryChild(@PathVariable("categoryId") UUID categoryId, Model model, String keyword) {
        model.addAttribute("userPermission", userService.getAllPermissionsByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        if (Objects.nonNull(keyword)) {
            model.addAttribute("categories", categoryService.findByKeywordForChild(keyword, categoryId).getData());
            model.addAttribute("parentId",categoryId);
        } else {
            List<CategoryResponse> all = categoryService.getChildCategories(categoryId);
            model.addAttribute("categories",all);
            model.addAttribute("parentId",categoryId);
        }

        return "category-child";
    }
    @PostMapping("/children/add")
    public String addChildCategory(@ModelAttribute CategoryRequest categoryRequest, Model model) {
        BaseResponse<CategoryResponse> response = categoryService.create(categoryRequest);
        model.addAttribute("categories", response.getMessage());
        return "redirect:/dashboard/categories/children/" + categoryRequest.getParentId();
    }
    @PostMapping("/children/update")
    public String updateChildCategory(@RequestParam("id") UUID id, @RequestParam("parent") UUID parentId, @ModelAttribute CategoryRequest categoryRequest) {
        categoryService.update(categoryRequest, id);
        return "redirect:/dashboard/categories/children/" + parentId;
    }
    @PostMapping("/children/delete")
    public String deleteChildCategory(@RequestParam("id") UUID id, @RequestParam("parent") UUID parentId) {
        categoryService.delete(id);
        return "redirect:/dashboard/categories/children/" + parentId;
    }
}
