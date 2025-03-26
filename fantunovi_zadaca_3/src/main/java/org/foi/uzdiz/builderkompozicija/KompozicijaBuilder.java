package org.foi.uzdiz.builderkompozicija;

import java.util.HashMap;
import java.util.Map;

public class KompozicijaBuilder {
    private Map<String, Kompozicija> kompozicije = new HashMap<>();

    public KompozicijaBuilder dodajPrijevoznoSredstvo(String oznakaKompozicije, String oznakaPrijevoznogSredstva, String uloga) {
        Kompozicija kompozicija = kompozicije.computeIfAbsent(oznakaKompozicije, Kompozicija::new);
        kompozicija.dodajPrijevoznoSredstvo(new PrijevoznoSredstvo(oznakaPrijevoznogSredstva, uloga));
        return this;
    }

    public Map<String, Kompozicija> build() {
        return kompozicije;
    }
}
