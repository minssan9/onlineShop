package com.example.demo.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.minssan9.shop.accounts.Account;
import com.minssan9.shop.accounts.AccountRoles;
import com.minssan9.shop.accounts.AccountService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthConfigTest {

    @Autowired
    AccountService accountService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void getAccessToken() throws Exception {

        // Given

        String userId = "관리자";
        String password = "pass";
        String email = "kimnoin7@email.com";
        String name = "kimnoin";

        Account account = Account.builder()
                .accountId(userId)
                .password(password)
                .address(email)
                .name(name)
                .roles(new HashSet<>(Arrays.asList(AccountRoles.ADMIN,AccountRoles.USER)))
                .build();

        accountService.createAccount(account);

        String clientId = "id";
        String clientSecret = "secret";

        this.mockMvc.perform(post("/oauth/token")
                .with(httpBasic(clientId, clientSecret))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("username", userId)
                .param("password", password)
                .param("grant_type", "password"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token").exists());

    }
}