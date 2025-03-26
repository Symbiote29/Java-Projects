package org.foi.uzdiz.compositevoznired;

import java.util.ArrayList;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.foi.uzdiz.builderstanica.Stanica;
import org.foi.uzdiz.factorypruga.Pruga;
import org.foi.uzdiz.factoryucitavanjedatoteka.ZodDatoteka;
import org.foi.uzdiz.factoryucitavanjedatoteka.ZsDatoteka;
import org.foi.uzdiz.factoryucitavanjedatoteka.ZvrDatoteka;
import org.foi.uzdiz.fantunovi_zadaca_3.Graf;
import org.foi.uzdiz.mediatorvoznired.ConcreteVozniRedMediator;

public class Etape implements VozniRedComponent {
    private String oznakaPruge; 
    private String smjer;
    private LeafStanica polaznaStanica;
    private LeafStanica zavrsnaStanica;
    private String oznakaVlaka;   
    private String vrstVlaka;
    private String vrijemePolaska;
    private String oznakaDana;    
    private String trajanjeVoznje;
    private double udaljenost;
    
    private static ZsDatoteka zsDatoteka;
    private static ZodDatoteka zodDatoteka;
    private LeafStanica originalPolaznaStanica;
    private LeafStanica originalZavrsnaStanica;
    
    public Etape(String oznakaPruge, String smjer, LeafStanica polaznaStanica, LeafStanica zavrsnaStanica, 
            String oznakaVlaka, String vrstaVlaka, String vrijemePolaska, String oznakaDana, String trajanjeVoznje, double udaljenost) {
   this.oznakaPruge = oznakaPruge;
   this.smjer = smjer;
   
   this.originalPolaznaStanica = polaznaStanica;
   this.originalZavrsnaStanica = zavrsnaStanica;

   if ("O".equals(smjer)) {
       this.polaznaStanica = zavrsnaStanica;
       this.zavrsnaStanica = polaznaStanica;
   } else {
       this.polaznaStanica = polaznaStanica;
       this.zavrsnaStanica = zavrsnaStanica;
   }
   
   this.oznakaVlaka = oznakaVlaka;
   this.vrstVlaka = vrstaVlaka;
   this.vrijemePolaska = vrijemePolaska;
   this.oznakaDana = oznakaDana;
   this.trajanjeVoznje = trajanjeVoznje;
   this.udaljenost = udaljenost;
}

    @Override
    public void prikaziDetaljeOVoznomRedu() {
        String nazivDana = getNazivDanaIzZodDatoteke(oznakaDana);

        System.out.println("Etape: " + oznakaPruge + " (" + smjer + ") " +
                originalPolaznaStanica.getNaziv() + " -> " + originalZavrsnaStanica.getNaziv() +
                ", Vlak: " + oznakaVlaka + " (" + vrstVlaka + ")" +
                ", Vrijeme polaska: " + vrijemePolaska + ", Oznaka dana: " + nazivDana +
                ", Trajanje vožnje: " + trajanjeVoznje +
                ", Udaljenost: " + udaljenost + " km");
    }

    public LeafStanica getOriginalPolaznaStanica() {
        return originalPolaznaStanica;
    }

    public LeafStanica getOriginalZavrsnaStanica() {
        return originalZavrsnaStanica;
    }
    
    public static void postaviZsDatoteku(ZsDatoteka zsDatoteka) {
        Etape.zsDatoteka = zsDatoteka;
    }
    
    public static void postaviZodDatoteku(ZodDatoteka zodDatoteka) {
    	Etape.zodDatoteka = zodDatoteka;
    }

    public String getOznakaPruge() {
        return oznakaPruge;
    }

    public String getSmjer() {
        return smjer;
    }

    public LeafStanica getPolaznaStanica() {
        return polaznaStanica;
    }

    public LeafStanica getZavrsnaStanica() {
        return zavrsnaStanica;
    }

    public String getOznakaVlaka() {
        return oznakaVlaka;
    }

    public String getVrstaVlaka() {
        return vrstVlaka;
    }

    public String getVrijemePolaska() {
        return vrijemePolaska;
    }

    public String getOznakaDana() {
        return oznakaDana;
    }
    
    public String getTrajanjeVoznje() {
        return trajanjeVoznje;
    }
    
    public double getUdaljenost() {
        return udaljenost;
    }

    public void setUdaljenost(double udaljenost) {
        this.udaljenost = udaljenost;
    }

