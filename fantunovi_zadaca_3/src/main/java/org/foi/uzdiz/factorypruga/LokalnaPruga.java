package org.foi.uzdiz.factorypruga;

public class LokalnaPruga extends Pruga {
    public LokalnaPruga(String oznaka, String kategorijaPruge, String vrstaPruga, int brojKolosijeka, double doPoOsovini, double doPoDuznomM, String status) {
        super(oznaka, kategorijaPruge, vrstaPruga, brojKolosijeka, doPoOsovini, doPoDuznomM, status);
    }

    @Override
    public String getKategorija() {
        return "Lokalna";
    }
    
    @Override
    public String toString() {
        return "LokalnaPruga{" +
                "oznaka='" + oznaka + '\'' +
                ", kategorija='" + kategorijaPruge + '\'' +
                ", vrsta='" + vrstaPruge + '\'' +
                ", brojKolosijeka=" + brojKolosijeka +
                ", doPoOsovini=" + doPoOsovini +
                ", doPoDuznomM=" + doPoDuznomM +
                ", status='" + status + '\'' +
                '}';
    }
}
