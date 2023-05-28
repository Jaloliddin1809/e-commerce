package uz.g4.ecommerce.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.meta.generics.TelegramBot;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
@Component
@RequiredArgsConstructor
public class BotInitializer {
    private final TelegramBot telegramBot;
    @Bean
    public void init() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot((LongPollingBot) telegramBot);
            System.out.println("started");
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
