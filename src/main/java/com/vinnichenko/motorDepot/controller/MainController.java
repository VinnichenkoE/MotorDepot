package com.vinnichenko.motorDepot.controller;

import com.vinnichenko.motorDepot.connection.ConnectionPool;
import com.vinnichenko.motorDepot.controller.command.Command;
import com.vinnichenko.motorDepot.controller.command.CommandProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.vinnichenko.motorDepot.controller.command.RequestParameter.COMMAND_NAME;

public class MainController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        processRequest(req, resp);
    }

    @Override
    public void destroy() {
        ConnectionPool.getInstance().destroyPool();
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String commandName = req.getParameter(COMMAND_NAME);
        Command command = CommandProvider.getInstance().getCommand(commandName);
        command.execute(req, resp);
    }
}
