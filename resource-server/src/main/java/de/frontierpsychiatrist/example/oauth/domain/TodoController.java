package de.frontierpsychiatrist.example.oauth.domain;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Moritz Schulze
 */
@RestController
@RequestMapping(value = "/todos")
public class TodoController {

    private final TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping
    public List<Todo> todos() {
        return todoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Todo oneTodo(@PathVariable("id") Long id) {
        return todoRepository.findOne(id);
    }

    @GetMapping("/search/findByMessageLike")
    public List<Todo> findByMessageLike(@RequestParam("message") String message) {
        return todoRepository.findByMessageLike("%" + message + "%");
    }
}
