package org.example.commands;

import org.example.exceptions.NoCommandException;
import org.example.exceptions.RootException;
import org.example.exceptions.WrongArgumentException;
import org.example.managers.DataBaseManager;
import org.example.recources.Request;

import java.io.IOException;

public class LoginCommand implements BaseCommand{
    @Override
    public String execute(Request request) throws RootException, IOException, NoCommandException, WrongArgumentException {
        String[] sp = request.getMessage().split(" ");
        if (sp.length == 3){
            if (DataBaseManager.checkUser(request.getLogin(), request.getPassword())){
                return "welcome! " + DataBaseManager.getUserId(request.getLogin());
            } else return "Некорректный логин или пароль";

        } else throw new WrongArgumentException("command argument");
    }

    @Override
    public String getName() {
        return "login";
    }

    @Override
    public String getDescription() {
        return "null";
    }
}
