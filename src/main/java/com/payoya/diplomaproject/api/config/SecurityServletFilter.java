package com.payoya.diplomaproject.api.config;

import com.payoya.diplomaproject.api.entity.User;
import com.payoya.diplomaproject.api.repository.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Objects;

public class SecurityServletFilter extends HttpFilter {


    private IUserRepository userRepository;

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Logger.class);

    public SecurityServletFilter(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;


        log.info("request of this method is {}", request);
        log.info("method of this request is {}", httpServletRequest.getMethod());
        log.info("Requested URI of this method {}", httpServletRequest.getRequestURI());

        if(httpServletRequest.getMethod().equals("GET") && httpServletRequest.getRequestURI().equals("/api/v1/user/all")){

            //Getting authentication after successful login of user from SecurityContext
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName(); // This retrieves the username after authentication

            log.info("username is {} ", username);

            User activationToken = userRepository.findUserByUsername(username).orElse(null);
            log.info("activationToken = {}", activationToken);

            if(activationToken != null){
                ResponseEntity<String> responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("{\"message\": \"Before enter with your credentials please activate your mail address.\"}");
                response.getWriter().write(Objects.requireNonNull(responseEntity.getBody()));
                response.getWriter().flush();
                return;
            }
        }

        chain.doFilter(request, response);

    }
}
