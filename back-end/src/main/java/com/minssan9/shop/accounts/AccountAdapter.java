package com.minssan9.shop.accounts;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class AccountAdapter extends User {

    private Account account;

    public AccountAdapter(Account account) {
        super(account.getAccountId(), account.getPassword(), authorities(account.getRoles()));
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    private static Collection<? extends GrantedAuthority> authorities(Set<AccountRoles> roles) {
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        roles.stream().forEach(r -> authorityList.add(new SimpleGrantedAuthority("ROLE_" + r.name())));
        return authorityList;
    }
}
