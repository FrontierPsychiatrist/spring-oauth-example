package de.frontierpsychiatrist.example.oauth.domain;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Moritz Schulze
 */
public interface CredentialsRepository extends JpaRepository<Credentials, Long> {

    Credentials findByName(String name);

}
