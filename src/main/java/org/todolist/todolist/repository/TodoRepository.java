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
import java.util.Optional;

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

    public List<Todo> findAllByCompleted(boolean completed) throws SQLException {
        String sql = "SELECT * FROM todos WHERE completed = ? ORDER BY id DESC";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, completed);
            try (ResultSet rs = ps.executeQuery()) {
                List<Todo> out = new ArrayList<>();
                while (rs.next()) {
                    out.add(new Todo(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getBoolean("completed"),
                            rs.getTimestamp("start_datetime").toInstant(),
                            rs.getTimestamp("end_datetime") == null ? null : rs.getTimestamp("end_datetime").toInstant()
                    ));
                }
                return out;
            }
        }
}
}
