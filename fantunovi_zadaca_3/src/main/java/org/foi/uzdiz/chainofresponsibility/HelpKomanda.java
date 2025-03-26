package org.foi.uzdiz.chainofresponsibility;

public class HelpKomanda implements HandlerKomandi{
	@Override
    public boolean canHandle(String command) {
		return command.equalsIgnoreCase("HELP");
	}

	@Override
	public void handle(String command, ContextKomandi context) {
		System.out.println("Dostupne komande: help, q, IP, ISP oznakaPruge redoslijed, ISI2S polaznaStanica - odredisnaStanica, IK oznaka pruge");
	}
}
