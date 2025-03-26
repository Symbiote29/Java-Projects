package org.foi.uzdiz.factorypruga;

public class RegionalnaPrugaFactory extends PrugaFactory {
    @Override
    public Pruga createPruga(String oznaka, String kategorijaPruge, String vrstaPruga, int brojKolosijeka, double doPoOsovini, double doPoDuznomM, String status) {
        return new RegionalnaPruga(oznaka, kategorijaPruge, vrstaPruga, brojKolosijeka, doPoOsovini, doPoDuznomM, status);
    }
}
