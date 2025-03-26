package org.foi.uzdiz.buildervozilo;

public class Vozilo {
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

    public Vozilo(String oznaka, String opis, String proizvodjac, int godinaProizvodnje, String namjena, 
                   String vrstaPrijevoza, String vrstaPogona, double maxSnaga, int maxBrzina,
                   int brojSjedala, int brojStajanja, int brojKreveta, int kapacitetBicikala,
                   int kapacitetAutomobila, double nosivost, double povrsina, double zapremina, String status) {
        this.oznaka = oznaka;
        this.opis = opis;
        this.proizvodjac = proizvodjac;
        this.godinaProizvodnje = godinaProizvodnje;
        this.namjena = namjena;
        this.vrstaPrijevoza = vrstaPrijevoza;
        this.vrstaPogona = vrstaPogona;
        this.maxSnaga = maxSnaga;
        this.maxBrzina = maxBrzina;
        this.brojSjedala = brojSjedala;
        this.brojStajanja = brojStajanja;
        this.brojKreveta = brojKreveta;
        this.kapacitetBicikala = kapacitetBicikala;
        this.kapacitetAutomobila = kapacitetAutomobila;
        this.nosivost = nosivost;
        this.povrsina = povrsina;
        this.zapremina = zapremina;
        this.status = status;
    }

    public String getOznaka() { 
    	return oznaka; 
    
    }
    
    public String getOpis() {
    	return opis; 
    }
    
    public String getProizvodjac() {
    	return proizvodjac; 
    }
    
    public int getGodinaProizvodnje() {
    	return godinaProizvodnje; 
    }
    
    public String getNamjena() {
    	return namjena; 
    }
    
    public String getVrstaPrijevoza() {
    	return vrstaPrijevoza; 
    }
    
    public String getVrstaPogona() {
    	return vrstaPogona; 
    }
    
    public double getMaxSnaga() {
    	return maxSnaga; 
    }
    
    public int getMaxBrzina() {
    	return maxBrzina; 
    }
    
    public int getBrojSjedala() {
    	return brojSjedala; 
    }
    
    public int getBrojStajanja() {
    	return brojStajanja; 
    }
    
    public int getBrojKreveta() {
    	return brojKreveta; 
    }
    
    public int getKapacitetBicikala() {
    	return kapacitetBicikala; 
    }
    
    public int getKapacitetAutomobila() {
    	return kapacitetAutomobila; 
    }
    
    public double getNosivost() {
    	return nosivost; 
    }
    
    public double getPovrsina() {
    	return povrsina; 
    }
    
    public double getZapremina() {
    	return zapremina; 
    }
    
    public String getStatus() {
    	return status; 
    }
    

    @Override
    public String toString() {
        return "Vozilo [oznaka=" + oznaka + ", opis=" + opis + ", proizvodjac=" + proizvodjac + ", godinaProizvodnje=" + godinaProizvodnje +
                ", namjena=" + namjena + ", vrstaPrijevoza=" + vrstaPrijevoza + ", vrstaPogona=" + vrstaPogona +
                ", maxSnaga=" + maxSnaga + ", maxBrzina=" + maxBrzina + ", brojSjedala=" + brojSjedala +
                ", brojStajanja=" + brojStajanja + ", brojKreveta=" + brojKreveta + ", kapacitetBicikala=" + kapacitetBicikala +
                ", kapacitetAutomobila=" + kapacitetAutomobila + ", nosivost=" + nosivost + ", povrsina=" + povrsina +
                ", zapremina=" + zapremina + ", status=" + status + "]";
    }
}
