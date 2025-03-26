package org.foi.uzdiz.chainofresponsibility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.foi.uzdiz.builderkompozicija.Kompozicija;
import org.foi.uzdiz.builderkompozicija.PrijevoznoSredstvo;

public class IKKomandaHandler implements HandlerKomandi{
	@Override
	public boolean canHandle(String komanda) {
		return komanda.startsWith("IK") && !komanda.startsWith("IKKPV");
	}

	@Override
	public void handle(String komanda, ContextKomandi context) {
		String[] commandParts = komanda.split(" ");
        if (commandParts.length == 2) {
            String oznaka = commandParts[1].trim();
            Map<String, String> vozilaUloge = new HashMap<>();

            Kompozicija kompozicija = context.getZkDatoteka().dohvatiKompozicije().get(oznaka);

            if (kompozicija != null) {
                kompozicija.getPrijevoznaSredstva().forEach(prijevoznoSredstvo -> {
                    String oznakaVozila = prijevoznoSredstvo.getOznaka();
                    String uloga = prijevoznoSredstvo.getUloga();
                    vozilaUloge.put(oznakaVozila, uloga);
                });
            } else {
                System.out.println("Nema kompozicija za oznaku: " + oznaka);
                return;
            }

            List<String[]> compositionsList = new ArrayList<>();

            for (PrijevoznoSredstvo ps : kompozicija.getPrijevoznaSredstva()) {
                compositionsList.add(new String[]{ps.getOznaka(), ps.getUloga()});
            }

            context.getZpsDatoteka().setCompositionsList(compositionsList);
            context.getZpsDatoteka().obradiKomandu(oznaka, vozilaUloge);
        } else {
            System.out.println("Neispravna sintaksa. Koristite: IK oznaka");
        }
	}

}
