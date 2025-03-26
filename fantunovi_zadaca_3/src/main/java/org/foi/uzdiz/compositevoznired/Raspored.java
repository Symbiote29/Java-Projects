package org.foi.uzdiz.compositevoznired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.foi.uzdiz.builderstanica.Stanica;
import org.foi.uzdiz.factoryucitavanjedatoteka.ZsDatoteka;
import org.foi.uzdiz.fantunovi_zadaca_3.Graf;
import org.foi.uzdiz.observerkorisnik.Korisnik;
import org.foi.uzdiz.observerkorisnik.RegistarKorisnika;

public class Raspored implements VozniRedComponent{
	private List<Vlak> vlakovi;
	private ZsDatoteka zsDatoteka;
	private Map<String, Vlak> vlakoviMap;
	private RegistarKorisnika registarKorisnika;

    public Raspored() {
        this.vlakovi = new ArrayList<>();
        this.vlakoviMap = new HashMap<>();
    }

    public void setZsDatoteka(ZsDatoteka zsDatoteka) {
        this.zsDatoteka = zsDatoteka;
    }
    
    public void setRegistarKorisnika(RegistarKorisnika registar) {
        this.registarKorisnika = registar;
    }
    
    public RegistarKorisnika getRegistarKorisnika() {
        return registarKorisnika;
    }
    
    public void dodajVlak(Vlak vlak) {
        vlakovi.add(vlak);
        vlakoviMap.put(vlak.getOznakaVlaka(), vlak);
        
    }
 
    public List<Vlak> getVlakovi(){
    	return vlakovi;
    }


    @Override
    public void prikaziDetaljeOVoznomRedu() {
        System.out.println("+-----------------+----------------------+----------------------+------------------+-----------------------+-----------------------+");
        System.out.printf("| %-15s | %-20s | %-20s | %-16s | %-19s | %-19s |\n",
                "Oznaka vlaka", "Polazna stanica", "Odredišna stanica", 
                "Vrijeme polaska", "Vrijeme dolaska", "Ukupna udaljenost (km)");
        System.out.println("+-----------------+----------------------+----------------------+------------------+-----------------------+-----------------------+");

        vlakovi.forEach(vlak -> vlak.prikaziDetaljeOVoznomRedu());

        System.out.println("+-----------------+----------------------+----------------------+------------------+-----------------------+-----------------------+");
    }
    
    public Vlak dohvatiVLakPoOznaci(String oznakaVlaka) {
        System.out.println("Tražim vlak s oznakom: '" + oznakaVlaka + "'");
        Vlak vlak = vlakoviMap.get(oznakaVlaka);
        if (vlak == null) {
            System.out.println("Vlak s oznakom " + oznakaVlaka + " nije pronađen.");
        } else {
            System.out.println("Vlak s oznakom " + oznakaVlaka + " pronađen.");
        }
        return vlak;
    }

    public void dodajKorisnikaZaVlak(String ime, String prezime, String oznakaVlaka) {
    	Korisnik korisnik = registarKorisnika.nadjiKorisnika(ime, prezime);
        
        Vlak vlak = dohvatiVLakPoOznaci(oznakaVlaka);

        if (korisnik != null && vlak != null) {
            vlak.dodajObserver(korisnik);
            korisnik.pratiVlak(oznakaVlaka);
            System.out.println(ime + " " + prezime + " sada prati vlak " + oznakaVlaka);
        } else {
            System.out.println("Korisnik ili vlak nisu pronađeni.");
        }
    }

    public void dodajKorisnikaZaStanicu(String ime, String prezime, String nazivStanice) {
    	Korisnik korisnik = registarKorisnika.nadjiKorisnika(ime, prezime);
    	System.out.println("Korisnik u rasporedu: " + korisnik.toString());
        
        Stanica stanica = null;
        for (Stanica s : zsDatoteka.getStanice()) {
            if (s.getNaziv().equals(nazivStanice)) {
                stanica = s;
                break;
            }
        }

        if (korisnik != null && stanica != null) {
            stanica.dodajObserver(korisnik);
            korisnik.pratiStanicu(nazivStanice);
            System.out.println(ime + " " + prezime + " sada prati stanicu " + nazivStanice);
        } else {
            System.out.println("Korisnik ili stanica nisu pronađeni.");
        }
    }

