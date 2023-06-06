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
import uz.g4.ecommerce.domain.dto.response.BaseResponse;
import uz.g4.ecommerce.domain.dto.response.ProductResponse;
import uz.g4.ecommerce.service.product.ProductService;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/addProduct")
    public String add(@RequestBody ProductRequest productRequest) {
        BaseResponse<ProductResponse> response = productService.create(productRequest);
        return response.getMessage();
    }

    @PostMapping("/add")
    public ModelAndView addProduct(
            @RequestBody ProductRequest productRequest, BindingResult result
            , ModelAndView model) {

        String errors = errors(result);

        if (errors != null) {
            model.addObject("message", errors);
            model.setViewName("product");
            return model;
        }

        BaseResponse<ProductResponse> response = productService.create(productRequest);
        model.addObject("message", response.getMessage());
        model.setViewName("product");
        return model;
    }

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
