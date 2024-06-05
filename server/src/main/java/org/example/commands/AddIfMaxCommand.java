package org.example.commands;

import org.example.recources.Request;
import org.example.system.Reciever;

public class AddIfMaxCommand implements BaseCommand{

    @Override
    public String execute(Request request) {
        return Reciever.addIfMax(request.getLogin() ,request.getMusicBand());
    }

    @Override
    public String getName() {
        return "add_if_max";
    }

    @Override
    public String getDescription() {
        return "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего " +
                "элемента этой коллекции";
    }
}