    public static void kreirajEtapeIzPodataka(List<Map<String, String>> podaci, ZsDatoteka zsDatoteka, Raspored raspored, ZodDatoteka zodDatoteka, ConcreteVozniRedMediator mediator) {
        postaviZsDatoteku(zsDatoteka);
        postaviZodDatoteku(zodDatoteka);

        List<Vlak> vlakovi = new ArrayList<>();

        for (Map<String, String> red : podaci) {
            String oznakaP = red.get("Oznaka pruge");
            String smj = red.get("Smjer");
            String pocetnaS = red.get("Polazna stanica");
            String zavrsnaS = red.get("Odredišna stanica");
            String oznakaV = red.get("Oznaka vlaka");
            String vrstaV = red.get("Vrsta vlaka");
            String vrijemeP = red.get("Vrijeme polaska");
            String oznakaD = red.get("Oznaka dana");
            String trajanjeVoznje = red.get("Trajanje vožnje");

            String daniIzOznake = getNazivDanaIzZodDatoteke(oznakaD);

            Map<String, List<Stanica>> stanicePoPrugama = zsDatoteka.getStanicePoPrugama();
            List<Stanica> staniceNaPruzi = stanicePoPrugama.get(oznakaP);

            if (staniceNaPruzi == null || staniceNaPruzi.isEmpty()) {
                continue;
            }

            LeafStanica pocetak = null, kraj = null;

            if ("O".equals(smj)) {
                pocetak = new LeafStanica(staniceNaPruzi.get(staniceNaPruzi.size() - 1).getNaziv());
                kraj = new LeafStanica(staniceNaPruzi.get(0).getNaziv());
            } else {
                pocetak = new LeafStanica(staniceNaPruzi.get(0).getNaziv());
                kraj = new LeafStanica(staniceNaPruzi.get(staniceNaPruzi.size() - 1).getNaziv());
            }

            if (pocetnaS != null && !pocetnaS.isEmpty()) {
                pocetak = new LeafStanica(pocetnaS);
            }
            if (zavrsnaS != null && !zavrsnaS.isEmpty()) {
                kraj = new LeafStanica(zavrsnaS);
            }

            double udaljenost = izracunajUdaljenost(pocetak, kraj, oznakaP, zsDatoteka);

            Etape etapa = new Etape(oznakaP, smj, pocetak, kraj, oznakaV, vrstaV,
                                    vrijemeP, oznakaD, trajanjeVoznje, udaljenost);

            Vlak vlak = new Vlak(oznakaV, vrstaV);

            
            vlak.addEtape(etapa);
            vlakovi.add(vlak);
            
            if (mediator != null) {
                mediator.dodajVlak(vlak);
            }
        }

        for (Vlak vlak : vlakovi) {
            raspored.dodajVlak(vlak);
        }

        raspored.prikaziDetaljeOVoznomRedu();
    }


    public static double izracunajUdaljenost(LeafStanica start, LeafStanica end, String trackCode, ZsDatoteka zsDatoteka) {
        Map<String, List<Stanica>> stanicePoPrugama = zsDatoteka.getStanicePoPrugama();
        List<Stanica> staniceNaPruzi = stanicePoPrugama.get(trackCode);

        if (staniceNaPruzi == null) {
            return 0.0;
        }

        int startIndex = -1, endIndex = -1;
        for (int i = 0; i < staniceNaPruzi.size(); i++) {
            Stanica stanica = staniceNaPruzi.get(i);
            if (stanica.getNaziv().equals(start.getNaziv())) {
                startIndex = i;
            }
            if (stanica.getNaziv().equals(end.getNaziv())) {
                endIndex = i;
            }
        }

        if (startIndex == -1 || endIndex == -1) {
            return 0.0;
        }

        double udaljenost = 0.0;

        if (startIndex < endIndex) {
            for (int i = startIndex; i <= endIndex; i++) {
                if (i == startIndex) {
                    continue;
                }
                udaljenost += staniceNaPruzi.get(i).getUdaljenost();
            }
        } else {
            for (int i = endIndex; i <= startIndex; i++) {
                if (i == endIndex) {
                    continue;
                }
                udaljenost += staniceNaPruzi.get(i).getUdaljenost();
            }
        }

        return udaljenost;
    }
    
