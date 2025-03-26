package org.foi.uzdiz.factoryucitavanjedatoteka;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.foi.uzdiz.builderkompozicija.Kompozicija;
import org.foi.uzdiz.builderkompozicija.KompozicijaBuilder;
import org.foi.uzdiz.builderkompozicija.KompozicijaDirector;
import org.foi.uzdiz.singleton.UpravljacGresaka;

public class ZkDatoteka implements Datoteka {
    private static final int OCEKIVANI_BROJ_STUPACA = 3;
    private static final Set<String> DOZVOLJENE_ULOGE = Set.of("P", "V");
    private Map<String, List<String>> podaci = new HashMap<>();
    private Map<String, Kompozicija> kompozicije = new HashMap<>();
    private final UpravljacGresaka upravljacGresaka;

    public ZkDatoteka(UpravljacGresaka upravljacGresaka) {
    	this.upravljacGresaka = upravljacGresaka;
	}

	public void ucitajDatoteku(String putanja) {
        try (BufferedReader br = new BufferedReader(new FileReader(putanja))) {

            String linija = br.readLine();
            if (linija == null) {
            	upravljacGresaka.dodajGresku("Kompozicije.csv: Prazna datoteka");
                return;
            }

            linija = linija.replaceAll("^\uFEFF", "");
            if (!validirajHeader(linija.split(";"))) {
            	upravljacGresaka.dodajGresku("Kompozicije.csv: Neispravno zaglavlje");
                return;
            }

            Map<String, Set<String>> hashMapaKompozicija = new HashMap<>();
            obradiLinijeDatoteke(br, hashMapaKompozicija);
            
            buildKompozicije();

        } catch (IOException e) {
        	upravljacGresaka.dodajGresku("Kompozicije.csv: Greska pri ucitavanju datoteke" + e.getMessage());
        }
    }

    private boolean validirajHeader(String[] header) {
        if (header.length != OCEKIVANI_BROJ_STUPACA) {
        	upravljacGresaka.dodajGresku("Kompozicije.csv: Neispravan broj stupaca u zaglavlju");
            return false;
        }
        return header[0].trim().equalsIgnoreCase("Oznaka") &&
               header[1].trim().equalsIgnoreCase("Oznaka prijevoznog sredstva") &&
               header[2].trim().equalsIgnoreCase("Uloga");
    }

    private void obradiLinijeDatoteke(BufferedReader br, Map<String, Set<String>> compositionMap) throws IOException {
        String zadnjaOznaka = "";
        String linija;
        
        while ((linija = br.readLine()) != null) {
            if (linija.trim().isEmpty()) continue;

            linija = linija.replaceAll("^\uFEFF", "");
            String[] atributi = linija.split(";");
            
            if (atributi.length != OCEKIVANI_BROJ_STUPACA) continue;

            String oznaka = atributi[0].trim();
            String oznakaPrijevoznog = atributi[1].trim();
            String uloga = atributi[2].trim();

            if (!zadnjaOznaka.isEmpty() && !oznaka.equals(zadnjaOznaka)) {
                System.out.println();
            }
            zadnjaOznaka = oznaka;

            if (validirajAtribute(oznaka, oznakaPrijevoznog, uloga, compositionMap)) {
                podaci.computeIfAbsent(oznaka, k -> new ArrayList<>())
                    .add(oznakaPrijevoznog + "," + uloga);
            }
        }
    }
    
    private boolean validirajAtribute(String oznaka, String oznakaPrijevoznog, String uloga,
                                       Map<String, Set<String>> hashMapaKompozicija) {
        Set<String> setVozila = hashMapaKompozicija.computeIfAbsent(oznaka, k -> new HashSet<>());

        if (!setVozila.add(oznakaPrijevoznog)) {
        	upravljacGresaka.dodajGresku("Kompozicije.csv: Duplicirana oznaka prijevoznog sredstva" + oznakaPrijevoznog + " za kompoziciju: " + oznaka);
            return false;
        }

        if (!DOZVOLJENE_ULOGE.contains(uloga)) {
        	upravljacGresaka.dodajGresku("Kompozicije.csv: Neispravna uloga");
            return false;
        }

        return true;
    }

    private void buildKompozicije() {
        KompozicijaBuilder builder = new KompozicijaBuilder();
        KompozicijaDirector director = new KompozicijaDirector(builder);
        kompozicije = director.ucitajKompozicije(podaci);
    }

    public Map<String, Kompozicija> dohvatiKompozicije() {
        return kompozicije;
    }
}
