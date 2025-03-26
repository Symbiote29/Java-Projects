package org.foi.nwtis.podaci;

public class Dnevnik {
	String zahtjev;
	String put;
	String vrstaAplikacije;
	String vrijeme;
	
	public Dnevnik() {}
	
	public Dnevnik(String zahtjev, String put, String vrstaAplikacije, String vrijeme) {
		super();
		this.zahtjev = zahtjev;
		this.put = put;
		this.vrstaAplikacije = vrstaAplikacije;
		this.vrijeme = vrijeme;
	}
	
	public String getZahtjev() {
		return zahtjev;
	}
	
	public void setZahtjev(String zahtjev) {
		this.zahtjev = zahtjev;
	}
	
	public String getPut() {
		return put;
	}
	
	public void setPut(String put) {
		this.put = put;
	}
	
	public String getVrstaAplikacije() {
		return vrstaAplikacije;
	}
	
	public void setVrstaAplikacije(String vrstaAplikacije) {
		this.vrstaAplikacije = vrstaAplikacije;
	}
	
	public String getVrijeme() {
		return vrijeme;
	}
	
	public void setVrijeme(String vrijeme) {
		this.vrijeme = vrijeme;
	}
	
}
