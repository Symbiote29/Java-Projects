package org.foi.uzdiz.chainofresponsibility;

import java.util.List;
import org.foi.uzdiz.observerkorisnik.Korisnik;
import org.foi.uzdiz.observerkorisnik.RegistarKorisnika;

public class SVVKomandaHandler implements HandlerKomandi {

    @Override
    public boolean canHandle(String komanda) {
        return komanda.trim().toUpperCase().startsWith("SVV");
    }

    @Override
    public void handle(String komanda, ContextKomandi context) {
        String komandaSadrzaj = komanda.substring(3).trim();

        if (komandaSadrzaj.isEmpty()) {
            System.out.println("Neispravna komanda. Nedostaje oznaka vlaka, stanica ili broj.");
            return;
        }
        String[] komandaDijelovi = komandaSadrzaj.split(" - ");
        
        if (komandaDijelovi.length < 2) {
            System.out.println("Neispravna komanda. Nedostaje oznaka vlaka ili stanica.");
            return;
        }

        String oznakaVlaka = cleanString(komandaDijelovi[0].trim());
        String stanica = cleanString(komandaDijelovi[1].trim());
        String broj = (komandaDijelovi.length > 2) ? cleanString(komandaDijelovi[2].trim()) : "";

        if (oznakaVlaka.isEmpty()) {
            System.out.println("Oznaka vlaka je prazna!");
        } else {
            List<String> stanice = context.getRaspored().dohvatiStaniceZaVlak(oznakaVlaka);
            if (stanice.isEmpty()) {
                System.out.println("Nema stanica za vlak s oznakom: " + oznakaVlaka);
                return;
            }

            int koeficijent = Integer.parseInt(broj);

            for (String nazivStanice : stanice) {
                System.out.println(nazivStanice);
                obavijestiKorisnike(nazivStanice, context);

                try {
                    Thread.sleep(10000 / koeficijent);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("Simulacija završena.");
        }
    }

    private String cleanString(String input) {
        return input.replaceAll("[^a-zA-ZČĆŽŠĐčćžšđ0-9 ]", "").trim();
    }

    private void obavijestiKorisnike(String nazivStanice, ContextKomandi context) {
        RegistarKorisnika registar = context.getRegistar();

        for (Korisnik korisnik : registar.getKorisnici()) {
            System.out.println("Obavijest za korisnika " + korisnik.getIme() + " da je vlak stigao na stanicu " + nazivStanice);
        }
    }
}
