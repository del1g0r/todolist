package com.study.todolist;


import com.study.todolist.dao.TodoDAO;
import com.study.todolist.servlets.AddTaskServlet;
import com.study.todolist.servlets.DeleteTaskServlet;
import com.study.todolist.servlets.TodoListServlet;
import com.study.todolist.servlets.EditTaskServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Starter {
    public static void main(String[] args) throws Exception {
        TodoDAO dao = TodoDAO.INSTANCE;

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        TodoListServlet todoListServlet = new TodoListServlet(dao);
        context.addServlet(new ServletHolder(todoListServlet), "/todolist");

        AddTaskServlet addTaskServlet = new AddTaskServlet(dao);
        context.addServlet(new ServletHolder(addTaskServlet), "/todolist/add");

        DeleteTaskServlet deleteTaskServlet = new DeleteTaskServlet (dao);
        context.addServlet(new ServletHolder(deleteTaskServlet), "/todolist/delete/*");

        EditTaskServlet updateTaskServlet = new EditTaskServlet(dao);
        context.addServlet(new ServletHolder(updateTaskServlet), "/todolist/update/*");

        Server server = new Server(8081);
        server.setHandler(context);

        server.start();
    }
}
