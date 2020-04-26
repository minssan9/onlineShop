package com.minssan9.shop.carts;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.minssan9.shop.accounts.Account;
import com.minssan9.shop.items.Item;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@JsonSerialize(using = CartSerializer.class)
public class Cart {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Item item;

    @ManyToOne
    private Account account;

}
