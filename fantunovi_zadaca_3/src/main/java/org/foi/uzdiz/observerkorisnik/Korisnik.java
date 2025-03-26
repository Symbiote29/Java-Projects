package org.foi.uzdiz.observerkorisnik;

import java.util.ArrayList;
import java.util.List;

public class Korisnik implements Observer {
    private String ime;
    private String prezime;
    private List<String> prateciVlakovi;
    private List<String> prateceStanice;

    public Korisnik(String ime, String prezime) {
        this.ime = ime;
        this.prezime = prezime;
        this.prateciVlakovi = new ArrayList<>();
        this.prateceStanice = new ArrayList<>();
    }
    
    @Override
    public void update(String message) {
        System.out.println("Obavijest za " + ime + " " + prezime + ": " + message);
    }

    public boolean pratiVlak(String oznakaVlaka) {
    	System.out.println("Provjeravam da li korisnik prati vlak: " + oznakaVlaka);
        return prateciVlakovi.contains(oznakaVlaka);
    }

    public void pratiStanicu(String nazivStanice) {
        prateceStanice.add(nazivStanice);
    }

	public String getIme() {
		return this.ime;
	}

	public String getPrezime() {
		return this.prezime;
	}
	
	public List<String> getPrateceStanice() {
	    return prateceStanice;
	}
}
