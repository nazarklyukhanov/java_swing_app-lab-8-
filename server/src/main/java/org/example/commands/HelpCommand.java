package org.example.commands;

import org.example.recources.Request;
import org.example.system.Reciever;

public class HelpCommand implements BaseCommand{
    @Override
    public String execute(Request request) {
        return Reciever.help();
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "вывести справку по доступным командам";
    }
}
