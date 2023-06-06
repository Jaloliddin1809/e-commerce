package uz.g4.ecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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

@Controller
@RequiredArgsConstructor

@RequestMapping("/dashboard/products")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
     @PostMapping("/add")
     public String updateEmployee(@ModelAttribute ProductRequest productRequest) {
         productService.create(productRequest);
         return "redirect:/dashboard/products";
     }
    @GetMapping
    public String getList(Model model) {
        model.addAttribute("response", productService.findAll());
        model.addAttribute("categories",categoryService.findAllChildCategories().getData());
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

//    @PostMapping("/add")
//    public ModelAndView addProduct(
//            @RequestBody ProductRequest productRequest, BindingResult result
//            , ModelAndView model) {
//
//        String errors = errors(result);
//
//        if (errors != null) {
//            model.addObject("message", errors);
//            model.setViewName("product");
//            return model;
//        }

//        BaseResponse<ProductResponse> response = productService.create(productRequest);
//        model.addObject("message", response.getMessage());
//        model.setViewName("product");
//        return model;
//    }

    public String errors(BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> allErrors = result.getAllErrors();

            StringBuilder sb = new StringBuilder();
            for (ObjectError allError : allErrors) {
                sb.append(allError.getDefaultMessage()).append("\n");
            }
            return sb.toString();
        }
        return null;
    }
    @GetMapping("/all")
    public ModelAndView findByPage(
            ModelAndView view,
            @RequestParam Integer page
    ) {
        view.addObject("products", productService.findByPage(Optional.of(page), Optional.of(10)));
        return view;
    }
}
=======
//     @PostMapping("/add")
//     public ModelAndView addProduct(
//             @Valid @ModelAttribute ProductRequest productRequest,
//             BindingResult bindingResult){
//         ModelAndView view = new ModelAndView();
//         if (bindingResult.hasErrors()){
//             view.addObject("message", bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage));
//         }else {
//             BaseResponse response = productService.create(productRequest);
//             view.addObject("message", response.getMessage());
//         }
//         return view;
//     }

//     @PostMapping("/update/{id}")
//     public ModelAndView updateProduct(@PathVariable("id") UUID productId,
//                                       @RequestBody ProductRequest productRequest,
//                                       ModelAndView view, BindingResult result) {
//         String errors = errors(result);

//         if (errors != null) {
//             view.addObject("message", errors);
//             view.setViewName("update");
//             return view;
//         }
//         BaseResponse<ProductResponse> update = productService.update(productRequest, productId);
//         view.addObject("message", update.getMessage());
//         view.setViewName("update");
//         return view;
//     }

//     @GetMapping("/delete/{id}")
//     public ModelAndView deleteProduct(@PathVariable("id") UUID productId, ModelAndView view) {
//         if (productService.delete(productId)) {
//             view.addObject("message", "successfully deleted");
//             view.setViewName("delete");
//             return view;
//         }
//         view.addObject("message", "product not found");
//         view.setViewName("delete");
//         return view;
//     }

//     @GetMapping("/products")
//     public String findByPage(Model model) {
//         model.addAttribute("products", productService.findByPage(Optional.of(0), Optional.of(500)));
//         return "product";
//     }
}
