package com.openclassrooms.pay_my_buddy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    @Autowired
    AppConfig appConfig;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers(
                                        new AntPathRequestMatcher("/css/**", "GET"),
                                        new AntPathRequestMatcher("/img/**", "GET"),
                                        new AntPathRequestMatcher("/js/**", "GET"),
                                        new AntPathRequestMatcher("/users/mine/connections")
                                        )
                                .permitAll()
                )
                .authorizeHttpRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .permitAll()
                .and()
                .rememberMe()
                .tokenValiditySeconds(appConfig.getNbDaysRememberMe() * 24 * 60 * 60);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
