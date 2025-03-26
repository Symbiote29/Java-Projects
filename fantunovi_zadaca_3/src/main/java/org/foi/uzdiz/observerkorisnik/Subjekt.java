package org.foi.uzdiz.observerkorisnik;

public interface Subjekt {
	void dodajObserver(Observer observer);
    void removajObserver(Observer observer);
    void obavijestiObservere(String message);
}
