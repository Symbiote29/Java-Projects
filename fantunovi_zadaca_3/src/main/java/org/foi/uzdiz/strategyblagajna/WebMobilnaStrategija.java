package org.foi.uzdiz.strategyblagajna;

public class WebMobilnaStrategija implements StrategijaCijene {
	@Override
    public double izracunajCijenu(double osnovnaCijena, double udaljenost, boolean subotaNedjelja, double popustSuN, double popustWebMob) {
        double cijena = osnovnaCijena * udaljenost;

        if (subotaNedjelja) {
            cijena *= (1 - popustSuN);
        }

        cijena *= (1 - popustWebMob);

        return cijena;
    }
}