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
        repository.put(CommandName.LOGOUT, new Logout());
        repository.put(CommandName.ACCOUNT, new Account());
        repository.put(CommandName.APPOINT_DRIVER, new AppointDriver());
        repository.put(CommandName.ADD_BID, new AddBid());
        repository.put(CommandName.SAVE_BID, new SaveBid());
        repository.put(CommandName.APPOINT_BID, new AppointBid());
        repository.put(CommandName.VIEW_BIDS, new ViewBids());
        repository.put(CommandName.VIEW_VEHICLES, new ViewVehicles());
        repository.put(CommandName.VIEW_DRIVERS, new ViewDrivers());
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