    public static String getNazivDanaIzZodDatoteke(String oznakaDana) {
        for (Map<String, String> red : zodDatoteka.dohvatiPodatke()) {
            String oznakaDanaIzZod = red.get("Oznaka dana");
            if (oznakaDana.equals(oznakaDanaIzZod)) {
                return red.get("Dani vožnje");
            }
        }
        return "";
    }
    
    public String getIspisPolaznaStanica() {
        return originalPolaznaStanica.getNaziv();
    }

    public String getIspisZavrsnaStanica() {
        return originalZavrsnaStanica.getNaziv();
    }
    
    public void ispisStanicaPoPrugama(String oznakaPruge, String polaznaStanica, String zavrsnaStanica, String smjer) {
        Map<String, List<Stanica>> stanicePoPrugama = zsDatoteka.getStanicePoPrugama();
        List<Stanica> staniceNaPruzi = stanicePoPrugama.get(oznakaPruge);

        if (staniceNaPruzi == null || staniceNaPruzi.isEmpty()) {
            System.out.println("Nema stanica za prugu s oznakom: " + oznakaPruge);
            return;
        }

        boolean obrnutiSmjer = "O".equals(smjer);

        List<Stanica> staniceZaIspis = new ArrayList<>();
        boolean ispisivanje = false;

        for (int i = (obrnutiSmjer ? staniceNaPruzi.size() - 1 : 0); 
             (obrnutiSmjer ? i >= 0 : i < staniceNaPruzi.size()); 
             i = (obrnutiSmjer ? i - 1 : i + 1)) {
            
            Stanica stanica = staniceNaPruzi.get(i);

            if (stanica.getNaziv().equals(polaznaStanica)) {
                ispisivanje = true;
            }

            if (ispisivanje) {
                staniceZaIspis.add(stanica);
            }

            if (stanica.getNaziv().equals(zavrsnaStanica)) {
                break;
            }
        }

        if (obrnutiSmjer) {
            Collections.reverse(staniceZaIspis);
        }

        System.out.println("\nIspis tablice:");
        System.out.printf("%-20s %-20s %-20s%n", "Stanica", "Oznaka pruge", "Udaljenost od prve stanice (km)");
        System.out.println("---------------------------------------------------------------");

        double ukupnaUdaljenost = 0.0;
        for (Stanica stanica : staniceZaIspis) {
            ukupnaUdaljenost += stanica.getUdaljenost();
            System.out.printf("%-20s %-20s %-20.2f%n", stanica.getNaziv(), oznakaPruge, ukupnaUdaljenost);
        }
        System.out.println();
    }
    
    public List<String> dohvatiMedjustanice(String oznakaPruge) {
        List<String> medjustanice = new ArrayList<>();

        Map<String, List<Stanica>> stanicePoPrugama = zsDatoteka.getStanicePoPrugama();
        Set<String> posjeceneStanice = new HashSet<>();

        for (Map.Entry<String, List<Stanica>> entry : stanicePoPrugama.entrySet()) {
            String pruga = entry.getKey();
            List<Stanica> staniceNaPruzi = entry.getValue();

            if (!pruga.equals(oznakaPruge) && !jePrugaPovezana(oznakaPruge, pruga)) {
                continue;
            }

            boolean izmeduStanica = false;
            for (Stanica stanica : staniceNaPruzi) {
                if (stanica.getNaziv().equals(polaznaStanica.getNaziv())) {
                    izmeduStanica = true;
                    continue;
                }
                if (stanica.getNaziv().equals(zavrsnaStanica.getNaziv())) {
                    break;
                }
                if (izmeduStanica && !posjeceneStanice.contains(stanica.getNaziv())) {
                    medjustanice.add(stanica.getNaziv());
                    posjeceneStanice.add(stanica.getNaziv());
                }
            }
        }

        return medjustanice;
    }


    private boolean jePrugaPovezana(String pruga1, String pruga2) {
        List<String> prijelazi = dohvatiPrijelaze();

        return prijelazi.contains(pruga2);
    }

    public List<String> dohvatiSveStaniceNaEtapi(String oznakaPruge) {
        List<String> stanice = new ArrayList<>();

        stanice.add(getIspisPolaznaStanica());

        stanice.addAll(dohvatiMedjustanice(oznakaPruge));

        List<String> prijelazi = dohvatiPrijelaze();
        for (String prijelaz : prijelazi) {
            if (!stanice.contains(prijelaz)) {
                stanice.add(prijelaz);
            }
        }

        stanice.add(getIspisZavrsnaStanica());

        return stanice;
    }

