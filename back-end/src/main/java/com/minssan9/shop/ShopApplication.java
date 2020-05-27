package com.minssan9.shop;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.minssan9.shop.accounts.Account;
import com.minssan9.shop.accounts.AccountRoles;
import com.minssan9.shop.accounts.AccountService;
import com.minssan9.shop.chats.Chat;
import com.minssan9.shop.chats.ChatRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@PropertySource(value = {"classpath:account.properties" })
@CrossOrigin(origins = { "http://localhost:8091",  "http://localhost"})
public class ShopApplication {
    @Value("${mail.sender.username}")
    private String mailSenderUsername;
    @Value("${mail.sender.password}")
    private String mailSenderPassword;
    @Value("${user.admin.username}")
    private String userAdminUsername;
    @Value("${user.admin.password}")
    private String userAdminPassword;

    private Logger logger= LoggerFactory.getLogger(ShopApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ShopApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    @Bean
    public JavaMailSender getJavaMailSender() {
        // 구글 계정에서 보안 수준이 낮은 앱의 액세스 설정이 허용하기

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername(mailSenderUsername);
        mailSender.setPassword(mailSenderPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
    @Bean
    public ApplicationRunner applicationRunner() {
        return new ApplicationRunner() {

            @Autowired
            AccountService accountService;

            @Autowired
            ChatRepository chatRepository;
            
            @Override
            public void run(ApplicationArguments args) throws Exception {

                Set<AccountRoles> roles = new HashSet();
                roles.add(AccountRoles.USER);
                roles.add(AccountRoles.ADMIN);

                Account admin = Account.builder()
                        .accountId(userAdminUsername)
                        .email(mailSenderUsername + "@gmail.com")
                        .address("주인장집")
                        .name("관리자")
                        .password(userAdminPassword)
                        .phone("01000000000")
                        .roles(roles)
                        .level(50)
                        .point(7777)
                        .build();

//                Account saveAccount2 = accountService.createAccount(admin);

//                Chat chat2= Chat.builder()
//                        .account(saveAccount2)
//                        .build();

//                chatRepository.save(chat2);
            }
        };
    }
}
