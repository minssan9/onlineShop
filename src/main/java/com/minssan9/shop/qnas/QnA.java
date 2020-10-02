package com.minssan9.shop.qnas;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.minssan9.shop.accounts.Account;
import com.minssan9.shop.items.Item;

import lombok.*;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = "id")
@JsonSerialize(using = QnASerializer.class)
public class QnA {

    @Id
    @GeneratedValue
    private Long id;
    private String title;

    @ManyToOne
    private Account writer;

    private LocalDateTime qnaCreated;
    private String content;
    private int status;
    private String answer;
    @ManyToOne
    private Item item;

//    public Long getItemId() {
//        return item.getId();
//    }
//
//    public String getItemTitle() {
//        return item.getTitle();
//    }


}
