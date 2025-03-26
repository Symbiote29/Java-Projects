package org.foi.uzdiz.builderkompozicija;

public class PrijevoznoSredstvo {
	 private String oznaka;
	    private String uloga;

	    public PrijevoznoSredstvo(String oznaka, String uloga) {
	        this.oznaka = oznaka;
	        this.uloga = uloga;
	    }

	    public String getOznaka() {
	        return oznaka;
	    }

	    public String getUloga() {
	        return uloga;
	    }

	    @Override
	    public String toString() {
	        return "Prijevozno Sredstvo: " + oznaka + ", Uloga: " + uloga;
	    }
}
