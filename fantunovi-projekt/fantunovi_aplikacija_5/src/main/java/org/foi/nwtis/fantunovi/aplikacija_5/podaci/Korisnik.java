package org.foi.nwtis.fantunovi.aplikacija_5.podaci;

import lombok.Getter;
import lombok.Setter;

public class Korisnik {
	@Getter
	@Setter
	private String ime;
	@Getter
	@Setter
    private String prezime;
	@Getter
	@Setter
    private String korime;
	@Getter
	@Setter
    private String lozinka;
	@Getter
	@Setter
    private String email;

    public Korisnik() {
    }

    public Korisnik(String ime, String prezime, String korime, String lozinka, String email) {
    	this.ime = ime;
    	this.prezime = prezime;
    	this.korime = korime;
    	this.lozinka = lozinka;
    	this.email = email;
    }
    
}
