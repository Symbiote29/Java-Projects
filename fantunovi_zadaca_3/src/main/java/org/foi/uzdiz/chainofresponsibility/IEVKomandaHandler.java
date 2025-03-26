package org.foi.uzdiz.chainofresponsibility;

public class IEVKomandaHandler implements HandlerKomandi{

	@Override
	public boolean canHandle(String komanda) {
		return komanda.startsWith("IEV") && !komanda.startsWith("IEVD");
	}

	@Override
	public void handle(String komanda, ContextKomandi context) {
		String[] commandParts = komanda.split(" ");
        if (commandParts.length < 2 || commandParts[1].trim().isEmpty()) {
            System.out.println("Greška: Kriva komanda. Očekuje se 'IEV oznakaVlaka'.");
            return;
        }
        String oznakaVlaka = commandParts[1].trim();
        context.getRaspored().ispisEtapaZaVlak(oznakaVlaka);
	}

}
