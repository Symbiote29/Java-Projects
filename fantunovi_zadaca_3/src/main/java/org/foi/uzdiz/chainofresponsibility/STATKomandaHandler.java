package org.foi.uzdiz.chainofresponsibility;

import java.util.HashMap;
import java.util.Map;

import org.foi.uzdiz.compositevoznired.Vlak;
import org.foi.uzdiz.mediatorvoznired.ConcreteVozniRedMediator;

public class STATKomandaHandler implements HandlerKomandi {
    private ConcreteVozniRedMediator mediator;

    public STATKomandaHandler(ConcreteVozniRedMediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public boolean canHandle(String komanda) {
        return komanda.trim().equalsIgnoreCase("STAT");
    }

    @Override
    public void handle(String komanda, ContextKomandi context) {
        int ukupanBrojVlakova = mediator.getVlakovi().size();
        Map<String, Integer> vlakoviPoTipu = new HashMap<>();
        double ukupnaUdaljenost = 0;
        double najduzaEtapa = 0;
        double najkracaEtapa = Double.MAX_VALUE;

        for (Vlak vlak : mediator.getVlakovi()) {
            vlakoviPoTipu.put(vlak.getVrstaVlaka(),
                vlakoviPoTipu.getOrDefault(vlak.getVrstaVlaka(), 0) + 1);

            for (var etapa : vlak.getVlakoviEtape()) {
                double udaljenost = etapa.getUdaljenost();
                ukupnaUdaljenost += udaljenost;
                if (udaljenost > najduzaEtapa) najduzaEtapa = udaljenost;
                if (udaljenost < najkracaEtapa) najkracaEtapa = udaljenost;
            }
        }

        System.out.println("Statistika vlakova:");
        System.out.println("+-----------------------------------+------------------+");
        System.out.printf("| %-33s | %-16s |%n", "Ukupan broj vlakova", ukupanBrojVlakova);
        System.out.println("+-----------------------------------+------------------+");
        System.out.printf("| %-33s | %-16s |%n", "Prosječna udaljenost po vlaku", String.format("%.2f", (ukupnaUdaljenost / ukupanBrojVlakova)));
        System.out.printf("| %-33s | %-16s |%n", "Najduža etapa", String.format("%.2f km", najduzaEtapa));
        System.out.printf("| %-33s | %-16s |%n", "Najkraća etapa", String.format("%.2f km", najkracaEtapa));
        System.out.println("+-----------------------------------+------------------+");

        System.out.printf("| %-33s | %-16s |%n", "Tip vlaka", "Broj");
        System.out.println("+-----------------------------------+------------------+");
        vlakoviPoTipu.forEach((tip, broj) -> {
            if (tip == null || tip.trim().isEmpty()) {
                tip = "N";
            }
            System.out.printf("| %-33s | %-16d |%n", tip, broj);
        });
        System.out.println("+-----------------------------------+------------------+");
    }
}
