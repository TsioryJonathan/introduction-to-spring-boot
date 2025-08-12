package org.todolist.todolist.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.todolist.todolist.entity.Todo;
import org.todolist.todolist.repository.TodoRepository;

import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
public class TodoService {
    private final TodoRepository repository;
    public TodoService(TodoRepository repository) {
        this.repository = repository;
    }

    public List<Todo> listAll() throws SQLException {
        return repository.findAll();
    }
}
