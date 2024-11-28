package com.example.taskmanager.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan("com.example.taskmanager")
@EnableJpaRepositories(basePackages = "com.example.taskmanager.repository")
public class SpringConfig {

}
