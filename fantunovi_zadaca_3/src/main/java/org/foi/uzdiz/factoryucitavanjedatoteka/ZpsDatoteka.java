package org.foi.uzdiz.factoryucitavanjedatoteka;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.foi.uzdiz.buildervozilo.Vozilo;
import org.foi.uzdiz.buildervozilo.VoziloBuilder;
import org.foi.uzdiz.buildervozilo.VoziloDirector;
import org.foi.uzdiz.singleton.UpravljacGresaka;

public class ZpsDatoteka implements Datoteka {
    private static final int OCEKIVANI_BROJ_STUPACA = 18;
    private static final String[] OCEKIVANA_ZAGLAVLJA = {
        "Oznaka", "Opis", "Proizvođač", "Godina", "Namjena", "Vrsta prijevoza",
        "Vrsta pogona", "Maks brzina", "Maks snaga", "Broj sjedećih mjesta",
        "Broj stajaćih mjesta", "Broj bicikala", "Broj kreveta", "Broj automobila",
        "Nosivost", "Površina", "Zapremina", "Status"
    };

    private Set<String> jedinstveneOznake = new HashSet<>();
    private VoziloDirector voziloDirector;
    private List<Vozilo> listaVozila;
    private List<String[]> listaKompozicija;
    private final UpravljacGresaka upravljacGresaka;
    
    
    public ZpsDatoteka(UpravljacGresaka upravljacGresaka) {
    	this.listaVozila = new ArrayList<>();
        voziloDirector = new VoziloDirector(new VoziloBuilder(""));
        this.upravljacGresaka = upravljacGresaka;
    }

	public void setCompositionsList(List<String[]> compositionsList) {
        this.listaKompozicija = compositionsList;
    }

    @Override
    public void ucitajDatoteku(String putanja) {
        try (BufferedReader br = new BufferedReader(new FileReader(putanja))) {
            String linija;
            int redniBroj = 0;
            boolean zaglavljeProvjereno = false;

            while ((linija = br.readLine()) != null) {
                redniBroj++;

                if (preskociLiniju(linija)) {
                    continue;
                }

                if (!zaglavljeProvjereno) {
                    if (!provjeriZaglavlje(linija)) {
                    	upravljacGresaka.dodajGresku("Neispravno zaglavlje u datoteci ZPS i broj stupaca");
                        return;
                    }
                    zaglavljeProvjereno = true;
                    continue;
                }

                String[] atributi = linija.split(";");
                if (!provjeriBrojStupaca(atributi)) {
                	upravljacGresaka.dodajGresku("Redak " + redniBroj + " nema ispravan broj stupaca");
                    continue;
                }

                if (!validirajAtribute(atributi, redniBroj)) {
                	upravljacGresaka.dodajGresku("Neispravne vrijednosti u retku " + redniBroj);
                    continue;
                }

                Vozilo vozilo = buildajVozilo(atributi);
                if (vozilo != null) {
                    listaVozila.add(vozilo);
                }
            }
        } catch (IOException e) {
        	upravljacGresaka.dodajGresku("Pogreska pri ucitavanju ZPS datoteke");
        }
    }

    private boolean preskociLiniju(String linija) {
        return linija.trim().isEmpty() || linija.startsWith("#") || linija.replace(";", "").trim().isEmpty();
    }
    
    public List<Vozilo> getListaVozila() {
        return listaVozila;
    }
    
