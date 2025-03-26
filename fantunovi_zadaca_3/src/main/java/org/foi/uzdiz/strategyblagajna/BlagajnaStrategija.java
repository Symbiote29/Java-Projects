package org.foi.uzdiz.strategyblagajna;

public class BlagajnaStrategija implements StrategijaCijene {
	@Override
    public double izracunajCijenu(double osnovnaCijena, double udaljenost, boolean subotaNedjelja, double popustSuN, double dodatniPopust) {
        double cijena = osnovnaCijena * udaljenost;

        if (subotaNedjelja) {
            cijena *= (1 - popustSuN);
        }
        return cijena;
    }
}