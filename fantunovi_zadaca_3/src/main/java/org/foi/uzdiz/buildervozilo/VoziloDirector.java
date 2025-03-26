package org.foi.uzdiz.buildervozilo;

public class VoziloDirector {

    private VoziloBuilder builder;

    public VoziloDirector(VoziloBuilder builder) {
        this.builder = builder;
    }

    public Vozilo buildLokomotiva(String oznaka, String opis, String proizvodjac, int godinaProizvodnje, String namjena, String vrstaPrijevoza, String vrstaPogona,
    		int maxBrzina, double maxSnaga, int brojSjedala, int brojStajanja, int kapacitetBicikala, int brojKreveta, int kapacitetAutomobila, double nosivost,
    		double povrsina, double zapremnina, String status) {
        return builder.setOznaka(oznaka)
        		.setOpis(opis)
                .setProizvodjac(proizvodjac)
                .setGodinaProizvodnje(godinaProizvodnje)
                .setNamjena(namjena)
                .setVrstaPrijevoza(vrstaPrijevoza) 
                .setVrstaPogona(vrstaPogona)
                .setMaxBrzina(maxBrzina)
                .setMaxSnaga(maxSnaga) 
                .setBrojSjedala(brojSjedala) 
                .setBrojStajanja(brojStajanja)
                .setKapacitetBicikala(kapacitetBicikala)
                .setBrojKreveta(brojKreveta)
                .setKapacitetAutomobila(kapacitetAutomobila)
                .setNosivost(nosivost)
                .setPovrsina(povrsina)
                .setZapremina(zapremnina)
                .setStatus(status)
                .build();
    }

    public Vozilo buildPutnickoPrijevoznoSredstvo(String oznaka, String opis, String proizvodjac, int godinaProizvodnje, String namjena, String vrstaPrijevoza, String vrstaPogona,
    		int maxBrzina, double maxSnaga, int brojSjedala, int brojStajanja, int kapacitetBicikala, int brojKreveta, int kapacitetAutomobila, double nosivost,
    		double povrsina, double zapremnina, String status) {
        return builder.setOznaka(oznaka)
        		.setOpis(opis)
                .setProizvodjac(proizvodjac)
                .setGodinaProizvodnje(godinaProizvodnje)
                .setNamjena(namjena)
                .setVrstaPrijevoza(vrstaPrijevoza) 
                .setVrstaPogona(vrstaPogona)
                .setMaxBrzina(maxBrzina)
                .setMaxSnaga(maxSnaga) 
                .setBrojSjedala(brojSjedala) 
                .setBrojStajanja(brojStajanja)
                .setKapacitetBicikala(kapacitetBicikala)
                .setBrojKreveta(brojKreveta)
                .setKapacitetAutomobila(kapacitetAutomobila)
                .setNosivost(nosivost)
                .setPovrsina(povrsina)
                .setZapremina(zapremnina)
                .setStatus(status)
                .build();
    }
    
    public Vozilo buildPutnickoPrijevoznoSredstvoZaSpavanje(String oznaka, String opis, String proizvodjac, int godinaProizvodnje, String namjena, String vrstaPrijevoza, String vrstaPogona,
    		int maxBrzina, double maxSnaga, int brojSjedala, int brojStajanja, int kapacitetBicikala, int brojKreveta, int kapacitetAutomobila, double nosivost,
    		double povrsina, double zapremnina, String status) {
        return builder.setOznaka(oznaka)
        		.setOpis(opis)
                .setProizvodjac(proizvodjac)
                .setGodinaProizvodnje(godinaProizvodnje)
                .setNamjena(namjena)
                .setVrstaPrijevoza(vrstaPrijevoza) 
                .setVrstaPogona(vrstaPogona)
                .setMaxBrzina(maxBrzina)
                .setMaxSnaga(maxSnaga) 
                .setBrojSjedala(brojSjedala) 
                .setBrojStajanja(brojStajanja)
                .setKapacitetBicikala(kapacitetBicikala)
                .setBrojKreveta(brojKreveta)
                .setKapacitetAutomobila(kapacitetAutomobila)
                .setNosivost(nosivost)
                .setPovrsina(povrsina)
                .setZapremina(zapremnina)
                .setStatus(status)
                .build();
    }
    
