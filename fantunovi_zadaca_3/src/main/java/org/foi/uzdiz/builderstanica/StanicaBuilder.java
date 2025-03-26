package org.foi.uzdiz.builderstanica;

public class StanicaBuilder {
	private String naziv;
    private String oznakaPruge;
    private String vrsta;
    private String aktivnosti;
    private int brojPerona;
    private String status;
    private double udaljenost;

    public StanicaBuilder setNaziv(String naziv) {
        this.naziv = naziv;
        return this;
    }

    public StanicaBuilder setVrsta(String vrsta) {
        this.vrsta = vrsta;
        return this;
    }

    public StanicaBuilder setAktivnosti(String aktivnosti) {
        this.aktivnosti = aktivnosti;
        return this;
    }

    public StanicaBuilder setBrojPerona(int brojPerona) {
        this.brojPerona = brojPerona;
        return this;
    }

    public StanicaBuilder setStatus(String status) {
        this.status = status;
        return this;
    }

    public StanicaBuilder setUdaljenost(double udaljenost) {
        this.udaljenost = udaljenost;
        return this;
    }

    public StanicaBuilder setOznakaPruge(String oznakaPruge) {
        this.oznakaPruge = oznakaPruge;
        return this;
    }

    public String getNaziv() {
        return naziv;
    }

    public String getVrsta() {
        return vrsta;
    }

    public String getAktivnosti() {
        return aktivnosti;
    }

    public int getBrojPerona() {
        return brojPerona;
    }

    public String getStatus() {
        return status;
    }

    public double getUdaljenost() {
        return udaljenost;
    }

    public String getOznakaPruge() {
        return oznakaPruge;
    }

    public Stanica build() {
        return new Stanica(this);
    }
}
