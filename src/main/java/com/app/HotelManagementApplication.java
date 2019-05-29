package com.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@SpringBootApplication
@EnableJpaRepositories(basePackages="com.app.repository")
@Configuration
public class HotelManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelManagementApplication.class, args);
    }

    @Bean
    public ClassLoaderTemplateResolver yourTemplateResolver() {
        ClassLoaderTemplateResolver yourTemplateResolver = new ClassLoaderTemplateResolver();
        yourTemplateResolver.setPrefix("templates/");
        yourTemplateResolver.setSuffix(".html");
        yourTemplateResolver.setTemplateMode(TemplateMode.HTML);
        yourTemplateResolver.setCharacterEncoding("UTF-8");
        yourTemplateResolver.setOrder(0);  // this is iportant. This way spring
        //boot will listen to both places 0
        //and 1

        return yourTemplateResolver;
    }

}