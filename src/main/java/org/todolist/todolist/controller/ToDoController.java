package org.todolist.todolist.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.todolist.todolist.entity.Todo;
import org.todolist.todolist.repository.TodoRepository;
import org.todolist.todolist.service.TodoService;

import java.sql.SQLException;
import java.util.List;

@RestController
public class ToDoController {
    private final TodoService todoService;

    public ToDoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @GetMapping("/todos")
    public List<Todo> getAllTodos(@RequestParam(required = false) String status) throws SQLException {
        return todoService.listAll(status);
    }
}
