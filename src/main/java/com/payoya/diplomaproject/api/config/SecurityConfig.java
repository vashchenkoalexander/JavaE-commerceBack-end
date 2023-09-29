package com.payoya.diplomaproject.api.config;

import com.payoya.diplomaproject.api.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    /*
    Remove the ROLE_ prefix in security annotations for hasRole or hasAnyRole
     */
//    @Bean
//    GrantedAuthorityDefaults grantedAuthorityDefaults() {
//        return new GrantedAuthorityDefaults("");
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http.httpBasic().and().cors().and().csrf().disable()
                .requiresChannel(channel -> channel.anyRequest().requiresSecure())
                .userDetailsService(userService)
                .authorizeHttpRequests(auth -> auth.requestMatchers("/api/v1/user/new", "/api/v1/user/newUsers").permitAll().and())
                .authorizeHttpRequests(auth -> auth.requestMatchers(
                        "/api/v1/user/all", "/api/v1/user/{id}" , "/home"
                ).authenticated().and())
                .formLogin().and()
                .authorizeHttpRequests(auth -> auth.requestMatchers("/**").permitAll().and())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
