package com.minssan9.shop.chats;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.minssan9.shop.accounts.AccountSerializer;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@JsonSerialize(using = MessageSerializer.class)
public class Message {

    @Id
    @GeneratedValue
    private Long id;

    private String senderName;
    private String sendMessage;
    private int read;   // 읽지않음 0 읽음 1
    private LocalDateTime sendDateTime;

    @ManyToOne
    @JsonIgnore
    private Chat chat;
}
