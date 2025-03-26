package org.foi.uzdiz.factoryucitavanjedatoteka;

import org.foi.uzdiz.singleton.UpravljacGresaka;

public class DatotekaFactory {
    private final UpravljacGresaka upravljacGresaka;

    public DatotekaFactory(UpravljacGresaka upravljacGresaka) {
        this.upravljacGresaka = upravljacGresaka;
    }

    public Datoteka createDatoteka(String tipDatoteke) {
        switch (tipDatoteke) {
            case "--zs":
                return new ZsDatoteka(upravljacGresaka);
            case "--zps":
                return new ZpsDatoteka(upravljacGresaka);
            case "--zk":
                return new ZkDatoteka(upravljacGresaka);
            case "--zvr":
                return new ZvrDatoteka(upravljacGresaka);
            case "--zod":
                return new ZodDatoteka(upravljacGresaka);
            default:
            	upravljacGresaka.dodajGresku("Nepoznat tip datoteke: " + tipDatoteke);
                return null;
        }
    }
}
