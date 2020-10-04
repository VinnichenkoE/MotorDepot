package com.vinnichenko.motorDepot.controller.command;

import com.vinnichenko.motorDepot.controller.command.impl.*;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {
    private static final CommandProvider INSTANCE = new CommandProvider();

    private final Map<CommandName, Command> repository = new HashMap<>();

    private CommandProvider() {
        repository.put(CommandName.WELCOME_PAGE, new WelcomePage());
        repository.put(CommandName.WRONG_COMMAND, new WrongCommand());
        repository.put(CommandName.SAVE_USER, new SaveUser());
        repository.put(CommandName.REGISTRATION, new Registration());
        repository.put(CommandName.AUTHORIZATION, new Authorization());
    }

    public static CommandProvider getInstance() {
        return INSTANCE;
    }

    public Command getCommand(String commandName) {
        Command command;
        try {
            command = repository
                    .get(CommandName.valueOf(commandName.toUpperCase()));
        } catch (IllegalArgumentException | NullPointerException e) {
            command = repository.get(CommandName.WRONG_COMMAND);
        }
        return command;
    }
}
