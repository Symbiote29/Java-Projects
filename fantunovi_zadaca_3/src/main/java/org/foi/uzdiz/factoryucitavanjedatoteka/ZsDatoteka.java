package org.foi.uzdiz.factoryucitavanjedatoteka;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.foi.uzdiz.builderstanica.Stanica;
import org.foi.uzdiz.builderstanica.StanicaBuilder;
import org.foi.uzdiz.factorypruga.LokalnaPruga;
import org.foi.uzdiz.factorypruga.MedunarodnaPruga;
import org.foi.uzdiz.factorypruga.Pruga;
import org.foi.uzdiz.factorypruga.RegionalnaPruga;
import org.foi.uzdiz.fantunovi_zadaca_3.Graf;
import org.foi.uzdiz.singleton.TvrtkaZeljeznickogPrometa;
import org.foi.uzdiz.singleton.UpravljacGresaka;

public class ZsDatoteka implements PrugaDatoteka  {
    private static final int OCEKIVANI_BROJ_STUPACA = 17;
    private static final String[] OCEKIVANA_ZAGLAVLJA = {
        "Stanica", "Oznaka pruge", "Vrsta stanice", "Status stanice",
        "Putnici ul/iz", "Roba ut/ist", "Kategorija pruge", "Broj perona",
        "Vrsta pruge", "Broj kolosjeka", "DO po osovini", "DO po duznom m",
        "Status pruge", "Dužina", "Vrijeme normalni vlak", "Vrijeme ubrzani vlak", "Vrijeme brzi vlak"
    };

    private List<String> greske = new ArrayList<>();
    private List<Pruga> pruge;
    private List<Stanica> stanice = new ArrayList<>();
    private Graf graf;
     
    private final UpravljacGresaka upravljacGresaka;
    Map<String, List<Stanica>> stanicePoPrugama = new HashMap<>();
    
    public ZsDatoteka(UpravljacGresaka upravljacGresaka) {
        this.graf = new Graf();
        this.upravljacGresaka = upravljacGresaka;
        this.pruge = new ArrayList<>();
    }
    
    private void kreirajGraf() {
        Set<String> ispisanePruge = new HashSet<>();
        
        for (Pruga pruga : pruge) {
            String oznakaPruge = pruga.getOznaka();

            if (!ispisanePruge.contains(oznakaPruge)) {
                List<Stanica> staniceNaPrugi = getStaniceZaPrugu(oznakaPruge);

                for (int i = 0; i < staniceNaPrugi.size(); i++) {
                    Stanica trenutnaStanica = staniceNaPrugi.get(i);
                    String oznakaStanice = trenutnaStanica.getNaziv();
                    String vrstaStanice = trenutnaStanica.getVrsta();
                    
                    graf.addNode(oznakaStanice, vrstaStanice);

                    if (i > 0) {
                        Stanica prethodnaStanica = staniceNaPrugi.get(i - 1);
                        String oznakaPrethodneStanice = prethodnaStanica.getNaziv();
                        double udaljenost = trenutnaStanica.getUdaljenost();
                        
                        graf.addEdge(oznakaPrethodneStanice, oznakaStanice, udaljenost);
                    }
                }
                
                ispisanePruge.add(oznakaPruge);
            }
        }
    }

    public Graf getGraf() {
        return graf;
    }
    
    @Override
    public void ucitajDatoteku(String putanja) {
    	if (this.pruge == null) {
    	    this.pruge = new ArrayList<>();
    	}
    	
        try (BufferedReader br = new BufferedReader(new FileReader(putanja))) {
            String linija;
            int redniBroj = 0;
            boolean zaglavljeProvjereno = false;

            while ((linija = br.readLine()) != null) {
                redniBroj++;

                if (linija.replace(";", "").trim().isEmpty() || linija.startsWith("#")) {
                    continue;
                }

                String[] atributi = linija.split(";");
                
                if (atributi.length < OCEKIVANI_BROJ_STUPACA) {
                    atributi = Arrays.copyOf(atributi, OCEKIVANI_BROJ_STUPACA);
                    for (int i = atributi.length; i < OCEKIVANI_BROJ_STUPACA; i++) {
                        atributi[i] = "";
                    }
                }

                if (!zaglavljeProvjereno) {
                    if (!provjeriZaglavlje(linija)) {
                    	upravljacGresaka.dodajGresku("Stanice.csv: Neispravno zaglavlje i broj stupaca");
                        logirajGresku(redniBroj, "Neispravno zaglavlje i broj stupaca", atributi.length);
                        return;
                    }
                    zaglavljeProvjereno = true;
                    continue;
                }

                if (atributi.length >= OCEKIVANI_BROJ_STUPACA && validirajAtribute(atributi, redniBroj)) {

                    Stanica stanica = kreirajStanica(atributi);
                    if (stanica != null) {
                        stanice.add(stanica);
                        Pruga pruga = kreirajPrugu(atributi);
                        if (pruga != null) {
                            pruge.add(pruga);
                        }
                    }
                    
                    this.pruge = getPruge();
                    kreirajGraf();
                    
                } else {
                	upravljacGresaka.dodajGresku("Stanice.csv: Nema ispravan broj stupaca");
                    logirajGresku(redniBroj, "Nema ispravan broj stupaca", atributi.length);
                }
            }
        } catch (IOException e) {
        	upravljacGresaka.dodajGresku("Stanice.csv: Greska kod ucitavanja ZS datoteke");
        } finally {
            printajGreske();
        }
    }

