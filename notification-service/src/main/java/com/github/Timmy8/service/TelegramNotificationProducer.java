package com.github.Timmy8.service;

import com.github.Timmy8.entity.TGBotUser;
import com.github.Timmy8.repository.TelegramRepository;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Component
public class TelegramNotificationProducer implements SpringLongPollingBot,
        LongPollingSingleThreadUpdateConsumer, NotificationProducer {
    private final TelegramClient telegramClient;
    private final TelegramRepository repository;
    private final String botToken;

    public TelegramNotificationProducer(TelegramRepository repository, Environment env) {
        this.botToken = env.getProperty("telegram.bot.token", "No key!");
        this.telegramClient = new OkHttpTelegramClient(botToken);
        this.repository = repository;
    }

    @Override
    public void consume(Update update) {
        if (update.hasMessage())
            if (update.getMessage().getText().equals("/start")) {
                long chatId = update.getMessage().getChatId();
                User user = update.getMessage().getFrom();
                String userTag = user.getUserName();

                if (!repository.existsByChatId(chatId)) {
                    repository.save(new TGBotUser(chatId, userTag));
                }

                sendMessage(chatId, "Dear, " + user.getFirstName() + "\nWelcome to Notification Bot!");
            } else sendMessage(update.getMessage().getChatId(), "Sorry, this bot does not support communication");
    }

    @Override
    public void sendNotification(String message) {
        repository.findAll().forEach((TGBotUser -> sendMessage(TGBotUser.getChatId(), message)));
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }

    public void sendMessage(long chatId, String text){
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .build();
        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            System.err.println(e.getMessage());
        }
    }
}
