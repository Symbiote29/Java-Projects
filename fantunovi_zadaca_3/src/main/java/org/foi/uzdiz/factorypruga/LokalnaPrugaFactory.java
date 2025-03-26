package org.foi.uzdiz.factorypruga;

public class LokalnaPrugaFactory extends PrugaFactory {
    @Override
    public Pruga createPruga(String oznaka, String kategorijaPruge, String vrstaPruga, int brojKolosijeka, double doPoOsovini, double doPoDuznomM, String status) {
        return new LokalnaPruga(oznaka, kategorijaPruge, vrstaPruga, brojKolosijeka, doPoOsovini, doPoDuznomM, status);
    }
}
