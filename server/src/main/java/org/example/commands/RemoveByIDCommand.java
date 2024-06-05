package org.example.commands;

import org.example.recources.Request;
import org.example.system.Reciever;

public class RemoveByIDCommand implements BaseCommand{

    @Override
    public String execute(Request request) {
        return Reciever.remove_by_id(request.getLogin(),request.getMessage());
    }

    @Override
    public String getName() {
        return "remove_by_id";
    }

    @Override
    public String getDescription() {
        return "удалить элемент из коллекции по его id";
    }
}
