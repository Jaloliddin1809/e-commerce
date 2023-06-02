package uz.g4.ecommerce.service.product;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.g4.ecommerce.domain.dto.request.ProductRequest;
import uz.g4.ecommerce.domain.dto.response.*;
import uz.g4.ecommerce.domain.entity.product.ProductEntity;
import uz.g4.ecommerce.domain.entity.user.UserEntity;
import uz.g4.ecommerce.repository.product.ProductRepository;
import uz.g4.ecommerce.service.BaseService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductService implements BaseService<BaseResponse<ProductResponse>, ProductRequest> {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    @Override
    public BaseResponse<ProductResponse> create(ProductRequest productRequest) {
        ProductEntity product = modelMapper.map(productRequest, ProductEntity.class);
        if(productRepository.existsByName(product.getName())){
            return BaseResponse.<ProductResponse>builder()
                    .message("Already exists name "+product.getName())
                    .status(404)
                    .build();
        }
        if (Objects.nonNull(product.getName())&&Objects.nonNull(product.getPrice())) {
            productRepository.save(product);
            return BaseResponse.<ProductResponse>builder()
                    .message("Product saved ")
                    .status(201)
                    .data(modelMapper.map(product, ProductResponse.class))
                    .build();
        }
        return BaseResponse.<ProductResponse>builder()
                .message("Product not saved ")
                .status(400)
                .build();
    }
    @Override
    public BaseResponse<ProductResponse> update(ProductRequest productRequest, UUID id) {
        ProductEntity product = productRepository.getReferenceById(id);
        try {
            if (Objects.nonNull(productRequest.getName())) {
                product.setName(productRequest.getName());
            }
            if (Objects.nonNull(productRequest.getPrice())) {
                product.setPrice(productRequest.getPrice());
            }
            if (Objects.nonNull(productRequest.getAmount())) {
                product.setAmount(productRequest.getAmount());
            }
            if (Objects.nonNull(productRequest.getCategory())) {
                product.setCategory(productRequest.getCategory());
            }
            productRepository.save(product);
            return BaseResponse.<ProductResponse>builder()
                    .message("Product update ")
                    .status(200)
                    .data(modelMapper.map(product, ProductResponse.class))
                    .build();
        }catch (Exception e){
            return BaseResponse.<ProductResponse>builder()
                    .message("Product not updating ")
                    .status(400)
                    .build();
        }
    }


    @Override
    public Boolean delete(UUID id) {
        Optional<ProductEntity> product = productRepository.findById(id);
        if (product.isPresent()) {
            productRepository.delete(product.get());
            return true;
        }
        return false;
    }

    @Override
    public BaseResponse<ProductResponse> getById(UUID id) {
        Optional<ProductEntity> product = productRepository.findById(id);
        if (product.isPresent()) {
            return BaseResponse.<ProductResponse>builder()
                    .message("Success")
                    .status(200)
                    .data(modelMapper.map(product, ProductResponse.class))
                    .build();
        }
        return BaseResponse.<ProductResponse>builder()
                .message("No such product exists")
                .status(400)
                .build();
    }

    public BaseResponse<List<OrderResponse>> findAllOrders() {
        return null;
    }
    public BaseResponse<List<ProductResponse>> getListProductsByCategoryId(UUID categoryId) {
        return BaseResponse.<List<ProductResponse>>builder()
                .data(modelMapper
                        .map(productRepository.getProductEntitiesByCategory_Id(categoryId),
                                new TypeToken<List<ProductResponse>>(){}.getType()))
                .message("success")
                .status(200)
                .build();
    }
    public BaseResponse<List<ProductResponse>> findAll() {
        List<ProductEntity> list = productRepository.findAll();
        return BaseResponse.<List<ProductResponse>>builder()
                .status(200)
                .message("success")
                .data(modelMapper.map(list,
                        new TypeToken<List<ProductResponse>>(){}.getType()))
                .build();
    }

    public List<ProductEntity> findByPage(Optional<Integer> page, Optional<Integer> size) {
        if (page.isPresent()) {
            if (size.isPresent()) {
                return productRepository.findAll(PageRequest.of(page.get(), size.get())).getContent();
            }
            return productRepository.findAll(PageRequest.of(page.get(), 10)).getContent();
        }
        return size.map(integer -> productRepository.findAll(PageRequest.of(0, integer)).getContent()).orElseGet(productRepository::findAll);

    }
    public Optional<ProductEntity> getOneProduct(UUID id) {
        return productRepository.findById(id);
    }
}
