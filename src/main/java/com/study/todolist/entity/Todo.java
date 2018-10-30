package com.study.todolist.entity;

import java.time.LocalDate;

public class Todo {

    private int id;
    private LocalDate dueDate;
    private String name;
    private int priority;

    public Todo(int id, String name, LocalDate dueDate, int priority) {
        this.id = id;
        this.dueDate = dueDate;
        this.name = name;
        this.priority = priority;
    }

    public Todo(String name, LocalDate dueDate, int priority) {
        this(0, name, dueDate, priority);
    }

    public int getId() {
        return id;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }
}
