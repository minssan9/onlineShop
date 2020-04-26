package com.minssan9.shop.orders;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.minssan9.shop.accounts.Account;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {


    Page<Order> findByAccount(Account account, Pageable pageable);
    List<Order> findByOrderCreatedBetween(LocalDateTime start, LocalDateTime end);


    Page<Order> findByItem_Title(String title,Pageable pageable);
    Page<Order> findByAccount_AccountId(String accountId,Pageable pageable);
    Page<Order> findByPayment(String payment,Pageable pageable);
    Page<Order> findByStatus(int status,Pageable pageable);
}
