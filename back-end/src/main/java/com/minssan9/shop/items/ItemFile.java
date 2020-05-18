package com.minssan9.shop.items;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@JsonSerialize(using = ItemFileSerializer.class)
public class ItemFile {

    @Id
    @GeneratedValue
    private Long id;
    private String fileName;
    private String uploadPath;
    private String uuid;

    @ManyToOne
    private Item item;
}
