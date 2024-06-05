package org.example.commands;

import org.example.recources.Request;
import org.example.system.Reciever;

public class InfoCommand implements BaseCommand{

    @Override
    public String execute(Request request) {
        return Reciever.info();
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, " +
                "количество элементов и т.д.)";
    }
}
