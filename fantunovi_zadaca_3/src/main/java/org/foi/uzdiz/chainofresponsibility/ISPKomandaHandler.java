package org.foi.uzdiz.chainofresponsibility;

public class ISPKomandaHandler implements HandlerKomandi{
	@Override
	    public boolean canHandle(String command) {
	        return command.startsWith("ISP ");
    }

    @Override
    public void handle(String command, ContextKomandi context) {
        String[] parts = command.split(" ", 3);
        if (parts.length == 3) {
            String oznakaPruge = parts[1];
            String redoslijed = parts[2];
            System.out.println("Ispis stanica za prugu: " + oznakaPruge + ", redoslijed: " + redoslijed);
            context.getPrugaManager().ispisiStaniceZaPrugu(oznakaPruge, redoslijed);
        } else {
            System.out.println("Neispravna sintaksa. Koristite: ISP oznakaPruge redoslijed");
        }
    }
}
