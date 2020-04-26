package com.minssan9.shop.reviews;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReviewDto {

    private String content;
    private String likeStatus;
    ReviewFile reviewFile;
}
