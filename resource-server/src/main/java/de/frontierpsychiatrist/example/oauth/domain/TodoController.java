package de.frontierpsychiatrist.example.oauth.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Moritz Schulze
 */
@RestController
@RequestMapping(value = "/todos", method = {RequestMethod.GET})
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @RequestMapping
    public List<Todo> todos() {
        return todoRepository.findAll();
    }

    @RequestMapping(value = "/{id}")
    public Todo oneTodo(@PathVariable("id") Long id) {
        return todoRepository.findOne(id);
    }

    @RequestMapping(value = "/search/findByMessageLike")
    public List<Todo> findByMessageLike(@RequestParam("message") String message) {
        return todoRepository.findByMessageLike("%" + message + "%");
    }
}
