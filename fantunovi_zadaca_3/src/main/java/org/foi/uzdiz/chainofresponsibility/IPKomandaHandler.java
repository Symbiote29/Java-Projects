package org.foi.uzdiz.chainofresponsibility;

public class IPKomandaHandler implements HandlerKomandi{
	@Override
    public boolean canHandle(String command) {
		return command.equalsIgnoreCase("IP");
    }

	 @Override
    public void handle(String command, ContextKomandi context) {
        System.out.println("Izvršava se komanda IP: ispis stanica");
        context.getUcitavanje().ispisStanica(null);
    }
}
