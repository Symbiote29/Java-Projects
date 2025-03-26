package org.foi.uzdiz.factoryucitavanjedatoteka;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.foi.uzdiz.builderstanica.Stanica;
import org.foi.uzdiz.compositevoznired.Etape;
import org.foi.uzdiz.compositevoznired.LeafStanica;
import org.foi.uzdiz.compositevoznired.Raspored;
import org.foi.uzdiz.compositevoznired.Vlak;
import org.foi.uzdiz.factorypruga.Pruga;
import org.foi.uzdiz.managers.PrugaManager;
import org.foi.uzdiz.mediatorvoznired.ConcreteVozniRedMediator;
import org.foi.uzdiz.singleton.UpravljacGresaka;

public class ZvrDatoteka implements Datoteka {

    private static final int OCEKIVANI_BROJ_STUPACA = 9;
    private static final String[] OCEKIVANA_ZAGLAVLJA = {
            "Oznaka pruge", "Smjer", "Polazna stanica", "Odredišna stanica",
            "Oznaka vlaka", "Vrsta vlaka", "Vrijeme polaska", "Trajanje vožnje", "Oznaka dana"
        };
    private List<Map<String, String>> podaci = new ArrayList<>();
    private List<String> greske = new ArrayList<>();
    private final UpravljacGresaka upravljacGresaka;
    private PrugaManager prugaManager;
    private ZsDatoteka zsDatoteka;
    private Raspored raspored;
    private ZodDatoteka zodDatoteka;
    private ConcreteVozniRedMediator mediator;

    public ZvrDatoteka(UpravljacGresaka upravljacGresakaka) {
        this.upravljacGresaka = upravljacGresakaka;
    }

    public void setPrugaManager(PrugaManager prugaManager) {
        this.prugaManager = prugaManager;
    }

    public void setZsDatoteka(ZsDatoteka zsDatoteka) {
        this.zsDatoteka = zsDatoteka;
    }

    public void setRaspored(Raspored raspored) {
        this.raspored = raspored;
    }

    public void setZodDatoteka(ZodDatoteka zodDatoteka) {
        this.zodDatoteka = zodDatoteka;
    }

    @Override
    public void ucitajDatoteku(String putanja) {
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
                    if (!validirajHeader(atributi)) {
                        upravljacGresaka.dodajGresku("ZVR Greska | Red " + redniBroj + " | Neispravno zaglavlje i broj stupaca. Broj stupaca: " + atributi.length);
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
            upravljacGresaka.dodajGresku(String.format("ERROR | Ucitavanje datoteke %s | %s%n", putanja, e.getMessage()));
        } finally {
            if (!greske.isEmpty()) {
                System.out.println("Pronađene greške:");
                System.out.println(upravljacGresaka.prikaziGreske());
            }
        }
    }

    private boolean validirajHeader(String[] header) {
        if (header.length < OCEKIVANI_BROJ_STUPACA) {
            upravljacGresaka.dodajGresku("ZVR Greska | Neispravan broj stupaca u zaglavlju. Broj stupaca: " + header.length);
            return false;
        }

        if (header[0].startsWith("\uFEFF")) {
            header[0] = header[0].substring(1);
        }

        for (int i = 0; i < OCEKIVANI_BROJ_STUPACA; i++) {
            String praviH = header[i].trim();
            String ocekivaniH = OCEKIVANA_ZAGLAVLJA[i];

            if (!praviH.equalsIgnoreCase(ocekivaniH)) {
                upravljacGresaka.dodajGresku("ZVR Greska | Neispravan naziv stupca: Očekivano '" + ocekivaniH + "', dobiveno '" + praviH + "'");
                return false;
            }
        }

        return true;
    }

