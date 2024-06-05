package org.example.commands;

import org.example.exceptions.NoCommandException;
import org.example.exceptions.RootException;
import org.example.exceptions.WrongArgumentException;
import org.example.recources.Request;

import java.io.IOException;

public interface BaseCommand {
    String execute(Request request) throws RootException, IOException, NoCommandException, WrongArgumentException;
    String getName();
    String getDescription();
}
