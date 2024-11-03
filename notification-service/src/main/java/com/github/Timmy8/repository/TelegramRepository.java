package com.github.Timmy8.repository;

import com.github.Timmy8.entity.TGBotUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TelegramRepository extends CrudRepository<TGBotUser, UUID> {
    boolean existsByChatId(long chatId);
}
