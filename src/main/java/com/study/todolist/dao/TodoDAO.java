package com.study.todolist.dao;

import com.study.todolist.entity.Todo;

import javax.servlet.ServletException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TodoDAO {

    private static final String READ_ALL_SQL = "SELECT id, name, due_date, priority FROM todo_list";
    private static final String CREATE_SQL = "INSERT INTO todo_list(name, due_date, priority) VALUES (?, ?, ?)";
    private static final String READ_SQL = "SELECT id, name, due_date, priority FROM todo_list WHERE id = ?";
    private static final String UPDATE_SQL = "UPDATE todo_list SET name=?, due_date=?, priority=? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM todo_list WHERE id = ?";

    public static final TodoDAO INSTANCE = new TodoDAO();

    public void add(Todo item) throws ServletException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_SQL);) {
            statement.setString(1, item.getName());
            statement.setDate(2, Date.valueOf(item.getDueDate()));
            statement.setInt(3, item.getPriority());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    public void update(Todo item) throws ServletException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SQL);) {
            statement.setString(1, item.getName());
            statement.setDate(2, Date.valueOf(item.getDueDate()));
            statement.setInt(3, item.getPriority());
            statement.setInt(4, item.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    public void delete(int id) throws ServletException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private PreparedStatement prepareGetStatement(Connection connection, int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(READ_SQL);
        statement.setInt(1, id);
        return statement;
    }

    public Todo get(int id) throws ServletException {
        try (Connection connection = getConnection();
             PreparedStatement statement = prepareGetStatement(connection, id);
             ResultSet resultSet = statement.executeQuery();
        ) {
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                LocalDate dueDate = resultSet.getDate("due_date").toLocalDate();
                int priority = resultSet.getInt("priority");
                return new Todo(id, name, dueDate, priority);
            }
            return null;
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    public List<Todo> getAll() throws ServletException {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(READ_ALL_SQL)) {
            List<Todo> items = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                LocalDate dueDate = resultSet.getDate("due_date").toLocalDate();
                int priority = resultSet.getInt("priority");
                Todo todo = new Todo(id, name, dueDate, priority);
                items.add(todo);
            }
            return items;
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }


    private Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/todo";
        Connection connection = DriverManager.getConnection(url, "postgres", "123456");
        return connection;
    }
}
