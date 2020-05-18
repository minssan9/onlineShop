package com.minssan9.shop.accounts;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minssan9.shop.web.support.HtmlFormDataBuilder;
import com.minssan9.shop.web.support.HttpSessionUtils;
 

@Controller
@RequestMapping("/api/kakao")
public class KakaoController {

    private static final Logger log = LoggerFactory.getLogger(KakaoController.class);

    @Value("${kakao.client.id}")
    private String CLIENT_ID;

    @Value("${kakao.redirect.uri}")
    private String REDIRECT_URI;

    @Value("${kakao.token}")
    private String TOKEN_URI;

    @Value("${kakao.user.info}")
    private String USER_INFO_URI;

    @Autowired
    private ObjectMapper mapper;
    
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AccountService accountService;

    @GetMapping("/oauth")
    public String kakaoLogin(String code, Model model, HttpSession session) throws Exception {
        log.debug("code : {}", code);
        KakaoDto response = getAccessToken(code);
        JsonNode userInfo = getUserInfo(response.getAccess_token());
        Optional<Account> maybeAccount = accountService.loginKakao(userInfo.get("id").asLong());
        log.debug("UserInfoId : {}", userInfo.get("id"));
        log.debug("KakaoOauthDto : {}", response);
        if (!maybeAccount.isPresent()) {
            AccountDto userDto = AccountDto.builder()
                    .socialId(userInfo.get("id").asLong())
                    .socialCode(SocialCode.KAKAO)
                    .name(userInfo.get("properties").get("nickname").asText())
                    .build();
            maybeAccount = Optional.of(accountService.socialRegister(userDto));
        }
        session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, maybeAccount.get());
        return "redirect:/";
    }

    private KakaoDto getAccessToken(String code) {
    	
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodeFormJSON()
                .addParameter("grant_type", "authorization_code")
                .addParameter("client_id", CLIENT_ID)
                .addParameter("redirect_uri", REDIRECT_URI)
                .addParameter("code", code)
                .build();

        KakaoDto response = restTemplate.postForObject(TOKEN_URI, request, KakaoDto.class);
        return response;
    }

    private JsonNode getUserInfo(String accessToken) throws IOException {
    	
        log.debug("accessToken : {}", accessToken);
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodeFormJSON()
                .setHeader("Authorization", "Bearer " + accessToken)
                .build();

        String response = restTemplate.postForObject(USER_INFO_URI, request, String.class);
        log.debug("request : {}", request);
        log.debug("response : {}", response);

        JsonNode userInfo = mapper.readTree(response);
        log.debug("UserInfo : {}", userInfo);
        return userInfo;
    }
}