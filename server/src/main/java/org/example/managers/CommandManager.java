package org.example.managers;

import org.example.commands.*;
import org.example.recources.Request;

import java.util.ArrayDeque;
import java.util.HashMap;

public class CommandManager {
    private static HashMap<String, BaseCommand> commandMap;
    public static ArrayDeque<BaseCommand> lastSixCommand = new ArrayDeque<>();
    public CommandManager(){
        commandMap = new HashMap<>();
        commandMap.put("login", new LoginCommand());
        commandMap.put("reg", new RegisterCommand());
        commandMap.put("help", new HelpCommand());
        commandMap.put("add", new AddCommand());
        commandMap.put("info", new InfoCommand());
        commandMap.put("show", new ShowCommand());
        commandMap.put("save", new SaveCommand());
        commandMap.put("update_by_id", new UpdateByIDCommand());
        commandMap.put("remove_by_id", new RemoveByIDCommand());
        commandMap.put("clear", new ClearCommand());
        commandMap.put("add_if_max", new AddIfMaxCommand());
        commandMap.put("add_if_min", new AddIfMinCommand());
        commandMap.put("history", new HistoryCommand());
        commandMap.put("remove_any_by_label", new RemoveAnyByLabelCommand());
        commandMap.put("filter_contains_name", new FilterContainsNameCommand());
        commandMap.put("print_field_ascending_label", new PrintFieldAscendingLabelCommand());
    }

    public static String startExecuting(Request request) {
        String commandName = request.getMessage().split(" ")[0];
        if (commandMap.containsKey(commandName)){
            try {
                if (!(lastSixCommand == null) && lastSixCommand.size() == 13) {
                    lastSixCommand.pop();
                    lastSixCommand.addLast(commandMap.get(commandName));
                } else {
                    assert lastSixCommand != null;
                    lastSixCommand.addFirst(commandMap.get(commandName));
                }
                return commandMap.get(commandName).execute(request);
            } catch (Exception e){
                return e.getMessage();
            }
        } else{
            return "No command" + request.getMessage();
        }
    }

    public static HashMap<String, BaseCommand> getCommandMap() {
        return commandMap;
    }

}
