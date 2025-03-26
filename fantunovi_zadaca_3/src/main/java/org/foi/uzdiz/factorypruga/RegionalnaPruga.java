package org.foi.uzdiz.factorypruga;

public class RegionalnaPruga extends Pruga {
    public RegionalnaPruga(String oznaka, String kategorijaPruge, String vrstaPruga, int brojKolosijeka, double doPoOsovini, double doPoDuznomM, String status) {
        super(oznaka, kategorijaPruge, vrstaPruga, brojKolosijeka, doPoOsovini, doPoDuznomM, status);
    }

    @Override
    public String getKategorija() {
        return "Regionalna";
    }
    
    @Override
    public String toString() {
        return "RegionalnaPruga{" +
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
