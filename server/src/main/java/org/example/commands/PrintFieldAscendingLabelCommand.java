/**
 * The PrintFieldAscendingLabelCommand class represents a command to print the values of the "label" field of all elements in ascending order.
 * It implements the BaseCommand interface.
 */
package org.example.commands;

import org.example.exceptions.NoCommandException;
import org.example.exceptions.RootException;
import org.example.recources.Request;
import org.example.system.Reciever;

import java.io.IOException;

public class PrintFieldAscendingLabelCommand implements BaseCommand {

    /**
     * Executes the print_field_ascending_label command, printing the values of the "label" field of all elements in ascending order.
     * If the input contains more than one argument, it returns "Wrong argument".
     * Otherwise, it calls the printFieldAscendingLabel method of the Reciever class to perform the printing.
     *
     * @param request the input command line
     * @return the values of the "label" field of all elements in ascending order
     * @throws RootException       if a general exception occurs during command execution
     * @throws IOException         if an I/O exception occurs during command execution
     * @throws NoCommandException if the specified command is not found or invalid
     */
    @Override
    public String execute(Request request) throws RootException, IOException, NoCommandException {
        if (request.getMessage().split(" ").length > 1) {
            return "Wrong argument";
        }
        return Reciever.printFieldAscendingLabel();
    }

    /**
     * Gets the name of the command.
     *
     * @return the name of the command ("print_field_ascending_label")
     */
    @Override
    public String getName() {
        return "print_field_ascending_label";
    }

    /**
     * Gets the description of the command.
     *
     * @return the description of the command ("вывести значения поля label всех элементов в порядке возрастания")
     */
    @Override
    public String getDescription() {
        return "вывести значения поля label всех элементов в порядке возрастания";
    }
}
