package de.frontierpsychiatrist.example.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Moritz Schulze
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
public class ResourceServerMain {

    public static void main(String[] args) {
        SpringApplication.run(ResourceServerMain.class, args);
    }

}
