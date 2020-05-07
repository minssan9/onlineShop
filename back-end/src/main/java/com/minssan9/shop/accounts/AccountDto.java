package com.minssan9.shop.accounts;


import java.util.Objects;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class AccountDto {
    private String accountId;
    private String password;
    private String name;
    private String address;
    private String email;
    private String phone;

	private Long socialId;
	private SocialCode socialCode;
	private Set<AccountRoles> role; 

	public Account toConsumer() {
		role.add(AccountRoles.USER);
		return new Account(name, password, email, phone, socialId, socialCode, role);
	}

	public Account toAccount() {
		return new Account(name, password, email, phone, socialId, socialCode, role);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		AccountDto userDto = (AccountDto) o;
		return Objects.equals(name, userDto.name) && Objects.equals(email, userDto.email)
				&& Objects.equals(phone, userDto.phone);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, email, phone);
	}

	@Override
	public String toString() {
		return "AccountDto{" + "accountId=" + accountId + ", name='" + name + '\'' + ", password='" + password + '\''
				+ ", email='" + email + '\'' + ", phone='" + phone + '\'' + ", socialId=" + socialId
				+ ", socialCode=" + socialCode + ", role=" + role + '}';
	}
 
}
