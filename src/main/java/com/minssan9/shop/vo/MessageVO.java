package com.minssan9.shop.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;


@ToString
@Getter
public class MessageVO {

    @JsonProperty
    private String senderName;
    @JsonProperty
    private String sendMessage;
    @JsonProperty
    private String accountId;


}
