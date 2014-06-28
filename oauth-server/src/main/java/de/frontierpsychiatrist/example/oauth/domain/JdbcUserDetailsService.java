package de.frontierpsychiatrist.example.oauth.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author Moritz Schulze
 */
public class JdbcUserDetailsService implements UserDetailsService {

    @Autowired
    private CredentialsRepository credentialsRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Credentials credentials = credentialsRepository.findByName(username);
        if(credentials == null) {
            throw new UsernameNotFoundException("User " + username + " not found in database.");
        }
        return new User(credentials.getName(), credentials.getPassword(), credentials.isEnabled(), true, true, true, credentials.getAuthorities());
    }
}
