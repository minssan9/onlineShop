package com.minssan9.shop.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minssan9.shop.accounts.Account;

import java.time.LocalDateTime;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public void order(Account account) {

        Order order = Order.builder()
                .account(account)
                .payment("카드")
                .orderCreated(LocalDateTime.now())
                .status(0)
                .build();

        orderRepository.save(order);
    }
}

