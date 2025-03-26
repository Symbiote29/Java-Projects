package org.foi.uzdiz.builderstanica;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class StanicaDirector {
    private StanicaBuilder builder;

    public StanicaDirector(StanicaBuilder builder) {
        this.builder = builder;
    }

    public Stanica konstruirajStanicu(String naziv, String vrsta, String aktivnosti, int brojPerona, String status, double udaljenost, String oznakaPruge) {
        return builder
                .setNaziv(naziv)
                .setVrsta(vrsta)
                .setAktivnosti(aktivnosti)
                .setBrojPerona(brojPerona)
                .setStatus(status)
                .setUdaljenost(udaljenost)
                .setOznakaPruge(oznakaPruge)
                .build();
    }

    public Map<String, String> dohvatiDetaljeOStanicama(List<Stanica> stanice) {
        Map<String, Double> ukupnaDuljinaPoPrugama = new HashMap<>();
        Map<String, String> details = new HashMap<>();

        for (Stanica stanica : stanice) {
            String oznakaPruga = stanica.getOznakaPruge();
            ukupnaDuljinaPoPrugama.put(oznakaPruga, ukupnaDuljinaPoPrugama.getOrDefault(oznakaPruga, 0.0) + stanica.getUdaljenost());
        }

        for (String oznaka : ukupnaDuljinaPoPrugama.keySet()) {
            details.put(oznaka, "Ukupna duljina: " + ukupnaDuljinaPoPrugama.get(oznaka) + " metara");
        }

        return details;
    }
}
