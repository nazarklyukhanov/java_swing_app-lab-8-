/**
 * The HistoryCommand class represents a command to display the history of the last executed commands.
 * It implements the BaseCommand interface.
 */
package org.example.commands;

import org.example.exceptions.NoCommandException;
import org.example.exceptions.RootException;
import org.example.managers.CommandManager;
import org.example.recources.Request;

import java.io.IOException;

public class HistoryCommand implements BaseCommand {

    /**
     * Executes the history command, displaying the names of the last 13 executed commands.
     * If the input contains more than one argument, it returns "Wrong argument".
     * Otherwise, it retrieves the names of the commands from the CommandManager's history and prints them.
     *
     * @param request the input command line
     * @return an empty string
     * @throws RootException       if a general exception occurs during command execution
     * @throws IOException         if an I/O exception occurs during command execution
     * @throws NoCommandException if the specified command is not found or invalid
     */
    @Override
    public String execute(Request request) throws RootException, IOException, NoCommandException {
        if (request.getMessage().split(" ").length > 1) {
            return "Wrong argument";
        }
        String[] sp = new String[13];
        int n = 0;
        for (BaseCommand command : CommandManager.lastSixCommand) {
            sp[n] = command.getName();
            n += 1;
        }
        for (int i = 0; i < 13; i++) {
            if (!(sp[i] == null)) {
                System.out.println("-" + sp[i]);
            }
        }
        return "";
    }

    /**
     * Gets the name of the command.
     *
     * @return the name of the command ("history")
     */
    @Override
    public String getName() {
        return "history";
    }

    /**
     * Gets the description of the command.
     *
     * @return the description of the command ("вывести последние 13 команд (без их аргументов)")
     */
    @Override
    public String getDescription() {
        return "вывести последние 13 команд (без их аргументов)";
    }
}
