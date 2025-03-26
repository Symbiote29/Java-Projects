package org.foi.uzdiz.managers;

import org.foi.uzdiz.strategyblagajna.BlagajnaStrategija;
import org.foi.uzdiz.strategyblagajna.KupovinaUVlakuStrategija;
import org.foi.uzdiz.strategyblagajna.StrategijaCijene;
import org.foi.uzdiz.strategyblagajna.WebMobilnaStrategija;

public class StrategijaCijenaManager {
    private double cijenaNormalni;
    private double cijenaUbrzani;
    private double cijenaBrzi;
    private double popustSuN;
    private double popustWebMob;
    private double uvecanjeVlak;

    private StrategijaCijene strategijaCijene;

    public void postaviCijene(double cijenaNormalni, double cijenaUbrzani, double cijenaBrzi, double popustSuN, double popustWebMob, double uvecanjeVlak) {
        this.cijenaNormalni = cijenaNormalni;
        this.cijenaUbrzani = cijenaUbrzani;
        this.cijenaBrzi = cijenaBrzi;
        this.popustSuN = popustSuN;
        this.popustWebMob = popustWebMob;
        this.uvecanjeVlak = uvecanjeVlak;
    }

    public void setStrategijaNaTemeljuKupovine(String nacinKupovine) {
        switch (nacinKupovine.toLowerCase()) {
            case "wm":
                this.strategijaCijene = new WebMobilnaStrategija();
                break;
            case "b":
                this.strategijaCijene = new BlagajnaStrategija();
                break;
            case "v":
                this.strategijaCijene = new KupovinaUVlakuStrategija();
                break;
            default:
                throw new IllegalArgumentException("Nepoznat način kupovine: " + nacinKupovine);
        }
    }

    public double izracunajCijenu(String vrstaVlaka, double udaljenost, boolean jeLiSubotaNedjelja, String nacinKupovine) {
        double osnovnaCijena;
        switch (vrstaVlaka.toLowerCase()) {
            case "n":
                osnovnaCijena = cijenaNormalni;
                break;
            case "u":
                osnovnaCijena = cijenaUbrzani;
                break;
            case "b":
                osnovnaCijena = cijenaBrzi;
                break;
            default:
                throw new IllegalArgumentException("Nepoznata oznaka vlaka: " + vrstaVlaka);
        }

        setStrategijaNaTemeljuKupovine(nacinKupovine);
        if (strategijaCijene != null) {
            double dodatniPopustIliUvecanje = 0.0;

            switch (nacinKupovine.toLowerCase()) {
                case "wm":
                    dodatniPopustIliUvecanje = popustWebMob;
                    break;
                case "v":
                    dodatniPopustIliUvecanje = uvecanjeVlak;
                    break;
                default:
                    dodatniPopustIliUvecanje = 0.0;
            }

            return strategijaCijene.izracunajCijenu(osnovnaCijena, udaljenost, jeLiSubotaNedjelja, popustSuN, dodatniPopustIliUvecanje);
        } else {
            throw new IllegalStateException("Strategija cijene nije postavljena.");
        }
    }

    public double getPopustSuN() {
        return popustSuN;
    }

    public double getPopustWebMob() {
        return popustWebMob;
    }

    public double getUvecanjeUVlaku() {
        return uvecanjeVlak;
    }
}
