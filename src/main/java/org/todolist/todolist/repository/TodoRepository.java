package org.todolist.todolist.repository;

import org.springframework.stereotype.Repository;
import org.todolist.todolist.entity.Todo;

import javax.sql.DataSource;
import java.sql.*;
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

    public List<Todo> createTodo(Todo todo) throws SQLException {
        String sql = "INSERT INTO todos(title, description, completed, start_datetime, end_datetime) VALUES (?, ?, ?, ?, ?)";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, todo.getTitle());
            ps.setString(2, todo.getDescription());
            ps.setBoolean(3, todo.isCompleted());
            ps.setTimestamp(4, Timestamp.from(todo.getStartDatetime()));
            if (todo.getEndDatetime() != null) {
                ps.setTimestamp(5, Timestamp.from(todo.getEndDatetime()));
            } else {
                ps.setNull(5, Types.TIMESTAMP);
            }

            ps.executeUpdate();

            return findAll();
        }
    }

    public List<Todo> deleteTodo(int id) throws SQLException {
        String sql = "DELETE FROM todos where id= ?";
        try(Connection con = dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1 , id);
            ps.executeUpdate();

            return findAll();
        }
    }

    public Todo updateTodo(Todo todo) throws SQLException {
        String sql = "UPDATE todos " +
                "SET title = ?, description = ?, completed = ?, start_datetime = ?, end_datetime = ? " +
                "WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, todo.getTitle());
            ps.setString(2, todo.getDescription());
            ps.setBoolean(3, todo.isCompleted());
            ps.setTimestamp(4, Timestamp.from(todo.getStartDatetime()));
            if (todo.getEndDatetime() != null) {
                ps.setTimestamp(5, Timestamp.from(todo.getEndDatetime()));
            } else {
                ps.setNull(5, Types.TIMESTAMP);
            }
            ps.setInt(6, todo.getId());

            ps.executeUpdate();

            return todo;
        }
    }

}
