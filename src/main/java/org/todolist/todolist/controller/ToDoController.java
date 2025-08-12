package org.todolist.todolist.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.todolist.todolist.entity.Todo;
import org.todolist.todolist.service.TodoService;

import java.time.Instant;
import java.util.List;

@RestController
public class ToDoController {
    private final TodoService todoService = new TodoService();

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @GetMapping("/todos")
    public List<Todo> getAllTodos() {
        return todoService.getAll();
    }
}
