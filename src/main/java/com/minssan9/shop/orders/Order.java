package com.minssan9.shop.orders;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.minssan9.shop.accounts.Account;
import com.minssan9.shop.items.Item;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="ORDERS")
@NoArgsConstructor @AllArgsConstructor
@Builder @Getter @Setter
@EqualsAndHashCode(of = "id")
@JsonSerialize(using = OrderSerializer.class)
public class Order {

    @Id
    @GeneratedValue
    private Long id;
    private LocalDateTime orderCreated;
    private String payment;
    private int status;
    private String options;

    @OneToOne
    private Item item;

    // 주문자
    @ManyToOne
    private Account account;
}
