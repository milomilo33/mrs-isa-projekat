package com.mrsisa.mrsisaprojekat.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:8081")
//                .allowedOrigins("https://mrs-isa-projekat-frontend.herokuapp.com/")
//                .allowedMethods("GET","PUT","POST","DELETE","OPTIONS");
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8081", "https://mrs-isa-projekat-frontend.herokuapp.com")
                .allowedMethods("GET","PUT","POST","DELETE","OPTIONS");
    }
}