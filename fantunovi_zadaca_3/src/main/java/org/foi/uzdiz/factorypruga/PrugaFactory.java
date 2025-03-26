package org.foi.uzdiz.factorypruga;

public abstract class PrugaFactory {
    public abstract Pruga createPruga(String oznaka, String kategorijaPruge, String vrstaPruga, int brojKolosijeka, double doPoOsovini, double doPoDuznomM, String status);
}
