package com.minssan9.shop.reviews;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@JsonSerialize(using = ReviewFileSerializer.class)
public class ReviewFile {

    @Id
    @GeneratedValue
    private Long id;
    private String fileName;
    private String uploadPath;
    private String uuid;


    @OneToOne
    private Review review;


}
