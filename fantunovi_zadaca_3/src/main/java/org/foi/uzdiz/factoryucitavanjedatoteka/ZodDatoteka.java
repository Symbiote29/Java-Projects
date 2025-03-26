package org.foi.uzdiz.factoryucitavanjedatoteka;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.foi.uzdiz.singleton.UpravljacGresaka;

public class ZodDatoteka implements Datoteka {

    private static final int OCEKIVANI_BROJ_STUPACA = 2;
    private List<Map<String, String>> podaci = new ArrayList<>();
    private List<String> greske = new ArrayList<>();
    private final UpravljacGresaka upravljacGresaka;

    public ZodDatoteka(UpravljacGresaka upravljacGresaka) {
    	this.upravljacGresaka = upravljacGresaka;
	}

	@Override
    public void ucitajDatoteku(String putanja) {
        try (BufferedReader br = new BufferedReader(new FileReader(putanja))) {
            String linija;
            int redniBroj = 0;
            boolean zaglavljeProvjereno = false;

            while ((linija = br.readLine()) != null) {
                redniBroj++;

                if (linija.trim().isEmpty() || linija.startsWith("#")) {
                    continue;
                }

                String[] atributi = linija.split(";");

                if (atributi[0] == null || atributi[0].trim().isEmpty()) {
                    upravljacGresaka.dodajGresku("ZOD Greska | Red " + redniBroj + " | Prvi stupac je empty ili null.");
                    continue;
                }

                if (atributi.length < OCEKIVANI_BROJ_STUPACA) {
                    atributi = Arrays.copyOf(atributi, OCEKIVANI_BROJ_STUPACA);
                    for (int i = atributi.length; i < OCEKIVANI_BROJ_STUPACA; i++) {
                        atributi[i] = "";
                    }
                }

                if (!zaglavljeProvjereno) {
                    if (!validirajHeader(atributi)) {
                        upravljacGresaka.dodajGresku("ZOD Greska | Red " + redniBroj + " | Neispravno zaglavlje i broj stupaca. Broj stupaca: " + atributi.length);
                        return;
                    }
                    zaglavljeProvjereno = true;
                    continue;
                }

                if (atributi.length >= OCEKIVANI_BROJ_STUPACA && validirajAtribute(atributi, redniBroj)) {
                    podaci.add(mapirajPodatke(atributi));
                } else {
                    upravljacGresaka.dodajGresku("Greska | Red " + redniBroj + " | Neispravan broj stupaca. Broj stupaca: " + atributi.length);
                }
            }
        } catch (IOException e) {
            upravljacGresaka.dodajGresku("ERROR | Ucitavanje datoteke " + putanja + " | " + e.getMessage());
        } finally {
            if (!greske.isEmpty()) {
                System.out.println("Pronađene greške:");
                System.out.println(upravljacGresaka.prikaziGreske());
            }
        }
    }

    private boolean validirajHeader(String[] header) {
        if (header[0].startsWith("\uFEFF")) {
            header[0] = header[0].substring(1);
        }

        if (header.length < OCEKIVANI_BROJ_STUPACA) {
            upravljacGresaka.dodajGresku("ZOD Greska | Neispravan broj stupaca u zaglavlju. Broj stupaca: " + header.length);
            return false;
        }

        return header[0].trim().equalsIgnoreCase("Oznaka dana") &&
               header[1].trim().equalsIgnoreCase("Dani vožnje");
    }

    private boolean validirajAtribute(String[] atributi, int redniBroj) {
        try {
            if (!atributi[0].isEmpty() && !atributi[0].matches("\\d+")) {
                upravljacGresaka.dodajGresku("Greska | Red " + redniBroj + " | Oznaka dana nije cijeli broj: " + atributi[0]);
                return false;
            }

        } catch (Exception e) {
            upravljacGresaka.dodajGresku("Greska | Red " + redniBroj + " | Pogreška prilikom validacije: " + e.getMessage());
            return false;
        }

        return true;
    }

    private Map<String, String> mapirajPodatke(String[] atributi) {
        Map<String, String> redPodaci = new HashMap<>();
        redPodaci.put("Oznaka dana", atributi[0] != null ? atributi[0].trim() : "");
        redPodaci.put("Dani vožnje", atributi[1] != null ? atributi[1].trim() : "");
        return redPodaci;
    }

    public void ispisPodataka() {
        if (podaci.isEmpty()) {
            upravljacGresaka.dodajGresku("Nema podataka za ispis.");
            return;
        }

        System.out.printf("%-15s %-30s%n", "Oznaka dana", "Dani vožnje");
        System.out.println("=".repeat(45));

        for (Map<String, String> red : podaci) {
            System.out.printf("%-15s %-30s%n",
                    red.get("Oznaka dana"),
                    red.get("Dani vožnje"));
        }
    }

    public List<Map<String, String>> dohvatiPodatke() {
        return podaci;
    }
}
