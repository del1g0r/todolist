package com.study.todolist.servlets;

import com.study.todolist.dao.TodoDAO;
import com.study.todolist.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class TodoListServlet extends HttpServlet {

    private TodoDAO dao;

    public TodoListServlet(TodoDAO dao) {
        this.dao = dao;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        Map<String, Object> params = new HashMap<>();
        params.put("todoList", dao.getAll());
        String page = pageGenerator.getPage("todoList.html", params);
        System.out.println("sending page: " + page);
        resp.getWriter().write(page);
    }
}
