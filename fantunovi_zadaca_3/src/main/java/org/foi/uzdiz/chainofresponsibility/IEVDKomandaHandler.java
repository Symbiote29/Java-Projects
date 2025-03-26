package org.foi.uzdiz.chainofresponsibility;

public class IEVDKomandaHandler implements HandlerKomandi{

	@Override
	public boolean canHandle(String komanda) {
		return komanda.startsWith("IEVD") && komanda.length() > 4;
	}

	@Override
	public void handle(String komanda, ContextKomandi context) {
	    String[] commandParts = komanda.split(" ");
	    if (commandParts.length < 2 || commandParts[1].trim().isEmpty()) {
	        System.out.println("Greška: Kriva komanda. Očekuje se 'IEVD dani'.");
	        return;
	    }
	    String dani = commandParts[1].trim();

	    System.out.println("Dani za ispis: " + dani);
	    System.out.println("Pozivam metodu ispisVlakovePoDanima...");
	    context.getRaspored().ispisVlakovePoDanima(dani);
	}
}