    public void ispisEtapaZaVlak(String oznakaVlaka) {
        boolean vlakFound = false;

        System.out.println("--------------------------------------------------------------------------------------------------------------"
                + "----------------------------------------------------------");
        System.out.printf("| %-13s | %-20s | %-20s | %-20s | %-19s | %-18s | %-22s | %-19s |\n",
                "Oznaka vlaka", 
                "Oznaka pruge", 
                "Polazna stanica", 
                "Odredišna stanica", 
                "Vrijeme polaska", 
                "Vrijeme dolaska", 
                "Ukupna udaljenost (km)", 
                "Dani u tjednu");
        System.out.println("--------------------------------------------------------------------------------------------------------------"
                         + "----------------------------------------------------------");
        
        for (Vlak vlak : vlakovi) {
            if (vlak.getOznakaVlaka().trim().equals(oznakaVlaka.trim())) {
                vlak.ispisEtapeVlaka();
                vlakFound = true;
            }
        }
        
        System.out.println("--------------------------------------------------------------------------------------------------------------"
                + "----------------------------------------------------------");

        if (!vlakFound) {
            System.out.println("Vlak s oznakom " + oznakaVlaka + " nije pronađen.");
        }
    }
    
    public void ispisStanicaZaVlak(String oznakaVlaka) {
        boolean vlakFound = false;

        System.out.println("--------------------------------------------------------------------------------------------------------------"
                + "----------------------------------------------------------");
        System.out.printf("| %-13s | %-20s | %-20s | %-20s | %-19s | %-18s | %-22s | %-19s |\n",
                "Oznaka vlaka", 
                "Oznaka pruge", 
                "Polazna stanica", 
                "Odredišna stanica", 
                "Vrijeme polaska", 
                "Vrijeme dolaska", 
                "Ukupna udaljenost (km)", 
                "Dani u tjednu");
        System.out.println("--------------------------------------------------------------------------------------------------------------"
                         + "----------------------------------------------------------");
        
        for (Vlak vlak : vlakovi) {
            if (vlak.getOznakaVlaka().trim().equals(oznakaVlaka.trim())) {
                vlak.ispisStanicaVlaka(oznakaVlaka);
                vlakFound = true;
            }
        }
        
        System.out.println("--------------------------------------------------------------------------------------------------------------"
                + "----------------------------------------------------------");

        if (!vlakFound) {
            System.out.println("Vlak s oznakom " + oznakaVlaka + " nije pronađen.");
        }
    }
    
    public void ispisStanicaZaVlak2(String oznakaVlaka) {
    	boolean vlakFound = false;
        System.out.println("----------------------------------------------------------");
        System.out.println("Stanice za vlak s oznakom: " + oznakaVlaka);
        System.out.println("----------------------------------------------------------");

        for (Vlak vlak : vlakovi) {
            if (vlak.getOznakaVlaka().trim().equals(oznakaVlaka.trim())) {
                vlak.ispisStanicaVlaka(oznakaVlaka);
                vlakFound = true;
            }
        }

        if (!vlakFound) {
            System.out.println("Vlak s oznakom " + oznakaVlaka + " nije pronađen.");
        }
    }
    
    public List<String> dohvatiStaniceZaVlak(String oznakaVlaka) {
        List<String> staniceZaVlak = new ArrayList<>();
        
        for (Vlak vlak : vlakovi) {
            if (vlak.getOznakaVlaka().trim().equals(oznakaVlaka.trim())) {
                staniceZaVlak = vlak.getStanicePoEtapama(oznakaVlaka);
                break;
            }
        }

        return staniceZaVlak;
    }

    public void ispisVlakovePoDanima(String dani) {
        Map<String, String> daniMapa = Map.of(
            "Po", "Pon",
            "U", "Uto",
            "Sr", "Sri",
            "Č", "Čet",
            "Pe", "Pet",
            "Su", "Sub",
            "N", "Ned"
        );
        
        System.out.println("Ispis vlakova za dane: " + dani);
        System.out.println("------------------------------------------------------------");

        List<String> uneseniDani = new ArrayList<>();
        for (int i = 0; i < dani.length(); i += 2) {
            if (i + 1 < dani.length()) {
                uneseniDani.add(dani.substring(i, i + 2));
            }
        }

        for (Vlak vlak : vlakovi) {
            boolean ispisanVlak = false;
            
            for (Etape etapa : vlak.getVlakoviEtape()) {
                String nazivDana = etapa.getNazivDanaIzZodDatoteke(etapa.getOznakaDana());
                if (nazivDana == null || nazivDana.isEmpty()) {
                    vlak.ispisEtapeVlaka();
                    ispisanVlak = true;
                    break;
                }

                for (String uneseniDan : uneseniDani) {
                    if (nazivDana.contains(uneseniDan)) {
                        vlak.ispisEtapeVlaka();
                        ispisanVlak = true;
                        break;
                    }
                }
                if (ispisanVlak) {
                    break;
                }
            }
        }
    }

