package com.thumbing.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thumbing.auth.filter.JWTAuthenticationFilter;
import com.thumbing.auth.handlers.DefaultAuthenticationFailureHandler;
import com.thumbing.auth.handlers.DefaultAuthenticationSuccessHandler;
import com.thumbing.auth.provider.DefaultAuthenticationProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/14 10:20
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static final String AUTHENTICATION_URL = "/login";

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DefaultAuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private DefaultAuthenticationProvider authenticationProvider;

    @Autowired
    private DefaultAuthenticationFailureHandler authenticationFailureHandler;

    @Value("${security.ignores}")
    private String ignores;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .requestMatchers().anyRequest()
                .and()
                .authorizeRequests()
                //拦截请求路由
                .anyRequest()
                .permitAll()
                .and()
                .addFilterBefore(buildJWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(WebSecurity web) {
        //忽略路由，不进入spring security 拦截链
        //TODO:做成配置
        web.ignoring().antMatchers(ignores.split(";"));
    }

    private JWTAuthenticationFilter buildJWTAuthenticationFilter() {
        JWTAuthenticationFilter filter = new JWTAuthenticationFilter(AUTHENTICATION_URL, authenticationSuccessHandler, authenticationFailureHandler, objectMapper);
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

    /**
     * 将 AuthenticationManager 注册为 bean , 方便配置 oauth server 的时候使用
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