    public Vozilo buildPutnickoPrijevoznoSredstvoKaoRestoran(String oznaka, String opis, String proizvodjac, int godinaProizvodnje, String namjena, String vrstaPrijevoza, String vrstaPogona,
    		int maxBrzina, double maxSnaga, int brojSjedala, int brojStajanja, int kapacitetBicikala, int brojKreveta, int kapacitetAutomobila, double nosivost,
    		double povrsina, double zapremnina, String status) {
        return builder.setOznaka(oznaka)
        		.setOpis(opis)
                .setProizvodjac(proizvodjac)
                .setGodinaProizvodnje(godinaProizvodnje)
                .setNamjena(namjena)
                .setVrstaPrijevoza(vrstaPrijevoza) 
                .setVrstaPogona(vrstaPogona)
                .setMaxBrzina(maxBrzina)
                .setMaxSnaga(maxSnaga) 
                .setBrojSjedala(brojSjedala) 
                .setBrojStajanja(brojStajanja)
                .setKapacitetBicikala(kapacitetBicikala)
                .setBrojKreveta(brojKreveta)
                .setKapacitetAutomobila(kapacitetAutomobila)
                .setNosivost(nosivost)
                .setPovrsina(povrsina)
                .setZapremina(zapremnina)
                .setStatus(status)
                .build();
    }
    
    public Vozilo buildTeretnoPrijevoznoSredstvoZaAutomobile(String oznaka, String opis, String proizvodjac, int godinaProizvodnje, String namjena, String vrstaPrijevoza, String vrstaPogona,
    		int maxBrzina, double maxSnaga, int brojSjedala, int brojStajanja, int kapacitetBicikala, int brojKreveta, int kapacitetAutomobila, double nosivost,
    		double povrsina, double zapremnina, String status) {
        return builder.setOznaka(oznaka)
        		.setOpis(opis)
                .setProizvodjac(proizvodjac)
                .setGodinaProizvodnje(godinaProizvodnje)
                .setNamjena(namjena)
                .setVrstaPrijevoza(vrstaPrijevoza) 
                .setVrstaPogona(vrstaPogona)
                .setMaxBrzina(maxBrzina)
                .setMaxSnaga(maxSnaga) 
                .setBrojSjedala(brojSjedala) 
                .setBrojStajanja(brojStajanja)
                .setKapacitetBicikala(kapacitetBicikala)
                .setBrojKreveta(brojKreveta)
                .setKapacitetAutomobila(kapacitetAutomobila)
                .setNosivost(nosivost)
                .setPovrsina(povrsina)
                .setZapremina(zapremnina)
                .setStatus(status)
                .build();
    }
    
    public Vozilo buildTeretnoPrijevoznoSredstvoZaPakiranuRobuUKontejnerima(String oznaka, String opis, String proizvodjac, int godinaProizvodnje, String namjena, String vrstaPrijevoza, String vrstaPogona,
    		int maxBrzina, double maxSnaga, int brojSjedala, int brojStajanja, int kapacitetBicikala, int brojKreveta, int kapacitetAutomobila, double nosivost,
    		double povrsina, double zapremnina, String status) {
        return builder.setOznaka(oznaka)
        		.setOpis(opis)
                .setProizvodjac(proizvodjac)
                .setGodinaProizvodnje(godinaProizvodnje)
                .setNamjena(namjena)
                .setVrstaPrijevoza(vrstaPrijevoza) 
                .setVrstaPogona(vrstaPogona)
                .setMaxBrzina(maxBrzina)
                .setMaxSnaga(maxSnaga) 
                .setBrojSjedala(brojSjedala) 
                .setBrojStajanja(brojStajanja)
                .setKapacitetBicikala(kapacitetBicikala)
                .setBrojKreveta(brojKreveta)
                .setKapacitetAutomobila(kapacitetAutomobila)
                .setNosivost(nosivost)
                .setPovrsina(povrsina)
                .setZapremina(zapremnina)
                .setStatus(status)
                .build();
    }
    
