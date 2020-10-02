package com.minssan9.shop.accounts;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AccountDto {

    private String accountId;
    private String password;
    private String name;
    private String address;
    private String email;
    private String phone;
}
