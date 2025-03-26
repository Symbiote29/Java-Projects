package org.foi.uzdiz.chainofresponsibility;

import org.foi.uzdiz.compositevoznired.Raspored;
import org.foi.uzdiz.factoryucitavanjedatoteka.ZkDatoteka;
import org.foi.uzdiz.factoryucitavanjedatoteka.ZpsDatoteka;
import org.foi.uzdiz.factoryucitavanjedatoteka.ZvrDatoteka;
import org.foi.uzdiz.fantunovi_zadaca_3.UcitavanjeDatoteka;
import org.foi.uzdiz.managers.PrugaManager;
import org.foi.uzdiz.managers.StrategijaCijenaManager;
import org.foi.uzdiz.mementokupovina.PovijestKupovina;
import org.foi.uzdiz.observerkorisnik.RegistarKorisnika;

public class ContextKomandi {
	private final UcitavanjeDatoteka ucitavanje;
    private final PrugaManager prugaManager;
    private final ZkDatoteka zkDatoteka;
    private final ZpsDatoteka zpsDatoteka;
    private final ZvrDatoteka zvrDatoteka;
    private final Raspored raspored;
    private final RegistarKorisnika registar;
    private final StrategijaCijenaManager strategijeCijenaManager;
    private final PovijestKupovina povijestKupovina;

    public ContextKomandi(UcitavanjeDatoteka ucitavanje, PrugaManager prugaManager, ZkDatoteka zkDatoteka, ZpsDatoteka zpsDatoteka, ZvrDatoteka zvrDatoteka, 
            Raspored raspored, RegistarKorisnika registar) {
        this.ucitavanje = ucitavanje;
        this.prugaManager = prugaManager;
        this.zkDatoteka = zkDatoteka;
        this.zpsDatoteka = zpsDatoteka;
        this.zvrDatoteka = zvrDatoteka;
        this.raspored = raspored;
        this.registar = registar;
        this.strategijeCijenaManager = new StrategijaCijenaManager();
        this.povijestKupovina = new PovijestKupovina();
    }

    public UcitavanjeDatoteka getUcitavanje() {
        return ucitavanje;
    }

    public PrugaManager getPrugaManager() {
        return prugaManager;
    }

    public ZkDatoteka getZkDatoteka() {
        return zkDatoteka;
    }

    public ZpsDatoteka getZpsDatoteka() {
        return zpsDatoteka;
    }
    
    public ZvrDatoteka getZvrDatoteka() {
    	return zvrDatoteka;
    }
    
    public Raspored getRaspored() {
    	return raspored;
    }
    
    public RegistarKorisnika getRegistar() {
    	return registar;
    }
    
    public StrategijaCijenaManager getStrategijeCijenaManager() {
        return strategijeCijenaManager;
    }
    
    public PovijestKupovina getPovijestKupovina() {
        return povijestKupovina;
    }
}
