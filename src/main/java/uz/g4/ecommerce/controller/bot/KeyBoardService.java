package uz.g4.ecommerce.controller.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import uz.g4.ecommerce.domain.dto.response.CategoryResponse;
import uz.g4.ecommerce.domain.dto.response.OrderResponse;
import uz.g4.ecommerce.domain.entity.user.UserState;
import uz.g4.ecommerce.service.category.CategoryService;
import uz.g4.ecommerce.service.order.OrderService;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KeyBoardService {
    private final CategoryService categoryService;
    private final OrderService orderService;

    public ReplyKeyboardMarkup mainMenu() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();

        row.add("📋 Categories");
        row.add("\uD83D\uDED2 Basket");
        keyboardRows.add(row);

        row = new KeyboardRow();
        row.add("\uD83D\uDCDC History");
        row.add("\uD83D\uDCB0 My balance");
        keyboardRows.add(row);

        row = new KeyboardRow();
        row.add("\uD83D\uDCB8 Fill balance");
        keyboardRows.add(row);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup shareContact() {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardButton button = new KeyboardButton("Please share contact ☎️");
        button.setRequestContact(true);
        keyboardRow.add(button);
        markup.setResizeKeyboard(true);
        markup.setKeyboard(List.of(keyboardRow));
        return markup;
    }

    public ReplyKeyboard getCategories() {
        InlineKeyboardMarkup inline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();

        List<CategoryResponse> parentCategories = categoryService.getParentCategories();

        for (int i = 0; i < parentCategories.size(); i++) {
            for (int j = 0; j < parentCategories.size(); j++) {
                button.setText(parentCategories.get(i).getType());
                button.setCallbackData(parentCategories.get(i).getId().toString());
                buttons.add(button);
            }
            rows.add(buttons);
            button = new InlineKeyboardButton();
            buttons = new ArrayList<>();
        }

        inline.setKeyboard(rows);
        return inline;
    }

    public ReplyKeyboard getOrders(Long chatId) {
        InlineKeyboardMarkup inline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();

        List<OrderResponse> orders = orderService.findByChatId(chatId);

        if (orders == null) {
            return null;
        }

        for (int i = 0; i < orders.size(); i++) {
            for (int j = 0; j < orders.size(); j++) {
                button.setText(orders.get(i).getProduct().getName());
                button.setCallbackData(orders.get(i).getId().toString());
                buttons.add(button);
            }
            rows.add(buttons);
            button = new InlineKeyboardButton();
            buttons = new ArrayList<>();
        }

        button = new InlineKeyboardButton("Order all");
        button.setCallbackData("ORDER_ALL");
        buttons.add(button);
        button = new InlineKeyboardButton("Remove all");
        button.setCallbackData("REMOVE_ALL");
        buttons.add(button);

        rows.add(buttons);
        inline.setKeyboard(rows);
        return inline;
    }


    public InlineKeyboardMarkup getProduct(UUID id, UUID productId) {
        InlineKeyboardMarkup inline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new LinkedList<>();
        List<InlineKeyboardButton> rows = new LinkedList<>();
        InlineKeyboardButton button = new InlineKeyboardButton("1️⃣");
        button.setCallbackData("1"+productId);
        rows.add(button);
        button = new InlineKeyboardButton("2️⃣");
        button.setCallbackData("2"+productId);
        rows.add(button);
        button = new InlineKeyboardButton("3️⃣");
        button.setCallbackData("3"+productId);
        rows.add(button);
        buttons.add(rows);
        rows = new LinkedList<>();
        button = new InlineKeyboardButton("4️⃣");
        button.setCallbackData("4"+productId);
        rows.add(button);
        button = new InlineKeyboardButton("5️⃣");
        button.setCallbackData("5"+productId);
        rows.add(button);
        button = new InlineKeyboardButton("6️⃣");
        button.setCallbackData("6"+productId);
        rows.add(button);
        buttons.add(rows);
        rows = new LinkedList<>();
        button = new InlineKeyboardButton("7️⃣");
        button.setCallbackData("7"+productId);
        rows.add(button);
        button = new InlineKeyboardButton("8️⃣");
        button.setCallbackData("8"+productId);
        rows.add(button);
        button = new InlineKeyboardButton("9️⃣");
        button.setCallbackData("9"+productId);
        rows.add(button);
        buttons.add(rows);
        rows = new LinkedList<>();
        button = new InlineKeyboardButton("🔙 Back");
        button.setCallbackData(UserState.PRODUCT_LIST.name() + id);
        rows.add(button);
        buttons.add(rows);
        inline.setKeyboard(buttons);
        return inline;
    }

    public InlineKeyboardMarkup addBasket(UUID productId) {
        List<InlineKeyboardButton> row = new ArrayList<>();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        InlineKeyboardButton button = new InlineKeyboardButton();


        button.setText("🔙 Back");
        button.setCallbackData(UserState.PRODUCT.name() + productId);
        row.add(button);
        button = new InlineKeyboardButton("My basket \uD83D\uDED2");
        button.setCallbackData(UserState.BASKET_LIST.name());
        row.add(button);
        rows.add(row);
        markup.setKeyboard(rows);
        return markup;
    }

    public InlineKeyboardMarkup Order(String data) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();


        button.setText("➖");
        button.setCallbackData("-"+data);
        row.add(button);
        button = new InlineKeyboardButton("Buy");
        button.setCallbackData(data);
        row.add(button);
        button = new InlineKeyboardButton();
        button.setText("➕");
        button.setCallbackData("+"+data);
        row.add(button);
        rows.add(row);

        row = new ArrayList<>();
        button = new InlineKeyboardButton("Remove");
        button.setCallbackData("REMOVE"+data);
        row.add(button);
        rows.add(row);

        row = new ArrayList<>();
        button = new InlineKeyboardButton("🔙 Back");
        button.setCallbackData("BACK");
        row.add(button);
        rows.add(row);

        markup.setKeyboard(rows);
        return markup;
    }

    public InlineKeyboardMarkup order() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton("🔙 Back");
        button.setCallbackData("back");
        row.add(button);
        rows.add(row);
        markup.setKeyboard(rows);
        return markup;
    }
}