    public Vozilo buildTeretnoPrijevoznoSredstvoZaRobuURasutomStanju(String oznaka, String opis, String proizvodjac, int godinaProizvodnje, String namjena, String vrstaPrijevoza, String vrstaPogona,
    		int maxBrzina, double maxSnaga, int brojSjedala, int brojStajanja, int kapacitetBicikala, int brojKreveta, int kapacitetAutomobila, double nosivost,
    		double povrsina, double zapremnina, String status) {
        return builder.setOznaka(oznaka)
        		.setOpis(opis)
                .setProizvodjac(proizvodjac)
                .setGodinaProizvodnje(godinaProizvodnje)
                .setNamjena(namjena)
                .setVrstaPrijevoza(vrstaPrijevoza) 
                .setVrstaPogona(vrstaPogona)
                .setMaxBrzina(maxBrzina)
                .setMaxSnaga(maxSnaga) 
                .setBrojSjedala(brojSjedala) 
                .setBrojStajanja(brojStajanja)
                .setKapacitetBicikala(kapacitetBicikala)
                .setBrojKreveta(brojKreveta)
                .setKapacitetAutomobila(kapacitetAutomobila)
                .setNosivost(nosivost)
                .setPovrsina(povrsina)
                .setZapremina(zapremnina)
                .setStatus(status)
                .build();
    }
    
    public Vozilo buildTeretnoPrijevoznoSredstvoZaRobuUTekucemStanju(String oznaka, String opis, String proizvodjac, int godinaProizvodnje, String namjena, String vrstaPrijevoza, String vrstaPogona,
    		int maxBrzina, double maxSnaga, int brojSjedala, int brojStajanja, int kapacitetBicikala, int brojKreveta, int kapacitetAutomobila, double nosivost,
    		double povrsina, double zapremnina, String status) {
        return builder.setOznaka(oznaka)
        		.setOpis(opis)
                .setProizvodjac(proizvodjac)
                .setGodinaProizvodnje(godinaProizvodnje)
                .setNamjena(namjena)
                .setVrstaPrijevoza(vrstaPrijevoza) 
                .setVrstaPogona(vrstaPogona)
                .setMaxBrzina(maxBrzina)
                .setMaxSnaga(maxSnaga) 
                .setBrojSjedala(brojSjedala) 
                .setBrojStajanja(brojStajanja)
                .setKapacitetBicikala(kapacitetBicikala)
                .setBrojKreveta(brojKreveta)
                .setKapacitetAutomobila(kapacitetAutomobila)
                .setNosivost(nosivost)
                .setPovrsina(povrsina)
                .setZapremina(zapremnina)
                .setStatus(status)
                .build();
    }
    
    public Vozilo buildTeretnoPrijevoznoSredstvoZaRobuUPlinovitomStanju(String oznaka, String opis, String proizvodjac, int godinaProizvodnje, String namjena, String vrstaPrijevoza, String vrstaPogona,
    		int maxBrzina, double maxSnaga, int brojSjedala, int brojStajanja, int kapacitetBicikala, int brojKreveta, int kapacitetAutomobila, double nosivost,
    		double povrsina, double zapremnina, String status) {
        return builder.setOznaka(oznaka)
        		.setOpis(opis)
                .setProizvodjac(proizvodjac)
                .setGodinaProizvodnje(godinaProizvodnje)
                .setNamjena(namjena)
                .setVrstaPrijevoza(vrstaPrijevoza) 
                .setVrstaPogona(vrstaPogona)
                .setMaxBrzina(maxBrzina)
                .setMaxSnaga(maxSnaga) 
                .setBrojSjedala(brojSjedala) 
                .setBrojStajanja(brojStajanja)
                .setKapacitetBicikala(kapacitetBicikala)
                .setBrojKreveta(brojKreveta)
                .setKapacitetAutomobila(kapacitetAutomobila)
                .setNosivost(nosivost)
                .setPovrsina(povrsina)
                .setZapremina(zapremnina)
                .setStatus(status)
                .build();
    }
}
