package org.foi.uzdiz.compositevoznired;

public class LeafStanica implements VozniRedComponent {
    private String naziv;

    public LeafStanica(String naziv) {
        this.naziv = naziv;
    }

    public String getNaziv() {
        return naziv;
    }

    @Override
    public void prikaziDetaljeOVoznomRedu() {
        System.out.println("Stanica: " + naziv);
    }
}
