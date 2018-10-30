package com.study.todolist.servlets;

import com.study.todolist.dao.TodoDAO;
import com.study.todolist.entity.Todo;
import com.study.todolist.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class AddTaskServlet extends HttpServlet {

    private TodoDAO dao;

    public AddTaskServlet(TodoDAO dao) {
        this.dao = dao;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("addTask.html", null);

        resp.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String name = req.getParameter("name");
        int priority = Integer.parseInt(req.getParameter("priority"));
        LocalDate dueDate = LocalDate.parse(req.getParameter("duedate"));

        dao.add(new Todo(name, dueDate, priority));

        resp.sendRedirect("/todolist");
    }
}