    private Vozilo buildajVozilo(String[] atributi) {
        String oznaka = atributi[0].trim();
        String opis = atributi[1].trim();
        String proizvodjac = atributi[2].trim();
        int godinaProizvodnje = Integer.parseInt(atributi[3].trim());
        String namjena = atributi[4].trim();
        String vrstaPrijevoza = atributi[5].trim();
        String vrstaPogona = atributi[6].trim();
        int maxBrzina = Integer.parseInt(atributi[7].trim());
    	double maxSnaga = Double.parseDouble(atributi[8].trim().replace(",", "."));
    	int brojSjedista = Integer.parseInt(atributi[9].trim());
    	int brojStajaca = Integer.parseInt(atributi[10].trim());
    	int brojBicikala = Integer.parseInt(atributi[11].trim());
    	int brojKreveta = Integer.parseInt(atributi[12].trim());
    	int brojAutomobila = Integer.parseInt(atributi[13].trim());
    	double nosivost = Double.parseDouble(atributi[14].trim().replace(",", "."));
    	double povrsina = Double.parseDouble(atributi[15].trim().replace(",", "."));
    	double zapremina = Double.parseDouble(atributi[16].trim().replace(",", "."));
        String status = atributi[17].trim();
        
        if (vrstaPrijevoza.equals("N")) {
            return voziloDirector.buildLokomotiva(oznaka, opis, proizvodjac, godinaProizvodnje, namjena, vrstaPrijevoza, vrstaPogona, maxBrzina, maxSnaga,
            		brojSjedista, brojStajaca, brojBicikala, brojKreveta, brojAutomobila, nosivost, povrsina, zapremina, status);
        } 
        else if (vrstaPrijevoza.equals("P")) {
        	return voziloDirector.buildPutnickoPrijevoznoSredstvo(oznaka, opis, proizvodjac, godinaProizvodnje, namjena, vrstaPrijevoza, vrstaPogona, maxBrzina, maxSnaga,
            		brojSjedista, brojStajaca, brojBicikala, brojKreveta, brojAutomobila, nosivost, povrsina, zapremina, status);
        }
        else if (vrstaPrijevoza.equals("TA")) {
            return voziloDirector.buildTeretnoPrijevoznoSredstvoZaAutomobile(oznaka, opis, proizvodjac, godinaProizvodnje, namjena, vrstaPrijevoza, vrstaPogona, maxBrzina, maxSnaga,
            		brojSjedista, brojStajaca, brojBicikala, brojKreveta, brojAutomobila, nosivost, povrsina, zapremina, status);
        }
        else if (vrstaPrijevoza.equals("TK")) {
        	return voziloDirector.buildTeretnoPrijevoznoSredstvoZaPakiranuRobuUKontejnerima(oznaka, opis, proizvodjac, godinaProizvodnje, namjena, vrstaPrijevoza, vrstaPogona, maxBrzina, maxSnaga,
            		brojSjedista, brojStajaca, brojBicikala, brojKreveta, brojAutomobila, nosivost, povrsina, zapremina, status);
        }
        else if (vrstaPrijevoza.equals("TRS")) {
        	return voziloDirector.buildTeretnoPrijevoznoSredstvoZaRobuURasutomStanju(oznaka, opis, proizvodjac, godinaProizvodnje, namjena, vrstaPrijevoza, vrstaPogona, maxBrzina, maxSnaga,
            		brojSjedista, brojStajaca, brojBicikala, brojKreveta, brojAutomobila, nosivost, povrsina, zapremina, status);
        }
        else if (vrstaPrijevoza.equals("TTS")) {
        	return voziloDirector.buildTeretnoPrijevoznoSredstvoZaRobuUTekucemStanju(oznaka, opis, proizvodjac, godinaProizvodnje, namjena, vrstaPrijevoza, vrstaPogona, maxBrzina, maxSnaga,
            		brojSjedista, brojStajaca, brojBicikala, brojKreveta, brojAutomobila, nosivost, povrsina, zapremina, status);
        }

        return null;
    }


    private boolean provjeriZaglavlje(String linija) {
        linija = linija.replaceAll("^\uFEFF", "");

        String[] zaglavlja = linija.split(";");
        if (zaglavlja.length != OCEKIVANI_BROJ_STUPACA) {
        	upravljacGresaka.dodajGresku("Neispravno zaglavlje. Ocekivano stupaca: " + OCEKIVANI_BROJ_STUPACA + ", dobiveno: " + zaglavlja.length);
            return false;
        }
        for (int i = 0; i < OCEKIVANI_BROJ_STUPACA; i++) {
            if (!zaglavlja[i].trim().equalsIgnoreCase(OCEKIVANA_ZAGLAVLJA[i])) {
            	upravljacGresaka.dodajGresku("Neispravno zaglavlje. Ocekivano: " + OCEKIVANA_ZAGLAVLJA[i] + ", dobiveno: " + zaglavlja[i].trim());
                return false;
            }
        }
        return true;
    }

    private boolean provjeriBrojStupaca(String[] atributi) {
        return atributi.length == OCEKIVANI_BROJ_STUPACA;
    }

