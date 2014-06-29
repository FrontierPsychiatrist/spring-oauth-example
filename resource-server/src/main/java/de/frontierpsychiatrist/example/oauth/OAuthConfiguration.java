package de.frontierpsychiatrist.example.oauth;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

/**
 * @author Moritz Schulze
 */
@Configuration
@EnableResourceServer
public class OAuthConfiguration extends ResourceServerConfigurerAdapter {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource_oauth")
    public DataSource oauthDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        TokenStore tokenStore = new JdbcTokenStore(oauthDataSource());
        resources.resourceId("oauth-services")
                .tokenStore(tokenStore);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/**").access("#oauth2.hasScope('read')");
    }
}
