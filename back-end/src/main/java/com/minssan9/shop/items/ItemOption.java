package com.minssan9.shop.items;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
@ToString
public class ItemOption {

    @Id
    @GeneratedValue
    private Long id;
    private String option;

    @JsonIgnore
    @ManyToOne
    private Item item;
}
