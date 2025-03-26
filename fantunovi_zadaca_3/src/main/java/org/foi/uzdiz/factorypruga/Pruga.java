package org.foi.uzdiz.factorypruga;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.foi.uzdiz.builderstanica.Stanica;
import org.foi.uzdiz.statePrugeStanice.IspravnaState;
import org.foi.uzdiz.statePrugeStanice.KvarState;
import org.foi.uzdiz.statePrugeStanice.State;
import org.foi.uzdiz.statePrugeStanice.TestiranjeState;
import org.foi.uzdiz.statePrugeStanice.ZatvorenaState;

public abstract class Pruga {
    protected String oznaka;
    protected String kategorijaPruge;
    protected String vrstaPruge;
    protected int brojKolosijeka;
    protected double doPoOsovini;
    protected double doPoDuznomM;
    protected String status;

    protected List<String> imenaStanica = new ArrayList<>();
    protected double ukupnaUdaljenost = 0;
    public List<Stanica> stanice;
    
    private State stanjePruge;
    private LinkedHashMap<String, State> statusRelacija = new LinkedHashMap<>();

    public Pruga(String oznaka, String kategorijaPruga, String vrstaPruga, int brojKolosijeka, double doPoOsovini, double doPoDuznomM, String status) {
        this.oznaka = oznaka;
        this.kategorijaPruge = kategorijaPruga;
        this.vrstaPruge = vrstaPruga;
        this.brojKolosijeka = brojKolosijeka;
        this.doPoOsovini = doPoOsovini;
        this.doPoDuznomM = doPoDuznomM;
        this.status = status;
        this.stanice = new ArrayList<>();
        this.stanjePruge = new IspravnaState();
    }

    public abstract String getKategorija();
    
    public List<Stanica> getStanice() {
        return this.stanice;
    }
    
    public List<String> getImenaStanica() {
        return stanice.stream().map(Stanica::getNaziv).collect(Collectors.toList());
    }

    public String getOznaka() {
        return oznaka;
    }

    public String getVrstaPruga() {
        return vrstaPruge;
    }
    
    public int getBrojKolosijeka() {
        return brojKolosijeka;
    }

    public double getDoPoOsovini() {
        return doPoOsovini;
    }

    public double getDoPoDuznomM() {
        return doPoDuznomM;
    }

    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
    	this.status = status;
    }

    public void postaviStatusRelacije(String stanica1, String stanica2, String status) {
        String key = stanica1 + "-" + stanica2;

        switch (status) {
            case "K":
                statusRelacija.put(key, new KvarState());
                break;
            case "Z":
                statusRelacija.put(key, new ZatvorenaState());
                break;
            case "T":
                statusRelacija.put(key, new TestiranjeState());
                break;
            case "I":
                statusRelacija.put(key, new IspravnaState());
                break;
            default:
                throw new IllegalArgumentException("Nepoznat status: " + status);
        }

        System.out.println("Status pruge između " + stanica1 + " i " + stanica2 + " postavljen na: " + dohvatiStatusRelacije(stanica1, stanica2));
    }


    public String dohvatiStatusRelacije(String stanica1, String stanica2) {
        String key = kreirajRelacijuKey(stanica1, stanica2);
        State stanje = statusRelacija.getOrDefault(key, new IspravnaState());
        
        return stanje.getClass().getSimpleName();
    }


    private String kreirajRelacijuKey(String stanica1, String stanica2) {
        return stanica1 + "-" + stanica2;
    }

    public void promijeniStatus(String stanica1, String stanica2) {
    	stanjePruge.promijeniStatus(this, stanica1, stanica2);
    }
    
    public boolean jeDostupna(String stanica1, String stanica2) {
        if (statusRelacija.isEmpty()) {
            System.out.println("Status relacija je prazan. Sve relacije se tretiraju kao ispravne.");
            return true;
        }

        String key = stanica1 + "-" + stanica2;
        State trenutnoStanje = getStatusRelacija().get(key);

        if (trenutnoStanje != null && !trenutnoStanje.jeDostupna(this, stanica1, stanica2)) {
            System.out.println("Pruga između " + stanica1 + " i " + stanica2 + " nije dostupna.");
            return false;
        }

        if (trenutnoStanje == null) {
            System.out.println("Nema specifičnog stanja za relaciju " + key + ". Relacija se tretira kao ispravna.");
        }

        return true;
    }


    public void setState(State stanjePruge) {
        this.stanjePruge = stanjePruge;
    }

    public State getStanjePruge() {
        return stanjePruge;
    }
    
    public LinkedHashMap<String, State> getStatusRelacija() {
        return statusRelacija;
    }
    
    public boolean sadrziStanice(String stanica1, String stanica2) {
        return getImenaStanica().contains(stanica1) && getImenaStanica().contains(stanica2);
    }
}
