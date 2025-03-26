package org.foi.nwtis.fantunovi.aplikacija_5.podaci;

public class JsonSadrzaj {
	int status;
	String opis;
	
	public JsonSadrzaj() {}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public JsonSadrzaj(int status, String opis) {
		super();
		this.status = status;
		this.opis = opis;
	}
}
