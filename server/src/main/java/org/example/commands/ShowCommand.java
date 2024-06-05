package org.example.commands;

import org.example.recources.Request;
import org.example.system.Reciever;

public class ShowCommand implements BaseCommand{

    @Override
    public String execute(Request request) {
        return Reciever.show();
    }

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getDescription() {
        return "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }
}
