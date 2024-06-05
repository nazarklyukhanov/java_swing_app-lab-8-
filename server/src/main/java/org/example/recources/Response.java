package org.example.recources;

import java.io.Serializable;

public class Response implements Serializable {
    private String message;
    private static final long serialVersionUID = 5760575944040770153L;
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
