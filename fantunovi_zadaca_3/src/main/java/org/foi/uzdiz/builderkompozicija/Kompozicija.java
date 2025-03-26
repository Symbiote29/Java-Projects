package org.foi.uzdiz.builderkompozicija;

import java.util.ArrayList;
import java.util.List;

public class Kompozicija {
	private String oznaka;
    private List<PrijevoznoSredstvo> prijevoznaSredstva;

    public Kompozicija(String oznaka) {
        this.oznaka = oznaka;
        this.prijevoznaSredstva = new ArrayList<>();
    }

    public void dodajPrijevoznoSredstvo(PrijevoznoSredstvo ps) {
        prijevoznaSredstva.add(ps);
    }

    public String getOznaka() {
        return oznaka;
    }

    public List<PrijevoznoSredstvo> getPrijevoznaSredstva() {
        return prijevoznaSredstva;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Kompozicija ").append(oznaka).append(":\n");
        for (PrijevoznoSredstvo ps : prijevoznaSredstva) {
            sb.append(ps).append("\n");
        }
        return sb.toString();
    }
}