    public void pretraziPutIzmeduStanica(String polaznaStanica, String odredisnaStanica, List<Etape> etape, ZsDatoteka zsDatoteka) {
        Graf graf = new Graf();
        graf.izgradnjaGrafaIzEtapa(etape, zsDatoteka);
        List<String> put = graf.bfs(polaznaStanica, odredisnaStanica);
        if (!put.isEmpty()) {
            System.out.println("Put između stanica: " + put);
        }
    }

    public List<LeafStanica> getStaniceNaEtapi(String oznakaPruge, ZsDatoteka zsDatoteka) {
        List<LeafStanica> staniceNaEtapi = new ArrayList<>();
        
        Map<String, List<Stanica>> stanicePoPrugama = zsDatoteka.getStanicePoPrugama();
        List<Stanica> staniceNaPruzi = stanicePoPrugama.get(oznakaPruge);

        if (staniceNaPruzi == null || staniceNaPruzi.isEmpty()) {
            return staniceNaEtapi;
        }

        boolean obrnutiSmjer = "O".equals(smjer);
        boolean ispisivanje = false;

        for (int i = (obrnutiSmjer ? staniceNaPruzi.size() - 1 : 0); 
             (obrnutiSmjer ? i >= 0 : i < staniceNaPruzi.size()); 
             i = (obrnutiSmjer ? i - 1 : i + 1)) {
            
            Stanica stanica = staniceNaPruzi.get(i);

            if (stanica.getNaziv().equals(polaznaStanica.getNaziv())) {
                ispisivanje = true;
            }

            if (ispisivanje) {
                staniceNaEtapi.add(new LeafStanica(stanica.getNaziv()));
            }

            if (stanica.getNaziv().equals(zavrsnaStanica.getNaziv())) {
                break;
            }
        }

        return staniceNaEtapi;
    }


    public List<String> dohvatiPrijelaze() {
        List<String> prijelazi = new ArrayList<>();
        
        Map<String, List<Stanica>> stanicePoPrugama = zsDatoteka.getStanicePoPrugama();
        
        Set<String> posjeceneStanice = new HashSet<>();

        for (Map.Entry<String, List<Stanica>> entry : stanicePoPrugama.entrySet()) {
            String pruga = entry.getKey();
            List<Stanica> staniceNaPruzi = entry.getValue();
            
            if (!pruga.equals(oznakaPruge)) {
                continue;
            }
            
            boolean obrnutiSmjer = "O".equals(smjer);
            boolean izmeduStanica = false;

            for (int i = (obrnutiSmjer ? staniceNaPruzi.size() - 1 : 0); 
                 (obrnutiSmjer ? i >= 0 : i < staniceNaPruzi.size()); 
                 i = (obrnutiSmjer ? i - 1 : i + 1)) {
                    
                Stanica trenutnaStanica = staniceNaPruzi.get(i);
                
                if (trenutnaStanica.getNaziv().equals(polaznaStanica.getNaziv())) {
                    izmeduStanica = true;
                    continue;
                }
                
                if (trenutnaStanica.getNaziv().equals(zavrsnaStanica.getNaziv())) {
                    break;
                }
                
                if (izmeduStanica) {
                    if (!posjeceneStanice.contains(trenutnaStanica.getNaziv())) {
                        prijelazi.add(trenutnaStanica.getNaziv());
                        posjeceneStanice.add(trenutnaStanica.getNaziv());
                    }
                }
            }
        }
        
        return prijelazi;
    }

    public List<String> dohvatiZajednickeStanice(Etape drugaEtapa, ZsDatoteka zsDatoteka) {
        List<LeafStanica> stanicePrvaEtapa = this.getStaniceNaEtapi(this.getOznakaPruge(), zsDatoteka);
        List<LeafStanica> staniceDrugaEtapa = drugaEtapa.getStaniceNaEtapi(drugaEtapa.getOznakaPruge(), zsDatoteka);

        List<String> zajednickeStanice = new ArrayList<>();
        for (LeafStanica stanica1 : stanicePrvaEtapa) {
            for (LeafStanica stanica2 : staniceDrugaEtapa) {
                if (stanica1.getNaziv().equals(stanica2.getNaziv())) {
                    zajednickeStanice.add(stanica1.getNaziv());
                }
            }
        }
        return zajednickeStanice;
    }

    
}
