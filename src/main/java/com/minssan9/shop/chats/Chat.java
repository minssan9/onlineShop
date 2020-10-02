package com.minssan9.shop.chats;

import lombok.*;

import javax.persistence.*;

import com.minssan9.shop.accounts.Account;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Chat {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Account account;

    private int unRead;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private List<Message> messages;

}