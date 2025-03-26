package org.foi.uzdiz.builderkompozicija;

import java.util.List;
import java.util.Map;

public class KompozicijaDirector {
    public final KompozicijaBuilder builder;

    public KompozicijaDirector(KompozicijaBuilder builder) {
        this.builder = builder;
    }

    public Map<String, Kompozicija> ucitajKompozicije(Map<String, List<String>> podaci) {
        for (Map.Entry<String, List<String>> entry : podaci.entrySet()) {
            String oznakaKompozicije = entry.getKey();
            List<String> vozila = entry.getValue();

            for (String vozilo : vozila) {
                String[] atributi = vozilo.split(",");
                String oznakaPrijevoznogSredstva = atributi[0].trim();
                String uloga = atributi[1].trim();

                builder.dodajPrijevoznoSredstvo(oznakaKompozicije, oznakaPrijevoznogSredstva, uloga);
            }
        }
        return builder.build();
    }
}