    private boolean validirajAtribute(String[] atributi, int redniBroj) {
        try {
            String jedinstvenaOznaka = atributi[0];
            if (!jedinstveneOznake.add(jedinstvenaOznaka)) {
            	upravljacGresaka.dodajGresku("Duplicirana oznaka u retku " + redniBroj);
                return false;
            }

            String vrstaPrijevoza = atributi[5];
            if (!validnaVrstaPrijevoza(vrstaPrijevoza)) {
            	upravljacGresaka.dodajGresku("Neispravna vrijednost za Vrsta prijevoza u retku " + redniBroj);;
                return false;
            }

            String vrstaPogona = atributi[6];
            if (!validnaVrstaPogona(vrstaPogona)) {
            	upravljacGresaka.dodajGresku("Neispravna vrijednost za Vrsta pogona u retku " + redniBroj);
                return false;
            }

            int brojPerona = Integer.parseInt(atributi[7]);
            int brojKolosjeka = Integer.parseInt(atributi[9]);
            double doPoOsovini = Double.parseDouble(atributi[10].trim().replace(",", "."));
            double doPoDuznomM = Double.parseDouble(atributi[11].trim().replace(",", "."));
            int duzina = Integer.parseInt(atributi[13]);

            if (brojPerona < 0 || brojKolosjeka < 0 || doPoOsovini < 0 || doPoDuznomM < 0 || duzina < 0) {
            	upravljacGresaka.dodajGresku("Negativna vrijednost u retku " + redniBroj);
                return false;
            }

            if (atributi[2].equals("staj.") && brojPerona != 1) {
            	upravljacGresaka.dodajGresku("Broj perona za stanicu tipa 'staj.' u retku " + redniBroj + " mora biti 1.");
                return false;
            }

            if (atributi[8].equals("E") && doPoOsovini <= 0) {
            	upravljacGresaka.dodajGresku("DO po osovini za elektricnu prugu u retku " + redniBroj + " mora biti veće od 0.");
                return false;
            }

            return true;
        } catch (NumberFormatException e) {
        	upravljacGresaka.dodajGresku("Pogreska u parsiranju brojcane vrijednosti u retku " + redniBroj + ": " + e.getMessage());
            return false;
        }
    }

    private boolean validnaVrstaPrijevoza(String value) {
        return value.equals("N") || value.equals("P") || value.equals("TA") || 
               value.equals("TK") || value.equals("TRS") || value.equals("TTS");
    }

    private boolean validnaVrstaPogona(String value) {
        return value.equals("D") || value.equals("E") || value.equals("N");
    }
    
    public void obradiKomandu(String kompozicijaId, Map<String, String> ulogeVozila) {
        Set<String> oznakeVozila = getOznakeVozilaIzKompozicija(kompozicijaId);
        
        if (getListaVozila().size() < 0) {
        	upravljacGresaka.dodajGresku("Nema vozila u listi.");
        }

        getDetaljeOVozilima(oznakeVozila, ulogeVozila);
    }


    private Set<String> getOznakeVozilaIzKompozicija(String kompozicijaId) {
        Set<String> oznakeVozila = new HashSet<>();

        for (String[] kompozicija : listaKompozicija) {
            String trimanaKompozicijaId = kompozicija[0].trim();
            oznakeVozila.add(trimanaKompozicijaId);
        }

        if (oznakeVozila.isEmpty()) {
        	upravljacGresaka.dodajGresku("Nema vozila za zadani kompozicijaId: " + kompozicijaId);
        }

        return oznakeVozila;
    }

    private void getDetaljeOVozilima(Set<String> oznakeVozila, Map<String, String> ulogeVozila) {
        for (String oznakaVozila : oznakeVozila) {
            boolean pronadena = false;

            if (listaVozila.isEmpty()) {
            	upravljacGresaka.dodajGresku("prazna vozila");
                return;
            }
            
            for (Vozilo vozilo : listaVozila) {
                if (vozilo.getOznaka().equals(oznakaVozila)) {
                    pronadena = true;
                    
                    String role = ulogeVozila.get(oznakaVozila);
                    
                    System.out.printf("Oznaka: %-10s | Uloga: %-10s | Opis: %-40s | Proizvodac: %-30s | Godina: %-4d | Namjena: %-10s | Vrsta pogona: %-5s | Maks. brzina: %-5d%n",
                            vozilo.getOznaka(), role, vozilo.getOpis(), vozilo.getProizvodjac(),
                            vozilo.getGodinaProizvodnje(), vozilo.getNamjena(), vozilo.getVrstaPogona(), vozilo.getMaxBrzina());
                    break;
                }
            }

            if (!pronadena) {
            	upravljacGresaka.dodajGresku("Nema detalja za vozilo: " + oznakaVozila);
            }
        }
        System.out.println();
    }
}