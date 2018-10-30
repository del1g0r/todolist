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

public class DeleteTaskServlet extends HttpServlet {

    private TodoDAO dao;

    public DeleteTaskServlet(TodoDAO dao) {
        this.dao = dao;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        dao.delete(RequestParser.getIdfromUri(req.getRequestURI()));
        resp.sendRedirect("/todolist");
    }
}
