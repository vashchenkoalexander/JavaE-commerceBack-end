package com.payoya.diplomaproject.configsFV;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    /*
    this method for registry change view controller from first fields name to second fields name
    second name should choose from mappings from your controllers
    */
    registry.addRedirectViewController("hm", "/api/v1/user/all");
    registry.addRedirectViewController("/", "/api/v1/user/all");
  }
}
