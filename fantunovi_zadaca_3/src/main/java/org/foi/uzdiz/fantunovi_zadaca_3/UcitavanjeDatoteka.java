package org.foi.uzdiz.fantunovi_zadaca_3;

import java.util.ArrayList;
import java.util.List;

import org.foi.uzdiz.factorypruga.Pruga;
import org.foi.uzdiz.factoryucitavanjedatoteka.Datoteka;
import org.foi.uzdiz.factoryucitavanjedatoteka.DatotekaFactory;
import org.foi.uzdiz.factoryucitavanjedatoteka.ZkDatoteka;
import org.foi.uzdiz.factoryucitavanjedatoteka.ZodDatoteka;
import org.foi.uzdiz.factoryucitavanjedatoteka.ZpsDatoteka;
import org.foi.uzdiz.factoryucitavanjedatoteka.ZsDatoteka;
import org.foi.uzdiz.factoryucitavanjedatoteka.ZvrDatoteka;
import org.foi.uzdiz.managers.PrugaManager;
import org.foi.uzdiz.singleton.TvrtkaZeljeznickogPrometa;
import org.foi.uzdiz.singleton.UpravljacGresaka;

public class UcitavanjeDatoteka {
    private List<Pruga> svePruge = new ArrayList<>();
    private ZsDatoteka zsDatoteka;
    private ZkDatoteka zkDatoteka;
    private ZpsDatoteka zpsDatoteka;
    private ZvrDatoteka zvrDatoteka;
    private ZodDatoteka zodDatoteka;
    private final UpravljacGresaka upravljacGresaka;

    public UcitavanjeDatoteka(UpravljacGresaka upravljacGresaka) {
    	this.upravljacGresaka = upravljacGresaka;
	}

	public void obradiArgumente(String[] args) {
        DatotekaFactory factory = new DatotekaFactory(upravljacGresaka);

        for (int i = 0; i < args.length; i++) {
            String tipDatoteke = args[i];
            if (i + 1 < args.length) {
                String putanja = args[i + 1];
                try {
                    Datoteka datoteka = factory.createDatoteka(tipDatoteke);
                    datoteka.ucitajDatoteku(putanja);

                    if (datoteka instanceof ZsDatoteka) {
                        zsDatoteka = (ZsDatoteka) datoteka;
                        List<Pruga> ucitanePruge = zsDatoteka.getPruge();
                        svePruge.addAll(ucitanePruge);
                    }else if (datoteka instanceof ZkDatoteka) {
                        zkDatoteka = (ZkDatoteka) datoteka;
                    }else if (datoteka instanceof ZpsDatoteka) {
                    	zpsDatoteka = (ZpsDatoteka) datoteka;
                    }else if (datoteka instanceof ZvrDatoteka){
                    	zvrDatoteka = (ZvrDatoteka) datoteka;
                    }else {
                    	zodDatoteka = (ZodDatoteka) datoteka;
                    }
                    
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getMessage());
                }
                i++;
            }
        }
    }

    public void ispisStanica(String oznakaPruge) {
        if (zsDatoteka != null) {
            zsDatoteka.ispisStanicaPoPrugama(oznakaPruge);
        } else {
            System.out.println("ZsDatoteka nije ucitana.");
        }
    }
    
    public void ispisZkDatoteka() {
        if (zkDatoteka != null) {
            System.out.println("ZkDatoteka podaci: " + zkDatoteka.dohvatiKompozicije());
        } else {
            System.out.println("ZkDatoteka nije ucitana.");
        }
    }

    public ZkDatoteka getZkDatoteka() {
        return zkDatoteka;
    }
    
    public List<Pruga> getSvePruge() {
        return svePruge;
    }
    
    public ZsDatoteka getZsDatoteka() {
        return zsDatoteka;
    }
    
    public ZpsDatoteka getZpsDatoteka() {
        return zpsDatoteka;
    }
    
    public ZvrDatoteka getZvrDatoteka() {
    	return zvrDatoteka;
    }
    
    public ZodDatoteka getZodDatoteka() {
    	return zodDatoteka;
    }
}
