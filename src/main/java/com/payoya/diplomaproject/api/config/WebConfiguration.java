package com.payoya.diplomaproject.api.config;

import com.payoya.diplomaproject.api.repository.IUserRepository;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfiguration {

    private IUserRepository userRepository;

    public WebConfiguration(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public FilterRegistrationBean<SecurityServletFilter> activationFilter(){
        FilterRegistrationBean<SecurityServletFilter> activationToken = new FilterRegistrationBean<>();

        activationToken.setFilter(new SecurityServletFilter(userRepository));
        activationToken.addUrlPatterns("/login/*", "/api/v1/*");
        return activationToken;
    }

}
