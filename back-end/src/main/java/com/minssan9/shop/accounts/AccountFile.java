package com.minssan9.shop.accounts;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.minssan9.shop.items.Item;
import com.minssan9.shop.items.ItemFileSerializer;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@JsonSerialize(using = AccountFileSerializer.class)
public class AccountFile {

    @Id
    @GeneratedValue
    private Long id;
    private String fileName;
    private String uploadPath;
    private String uuid;

    @ManyToOne
    private Account account;
}
