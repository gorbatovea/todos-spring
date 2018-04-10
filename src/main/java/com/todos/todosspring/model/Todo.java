package com.todos.todosspring.model;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String userId;
    private  String task;
    private boolean done;

    public Todo(){}

    public Todo(String task, boolean done, String userId) {
        this.task = task;
        this.done = done;
        this.userId = userId;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTask() {
        return task;
    }

    public boolean isDone() {
        return done;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
