package com.github.Timmy8.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(schema = "manager", name = "tg_bot_user")
public class TGBotUser {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "chat_id")
    private long chatId;
    @Column(name = "user_tag")
    private String userTag;

    public TGBotUser(long chatId, String userTag) {
        this.chatId = chatId;
        this.userTag = userTag;
    }
}
