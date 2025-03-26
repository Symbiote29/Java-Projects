package org.foi.uzdiz.factorypruga;

public class MedunarodnaPruga extends Pruga {
    public MedunarodnaPruga(String oznaka, String kategorijaPruge, String vrstaPruga, int brojKolosijeka, double doPoOsovini, double doPoDuznomM, String status) {
        super(oznaka, kategorijaPruge, vrstaPruga, brojKolosijeka, doPoOsovini, doPoDuznomM, status);
    }

    @Override
    public String getKategorija() {
        return "Međunarodna";
    }
    
    @Override
    public String toString() {
        return "MedunarodnaPruga{" +
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
