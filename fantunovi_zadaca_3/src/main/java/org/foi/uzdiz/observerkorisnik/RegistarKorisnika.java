package org.foi.uzdiz.observerkorisnik;

import java.util.ArrayList;
import java.util.List;

public class RegistarKorisnika {
	private List<Korisnik> korisnici = new ArrayList<>();

    public void dodajKorisnika(String ime, String prezime) {
        if (nadjiKorisnika(ime, prezime) != null) {
            System.out.println("Korisnik " + ime + " " + prezime + " već postoji u registru.");
            return;
        }

        Korisnik korisnik = new Korisnik(ime, prezime);
        korisnici.add(korisnik);
        System.out.println("Korisnik " + ime + " " + prezime + " je dodan.");
    }

    public void prikaziKorisnike() {
        for (Korisnik korisnik : korisnici) {
            System.out.println(korisnik.getIme() + " " + korisnik.getPrezime());
        }
    }
    
    public Korisnik nadjiKorisnika(String ime, String prezime) {
        System.out.println("Popis korisnika prije traženja u klasi RegistarKorisnika:");
        for (Korisnik korisnik : korisnici) {
            System.out.println(korisnik.getIme() + " " + korisnik.getPrezime());
        }

        for (Korisnik korisnik : korisnici) {
            System.out.println("Provjeravam korisnika: " + korisnik.getIme() + " " + korisnik.getPrezime());
            System.out.println("Usporedba: " + korisnik.getIme().trim().toLowerCase() + " == " + ime.trim().toLowerCase());
            System.out.println("Usporedba: " + korisnik.getPrezime().trim().toLowerCase() + " == " + prezime.trim().toLowerCase());
            if (korisnik.getIme().trim().toLowerCase().equals(ime.trim().toLowerCase()) &&
                korisnik.getPrezime().trim().toLowerCase().equals(prezime.trim().toLowerCase())) {
                return korisnik;
            }
        }
        return null;
    }

    public List<Korisnik> getKorisnici() {
        return korisnici;
    }
}