    private boolean provjeriZaglavlje(String linija) {
        linija = linija.replaceAll("^\uFEFF", "");
        String[] zaglavlja = linija.split(";");
        if (zaglavlja.length != OCEKIVANI_BROJ_STUPACA) {
            return false;
        }
        for (int i = 0; i < OCEKIVANI_BROJ_STUPACA; i++) {
            if (!zaglavlja[i].trim().equalsIgnoreCase(OCEKIVANA_ZAGLAVLJA[i])) {
                return false;
            }
        }
        return true;
    }

    private void logirajGresku(int redniBroj, String poruka, int stupaca) {
        String greska = String.format("Greska | Red %d | %s. Broj stupaca: %d", redniBroj, poruka, stupaca);
        System.err.println(greska);
        greske.add(greska);
    }

    private void printajGreske() {
        if (!greske.isEmpty()) {
            System.out.println("Greske:");
            for (String greska : greske) {
                System.out.println(greska);
            }
        }
    }

    private boolean validirajAtribute(String[] atributi, int redniBroj) {
        try {
            if (!atributi[4].equals("DA") && !atributi[4].equals("NE")) {
            	upravljacGresaka.dodajGresku("Stanice.csv: Neispravna vrijednost za Putnici ul/iz");
                logirajGresku(redniBroj, "Neispravna vrijednost za Putnici ul/iz", 4);
                return false;
            }
            if (!atributi[5].equals("DA") && !atributi[5].equals("NE")) {
            	upravljacGresaka.dodajGresku("Stanice.csv: Neispravna vrijednost za Roba ut/ist");
                logirajGresku(redniBroj, "Neispravna vrijednost za Roba ut/ist", 5);
                return false;
            }

            int brojPerona = Integer.parseInt(atributi[7]);
            int brojKolosjeka = Integer.parseInt(atributi[9]);
            double doPoOsovini = Double.parseDouble(atributi[10].trim().replace(",", "."));
            double doPoDuznomM = Double.parseDouble(atributi[11].trim().replace(",", "."));
            int duzina = Integer.parseInt(atributi[13]);

            if (brojPerona < 0 || brojKolosjeka < 0 || doPoOsovini < 0 || doPoDuznomM < 0 || duzina < 0) {
            	upravljacGresaka.dodajGresku("Stanice.csv: Negativna vrijednost");
                logirajGresku(redniBroj, "Negativna vrijednost", 0);
                return false;
            }

            if (atributi[8].equals("E") && doPoOsovini <= 0) {
            	upravljacGresaka.dodajGresku("Stanice.csv: DO po osovini mora biti > 0 za elektricnu prugu");
                logirajGresku(redniBroj, "DO po osovini mora biti > 0 za elektricnu prugu", 8);
                return false;
            }

            return true;
        } catch (NumberFormatException e) {
        	upravljacGresaka.dodajGresku("Stanice.csv: Pogreska u parsiranju brojcane vrijednosti: " + e.getMessage());
            logirajGresku(redniBroj, "Pogreska u parsiranju brojcane vrijednosti: " + e.getMessage(), 0);
            return false;
        }
    }
    
    private Stanica kreirajStanica(String[] atributi) {
    	StanicaBuilder builder = new StanicaBuilder();
        return builder.setNaziv(atributi[0])
                .setOznakaPruge(atributi[1])
                .setVrsta(atributi[2])
                .setStatus(atributi[3])
                .setBrojPerona(Integer.parseInt(atributi[7]))
                .setAktivnosti(atributi[4] + ", " + atributi[5])
                .setUdaljenost(Integer.parseInt(atributi[13]))
                .build();
    }
    
