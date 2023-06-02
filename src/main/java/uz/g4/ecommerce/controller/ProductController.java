package uz.g4.ecommerce.controller;

import lombok.RequiredArgsConstructor;
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
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

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

    @PostMapping("/update/{id}")
    public ModelAndView updateProduct(@PathVariable("id") UUID productId,
                                      @RequestBody ProductRequest productRequest,
                                      ModelAndView view, BindingResult result) {
        String errors = errors(result);

        if (errors != null) {
            view.addObject("message", errors);
            view.setViewName("update");
            return view;
        }
        BaseResponse<ProductResponse> update = productService.update(productRequest, productId);
        view.addObject("message", update.getMessage());
        view.setViewName("update");
        return view;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteProduct(@PathVariable("id") UUID productId, ModelAndView view) {
        if (productService.delete(productId)) {
            view.addObject("message", "successfully deleted");
            view.setViewName("delete");
            return view;
        }
        view.addObject("message", "product not found");
        view.setViewName("delete");
        return view;
    }
}
