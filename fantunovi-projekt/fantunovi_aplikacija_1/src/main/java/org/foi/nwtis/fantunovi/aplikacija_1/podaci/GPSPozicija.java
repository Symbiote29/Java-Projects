package org.foi.nwtis.fantunovi.aplikacija_1.podaci;

public record GPSPozicija(double zemljopisnaDuzina, double zemljopisnaSirina) {

	public double vratiDuzinu() {
		return zemljopisnaDuzina;
	}

	public double vratiSirinu() {
		return zemljopisnaSirina;
	}

	public boolean jeLiJednako(GPSPozicija other) {
		return zemljopisnaDuzina == other.zemljopisnaDuzina && zemljopisnaSirina == other.zemljopisnaSirina;
	}
}
