package uz.g4.ecommerce.service.product;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.g4.ecommerce.domain.dto.request.HistoryRequest;
import uz.g4.ecommerce.domain.dto.request.OrderRequest;
import uz.g4.ecommerce.domain.dto.request.ProductRequest;
import uz.g4.ecommerce.domain.dto.response.*;
import uz.g4.ecommerce.domain.entity.category.CategoryEntity;
import uz.g4.ecommerce.domain.entity.order.OrderEntity;
import uz.g4.ecommerce.domain.entity.order.OrderStatus;
import uz.g4.ecommerce.domain.entity.product.ProductEntity;
import uz.g4.ecommerce.domain.entity.user.UserEntity;
import uz.g4.ecommerce.repository.order.OrderRepository;
import uz.g4.ecommerce.repository.product.ProductRepository;
import uz.g4.ecommerce.service.BaseService;
import uz.g4.ecommerce.service.category.CategoryService;
import uz.g4.ecommerce.service.history.HistoryService;
import uz.g4.ecommerce.service.order.OrderService;
import uz.g4.ecommerce.service.user.UserService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService implements BaseService<BaseResponse<ProductResponse>, ProductRequest> {
    private final ProductRepository productRepository;
    private final OrderService orderService;
    private final UserService userService;
    private final HistoryService historyService;
    private final CategoryService categoryService;
    private final OrderRepository orderRepository;
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
//         if (Objects.nonNull(product.getName())&&Objects.nonNull(product.getPrice())) {
//             productRepository.save(product);
//         }

        BaseResponse<CategoryResponse> byId = categoryService.getById(productRequest.getCategory());

        if (byId.getStatus() == 400) {
            return BaseResponse.<ProductResponse>builder()
                    .message("Category not found")
                    .status(400)
                    .build();
        }
        ProductEntity product = modelMapper.map(productRequest, ProductEntity.class);

        product.setCategory(modelMapper.map(byId.getData(), CategoryEntity.class));
        productRepository.save(product);
        return BaseResponse.<ProductResponse>builder()
                .message("Product saved")
                .status(200)
                .data(modelMapper.map(product, ProductResponse.class))
                .build();

    }

    @Override
    public BaseResponse<ProductResponse> update(ProductRequest productRequest, UUID id) {
        Optional<ProductEntity> product = productRepository.findById(id);


        if (product.isPresent()) {
            if (productRequest.getName() != null) {
                product.get().setName(productRequest.getName());
            }
            if (productRequest.getPrice() != null) {
                product.get().setPrice(productRequest.getPrice());
            }
            if (productRequest.getAmount() != null) {
                product.get().setAmount(productRequest.getAmount());
            }
            BaseResponse<CategoryResponse> category = categoryService.getById(productRequest.getCategory());
            product.get().setCategory(modelMapper.map(category.getData(), CategoryEntity.class));
            productRepository.save(product.get());
            return BaseResponse.<ProductResponse>builder()
                    .status(200)
                    .message("Successfully updated")
                    .data(modelMapper.map(product, ProductResponse.class))
                    .build();
        } 
        return BaseResponse.<ProductResponse>builder()
                .message("Product not found")
                .status(400)
                .build();
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
                .message("product not found")
                .status(400)
                .build();
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

//     public List<ProductResponse> getListByCategoryId(UUID categoryId) {
//        return productRepository.findProductEntityByCategory_Id(categoryId).stream()
//                 .map((product) -> modelMapper.map(product, ProductResponse.class))
//                 .collect(Collectors.toList());
    }

    public List<ProductResponse> findByPage(Optional<Integer> page, Optional<Integer> size) {
        if (page.isPresent()) {
            if (size.isPresent()) {
                return productRepository.findAll(PageRequest.of(page.get(), size.get())).getContent()
                        .stream().map((product) -> modelMapper.map(product, ProductResponse.class))
                        .collect(Collectors.toList());
            }
            return productRepository.findAll(PageRequest.of(page.get(), 10)).getContent()
                    .stream().map((product) -> modelMapper.map(product, ProductResponse.class))
                    .collect(Collectors.toList());
        }
        return size.map(integer -> productRepository.findAll(PageRequest.of(0, integer))
                .getContent().stream().map(product->modelMapper.map(product,ProductResponse.class)))
                .orElseGet(() -> productRepository.findAll().stream().map(product->modelMapper.map(product,ProductResponse.class))).collect(Collectors.toList());
    }


    public List<CategoryResponse> getChildCategory(String uuid) {
        return categoryService.getChildCategories(UUID.fromString(uuid));
    }

    public List<ProductResponse> getProduct(String uuid) {
        List<ProductEntity> productEntityByCategoryId = productRepository.findProductEntityByCategory_Id(UUID.fromString(uuid));

        if (productEntityByCategoryId.isEmpty()) {
            return null;
        }
        return productEntityByCategoryId.stream()
                .map((product) -> modelMapper.map(product, ProductResponse.class))
                .collect(Collectors.toList());
    }
    public Optional<ProductEntity> getOneProduct(UUID id) {
        return productRepository.findById(id);

    public BaseResponse<ProductResponse> getByName(String data) {
        Optional<ProductEntity> productEntityByName = productRepository.findProductEntityByName(data);

        if (productEntityByName.isPresent()) {
            return BaseResponse.<ProductResponse>builder()
                    .message("success")
                    .status(200)
                    .data(modelMapper.map(productEntityByName, ProductResponse.class))
                    .build();
        }
        return BaseResponse.<ProductResponse>builder()
                .message("fail")
                .status(400)
                .build();
    }

    public BaseResponse<ProductResponse> addBasket(String data, Long chatId) {
        Optional<ProductEntity> product = productRepository.findById(UUID.fromString(data.substring(1)));
        Integer amount = Integer.parseInt(data.substring(0, 1));
        if (product.isPresent()) {
            BaseResponse<UserResponse> user = userService.getByChatId(chatId);
            if (user.getStatus() == 200) {
                if (product.get().getAmount() >= amount) {
                    orderService.create(OrderRequest.builder()
                            .product(product.get())
                            .orderState(OrderStatus.IN_CART)
                            .amount(amount)
                            .user(modelMapper.map(user.getData(), UserEntity.class))
                            .build());
                    return BaseResponse.<ProductResponse>builder()
                            .message("Successfully saved")
                            .status(200)
                            .build();
                }
                return BaseResponse.<ProductResponse>builder()
                        .message("There are not enough products")
                        .status(400)
                        .build();
            }
            return BaseResponse.<ProductResponse>builder()
                    .message("User not found")
                    .status(400)
                    .build();
        }
        return BaseResponse.<ProductResponse>builder()
                .message("Product not found")
                .status(400)
                .build();
    }

    public BaseResponse<ProductResponse> updateProduct(Integer amount, UUID id) {
        BaseResponse<ProductResponse> byId = getById(id);
        if (byId.getStatus() == 200) {
            if (byId.getData().getAmount() >= amount) {
                productRepository.updateProductAmount(amount, id);
                if (byId.getData().getAmount() == 0) {
                    productRepository.deleteById(id);
                }
                return BaseResponse.<ProductResponse>builder()
                        .message("success")
                        .status(200)
                        .build();
            }
            return BaseResponse.<ProductResponse>builder()
                    .message("Product amount not enough")
                    .status(400)
                    .build();
        }

        return BaseResponse.<ProductResponse>builder()
                .message("Product not found")
                .status(400)
                .build();
    }

    public BaseResponse<OrderResponse> orderAll(Long chatId) {
        List<OrderEntity> byChatId = orderRepository.findByChatId(chatId);

        if (!byChatId.isEmpty()) {
            for (OrderEntity orderEntity : byChatId) {
                if ((orderEntity.getAmount() * orderEntity.getProduct().getPrice())
                        <= orderEntity.getUser().getBalance()) {
                    userService.updateBalance(orderEntity.getUser().getId(), orderEntity.getAmount() *
                            orderEntity.getProduct().getPrice());
                    BaseResponse<ProductResponse> response =
                            updateProduct(orderEntity.getAmount(), orderEntity.getProduct().getId());
                    if (response.getStatus() == 400) {
                        return BaseResponse.<OrderResponse>builder()
                                .message(response.getMessage())
                                .status(400)
                                .build();
                    }
                    historyService.create(
                            HistoryRequest.builder()
                                    .productName(orderEntity.getProduct().getName())
                                    .price(orderEntity.getProduct().getPrice())
                                    .totalPrice(orderEntity.getAmount() * orderEntity.getProduct().getPrice())
                                    .amount(orderEntity.getAmount())
                                    .user(orderEntity.getUser())
                                    .build()
                    );
                    orderRepository.deleteById(orderEntity.getId());
                }
                return BaseResponse.<OrderResponse>builder()
                        .message("User balance not enough")
                        .status(400)
                        .build();
            }
        }
        return BaseResponse.<OrderResponse>builder()
                .message("You have no orders")
                .status(400)
                .build();
    }

    public BaseResponse<OrderResponse> order(String data) {
        BaseResponse<OrderResponse> orderResponse = orderService.getById(UUID.fromString(data));

        if (orderResponse.getStatus() != 400) {
            OrderResponse order = orderResponse.getData();
            if (order.getAmount() * order.getProduct().getPrice() < order.getUser().getBalance()) {
                userService.updateBalance(order.getUser().getId(),
                        order.getAmount() * order.getProduct().getPrice());
                BaseResponse<ProductResponse> response =
                        updateProduct(order.getAmount(), order.getProduct().getId());
                if (response.getStatus() == 400) {
                    return BaseResponse.<OrderResponse>builder()
                            .message(response.getMessage())
                            .status(400)
                            .build();
                }
                historyService.create(
                        HistoryRequest.builder()
                                .productName(order.getProduct().getName())
                                .price(order.getProduct().getPrice())
                                .totalPrice(order.getAmount() * order.getProduct().getPrice())
                                .amount(order.getAmount())
                                .user(order.getUser())
                                .build()
                );
                orderRepository.deleteById(order.getId());
                return BaseResponse.<OrderResponse>builder()
                        .message("successfully purchased")
                        .status(200)
                        .build();
            }
            return BaseResponse.<OrderResponse>builder()
                    .message("User balance not enough")
                    .status(400)
                    .build();
        }
        return BaseResponse.<OrderResponse>builder()
                .message("Order not found")
                .status(400)
                .build();
    }
}
