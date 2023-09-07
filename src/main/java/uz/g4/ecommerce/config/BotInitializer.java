package uz.g4.ecommerce.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import uz.g4.ecommerce.controller.bot.BotController;

@Component
@RequiredArgsConstructor
public class BotInitializer {
    private final BotController controller;

    @Bean
    public void run() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(controller);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