    private boolean validirajAtribute(String[] atributi, int redniBroj) {
        try {
            if (atributi[0].isEmpty()) {
                upravljacGresaka.dodajGresku("Greska | Red " + redniBroj + " | Oznaka pruge je prazna ili null.");
                return false;
            }

            if (atributi[1].isEmpty()) {
                upravljacGresaka.dodajGresku("Greska | Red " + redniBroj + " | Smjer je prazno ili null.");
                return false;
            }

            if (atributi[4].isEmpty()) {
                upravljacGresaka.dodajGresku("Greska | Red " + redniBroj + " | Oznaka vlaka je prazna ili null.");
                return false;
            }

            if (atributi[6].isEmpty()) {
                upravljacGresaka.dodajGresku("Greska | Red " + redniBroj + " | Vrijeme polaska je prazno ili null.");
                return false;
            }

            if (atributi[7].isEmpty()) {
                upravljacGresaka.dodajGresku("Greska | Red " + redniBroj + " | Trajanje vožnje je prazno ili null.");
                return false;
            }

            if (!atributi[6].matches("\\d{1,2}:\\d{2}")) {
                upravljacGresaka.dodajGresku("Greska | Red " + redniBroj + " | Vrijeme polaska nije u ispravnom formatu: " + atributi[6]);
                return false;
            }

            if (!atributi[7].matches("(\\d{1,2}:\\d{2}|\\d{1,2})")) {
                upravljacGresaka.dodajGresku("Greska | Red " + redniBroj + " | Trajanje vožnje nije u ispravnom formatu: " + atributi[7]);
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
        redPodaci.put("Oznaka pruge", safeTrim(atributi[0]));
        redPodaci.put("Smjer", safeTrim(atributi[1]));
        redPodaci.put("Polazna stanica", safeTrim(atributi[2]));
        redPodaci.put("Odredišna stanica", safeTrim(atributi[3]));
        redPodaci.put("Oznaka vlaka", safeTrim(atributi[4]));
        redPodaci.put("Vrsta vlaka", safeTrim(atributi[5]));
        redPodaci.put("Vrijeme polaska", safeTrim(atributi[6]));
        redPodaci.put("Trajanje vožnje", safeTrim(atributi[7]));
        redPodaci.put("Oznaka dana", safeTrim(atributi[8]));
        return redPodaci;
    }

    private String safeTrim(String value) {
        return value == null ? "" : value.trim();
    }

    public void ispisPodataka() {
        if (podaci.isEmpty()) {
            System.out.println("Nema podataka za ispis.");
            return;
        }

        System.out.printf("%-15s %-10s %-20s %-20s %-15s %-15s %-15s %-15s %-15s%n",
                "Oznaka pruge", "Smjer", "Polazna stanica", "Odredišna stanica", 
                "Oznaka vlaka", "Vrsta vlaka", "Vrijeme polaska", "Trajanje vožnje", "Oznaka dana");
        System.out.println("=".repeat(135));

        for (Map<String, String> red : podaci) {
            System.out.printf("%-15s %-10s %-20s %-20s %-15s %-15s %-15s %-15s %-15s%n",
                    red.get("Oznaka pruge"),
                    red.get("Smjer"),
                    red.get("Polazna stanica"),
                    red.get("Odredišna stanica"),
                    red.get("Oznaka vlaka"),
                    red.get("Vrsta vlaka"),
                    red.get("Vrijeme polaska"),
                    red.get("Trajanje vožnje"),
                    red.get("Oznaka dana"));
        }
    }

    public List<Map<String, String>> dohvatiPodatke() {
        return podaci;
    }

    public void kreirajVozniRed() {
        Etape.kreirajEtapeIzPodataka(podaci, zsDatoteka, raspored, zodDatoteka, mediator);
    }
    
    public String dohvatiVrstuVlakaPoOznaci(String oznakaVlaka) {
        for (Map<String, String> red : podaci) {
            String oznakaVlak = red.get("Oznaka vlaka").trim();
            if (oznakaVlaka.trim().equalsIgnoreCase(oznakaVlak)) {
                String vrstaVlaka = red.get("Vrsta vlaka");
                if (vrstaVlaka == null || vrstaVlaka.trim().isEmpty()) {
                    vrstaVlaka = "n";
                }
                return vrstaVlaka;
            }
        }
        System.out.println("Oznaka vlaka nije pronađena: " + oznakaVlaka);
        return null;
    }

    public void setMediator(ConcreteVozniRedMediator mediator) {
        this.mediator = mediator;
    }
    
}
