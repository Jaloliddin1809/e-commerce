package uz.g4.ecommerce.service.product;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.g4.ecommerce.domain.dto.request.ProductRequest;
import uz.g4.ecommerce.domain.dto.response.BaseResponse;
import uz.g4.ecommerce.domain.dto.response.OrderResponse;
import uz.g4.ecommerce.domain.dto.response.ProductResponse;
import uz.g4.ecommerce.domain.entity.product.ProductEntity;
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
        if (Objects.nonNull(product.getName()) && Objects.nonNull(product.getPrice())) {
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
        } catch (Exception e) {
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

    public List<OrderResponse> findAll() {
        return null;
    }

    public List<ProductEntity> getListByCategoryId(UUID categoryId) {
        return productRepository.getProductEntitiesByCategory_Id(categoryId);
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
}
