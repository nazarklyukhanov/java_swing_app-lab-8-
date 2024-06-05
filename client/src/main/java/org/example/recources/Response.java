package org.example.recources;

import java.io.Serializable;

public class Response implements Serializable {
    private static final long serialVersionUID = 5760575944040770153L;
    private String message;

    public Response(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
