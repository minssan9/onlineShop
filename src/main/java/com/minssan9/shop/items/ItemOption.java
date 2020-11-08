package com.minssan9.shop.items;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.awt.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class ItemOption {

    @Id
    @GeneratedValue
    private Long id;
    private String options;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;
}
