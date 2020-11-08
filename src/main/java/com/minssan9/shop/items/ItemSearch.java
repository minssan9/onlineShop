package com.minssan9.shop.items;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.minssan9.shop.qnas.QnA;
import com.minssan9.shop.reviews.Review;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter @Setter
@ToString
public class ItemSearch {
private Long id;
    private String title;
    private LocalDateTime itemCreated;
    private int sellCount;
    private int price;
    private int discount;
    private int savings;
    private int deliveryDate;
}