    private Pruga kreirajPrugu(String[] atributi) {
        try {
            String oznaka = atributi[1];
            String kategorijaPruge = atributi[6];
            String vrstaPruga = atributi[8];
            int brojKolosijeka = Integer.parseInt(atributi[9]);
            double doPoOsovini = Double.parseDouble(atributi[10].trim().replace(",", "."));
            double doPoDuznomM = Double.parseDouble(atributi[11].trim().replace(",", "."));
            String status = atributi[12];

            switch (kategorijaPruge) {
                case "L":
                    return new LokalnaPruga(oznaka, kategorijaPruge, vrstaPruga, brojKolosijeka, doPoOsovini, doPoDuznomM, status);
                case "M":
                    return new MedunarodnaPruga(oznaka, kategorijaPruge, vrstaPruga, brojKolosijeka, doPoOsovini, doPoDuznomM, status);
                case "R":
                    return new RegionalnaPruga(oznaka, kategorijaPruge, vrstaPruga, brojKolosijeka, doPoOsovini, doPoDuznomM, status);
                default:
                	upravljacGresaka.dodajGresku("Stanice.csv: Nepoznata kategorija pruge");
                    System.err.printf("Upozorenje: Nepoznata kategorija pruge '%s' za oznaku '%s'%n", kategorijaPruge, oznaka);
                    return null;
            }
        } catch (NumberFormatException e) {
        	upravljacGresaka.dodajGresku("Stanice.csv: Greska u parsiranju brojcane vrijednosti: " + e.getMessage());
            return null;
        }
    }
    
    @Override
    public List<Pruga> getPruge() {
        return this.pruge;
    }
    
    public List<Stanica> getStanice() {
        return this.stanice;
    }

    public Map<String, List<Stanica>> getStanicePoPrugama() {
        return this.stanicePoPrugama;
    }

    public void ispisStanicaPoPrugama(String oznakaPruge) {
        Map<String, Double> ukupnaDuljinaPoPrugama = new HashMap<>();

        for (Stanica stanica : stanice) {
            String oznakaPruga = stanica.getOznakaPruge();
            ukupnaDuljinaPoPrugama.put(oznakaPruga, ukupnaDuljinaPoPrugama.getOrDefault(oznakaPruga, 0.0) + stanica.getUdaljenost());
            stanicePoPrugama.computeIfAbsent(oznakaPruga, k -> new ArrayList<>()).add(stanica);
        }

        Set<String> ispisanePruge = new HashSet<>();

        System.out.printf("%-15s %-20s %-20s %-20s%n", "Oznaka pruge", "Pocetna stanica", "Zavrsna stanica", "Ukupna udaljenost (m)");
        System.out.println("--------------------------------------------------------------------------");

        for (Pruga pruga : pruge) {
            String oznakaPrugaPruga = pruga.getOznaka();

            if (!ispisanePruge.contains(oznakaPrugaPruga) && (oznakaPruge == null || oznakaPruge.equals(oznakaPrugaPruga))) {
                List<Stanica> staniceNaPruzi = stanicePoPrugama.getOrDefault(oznakaPrugaPruga, new ArrayList<>());

                if (!staniceNaPruzi.isEmpty()) {
                    Stanica pocetnaStanica = staniceNaPruzi.get(0);
                    Stanica zavrsnaStanica = staniceNaPruzi.get(staniceNaPruzi.size() - 1);

                    double ukupnaUdaljenost = ukupnaDuljinaPoPrugama.getOrDefault(oznakaPrugaPruga, 0.0);

                    System.out.printf("%-15s %-20s %-20s %-20.2f%n",
                            oznakaPrugaPruga,
                            pocetnaStanica.getNaziv(),
                            zavrsnaStanica.getNaziv(),
                            ukupnaUdaljenost);
                } else {
                    System.out.printf("%-15s %-20s %-20s %-20s%n", oznakaPrugaPruga, "N/A", "N/A", "0.00");
                }

                ispisanePruge.add(oznakaPrugaPruga);
            }
        }
    }
    
    public List<Stanica> getStaniceZaPrugu(String oznakaPruge) {
        List<Stanica> staniceZaPrugu = new ArrayList<>();

        for (Stanica stanica : stanice) {
            if (stanica.getOznakaPruge().equals(oznakaPruge)) {
                staniceZaPrugu.add(stanica);
            }
        }
        
        return staniceZaPrugu;
    }
}