    public void ispisStanicaVoznogRedaNaOdredenDan(String polaznaStanica, String odredisnaStanica, String dan, String odVr, String doVr) {
        boolean found = false;

        System.out.println("+---------------------------------------------------------------------------------------------------------+");
        System.out.printf("| %-15s | %-15s | %-55s |\n", "Oznaka vlaka", "Oznaka pruge", "Stanice na ruti");
        System.out.println("+---------------------------------------------------------------------------------------------------------+");

        for (Vlak vlak : vlakovi) { 
            List<String> trenutnaRuta = new ArrayList<>();
            String zadnjaStanica = polaznaStanica;

            for (Etape etapa : vlak.getVlakoviEtape()) {
                boolean dodavanjeStanica = false;

                String vrijemePolaska = etapa.getVrijemePolaska();
                String nazivDana = Etape.getNazivDanaIzZodDatoteke(etapa.getOznakaDana());

                if (jeVrijemeUnutarIntervala(vrijemePolaska, odVr, doVr) && nazivDana.contains(dan)) {
                    List<String> staniceNaEtapi = etapa.dohvatiSveStaniceNaEtapi(etapa.getOznakaPruge());

                    for (String stanica : staniceNaEtapi) {
                        if (stanica.equals(zadnjaStanica)) {
                            dodavanjeStanica = true;
                        }
                        if (dodavanjeStanica) {
                            trenutnaRuta.add(stanica);
                        }
                        if (stanica.equals(odredisnaStanica)) {
                            dodavanjeStanica = false;
                            break;
                        }
                    }

                    if (trenutnaRuta.contains(odredisnaStanica)) {
                        found = true;
                        System.out.printf("| %-15s | %-15s | %-55s |\n", vlak.getOznakaVlaka(), etapa.getOznakaPruge(), String.join(" - ", trenutnaRuta));

                        etapa.pretraziPutIzmeduStanica(polaznaStanica, odredisnaStanica, vlak.getVlakoviEtape(), zsDatoteka);
                        
                        break;
                    }

                    if (!trenutnaRuta.isEmpty()) {
                        zadnjaStanica = trenutnaRuta.get(trenutnaRuta.size() - 1);
                    }
                }
            }
        }

        if (!found) {
            System.out.println("Nema vlakova koji zadovoljavaju uvjete.");
        }
        System.out.println("+---------------------------------------------------------------------------------------------------------+");
    }

    public boolean jeVrijemeUnutarIntervala(String vrijemePolaska, String odVr, String doVr) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");

            Date vrijemePolaskaDate = format.parse(vrijemePolaska);
            Date odVrDate = format.parse(odVr);
            Date doVrDate = format.parse(doVr);

            long vrijemePolaskaMin = vrijemePolaskaDate.getTime() / (60 * 1000);
            long odVrMin = odVrDate.getTime() / (60 * 1000);
            long doVrMin = doVrDate.getTime() / (60 * 1000);

            return vrijemePolaskaMin >= odVrMin && vrijemePolaskaMin <= doVrMin;
        } catch (ParseException e) {
            System.err.println("Greška pri parsiranju vremena: " + e.getMessage());
            return false;
        }
    }
    
    public List<Vlak> getVlakoviZaRutu(List<String> ruta) {
        List<Vlak> vlakoviZaRutu = new ArrayList<>();

        for (Vlak vlak : vlakovi) {
            if (vlakProlaziKrozRutneStanice(vlak, ruta)) {
                vlakoviZaRutu.add(vlak);
            }
        }
        return vlakoviZaRutu;
    }

    private boolean vlakProlaziKrozRutneStanice(Vlak vlak, List<String> ruta) {
        boolean prolaziKrozSveStanice = false;
        
        for (Etape etapa : vlak.getVlakoviEtape()) {
            List<String> staniceNaEtapi = etapa.dohvatiSveStaniceNaEtapi(etapa.getOznakaPruge());
            
            if (staniceNaEtapi.containsAll(ruta)) {
                prolaziKrozSveStanice = true;
                break;
            }
        }

        return prolaziKrozSveStanice;
    }

    
}
