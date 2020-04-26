package com.minssan9.shop.qnas;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.minssan9.shop.accounts.Account;

import java.time.LocalDateTime;
import java.util.List;

public interface QnARepository extends JpaRepository<QnA, Long> {

    Page<QnA> findByItem_Id(Long itemId, Pageable pageable);
    Page<QnA> findByItem_Title(String keyword,Pageable pageable);
    List<QnA> findByQnaCreatedBetween(LocalDateTime start, LocalDateTime end);
    Page<QnA> findByTitleContains(String keyword,Pageable pageable);
    Page<QnA> findByWriter(String keyword, Pageable pageable);
    Page<QnA> findByStatus(int status,Pageable pageable);
}
