package org.foi.uzdiz.buildervozilo;

public class VoziloBuilder {
	private String oznaka;
    private String opis;
    private String proizvodjac;
    private int godinaProizvodnje;
    private String namjena;
    private String vrstaPrijevoza;
    private String vrstaPogona;
    private int maxBrzina;
    private double maxSnaga;
    private int brojSjedala;
    private int brojStajanja;
    private int brojKreveta;
    private int kapacitetBicikala;
    private int kapacitetAutomobila;
    private double nosivost;
    private double povrsina;
    private double zapremina;
    private String status;

    public VoziloBuilder(String oznaka) {
        this.oznaka = oznaka;
    }

    public VoziloBuilder setOznaka(String oznaka) {
        this.oznaka = oznaka;
        return this;
    }
    
    public VoziloBuilder setOpis(String opis) {
        this.opis = opis;
        return this;
    }

    public VoziloBuilder setProizvodjac(String proizvodjac) {
        this.proizvodjac = proizvodjac;
        return this;
    }

    public VoziloBuilder setGodinaProizvodnje(int godinaProizvodnje) {
        this.godinaProizvodnje = godinaProizvodnje;
        return this;
    }

    public VoziloBuilder setNamjena(String namjena) {
        this.namjena = namjena;
        return this;
    }

    public VoziloBuilder setVrstaPrijevoza(String vrstaPrijevoza) {
        this.vrstaPrijevoza = vrstaPrijevoza;
        return this;
    }

    public VoziloBuilder setVrstaPogona(String vrstaPogona) {
        this.vrstaPogona = vrstaPogona;
        return this;
    }

    public VoziloBuilder setMaxSnaga(double maxSnaga) {
        this.maxSnaga = maxSnaga;
        return this;
    }

    public VoziloBuilder setMaxBrzina(int maxBrzina) {
        this.maxBrzina = maxBrzina;
        return this;
    }

    public VoziloBuilder setBrojSjedala(int brojSjedala) {
        this.brojSjedala = brojSjedala;
        return this;
    }

    public VoziloBuilder setBrojStajanja(int brojStajanja) {
        this.brojStajanja = brojStajanja;
        return this;
    }

    public VoziloBuilder setBrojKreveta(int brojKreveta) {
        this.brojKreveta = brojKreveta;
        return this;
    }

    public VoziloBuilder setKapacitetBicikala(int kapacitetBicikala) {
        this.kapacitetBicikala = kapacitetBicikala;
        return this;
    }

    public VoziloBuilder setKapacitetAutomobila(int kapacitetAutomobila) {
        this.kapacitetAutomobila = kapacitetAutomobila;
        return this;
    }

    public VoziloBuilder setNosivost(double nosivost) {
        this.nosivost = nosivost;
        return this;
    }

    public VoziloBuilder setPovrsina(double povrsina) {
        this.povrsina = povrsina;
        return this;
    }

    public VoziloBuilder setZapremina(double zapremina) {
        this.zapremina = zapremina;
        return this;
    }

    public VoziloBuilder setStatus(String status) {
        this.status = status;
        return this;
    }

    public Vozilo build() {
        return new Vozilo(oznaka, opis, proizvodjac, godinaProizvodnje, namjena, vrstaPrijevoza, vrstaPogona,
                          maxSnaga, maxBrzina, brojSjedala, brojStajanja, brojKreveta, kapacitetBicikala,
                          kapacitetAutomobila, nosivost, povrsina, zapremina, status);
    }
}
