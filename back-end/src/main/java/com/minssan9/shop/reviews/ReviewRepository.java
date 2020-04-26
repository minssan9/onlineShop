package com.minssan9.shop.reviews;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findByItem_Id(Long id, Pageable pageable);
    Page<Review> findByItem_Title(String keyword,Pageable pageable);

    Page<Review> findByAccount_AccountId(String accountId, Pageable pageable);

    Page<Review> findByContentContains(String content, Pageable pageable);

    List<Review> findByReviewCreatedBetween(LocalDateTime start, LocalDateTime end);
}
