package org.foi.uzdiz.chainofresponsibility;

import org.foi.uzdiz.managers.StrategijaCijenaManager;

public class CVPKomandaHandler implements HandlerKomandi {

    @Override
    public boolean canHandle(String komanda) {
        return komanda.trim().toUpperCase().startsWith("CVP");
    }

    @Override
    public void handle(String komanda, ContextKomandi context) {
        System.out.println("Handler CVP aktiviran. Komanda: " + komanda);

        String[] dijelovi = komanda.substring(3).trim().split(" ");
        if (dijelovi.length < 6) {
            System.out.println("Greška: Nedovoljno parametara. Očekuje se sintaksa 'CVP cijenaNormalni cijenaUbrzani cijenaBrzi popustSuN popustWebMob uvecanjeVlak'.");
            return;
        }

        try {
            double cijenaNormalni = Double.parseDouble(dijelovi[0].replace(",", "."));
            double cijenaUbrzani = Double.parseDouble(dijelovi[1].replace(",", "."));
            double cijenaBrzi = Double.parseDouble(dijelovi[2].replace(",", "."));
            double popustSuN = Double.parseDouble(dijelovi[3].replace(",", ".")) / 100;
            double popustWebMob = Double.parseDouble(dijelovi[4].replace(",", ".")) / 100;
            double uvecanjeVlak = Double.parseDouble(dijelovi[5].replace(",", ".")) / 100;

            StrategijaCijenaManager strategijeCijenaManager = context.getStrategijeCijenaManager();
            strategijeCijenaManager.postaviCijene(cijenaNormalni, cijenaUbrzani, cijenaBrzi, popustSuN, popustWebMob, uvecanjeVlak);

            System.out.println("Cijene i popusti uspješno postavljeni sa sljedećim vrijednostima:");
            System.out.println("Cijena za normalni vlak: " + cijenaNormalni + " €/km");
            System.out.println("Cijena za ubrzani vlak: " + cijenaUbrzani + " €/km");
            System.out.println("Cijena za brzi vlak: " + cijenaBrzi + " €/km");
            System.out.println("Popust za subotu i nedjelju: " + (popustSuN * 100) + "%");
            System.out.println("Popust za kupovinu preko web/mobilne aplikacije: " + (popustWebMob * 100) + "%");
            System.out.println("Uvećanje za kupovinu karte u vlaku: " + (uvecanjeVlak * 100) + "%");

        } catch (NumberFormatException e) {
            System.out.println("Greška: Krivi format brojeva u komandi.");
        }
    }

}
