package com.minssan9.shop.reviews;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.minssan9.shop.accounts.Account;
import com.minssan9.shop.items.Item;
import com.minssan9.shop.reviews.ReviewFile;

import lombok.*;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * id
 * 작성자
 * 아이템
 * 리뷰글
 * 좋아요상태
 * 작성일자
 * 파일
 */
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@JsonSerialize(using = ReviewSerializer.class)
public class Review {

    @Id
    @GeneratedValue
    private Long id;
    private LocalDateTime reviewCreated;
    private String content;
    private String likeStatus;

    @ManyToOne
    private Account account;

    @ManyToOne
    private Item item;

    @OneToOne(mappedBy = "review",cascade = CascadeType.ALL)
    private ReviewFile reviewFile;


}
