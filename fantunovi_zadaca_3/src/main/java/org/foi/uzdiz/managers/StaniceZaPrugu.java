package org.foi.uzdiz.managers;

import java.util.List;

import org.foi.uzdiz.builderstanica.Stanica;

public class StaniceZaPrugu {
	private String oznakaPruge;
    private List<Stanica> stanice;

    public StaniceZaPrugu(String oznakaPruge, List<Stanica> stanice) {
        this.oznakaPruge = oznakaPruge;
        this.stanice = stanice;
    }

    public String getOznakaPruge() {
        return oznakaPruge;
    }

    public List<Stanica> getStanice() {
        return stanice;
    }

    public Stanica getStanicaPoImenu(String naziv) {
        for (Stanica stanica : stanice) {
            if (stanica.getNaziv().equalsIgnoreCase(naziv)) {
                return stanica;
            }
        }
        return null;
    }
}
