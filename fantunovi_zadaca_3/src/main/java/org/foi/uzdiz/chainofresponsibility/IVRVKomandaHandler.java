package org.foi.uzdiz.chainofresponsibility;

public class IVRVKomandaHandler implements HandlerKomandi{

	@Override
	public boolean canHandle(String komanda) {
		return komanda.startsWith("IVRV");
	}

	@Override
	public void handle(String komanda, ContextKomandi context) {
		System.out.println("Nekako aktivirano ovo. Komanda: " + komanda);
		String[] commandParts = komanda.split(" ");
        if (commandParts.length < 2 || commandParts[1].trim().isEmpty()) {
            System.out.println("Greška: Kriva komanda. Očekuje se 'IVRV oznaka vlaka'.");
            return;
        }
        String vlakOznaka = commandParts[1].trim();

        if (!vlakOznaka.matches("\\d+")) {
            System.out.println("Greška: Oznaka vlaka mora biti broj.");
            return;
        }

        context.getRaspored().ispisStanicaZaVlak(vlakOznaka);
	}

}
