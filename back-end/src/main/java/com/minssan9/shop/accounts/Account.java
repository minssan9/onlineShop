package com.minssan9.shop.accounts;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.minssan9.shop.carts.Cart;
import com.minssan9.shop.chats.Chat;
import com.minssan9.shop.orders.Order;
import com.minssan9.shop.qnas.QnA;
import com.minssan9.shop.reviews.Review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@ToString(exclude = "chat")
@JsonSerialize(using = AccountSerializer.class)
public class Account {
//    public static final GuestAccount GUEST_USER = new GuestAccount();

    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String accountId;
    private String password;
    private String name;
    private String address;     // email (변경예정)
    private String phone;
    private String email;
    private int level;
    private int point;

    @OneToOne(mappedBy = "account")
    private Chat chat;

    @OneToOne(mappedBy = "account")
    private AccountFile accountFile;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<AccountRoles> roles;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private Set<Cart> carts;

    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL)
    private List<QnA> qnAList;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Order> orderList;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Review> reviewList;

    @Column(unique = true)
    private Long socialId;

    @Column
    @Enumerated(EnumType.STRING)
    private SocialCode socialCode;

//    public AccountDto toAccountDto() {
//        return AccountDto.builder()
//                .accountId(accountId)
//                .name(name)
//                .password(password)
//                .phone(phone)
//                .email(email)
//                .role(roles)
//                .build();
//    }
//    public Account (String name, String password, String email, String phone, Long socialId, SocialCode  socialCode, Set<AccountRoles>  roles){
//        this.name=name;
//        this.password = password;
//        this.email=email;
//        this.phone=phone;
//        this.socialId = socialId;
//        this.socialCode = socialCode;
//        this.roles= roles;
//    }
//
//    @Override
//    public String toString() {
//        return "Account{" + "accountId=" + accountId + ", name='" + name + '\'' + ", password='" + password + '\''
//                + ", email='" + email + '\'' + ", phone='" + phone + '\'' + ", socialId=" + socialId
//                + ", socialCode=" + socialCode + ", role=" + roles + '}';
//    }
//


//    public boolean isGuestUser() {
//        return false;
//    }
//	private static class GuestAccount extends Account {
//        @Override
//        public boolean isGuestUser() {
//            return true;
//        }
//    }
//
//    public boolean matchPassword(String inputPassword, PasswordEncoder passwordEncoder) {
//        return passwordEncoder.matches(inputPassword, password);
//    }

}
