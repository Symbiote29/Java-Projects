package org.foi.uzdiz.mementokupovina;

import java.util.List;

public class Karta {
    private final String oznakaVlaka;
    private final List<String> ruta;
    private final String datumPutovanja;
    private final double osnovnaCijena;
    private final double popustSubotaNedjelja;
    private final double popustNacinKupovine;
    private final double uvecanjeKupovinaUVlaku;
    private final double konacnaCijena;
    private final String nacinKupovine;

    public Karta(String vlak, List<String> ruta, String datumPutovanja,
                 double osnovnaCijena, double popustSubotaNedjelja, double popustNacinKupovine,
                 double uvecanjeKupovinaUVlaku, double konacnaCijena, String nacinKupovine) {
        this.oznakaVlaka = vlak;
        this.ruta = ruta;
        this.datumPutovanja = datumPutovanja;
        this.osnovnaCijena = osnovnaCijena;
        this.popustSubotaNedjelja = popustSubotaNedjelja;
        this.popustNacinKupovine = popustNacinKupovine;
        this.uvecanjeKupovinaUVlaku = uvecanjeKupovinaUVlaku;
        this.konacnaCijena = konacnaCijena;
        this.nacinKupovine = nacinKupovine;
    }
    
    public String getOznakaVlaka() {
        return oznakaVlaka;
    }

    public List<String> getRuta() {
        return ruta;
    }

    public String getDatumPutovanja() {
        return datumPutovanja;
    }

    public double getOsnovnaCijena() {
        return osnovnaCijena;
    }

    public double getPopustSubotaNedjelja() {
        return popustSubotaNedjelja;
    }

    public double getPopustNacinKupovine() {
        return popustNacinKupovine;
    }

    public double getUvecanjeKupovinaUVlaku() {
        return uvecanjeKupovinaUVlaku;
    }

    public double getKonacnaCijena() {
        return konacnaCijena;
    }

    public String getNacinKupovine() {
        return nacinKupovine;
    }

    @Override
    public String toString() {
        String header = String.format("| %-10s | %-40s | %-15s | %-12s | %-7s | %-7s | %-7s | %-12s | %-10s |",
                "Vlak", "Ruta", "Datum", "Osn. cijena", "Pop. SuN", "Pop. NK", "Uveć. UV", "Kon. cijena", "Način kup.");
        String separator = new String(new char[header.length()]).replace("\0", "-");
        String kartaPodaci = String.format("| %-10s | %-40s | %-15s | %-12.2f | %-7.2f | %-7.2f | %-7.2f | %-12.2f | %-10s |",
                oznakaVlaka,
                String.join(" -> ", ruta),
                datumPutovanja,
                osnovnaCijena,
                popustSubotaNedjelja,
                popustNacinKupovine,
                uvecanjeKupovinaUVlaku,
                konacnaCijena,
                nacinKupovine);

        return header + "\n" + separator + "\n" + kartaPodaci;
    }

}
