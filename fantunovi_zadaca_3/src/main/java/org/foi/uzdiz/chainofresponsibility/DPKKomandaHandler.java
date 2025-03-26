package org.foi.uzdiz.chainofresponsibility;

import java.util.Arrays;

import org.foi.uzdiz.observerkorisnik.Korisnik;

public class DPKKomandaHandler implements HandlerKomandi {

    @Override
    public boolean canHandle(String komanda) {
        return komanda.trim().toUpperCase().startsWith("DPK");
    }

    @Override
    public void handle(String komanda, ContextKomandi context) {
        String komandaSadrzaj = komanda.substring(3).trim();
        String[] dpkKomanda = komandaSadrzaj.split(" - ");

        if (dpkKomanda.length < 2) {
            System.out.println("Neispravna komanda. Nedostaje oznaka vlaka ili stanica.");
            return;
        }

        String imePrezime = cleanString(dpkKomanda[0].trim());
        String[] korisnikInformacije = imePrezime.split(" ");

        System.out.println("korisnikInformacije: " + Arrays.toString(korisnikInformacije));

        if (korisnikInformacije.length >= 2) {
            String imeKorisnika = cleanString(korisnikInformacije[0].trim());
            String prezimeKorisnika = cleanString(String.join(" ", Arrays.copyOfRange(korisnikInformacije, 1, korisnikInformacije.length)).trim());


            String vlOznaka = cleanString(dpkKomanda[1].trim());

            if (vlOznaka.isEmpty()) {
                System.out.println("Oznaka vlaka je prazna!");
            } else {
                System.out.println("Oznaka vlaka je: " + vlOznaka);
            }

            String nazivStanice = null;
            if (dpkKomanda.length == 3) {
                nazivStanice = cleanString(dpkKomanda[2].trim());
                System.out.println("Naziv stanice je: " + nazivStanice);
            }

            System.out.println("Popis korisnika prije traženja u klasi DPKKomandaHandler:");
            for (Korisnik korisnik : context.getRegistar().getKorisnici()) {
                System.out.println(korisnik.getIme() + " " + korisnik.getPrezime());
            }
            
            Korisnik korisnikZaPraćenje = context.getRegistar().nadjiKorisnika(imeKorisnika, prezimeKorisnika);
            System.out.println("KOrisnik za pracenje: " + korisnikZaPraćenje.getIme() + " " + korisnikZaPraćenje.getPrezime());

            if (korisnikZaPraćenje != null) {
            	context.getRaspored().setRegistarKorisnika(context.getRegistar());
                if (nazivStanice != null) {
                    context.getRaspored().dodajKorisnikaZaStanicu(imeKorisnika, prezimeKorisnika, nazivStanice);
                } else if (!vlOznaka.isEmpty()) {
                    context.getRaspored().dodajKorisnikaZaVlak(imeKorisnika, prezimeKorisnika, vlOznaka);
                }
            } else {
                System.out.println("Korisnik s imenom " + imeKorisnika + " " + prezimeKorisnika + " nije pronađen.");
            }
        } else {
            System.out.println("Neispravan format imena i prezimena.");
        }
    }



    private String cleanString(String input) {
        return input.replaceAll("[^a-zA-ZČĆŽŠĐčćžšđ0-9 ]", "").trim();
    }
}
