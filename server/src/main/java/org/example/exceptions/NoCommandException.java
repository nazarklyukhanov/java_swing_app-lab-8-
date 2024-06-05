package org.example.exceptions;

public class NoCommandException extends Exception{
    public NoCommandException(String name){
        super("No command " + name);
    }
}
