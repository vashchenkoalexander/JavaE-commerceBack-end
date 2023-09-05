package com.payoya.diplomaproject.api.config;

import com.payoya.diplomaproject.api.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        //http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        return http.csrf().disable()
                .userDetailsService(userService)
                //.authorizeHttpRequests(auth -> auth.requestMatchers("/api/v1/user/all").hasAuthority("USER"))
//                .authorizeHttpRequests()
//                .requestMatchers( "/api/v1/user/all","/api/v1/user/new").permitAll().and()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/user/new").permitAll().and()
                .authorizeHttpRequests(auth -> auth.requestMatchers("/api/v1/user/all").hasAnyAuthority("ADMIN","USER"))
                .authorizeHttpRequests()
                .requestMatchers("/home")
                .authenticated()
                .and().formLogin().and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
