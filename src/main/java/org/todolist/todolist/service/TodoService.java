package org.todolist.todolist.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.todolist.todolist.entity.Todo;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class TodoService {
    Todo todo1 = new Todo(1 , "Todo1" , "Test" , false , Instant.now() , Instant.now());
    public List<Todo> getAll(){
        return List.of(
                todo1)
        ;
    }

}
