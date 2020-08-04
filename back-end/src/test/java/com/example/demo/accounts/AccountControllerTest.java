package com.example.demo.accounts;

import com.minssan9.shop.accounts.Account;
import com.minssan9.shop.accounts.AccountRepository;
import com.minssan9.shop.accounts.AccountRoles;
import com.minssan9.shop.accounts.AccountService;
import com.minssan9.shop.items.ItemFileRepository;
import com.minssan9.shop.items.ItemRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class AccountControllerTest {


    @Before     // 각 테스트 하기전 초기화
    public void reset() {
        this.accountRepository.deleteAll();
    }

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void getAccount() throws Exception {

        createAccount();

        this.mockMvc.perform(get("/api/accounts")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    public Account createAccount() {

        Set<AccountRoles> roles = new HashSet();

        roles.add(AccountRoles.USER);
        roles.add(AccountRoles.ADMIN);

        Account admin = Account.builder()
                .accountId("관리자1")
                .address("관리자@email.com")
                .name("관리자")
                .password("pass")
                .roles(roles)
                .level(50)
                .point(7777)
                .build();

        Account account = accountService.createAccount(admin);
        return account;
    }


    // given
    public String getAccessToken() throws Exception {

        String email = "관리자1";
        String password = "pass";

        String id = "id";
        String secret = "secret";
        ResultActions resultActions = this.mockMvc.perform(post("/oauth/token")
                .with(httpBasic(id, secret))
                .param("username", email)
                .param("password", password)
                .param("grant_type", "password"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token").exists());

        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        Jackson2JsonParser parser = new Jackson2JsonParser();

        return parser.parseMap(contentAsString).get("access_token").toString();
    }



}