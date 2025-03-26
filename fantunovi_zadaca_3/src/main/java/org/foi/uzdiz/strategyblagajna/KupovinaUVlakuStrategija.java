package org.foi.uzdiz.strategyblagajna;

public class KupovinaUVlakuStrategija implements StrategijaCijene {
	@Override
    public double izracunajCijenu(double osnovnaCijena, double udaljenost, boolean subotaNedjelja, double popustSuN, double uvecanjeUVlaku) {
        double cijena = osnovnaCijena * udaljenost;

        if (subotaNedjelja) {
            cijena *= (1 - popustSuN);
        }

        cijena *= (1 + uvecanjeUVlaku);

        return cijena;
    }
}