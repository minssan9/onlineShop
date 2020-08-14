package com.minssan9.shop.accounts;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.minssan9.shop.carts.Cart;
import com.minssan9.shop.chats.Chat;
import com.minssan9.shop.orders.Order;
import com.minssan9.shop.qnas.QnA;
import com.minssan9.shop.reviews.Review;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@ToString
@JsonSerialize(using = AccountSerializer.class)
public class Account {

    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String accountId;
    private String password;
    private String name;
    private String address;     // email (변경예정)
    private String phone;
    private String email;
    private int level;
    private int point;


    @OneToOne(mappedBy = "account")
    private Chat chat;

    @OneToOne(mappedBy = "account")
    private AccountFile accountFile;


    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<AccountRoles> roles;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private Set<Cart> carts;

    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL)
    private List<QnA> qnAList;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Order> orderList;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Review> reviewList;


}
