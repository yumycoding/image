package com.yumy.image;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
public class ImageApplication {


    public static void main(String[] args) {
        SpringApplication.run(ImageApplication.class, args);

    }


    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory configFactory = new MultipartConfigFactory();
        configFactory.setLocation("/home/temp");
        return configFactory.createMultipartConfig();
    }

}
