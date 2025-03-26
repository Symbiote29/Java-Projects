package org.foi.uzdiz.commandVracanjeKarte;

public class CommandInvoker {
	private Command command;
	
	public void setCommand(Command command) {
        this.command = command;
    }

    public void executeCommand() {
        if (command != null) {
            command.execute();
        } else {
            System.out.println("Nema postavljene komande za izvršavanje.");
        }
    }
}
