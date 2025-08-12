package org.todolist.todolist.entity;

import lombok.*;

import java.time.Instant;


@Getter
@AllArgsConstructor
@Setter
@ToString
@EqualsAndHashCode
public class Todo {
    private int id;
    private String title;
    private String description;
    private boolean completed;
    private Instant startDatetime;
    private Instant endDatetime;
}
