package uz.g4.ecommerce.controller.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import uz.g4.ecommerce.domain.dto.request.UserRequest;
import uz.g4.ecommerce.domain.dto.request.UserStateDto;
import uz.g4.ecommerce.domain.dto.response.*;
import uz.g4.ecommerce.domain.entity.user.Role;
import uz.g4.ecommerce.domain.entity.user.UserState;
import uz.g4.ecommerce.service.history.HistoryService;
import uz.g4.ecommerce.service.order.OrderService;
import uz.g4.ecommerce.service.product.ProductService;
import uz.g4.ecommerce.service.user.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BotService {
    private final UserService userService;
    private final KeyBoardService keyBoardService;
    private final HistoryService historyService;
    private final OrderService orderService;
    private final ProductService productService;


    public UserState checkState(Long chatId) {
        BaseResponse<UserStateDto> userState =
                userService.getUserState(chatId);
        if (userState.getStatus() == 200) {
            return userState.getData().getState();
        }

        return UserState.START;
    }

    public SendMessage registerUser(Long chatId, Contact contact) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());

        BaseResponse<UserResponse> response = userService.create(
                UserRequest.builder()
                        .username(contact.getFirstName())
                        .chatId(chatId.toString())
                        .name(contact.getLastName())
                        .password("1")
                        .roles(Set.of(Role.USER))
                        .permissions(null)
                        .build()
        );

        if (response.getStatus() == 200) {
            userService.updateState(new UserStateDto(chatId, UserState.REGISTERED));
            sendMessage.setReplyMarkup(keyBoardService.mainMenu());
        }

        sendMessage.setText(response.getMessage());
        return sendMessage;
    }

    public SendMessage shareContact(Long chatId) {
        SendMessage sendMessage = new SendMessage(chatId.toString(), "Please register");
        sendMessage.setReplyMarkup(keyBoardService.shareContact());
        return sendMessage;
    }

    public UserState nextMenu(String text, Long chatId) {
        UserState userState = null;
        switch (text) {
            case "📋 Categories" -> userState = UserState.CATEGORIES;
            case "\uD83D\uDED2 Basket" -> userState = UserState.BASKET_LIST;
            case "\uD83D\uDCDC History" -> userState = UserState.ORDERS_HISTORY;
            case "\uD83D\uDCB0 My balance" -> userState = UserState.GET_BALANCE;
            case "\uD83D\uDCB8 Fill balance" -> userState = UserState.ADD_BALANCE;
        }
        userService.updateState(new UserStateDto(chatId, userState));
        return userState;
    }

    public SendMessage getBalance(Long chatId) {
        userService.updateState(new UserStateDto(chatId, UserState.REGISTERED));
        SendMessage sendMessage = new SendMessage();
        BaseResponse<Double> balance = userService.getBalance(chatId);
        sendMessage.setChatId(chatId);
        sendMessage.setText("Your balance is : " + balance.getData());
        return sendMessage;
    }

    public SendMessage addBalance(Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Please enter the amount (amount > 0)");
        sendMessage.setChatId(chatId);
        return sendMessage;
    }

    public SendMessage getCategories(Long chatId) {
        userService.updateState(new UserStateDto(chatId, UserState.CATEGORIES));
        ReplyKeyboard replyKeyboard = keyBoardService.getCategories();
        SendMessage sendMessage = new SendMessage(chatId.toString(), "Select parent category");
        sendMessage.setReplyMarkup(replyKeyboard);
        return sendMessage;
    }

    public SendMessage getOrders(Long chatId) {
        userService.updateState(new UserStateDto(chatId, UserState.BASKET_LIST));
        ReplyKeyboard orders = keyBoardService.getOrders(chatId);

        SendMessage sendMessage = new SendMessage();

        if (orders == null) {
            sendMessage.setChatId(chatId);
            sendMessage.setText("Basket is empty");
            return sendMessage;
        }
        sendMessage.setChatId(chatId);
        sendMessage.setText("Your basket");
        sendMessage.setReplyMarkup(orders);
        return sendMessage;
    }

    public SendMessage getOrdersHistory(Long chatId) {
        userService.updateState(new UserStateDto(chatId, UserState.REGISTERED));
        List<HistoryResponse> userHistories = historyService.getUserHistories(chatId);
        SendMessage sendMessage = new SendMessage();
        if (userHistories.isEmpty()) {
            sendMessage.setChatId(chatId);
            sendMessage.setText("You have not histories");
            return sendMessage;
        }

        StringBuilder sb = new StringBuilder();
        for (HistoryResponse userHistory : userHistories) {
            String history =
                    "✍️ Name: " + userHistory.getProductName() +
                            ",\n💵 Price: " + userHistory.getPrice() +
                            ",\n🔢 Amount: " + userHistory.getAmount() +
                            ",\n \uD83D\uDCB8 Total Price: " + userHistory.getTotalPrice() +
                            "\n-----------------------------------\n";
            sb.append(history);
        }
        sendMessage.setChatId(chatId);
        sendMessage.setText(sb.toString());
        return sendMessage;
    }

    public SendMessage fillBalance(Long chatId, String text) {
        BaseResponse<UserResponse> response = userService.fillBalance(chatId, text);
        userService.updateState(new UserStateDto(chatId, UserState.REGISTERED));
        return new SendMessage(chatId.toString(), response.getMessage());
    }

    public SendMessage getChildCategoriesOrProducts(Long chatId, String data) {
        SendMessage sendMessage = new SendMessage();

        List<CategoryResponse> childCategories = productService.getChildCategory(data);
        List<ProductResponse> products = productService.getProduct(data);

        InlineKeyboardMarkup inline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();

        if (products == null) {
            userService.updateState(new UserStateDto(chatId, UserState.CATEGORIES));

            for (CategoryResponse childCategory : childCategories) {
                rows.add(getChildCategoriesButton(childCategory));
            }

            button.setText("🔙 Back");
            button.setCallbackData("BACK");
            buttons.add(button);

            rows.add(buttons);
            inline.setKeyboard(rows);

            sendMessage.setText("Select child category");

        } else {
            userService.updateState(new UserStateDto(chatId, UserState.PRODUCT_LIST));

            for (ProductResponse product : products) {
                rows.add(getProductsButton(product));
            }
            button.setText("🔙 Back");
            button.setCallbackData(products.get(0).getName());
            buttons.add(button);

            rows.add(buttons);
            inline.setKeyboard(rows);

            sendMessage.setText("Select one product");
        }
        sendMessage.setChatId(chatId);
        sendMessage.setReplyMarkup(inline);
        return sendMessage;

    }

    private List<InlineKeyboardButton> getProductsButton(ProductResponse product) {
        InlineKeyboardButton button = new InlineKeyboardButton(product.getName());
        button.setCallbackData(product.getId().toString());
        return List.of(button);
    }

    private List<InlineKeyboardButton> getChildCategoriesButton(CategoryResponse childCategory) {
        InlineKeyboardButton button = new InlineKeyboardButton(childCategory.getType());
        button.setCallbackData(childCategory.getId().toString());
        return List.of(button);
    }

    public ProductResponse getProduct(String data) {
        BaseResponse<ProductResponse> byName = productService.getByName(data);

        if (byName.getStatus() == 200) {
            return byName.getData();
        }
        return null;
    }


    public EditMessageText getProduct(String data, Long chatId, Integer messageId) {
        userService.updateState(new UserStateDto(chatId, UserState.PRODUCT));
        BaseResponse<ProductResponse> byId = productService.getById(UUID.fromString(data));
        EditMessageText edit = new EditMessageText();
        edit.setChatId(chatId);
        edit.setText(byId.getData().getName());
        edit.setMessageId(messageId);
        edit.setReplyMarkup(keyBoardService.getProduct(byId.getData().getCategory().getId(), byId.getData().getId()));
        return edit;
    }

    public EditMessageText getProductsByCategory(String substring, Long chatId, Integer messageId) {
        userService.updateState(new UserStateDto(chatId, UserState.PRODUCT_LIST));
        SendMessage childCategoriesOrProducts = getChildCategoriesOrProducts(chatId, substring);
        EditMessageText edit = new EditMessageText();
        edit.setText(childCategoriesOrProducts.getText());
        edit.setReplyMarkup((InlineKeyboardMarkup) childCategoriesOrProducts.getReplyMarkup());
        edit.setChatId(chatId);
        edit.setMessageId(messageId);
        return edit;
    }

    public EditMessageText addBasket(Integer messageId, String data, Long chatId) {
        BaseResponse<ProductResponse> response = productService.addBasket(data, chatId);
        BaseResponse<ProductResponse> byId = productService.getById(UUID.fromString(data.substring(1)));

        EditMessageText edit = new EditMessageText();
        edit.setMessageId(messageId);
        edit.setText(response.getMessage());
        edit.setChatId(chatId);
        edit.setReplyMarkup(keyBoardService.addBasket(byId.getData().getId()));
        return edit;
    }


    public EditMessageText goToMyBasket(Long chatId, Integer messageId) {
        userService.updateState(new UserStateDto(chatId, UserState.BASKET_LIST));
        ReplyKeyboard orders = keyBoardService.getOrders(chatId);
        EditMessageText edit = new EditMessageText();
        String text;
        if (orders == null) {
            text = "You have no orders";
        } else {
            text = "Your basket";
        }
        edit.setText(text);
        edit.setMessageId(messageId);
        edit.setChatId(chatId);
        edit.setReplyMarkup((InlineKeyboardMarkup) orders);
        return edit;
    }

    public EditMessageText orderAll(Long chatId, Integer messageId) {
        BaseResponse<OrderResponse> response = productService.orderAll(chatId);
        EditMessageText edit = new EditMessageText();
        String text;
        if (response.getStatus() != 400) {
            text = "All orders have been purchased successfully";
        } else {
            text = response.getMessage();
        }
        edit.setText(text);
        edit.setMessageId(messageId);
        edit.setChatId(chatId);
        return edit;
    }

    public EditMessageText removeAll(Long chatId, Integer messageId) {
        EditMessageText edit = new EditMessageText();
        BaseResponse<OrderResponse> response = orderService.removeAll(chatId);
        edit.setMessageId(messageId);
        edit.setChatId(chatId);
        edit.setText(response.getMessage());
        return edit;
    }

    public EditMessageText order(String data, Long chatId, Integer messageId) {
        userService.updateState(new UserStateDto(chatId, UserState.BASKET));
        BaseResponse<OrderResponse> response = orderService.getById(UUID.fromString(data));
        OrderResponse order = response.getData();
        EditMessageText edit = new EditMessageText();
        StringBuilder sb = new StringBuilder();
        String history =
                "✍️ Name: " + order.getProduct().getName() +
                        ",\n💵 Total price: " + order.getAmount() * order.getProduct().getPrice() +
                        ",\n🔢 Amount: " + order.getAmount() +
                        ",\n \uD83D\uDCB8 Order state: " + order.getOrderState() +
                        "\n-----------------------------------\n";
        edit.setText(sb.append(history).toString());
        edit.setChatId(chatId);
        edit.setMessageId(messageId);
        edit.setReplyMarkup(keyBoardService.Order(data));
        return edit;
    }

    public EditMessageText plusOrMinusOrderAmount(String orderId, Long chatId, Integer messageId) {
        BaseResponse<OrderResponse> response = orderService.plusOrMinusOrderAmount(orderId);

        if (response.getStatus() == 200) {
            return order(orderId.substring(1), chatId, messageId);
        }
        return goToMyBasket(chatId, messageId);
    }


    public EditMessageText removeOrder(String data, Long chatId, Integer messageId) {
        orderService.delete(UUID.fromString(data.substring(6)));
        EditMessageText edit = new EditMessageText();
        edit.setText("order successfully deleted");
        edit.setMessageId(messageId);
        edit.setChatId(chatId);
        edit.setReplyMarkup(keyBoardService.order());
        return edit;
    }

    public EditMessageText buy(String data, Long chatId, Integer messageId) {
        BaseResponse<OrderResponse> order = productService.order(data);
        EditMessageText edit = new EditMessageText();
        String text;
        if (order.getStatus() != 400) {
            text = "order have been purchased successfully";
        } else {
            text = order.getMessage();
        }
        edit.setText(text);
        edit.setMessageId(messageId);
        edit.setChatId(chatId);
        edit.setReplyMarkup(keyBoardService.order());
        return edit;
    }
}
