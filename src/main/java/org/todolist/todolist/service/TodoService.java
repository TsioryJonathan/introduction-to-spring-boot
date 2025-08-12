package org.todolist.todolist.service;

import org.springframework.stereotype.Service;
import org.todolist.todolist.entity.Todo;
import org.todolist.todolist.repository.TodoRepository;

import java.sql.SQLException;
import java.util.List;

@Service
public class TodoService {
    private final TodoRepository repository;
    public TodoService(TodoRepository repository) {
        this.repository = repository;
    }

    public List<Todo> listAll(String status) throws SQLException {
        if(status == null || status.isEmpty()) {
            return repository.findAll();
        }
        boolean statusBool = status.equals("done");
        return repository.findAllByCompleted(statusBool);
    }
}
