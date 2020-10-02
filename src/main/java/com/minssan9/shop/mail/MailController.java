package com.minssan9.shop.mail;

import com.minssan9.shop.ShopApplication;
import com.minssan9.shop.accounts.Account;
import com.minssan9.shop.accounts.AccountRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mails")
public class MailController {

    private Logger logger= LoggerFactory.getLogger(ShopApplication.class);

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    AccountRepository accountRepository;

    @PostMapping
    public ResponseEntity sendMail(@RequestBody Mail mail){

        logger.info("메일보내기:"+mail);

        SimpleMailMessage msg=new SimpleMailMessage();

        List<Account> accounts = accountRepository.findAll();

        for(Account account:accounts)
        {
            logger.info(account.getEmail()+"에게 메일 전송");

            msg.setFrom("aa@aa.com");       // 계정설정
            msg.setTo(account.getEmail());
            msg.setSubject(mail.getTitle());
            msg.setText(mail.getContent());

            mailSender.send(msg);
        }

        logger.info("메일을 "+accounts.size()+"의 회원에게 전송 완료:");
        return new ResponseEntity(HttpStatus.OK);
    }
}
