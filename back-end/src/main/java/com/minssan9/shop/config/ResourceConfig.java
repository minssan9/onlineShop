package com.minssan9.shop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableResourceServer
public class
ResourceConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("shop");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {


        http
                .anonymous()
                .and()
                .authorizeRequests()
                .mvcMatchers(HttpMethod.POST, "/api/accounts/join/check").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/websockethandler/**").permitAll()
                .antMatchers("/app/**").permitAll()
                .mvcMatchers(HttpMethod.GET, "/api/**").permitAll()  // 해당 요청은 누구나 가능하며
                .mvcMatchers(HttpMethod.POST, "/api/accounts").permitAll()
                .mvcMatchers(HttpMethod.POST, "/api/accounts/join/check").permitAll()
                .anyRequest().authenticated()       //  나머지 요청은 권한이 필요합니다.
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new OAuth2AccessDeniedHandler());

        // h2-console
        http
                .csrf()
                .requireCsrfProtectionMatcher(new AntPathRequestMatcher("!/h2-console/**"))
                .and()
                .headers()
                .addHeaderWriter(new StaticHeadersWriter("X-Content-Security-Policy", "script-src 'self'"))
                .frameOptions()
                .disable();
    }
}
