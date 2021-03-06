package com.minssan9.shop.accounts;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountId(String userId);

    Page<Account> findByAccountId(String accountId, Pageable pageable);
    Page<Account> findByName(String name, Pageable pageable);
    Page<Account> findByAddress(String accountId, Pageable pageable);
}
