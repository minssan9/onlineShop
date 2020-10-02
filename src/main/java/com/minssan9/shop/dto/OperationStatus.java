package com.minssan9.shop.dto;

import lombok.Builder;
import lombok.Data;


@Data
public class OperationStatus {

    private String today;
    private int sales;                       // 매출액
    private int paymentCompletedCount;       // 결제완료수
    private int shippingPreparationCount;    // 배송준비수
    private int shippingCount;               // 배송중수
    private int shippingCompleteCount;       // 배송중수
    private int qnaCount;                    // 상품문의수
    private int reviewCount;                 // 상품후기수
}
