package com.minssan9.shop.accounts;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.minssan9.shop.exception.UnAuthenticationException;

@Service
public class AccountService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Account createAccount(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Account account = accountRepository.findByAccountId(userName).orElseThrow(() -> new UsernameNotFoundException(userName));
        return new AccountAdapter(account);
    }
 
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    public Account register(AccountDto signUpAccount) {
        String encodedPassword = bCryptPasswordEncoder.encode(signUpAccount.getPassword());
        return accountRepository.save(signUpAccount.builder().password(encodedPassword).build().toConsumer());
    }

    public Account socialRegister(AccountDto signUpAccount) {
        return accountRepository.save(signUpAccount.toConsumer());
    }

    public Account findById(Long id) {
        return accountRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Optional<Account> loginKakao(Long id) {
        return accountRepository.findBySocialIdAndSocialCode(id, SocialCode.KAKAO);
    }

    public Account login(String username, String password) throws UnAuthenticationException {
        return accountRepository.findByAccountId(username)
                .filter(account -> account.matchPassword(password, bCryptPasswordEncoder))
                .orElseThrow(() -> new UnAuthenticationException("비밀번호가 올바르지 않습니다."));
    }

    public List<AccountDto> findAll() {
        return accountRepository.findAll().stream().map(a -> a.toAccountDto()).collect(Collectors.toList());
    }

    public List<Account> findAllUsers() {
        return accountRepository.findAll();
    }


}
