package uz.g4.ecommerce.controller.bot;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import uz.g4.ecommerce.domain.dto.response.ProductResponse;
import uz.g4.ecommerce.domain.entity.user.UserState;

@Component
@Slf4j
@RequiredArgsConstructor
public class BotController extends TelegramLongPollingBot {
    private final BotService botService;

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            Long chatId = message.getChatId();
            String text = message.getText();

            UserState userState = botService.checkState(chatId);
            SendMessage sendMessage = null;

            switch (userState) {
                case START -> {
                    if (message.hasContact()) {
                        sendMessage = botService.registerUser(chatId, message.getContact());
                    } else {
                        sendMessage = botService.shareContact(chatId);
                    }
                }
                case REGISTERED, BASKET_LIST, PRODUCT_LIST, PRODUCT, BASKET, CATEGORIES -> {
                    UserState state = botService.nextMenu(text, chatId);
                    switch (state) {
                        case GET_BALANCE -> sendMessage = botService.getBalance(chatId);
                        case ADD_BALANCE -> sendMessage = botService.addBalance(chatId);
                        case CATEGORIES -> sendMessage = botService.getCategories(chatId);
                        case BASKET_LIST -> sendMessage = botService.getOrders(chatId);
                        case ORDERS_HISTORY -> sendMessage = botService.getOrdersHistory(chatId);
                    }
                }
                case ADD_BALANCE -> sendMessage = botService.fillBalance(chatId, text);
            }
            execute(sendMessage);
        } else {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            Long chatId = callbackQuery.getMessage().getChatId();
            String data = callbackQuery.getData();
            Integer messageId = callbackQuery.getMessage().getMessageId();

            UserState userState = botService.checkState(chatId);
            SendMessage sendMessage = null;

            switch (userState) {
                case BASKET -> {
                    if (data.equals("BACK")) {
                        EditMessageText edit = botService.goToMyBasket(chatId, messageId);
                        execute(edit);
                    } else if (data.startsWith("+") || data.startsWith("-")) {
                        EditMessageText edit = botService.plusOrMinusOrderAmount(data, chatId, messageId);
                        execute(edit);
                    } else if (data.startsWith("REMOVE")) {
                        EditMessageText edit = botService.removeOrder(data, chatId, messageId);
                        execute(edit);
                    } else if (data.equals("back")) {
                        EditMessageText edit = botService.goToMyBasket(chatId, messageId);
                        execute(edit);
                    } else {
                        EditMessageText edit = botService.buy(data, chatId, messageId);
                        execute(edit);
                    }
                }
                case BASKET_LIST -> {
                    if (data.equals("ORDER_ALL")) {
                        EditMessageText edit = botService.orderAll(chatId, messageId);
                        execute(edit);
                    } else if (data.equals("REMOVE_ALL")) {
                        EditMessageText edit = botService.removeAll(chatId, messageId);
                        execute(edit);
                    } else {
                       EditMessageText edit = botService.order(data, chatId, messageId);
                       execute(edit);
                    }
                }
                case PRODUCT -> {
                    if (data.startsWith("PRODUCT_LIST")) {
                        EditMessageText edit = botService.getProductsByCategory(data.substring(12), chatId, messageId);
                        execute(edit);
                    } else if (data.startsWith("PRODUCT")) {
                        EditMessageText edit = botService.getProduct(data.substring(7), chatId, messageId);
                        execute(edit);
                    } else if (data.equals("BASKET_LIST")) {
                        EditMessageText edit = botService.goToMyBasket(chatId, messageId);
                        execute(edit);
                    } else {
                        EditMessageText edit = botService.addBasket(messageId, data, chatId);
                        execute(edit);
                    }
                }
                case PRODUCT_LIST -> {
                    ProductResponse product = botService.getProduct(data);
                    if (product != null) {
                        if (data.equals(product.getName())) {
                            sendMessage = botService.getChildCategoriesOrProducts(chatId,
                                    product.getCategory().getParent().getId().toString());
                            ReplyKeyboard replyKeyboard = sendMessage.getReplyMarkup();
                            EditMessageText edit = new EditMessageText();
                            edit.setMessageId(messageId);
                            edit.setText(sendMessage.getText());
                            edit.setReplyMarkup((InlineKeyboardMarkup) replyKeyboard);
                            edit.setChatId(chatId);
                            execute(edit);
                        }
                    } else {
                        EditMessageText edit = botService.getProduct(data, chatId, messageId);
                        execute(edit);
                    }
                    return;
                }
                case CATEGORIES -> {
                    if (data.equals("BACK")) {
                        sendMessage = botService.getCategories(chatId);
                        ReplyKeyboard replyKeyboard = sendMessage.getReplyMarkup();
                        EditMessageText edit = new EditMessageText();
                        edit.setText(sendMessage.getText());
                        edit.setChatId(chatId);
                        edit.setMessageId(messageId);
                        edit.setReplyMarkup((InlineKeyboardMarkup) replyKeyboard);
                        execute(edit);
                    }
                    sendMessage = botService.getChildCategoriesOrProducts(chatId, data);
                    ReplyKeyboard replyKeyboard = sendMessage.getReplyMarkup();
                    EditMessageText set = new EditMessageText();
                    set.setChatId(chatId);
                    set.setText(sendMessage.getText());
                    set.setMessageId(messageId);
                    set.setReplyMarkup((InlineKeyboardMarkup) replyKeyboard);
                    execute(set);
                    return;
                }
            }
            execute(sendMessage);
        }
    }

    @Override
    public String getBotUsername() {
        return "t.me/khamroz_bot";
    }

    @Override
    public String getBotToken() {
        return "6102106138:AAGzeiG916qRkjrBvcW-baSbgIOQh6IpYsc";
    }
}
