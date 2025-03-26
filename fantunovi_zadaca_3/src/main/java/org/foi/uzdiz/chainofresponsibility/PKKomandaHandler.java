package org.foi.uzdiz.chainofresponsibility;

import java.util.List;

import org.foi.uzdiz.observerkorisnik.Korisnik;

public class PKKomandaHandler implements HandlerKomandi {

    @Override
    public boolean canHandle(String komanda) {
        return komanda.trim().equalsIgnoreCase("PK");
    }

    @Override
    public void handle(String komanda, ContextKomandi context) {
        List<Korisnik> korisnici = context.getRegistar().getKorisnici();

        if (korisnici.isEmpty()) {
            System.out.println("Registar korisnika je prazan.");
        } else {
            System.out.println("Popis korisnika:");
            for (Korisnik korisnik : korisnici) {
                System.out.println(korisnik.getIme() + " " + korisnik.getPrezime());
            }
        }
    }
}