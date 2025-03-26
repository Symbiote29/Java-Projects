package org.foi.uzdiz.managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.foi.uzdiz.builderstanica.Stanica;
import org.foi.uzdiz.builderstanica.StanicaDirector;
import org.foi.uzdiz.chainofresponsibility.ContextKomandi;
import org.foi.uzdiz.factorypruga.Pruga;
import org.foi.uzdiz.factoryucitavanjedatoteka.ZsDatoteka;
import org.foi.uzdiz.fantunovi_zadaca_3.Graf;
import org.foi.uzdiz.fantunovi_zadaca_3.Graf.Edge;

public class PrugaManager {
    private List<Pruga> svePruge = new ArrayList<>();
    private final Graf graf;
    private ZsDatoteka zsDatoteka;

    public PrugaManager(StanicaDirector stanicaDirector, List<Pruga> svePruge, ZsDatoteka zsDatoteka) {
        this.svePruge = svePruge;
        this.zsDatoteka = zsDatoteka;
        this.graf = zsDatoteka.getGraf();
    }
    
    public ZsDatoteka getZsDatoteka() {
        return this.zsDatoteka;
    }
    
    public StaniceZaPrugu ispisiStaniceZaPrugu(String oznakaPruge, String redoslijed) {
        if (zsDatoteka == null) {
            System.out.println("ZsDatoteka nije ucitana.");
            return null;
        }

        Pruga pruga = pronadiPrugu(oznakaPruge);
        if (pruga == null) {
            System.out.println("Pruga s oznakom " + oznakaPruge + " nije pronadena.");
            return null;
        }

        System.out.printf("\nOznaka pruge='%s', kategorija='%s', vrsta='%s', brojKolosijeka=%d, doPoOsovini=%.1f, doPoDuznomM=%.1f, status='%s'%n",
                pruga.getOznaka(), pruga.getKategorija(), pruga.getVrstaPruga(), pruga.getBrojKolosijeka(), pruga.getDoPoOsovini(), pruga.getDoPoDuznomM(), pruga.getStatus());

        List<Stanica> stanice = zsDatoteka.getStaniceZaPrugu(oznakaPruge);

        if (stanice != null && !stanice.isEmpty()) {
            System.out.println("\nNaziv stanice        Vrsta stanice   Kilometara od početne stanice");
            System.out.println("-----------------------------------------------------------");

            if (redoslijed.equalsIgnoreCase("O")) {
                Collections.reverse(stanice);
            }

            double ukupnaDuljina = 0.0;

            for (int i = 0; i < stanice.size(); i++) {
                Stanica stanica = stanice.get(i);
                String nazivStanice = stanica.getNaziv();
                String vrstaStanice = stanica.getVrsta();

                if (redoslijed.equalsIgnoreCase("N")) {
                    ukupnaDuljina += stanica.getUdaljenost();
                }
                else if (redoslijed.equalsIgnoreCase("O")) {
                    if (i == 0) {
                        ukupnaDuljina = 0.0;
                    } else {
                        ukupnaDuljina += stanice.get(i - 1).getUdaljenost();
                    }
                }

                System.out.printf("%-20s %-15s %.2f km%n", nazivStanice, vrstaStanice, ukupnaDuljina);
            }

            System.out.printf("\nUkupna duljina: %.2f metara%n", ukupnaDuljina);
        } else {
            System.out.println("Nema stanica za prugu: " + oznakaPruge);
        }
        return new StaniceZaPrugu(oznakaPruge, stanice);
    }

    public List<Pruga> getPruge() {
        return this.svePruge;
    }
    
    public Pruga pronadiPrugu(String oznakaPruge) {
        for (Pruga pruga : svePruge) {
            if (pruga.getOznaka().equals(oznakaPruge)) {
                return pruga;
            }
        }
        return null;
    }


    public List<String> pretraziPut(String pocetnaStanica, String zavrsnaStanica) {
        return graf.bfs(pocetnaStanica, zavrsnaStanica);
    }
    
    public void ispisiGraf() {
        graf.printajGraf();
    }
    
    public void ispisiPut(String pocetnaStanica, String zavrsnaStanica) {
        List<String> put = pretraziPut(pocetnaStanica, zavrsnaStanica);

        if (put == null || put.isEmpty()) {
            System.out.printf("Nema dostupnog puta izmedu %s i %s.%n", pocetnaStanica, zavrsnaStanica);
            return;
        }

        System.out.println("\nRuta: " + String.join(" -> ", put) + "\n");
    }
    
    public double izracunajUdaljenost(List<String> ruta) {
        double ukupnaUdaljenost = 0.0;

        for (int i = 0; i < ruta.size() - 1; i++) {
            String trenutnaStanica = ruta.get(i);
            String sljedecaStanica = ruta.get(i + 1);

            Edge edge = graf.getEdge(trenutnaStanica, sljedecaStanica);
            if (edge != null) {
                ukupnaUdaljenost += edge.getUdaljenost();
            }
        }

        return ukupnaUdaljenost;
    }

    public boolean jeRutaDostupna(List<String> ruta, ContextKomandi context) {
        String pocetnaStanica = ruta.get(0);
        String zavrsnaStanica = ruta.get(ruta.size() - 1);

        Pruga pruga = getPrugaZaStanice(pocetnaStanica, zavrsnaStanica, context);

        if (pruga == null) {
            System.out.println("Nema pruge između stanica " + pocetnaStanica + " i " + zavrsnaStanica);
            return false;
        }

        if (!pruga.jeDostupna(pocetnaStanica, zavrsnaStanica)) {
            System.out.println("Pruga između " + pocetnaStanica + " i " + zavrsnaStanica + " nije dostupna.");
            return false;
        }

        return true;
    }

    private Pruga getPrugaZaStanice(String stanica1, String stanica2, ContextKomandi context) {
        Map<String, List<Stanica>> stanicePoPrugama = context.getPrugaManager().getZsDatoteka().getStanicePoPrugama();
        
        System.out.println("Broj unosa u stanicePoPrugama: " + stanicePoPrugama.size());

        
        for (Map.Entry<String, List<Stanica>> entry : stanicePoPrugama.entrySet()) {
            String oznakaPruge = entry.getKey();
            List<Stanica> stanice = entry.getValue();

            boolean sadrziStanica1 = stanice.stream().anyMatch(s -> s.getNaziv().equals(stanica1));
            boolean sadrziStanica2 = stanice.stream().anyMatch(s -> s.getNaziv().equals(stanica2));

            if (sadrziStanica1 && sadrziStanica2) {
                return context.getPrugaManager().pronadiPrugu(oznakaPruge);
            }
        }

        System.out.println("Nema pruge između stanica " + stanica1 + " i " + stanica2);
        return null;
    }
}

