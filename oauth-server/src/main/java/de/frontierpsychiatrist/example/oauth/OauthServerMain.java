package de.frontierpsychiatrist.example.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Moritz Schulze
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class OauthServerMain {

    public static void main(String[] args) {
        SpringApplication.run(OauthServerMain.class, args);
    }

}
