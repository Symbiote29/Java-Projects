package org.foi.uzdiz.mediatorvoznired;

import java.util.ArrayList;
import java.util.List;

import org.foi.uzdiz.builderstanica.Stanica;
import org.foi.uzdiz.compositevoznired.Etape;
import org.foi.uzdiz.compositevoznired.Vlak;
import org.foi.uzdiz.observerkorisnik.Korisnik;

public class ConcreteVozniRedMediator implements VozniRedMediator {
    private List<Stanica> stanice;
    private List<Vlak> vlakovi;
    private List<Korisnik> korisnici;

    public ConcreteVozniRedMediator() {
        stanice = new ArrayList<>();
        vlakovi = new ArrayList<>();
        korisnici = new ArrayList<>();
    }

    public void dodajStanicu(Stanica stanica) {
        stanice.add(stanica);
    }

    public void dodajVlak(Vlak vlak) {
        vlakovi.add(vlak);
    }

    public void dodajKorisnika(Korisnik korisnik) {
        korisnici.add(korisnik);
    }

    @Override
    public void obavijestiKorisnikeOVoznomRedu(String vlakOznaka) {
        for (Korisnik korisnik : korisnici) {
            if (korisnik.pratiVlak(vlakOznaka)) {
                korisnik.update("Vlak " + vlakOznaka + " ima novo ažuriranje.");
            }
        }
    }

    @Override
    public void obavijestiStaniceOEtapi(Vlak vlak, Etape etapa) {
        for (Stanica stanica : stanice) {
            if (stanica.getNaziv().equals(etapa.getZavrsnaStanica())) {
                stanica.obavijestiObservere("Vlak " + vlak.getOznakaVlaka() + " dolazi na vašu stanicu.");
            }
        }
    }

    @Override
    public void posaljiPoruku(String message, Stanica stanica) {
        System.out.println("Poruka od stanice " + stanica.getNaziv() + ": " + message);
    }

    public List<Vlak> getVlakovi() {
        return vlakovi;
    }
}
