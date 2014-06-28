package de.frontierpsychiatrist.example.oauth.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Moritz Schulze
 */
public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findByMessageLike(String message);

}
