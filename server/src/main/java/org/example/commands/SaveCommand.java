package org.example.commands;

import org.example.recources.Request;
import org.example.system.Reciever;

public class SaveCommand implements BaseCommand{

    @Override
    public String execute(Request request) {
        try {
            return Reciever.save();
        } catch (Exception e){
            System.out.println(e.getMessage());
            return "Error";
        }
    }

    @Override
    public String getName() {
        return "save";
    }

    @Override
    public String getDescription() {
        return "сохранить коллекцию в файл";
    }
}
