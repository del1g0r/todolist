package com.study.todolist.servlets;

import com.study.todolist.dao.TodoDAO;
import com.study.todolist.entity.Todo;
import com.study.todolist.service.RequestParser;
import com.study.todolist.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class EditTaskServlet extends HttpServlet {

    private TodoDAO dao;

    public EditTaskServlet(TodoDAO dao) {
        this.dao = dao;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        Map<String, Object> params = new HashMap<>();
        params.put("todo", dao.get(RequestParser.getIdfromUri(req.getRequestURI())));
        String page = pageGenerator.getPage("editTask.html", params);
        resp.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        int priority = Integer.parseInt(req.getParameter("priority"));
        LocalDate dueDate = LocalDate.parse(req.getParameter("duedate"));

        Todo item = dao.get(RequestParser.getIdfromUri(req.getRequestURI()));
        dao.update(item.getId(), new Todo(item.getId(), name, dueDate, priority));

        resp.sendRedirect("/todolist");
    }
}
