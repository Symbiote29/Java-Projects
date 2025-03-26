package org.foi.uzdiz.commandVracanjeKarte;

import org.foi.uzdiz.chainofresponsibility.ContextKomandi;
import org.foi.uzdiz.chainofresponsibility.HandlerKomandi;
import org.foi.uzdiz.mementokupovina.Karta;
import org.foi.uzdiz.mementokupovina.PovijestKupovina;

public class VracanjeKupljeneKarte implements Command, HandlerKomandi {
    private int indeksKarte;
    private PovijestKupovina povijestKupovina;

    public VracanjeKupljeneKarte(PovijestKupovina povijestKupovina) {
        this.povijestKupovina = povijestKupovina;
    }

    public void setParams(int indeksKarte) {
        this.indeksKarte = indeksKarte;
    }

    @Override
    public boolean canHandle(String komanda) {
        return komanda.trim().toUpperCase().startsWith("VRT");
    }

    @Override
    public void handle(String komanda, ContextKomandi context) {
        String[] dijelovi = komanda.split(" ");
        if (dijelovi.length != 2) {
            System.out.println("Neispravna sintaksa. Koristite: VRT indeksKarte");
            return;
        }

        try {
            indeksKarte = Integer.parseInt(dijelovi[1].trim());

            execute();
        } catch (NumberFormatException e) {
            System.out.println("Indeks karte mora biti cijeli broj. Greška: " + e.getMessage());
        }
    }

    @Override
    public void execute() {
        int zeroBasedIndex = indeksKarte - 1;

        if (zeroBasedIndex >= 0 && zeroBasedIndex < povijestKupovina.getKupovine().size()) {
            Karta karta = povijestKupovina.ukloniKupovinu(zeroBasedIndex);
            System.out.println("Karta uspješno vraćena: ");
            System.out.println(karta);
        } else {
            System.out.println("Greška: Ne postoji karta s indeksom " + indeksKarte);
        }
    }
}
