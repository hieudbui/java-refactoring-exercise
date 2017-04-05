package com.h2rd.refactoring;

import com.h2rd.refactoring.usermanagement.dao.MemoryBackedUserDao;
import com.h2rd.refactoring.usermanagement.dao.UserDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.h2rd.refactoring.usermanagement"})
public class ApplicationConfiguration {
    @Bean
    Object testService() {
        return new Object();
    }

    @Bean
    UserDao testUserDao() {
        return new MemoryBackedUserDao();
    }
}
