package com.minssan9.shop.items;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.minssan9.shop.qnas.QnA;
import com.minssan9.shop.reviews.Review;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@EqualsAndHashCode(of = "id")
@JsonSerialize(using = ItemSerializer.class)
@ToString
public class Item {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private LocalDateTime itemCreated;
    private int sellCount;
    private int price;
    private int discount;
    private int savings;
    private int deliveryDate;
    @Column(length = 3000)
    private String content;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ItemOption> options;
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<ItemFile> itemFileList;
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<QnA> qnAList;
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<Review> reviewList;



//    private List<String> options;
//    private List<String> tag;
//    private List<String> contentsImageSrc;
//    private List<String> carouselSrc;

}
