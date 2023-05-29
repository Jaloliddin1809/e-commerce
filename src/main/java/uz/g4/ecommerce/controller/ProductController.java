package uz.g4.ecommerce.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uz.g4.ecommerce.domain.dto.request.ProductRequest;
import uz.g4.ecommerce.domain.dto.request.UserLoginRequest;
import uz.g4.ecommerce.domain.dto.response.BaseResponse;
import uz.g4.ecommerce.domain.dto.response.ProductResponse;
import uz.g4.ecommerce.service.product.ProductService;
import uz.g4.ecommerce.service.user.UserService;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class ProductController {
    private final ProductService productService;
    @GetMapping("/products")
    public ModelAndView login(ModelAndView view) {
        view.setViewName("product");
        return view;
    }

//    @PostMapping("/add")
//    public ModelAndView addProduct(
//            @Valid @ModelAttribute ProductRequest productRequest,
//            BindingResult bindingResult){
//        ModelAndView view = new ModelAndView();
//        if (bindingResult.hasErrors()){
//            view.addObject("message", bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage));
//        }else {
//            BaseResponse response = productService.create(productRequest);
//            view.addObject("message", response.getMessage());
//        }
//        return view;
//    }

    @PostMapping("/add")
    public BaseResponse<ProductResponse> addProduct(
            @RequestBody ProductRequest productRequest){
        return productService.create(productRequest);
    }
    @PostMapping("/update/{id}")
    public BaseResponse<ProductResponse> updateProduct( @PathVariable("id") UUID productId,
            @RequestBody ProductRequest productRequest){
        return productService.update(productRequest, productId);
    }
    @GetMapping("/delete/{id}")
    public boolean deleteProduct( @PathVariable("id") UUID productId){
        return productService.delete(productId);
    }
}
