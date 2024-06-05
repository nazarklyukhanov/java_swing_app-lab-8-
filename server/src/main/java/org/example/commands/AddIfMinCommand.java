package org.example.commands;

import org.example.recources.Request;
import org.example.system.Reciever;

public class AddIfMinCommand implements BaseCommand{
    @Override
    public String execute(Request request) {
        return Reciever.addIfMin(request.getMusicBand());
    }

    @Override
    public String getName() {
        return "add_if_min";
    }

    @Override
    public String getDescription() {
        return "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции";
    }
}
