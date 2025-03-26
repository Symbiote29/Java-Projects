package org.foi.uzdiz.mementokupovina;

import java.util.ArrayList;
import java.util.List;

public class PovijestKupovina {
	private final List<Karta> kupovine = new ArrayList<>();

    public void dodajKupovinu(Karta kupovina) {
        kupovine.add(kupovina);
    }

    public List<Karta> getKupovine() {
        return new ArrayList<>(kupovine);
    }
    
    public Karta ukloniKupovinu(int indeks) {
        if (indeks >= 0 && indeks < kupovine.size()) {
            return kupovine.remove(indeks);
        }
        return null;
    }
}
