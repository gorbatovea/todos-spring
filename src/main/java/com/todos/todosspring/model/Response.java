package com.todos.todosspring.model;

public class Response {
    private final boolean successfully;
    private final String status;

    public Response(boolean successfully, String status) {
        this.successfully = successfully;
        this.status = status;
    }

    public boolean isSuccessfully() {
        return successfully;
    }

    public String getStatus() {
        return status;
    }
}
