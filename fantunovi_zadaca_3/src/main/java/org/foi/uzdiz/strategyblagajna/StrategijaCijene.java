package org.foi.uzdiz.strategyblagajna;

public interface StrategijaCijene {
    double izracunajCijenu(double osnovnaCijena, double udaljenost, boolean subotaNedjelja, double popustSuN, double dodatniPopustIliUvecanje);
}
