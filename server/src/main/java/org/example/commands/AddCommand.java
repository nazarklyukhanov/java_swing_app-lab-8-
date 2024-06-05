package org.example.commands;

import org.example.recources.Request;
import org.example.system.Reciever;

public class AddCommand implements BaseCommand{
    @Override
    public String execute(Request request) {
        return Reciever.add(request.getMusicBand(), request.getLogin());
    }

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getDescription() {
        return "добавить новый элемент в коллекцию";
    }
}
