//package com.minssan9.shop.carts;
//
//
//import com.minssan9.shop.accounts.Account;
//import com.minssan9.shop.accounts.AccountRepository;
//import com.minssan9.shop.accounts.AccountRoles;
//import com.minssan9.shop.accounts.AccountService;
//import com.minssan9.shop.items.Item;
//import com.minssan9.shop.items.ItemFile;
//import com.minssan9.shop.items.ItemFileRepository;
//import com.minssan9.shop.items.ItemRepository;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//
//import java.time.LocalDateTime;
//import java.util.HashSet;
//import java.util.Set;
//
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
//@SpringBootTest
//@RunWith(SpringRunner.class)
//@AutoConfigureMockMvc
//public class CartControllerTest {
//
//
//    @Before     // 각 테스트 하기전 초기화
//    public void reset() {
//        this.accountRepository.deleteAll();
//        this.itemRepository.deleteAll();
//        this.itemFileRepository.deleteAll();
//    }
//
//    @Autowired
//    MockMvc mockMvc;
//
//    @Autowired
//    AccountService accountService;
//
//    @Autowired
//    AccountRepository accountRepository;
//
//    @Autowired
//    ItemRepository itemRepository;
//
//    @Autowired
//    ItemFileRepository itemFileRepository;
//
//
//
//    @Test
//    public void createCart() throws Exception {
//
//        // Given
//        createAccount();
//        Item item = createItem();
//
//
//        this.mockMvc.perform(post("/api/carts/{itemId}", item.getId())
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("item.id").value(item.getId()));
//    }
//
//    @Test
//    public void getCarts() throws Exception {
//
//        // Given
//        createAccount();
//
//        this.mockMvc.perform(get("/api/carts")
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//
//
//    public Account createAccount() {
//
//        Set<AccountRoles> roles = new HashSet();
//
//        roles.add(AccountRoles.USER);
//        roles.add(AccountRoles.ADMIN);
//
//        Account admin = Account.builder()
//                .accountId("관리자1")
//                .address("관리자@email.com")
//                .name("관리자")
//                .password("pass")
//                .roles(roles)
//                .level(50)
//                .point(7777)
//                .build();
//
//        Account account = accountService.createAccount(admin);
//        return account;
//    }
//
//    // given
//    public String getAccessToken() throws Exception {
//
//        String email = "관리자1";
//        String password = "pass";
//
//        String id = "id";
//        String secret = "secret";
//        ResultActions resultActions = this.mockMvc.perform(post("/oauth/token")
//                .with(httpBasic(id, secret))
//                .param("username", email)
//                .param("password", password)
//                .param("grant_type", "password"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("access_token").exists());
//
//        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
//        Jackson2JsonParser parser = new Jackson2JsonParser();
//
//        return parser.parseMap(contentAsString).get("access_token").toString();
//    }
//
//    public Item createItem() {
//
//        Item item = Item.builder()
//                .content("내용")
//                .deliveryDate(1)
//                .discount(10000)
//                .itemCreated(LocalDateTime.now())
//                .price(300000)
//                .savings(0)
//                .title("제목")
//                .build();
//
//        Item save = itemRepository.save(item);
//
//
//        ItemFile itemFile = ItemFile.builder()
//                .fileName("1.png")
//                .uploadPath("C:\\Users\\kim\\git\\Market\\project\\src\\main\\resources\\templates\\uploads")
//                .uuid("130a484e-9bfe-4293-8c04-d4b89f053882")
//                .item(save)
//                .build();
//
//        itemFileRepository.save(itemFile);
//
//        return save;
//    }
//
//
//
//}