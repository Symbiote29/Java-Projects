package org.foi.uzdiz.compositevoznired;

import java.time.LocalTime;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.foi.uzdiz.builderstanica.Stanica;
import org.foi.uzdiz.observerkorisnik.Observer;
import org.foi.uzdiz.observerkorisnik.Subjekt;

public class Vlak implements VozniRedComponent, Subjekt{
	private String oznakaVlaka;
    private String vrstaVlaka;
    private List<Etape> vlakoviEtape;
    private List<Observer> observers;

    public Vlak(String trainId, String trainType) {
        this.oznakaVlaka = trainId;
        this.vrstaVlaka = trainType != null ? trainType : "N";
        this.vlakoviEtape = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    public String getOznakaVlaka() {
        return oznakaVlaka;
    }
    
    public String getVrstaVlaka() {
    	return vrstaVlaka;
    }
    
    public List<Etape> getVlakoviEtape(){
    	return vlakoviEtape;
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
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
    
    public void addEtape(Etape etape) {
    	vlakoviEtape.add(etape);
        vlakoviEtape.sort(Comparator.comparing(Etape::getVrijemePolaska));

        obavijestiObservere("Nova etapa dodana za vlak " + oznakaVlaka + ": " + etape);
    }
    
    @Override
    public void prikaziDetaljeOVoznomRedu() {
    	for (Etape etapa : vlakoviEtape) {
            String vrijemeDolaska = izracunajVrijemeDolaska(etapa);
            double udaljenost = etapa.getUdaljenost();

            System.out.printf("| %-13s | %-20s | %-20s | %-16s | %-19s | %-19.2f |\n",
                    oznakaVlaka, 
                    etapa.getIspisPolaznaStanica(),
                    etapa.getIspisZavrsnaStanica(),
                    etapa.getVrijemePolaska(), 
                    vrijemeDolaska, 
                    udaljenost);
        }
    }
    
    public String izracunajVrijemeDolaska(Etape etapa) {
        String vrijemePolaska = etapa.getVrijemePolaska();
        String trajanjeVoznje = etapa.getTrajanjeVoznje();

        return izracunajDolazak(vrijemePolaska, trajanjeVoznje);
    }

    private String izracunajDolazak(String vrijemePolaska, String trajanjeVoznje) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
        LocalTime polazak = LocalTime.parse(vrijemePolaska, formatter);
        LocalTime trajanje = LocalTime.parse(trajanjeVoznje, formatter);
        LocalTime dolazak = polazak.plusHours(trajanje.getHour()).plusMinutes(trajanje.getMinute());

        return dolazak.format(formatter);
    }
    
    public void ispisEtapeVlaka() {
        List<Etape> etapeZaIspis = new ArrayList<>(vlakoviEtape);

        for (int i = 0; i < etapeZaIspis.size(); i++) {
            Etape etapa = etapeZaIspis.get(i);

            if ("O".equals(etapa.getSmjer())) {
                Collections.reverse(etapeZaIspis);
                break;
            }
        }

        for (Etape etapa : etapeZaIspis) {

            String vrijemeDolaska = izracunajVrijemeDolaska(etapa);
            String nazivDana = etapa.getNazivDanaIzZodDatoteke(etapa.getOznakaDana());

            System.out.printf("| %-13s | %-20s | %-20s | %-20s | %-19s | %-18s | %-22.2f | %-19s |\n",
                    oznakaVlaka,
                    etapa.getOznakaPruge(),
                    etapa.getIspisPolaznaStanica(),
                    etapa.getIspisZavrsnaStanica(),
                    etapa.getVrijemePolaska(),
                    vrijemeDolaska,
                    etapa.getUdaljenost(),
                    nazivDana);
        }
    }
    
    public void ispisStanicaVlaka(String oznaka) {
        List<Etape> etapeZaIspis = new ArrayList<>(vlakoviEtape);

        for (int i = 0; i < etapeZaIspis.size(); i++) {
            Etape etapa = etapeZaIspis.get(i);

            if ("O".equals(etapa.getSmjer())) {
                Collections.reverse(etapeZaIspis);
                break;
            }
        }

        for (Etape etapa : etapeZaIspis) {
            String vrijemeDolaska = izracunajVrijemeDolaska(etapa);
            String nazivDana = etapa.getNazivDanaIzZodDatoteke(etapa.getOznakaDana());

            System.out.printf("+-----------------+----------------------+----------------------+----------------------+---------------------+---------------------+--------------------------+----------------------+\n");
            System.out.printf("| %-15s | %-20s | %-20s | %-20s | %-19s | %-19s | %-24.2f | %-19s |\n", 
                                oznaka, 
                                etapa.getOznakaPruge(), 
                                etapa.getIspisPolaznaStanica(), 
                                etapa.getIspisZavrsnaStanica(), 
                                etapa.getVrijemePolaska(), 
                                vrijemeDolaska, 
                                etapa.getUdaljenost(), 
                                nazivDana);
            System.out.printf("+-----------------+----------------------+----------------------+----------------------+---------------------+---------------------+--------------------------+----------------------+\n");


            String oznakaPruge = etapa.getOznakaPruge();
            etapa.ispisStanicaPoPrugama(oznakaPruge, etapa.getIspisPolaznaStanica(), etapa.getIspisZavrsnaStanica(), etapa.getSmjer());
        }
    }
    
    public List<String> getStanicePoEtapama(String oznakaVlaka) {
        List<String> staniceNaRuti = new ArrayList<>();

        for (Etape etapa : vlakoviEtape) {
            if (etapa.getOznakaVlaka().equals(oznakaVlaka)) {
                List<String> stanice = etapa.dohvatiSveStaniceNaEtapi(etapa.getOznakaPruge());
                staniceNaRuti.addAll(stanice);
            }
        }

        return staniceNaRuti;
    }
}
