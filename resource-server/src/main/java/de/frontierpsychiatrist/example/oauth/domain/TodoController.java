package de.frontierpsychiatrist.example.oauth.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author Moritz Schulze
 */
@RestController
@RequestMapping(value = "/todos", method = {RequestMethod.GET, RequestMethod.OPTIONS})
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @PostConstruct
    public void insertSomeTodos() throws ParseException {
        Todo todo = new Todo();
        todo.setMessage("Write an oauth example application.");
        todo.setDone(false);
        todoRepository.save(todo);
        Todo todo2 = new Todo();
        todo2.setMessage("Do grocery shopping.");
        todo2.setDone(true);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        todo2.setDoneTime(sdf.parse("2014-06-28 11:23:31"));
        todoRepository.save(todo2);
    }

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
