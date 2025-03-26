package org.foi.uzdiz.builderstanica;

import java.util.ArrayList;
import java.util.List;

import org.foi.uzdiz.factorypruga.Pruga;
import org.foi.uzdiz.observerkorisnik.Observer;
import org.foi.uzdiz.observerkorisnik.Subjekt;

public class Stanica implements Subjekt{
	private String naziv;
    private String oznakaPruge;
    private String vrsta;
    private String aktivnosti;
    private int brojPerona;
    private String status;
    private double udaljenost;
    private List<Observer> observers;
    
    public Stanica(StanicaBuilder builder) {
        this.naziv = builder.getNaziv();
        this.oznakaPruge = builder.getOznakaPruge();
        this.vrsta = builder.getVrsta();
        this.aktivnosti = builder.getAktivnosti();
        this.brojPerona = builder.getBrojPerona();
        this.status = builder.getStatus();
        this.udaljenost = builder.getUdaljenost();
        this.observers = new ArrayList<>();
    }

    public String getNaziv() {
        return naziv;
    }

    public String getVrsta() {
        return vrsta;
    }

    public String getAktivnosti() {
        return aktivnosti;
    }

    public int getBrojPerona() {
        return brojPerona;
    }

    public String getStatus() {
        return status;
    }

    public double getUdaljenost() {
        return udaljenost;
    }

    public String getOznakaPruge() {
        return oznakaPruge;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Stanica)) return false;
        Stanica other = (Stanica) obj;
        return naziv != null && naziv.equals(other.naziv);
    }

    @Override
    public int hashCode() {
        return naziv != null ? naziv.hashCode() : 0;
    }

    @Override
    public String toString() {
        return naziv;
    }
    
    @Override
    public void dodajObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removajObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void obavijestiObservere(String message) {
    	if (observers != null && !observers.isEmpty()) {
            for (Observer observer : observers) {
                observer.update(message);
            }
        } else {
            System.out.println("Nema obavjestioce za ovu stanicu.");
        }
    }
    
    public void vlakStigao(String oznakaVlaka) {
        String message = "Vlak " + oznakaVlaka + " je stigao na stanicu " + naziv;
        obavijestiObservere(message);
    }
    
    public void updateStatus(String noviStatus) {
        this.status = noviStatus;
        obavijestiObservere("Status stanice " + naziv + " promijenjen u: " + noviStatus);
    }

    public void obavijesti(String obavijest) {
        System.out.println("Stanica " + naziv + ": " + obavijest);
    }

    public void updateStatusPruge(String noviStatus) {
        this.status = noviStatus;
        obavijestiObservere("Status stanice " + naziv + " promijenjen u: " + noviStatus);
    }
}
