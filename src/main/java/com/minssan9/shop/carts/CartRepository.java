package com.minssan9.shop.carts;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByAccount_AccountIdAndItem_Id(String accountId, Long itemId);

    List<Cart> findByAccount_AccountId(String accountId);
}
