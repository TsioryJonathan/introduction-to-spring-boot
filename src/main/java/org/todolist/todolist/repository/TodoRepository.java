package org.todolist.todolist.repository;

import org.springframework.stereotype.Repository;
import org.todolist.todolist.entity.Todo;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TodoRepository {
    private final DataSource dataSource;

    public TodoRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Todo> findAll() throws SQLException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM todos");
             ResultSet rs = ps.executeQuery()) {

            List<Todo> todos = new ArrayList<>();
            while (rs.next()) {
                todos.add(new Todo(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getBoolean("completed"),
                        rs.getTimestamp("start_datetime").toInstant(),
                        rs.getTimestamp("end_datetime") != null ? rs.getTimestamp("end_datetime").toInstant() : null
                ));
            }
            return todos;
        }
    }
}
