package org.example.commands;

import org.example.recources.Request;
import org.example.system.Reciever;

public class UpdateByIDCommand implements BaseCommand {

    @Override
    public String execute(Request request) {
        return Reciever.update_by_id(request.getLogin() ,request.getMessage(), request.getMusicBand());
    }

    @Override
    public String getName() {
        return "update_by_id";
    }

    @Override
    public String getDescription() {
        return "обновить значение элемента коллекции, id которого равен заданному";
    }
}
