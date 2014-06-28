package de.frontierpsychiatrist.example.oauth;

import de.frontierpsychiatrist.example.oauth.domain.JdbcUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author Moritz Schulze
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return new JdbcUserDetailsService();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/webjars/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/login", "/logout.do").permitAll()
                .antMatchers("/**").authenticated()
            .and()
            .formLogin()
                .loginProcessingUrl("/login.do")
                .usernameParameter("name")
                .loginPage("/login")
            .and()
            .logout()
                //To match GET requests we have to use a request matcher.
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout.do"))
            .and()
            .userDetailsService(userDetailsService());
    }
}
