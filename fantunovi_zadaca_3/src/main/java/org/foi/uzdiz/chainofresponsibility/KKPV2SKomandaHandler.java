package org.foi.uzdiz.chainofresponsibility;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.foi.uzdiz.managers.StrategijaCijenaManager;
import org.foi.uzdiz.mementokupovina.Karta;

public class KKPV2SKomandaHandler implements HandlerKomandi {

    @Override
    public boolean canHandle(String komanda) {
        return komanda.trim().toUpperCase().startsWith("KKPV2S");
    }
    
    @Override
    public void handle(String komanda, ContextKomandi context) {
        String[] dijelovi = komanda.split(" - ");
        if (dijelovi.length < 5) {
            System.out.println("Neispravna sintaksa. Koristite: KKPV2S oznaka - polaznaStanica - odredišnaStanica - datum - načinKupovine");
            return;
        }

        try {
            String oznakaVlaka = dijelovi[0].split(" ")[1].trim();
            String polaznaStanica = dijelovi[1].trim();
            String odredisnaStanica = dijelovi[2].trim();
            String datumPutovanja = dijelovi[3].trim();
            String nacinKupovine = dijelovi[4].trim();

            List<String> ruta = context.getPrugaManager().pretraziPut(polaznaStanica, odredisnaStanica);
            if (ruta.isEmpty()) {
                System.out.println("Nema rute između " + polaznaStanica + " i " + odredisnaStanica + ".");
                return;
            }

            if (!context.getPrugaManager().jeRutaDostupna(ruta, context)) {
                System.out.println("Jedna ili više pruga na ruti nisu dostupne.");
                return;
            }

            double udaljenost = context.getPrugaManager().izracunajUdaljenost(ruta);
            System.out.println("Ruta pronađena: " + String.join(" -> ", ruta) + ", ukupna udaljenost: " + udaljenost + " km");

            String vrstaVlaka = context.getZvrDatoteka().dohvatiVrstuVlakaPoOznaci(oznakaVlaka);
            if (vrstaVlaka == null) {
                System.out.println("Greška: Nema podataka za oznaku vlaka " + oznakaVlaka);
                return;
            }

            boolean jeLiSubNed = jeSubotaIliNedjelja(datumPutovanja);

            StrategijaCijenaManager strategije = context.getStrategijeCijenaManager();
            strategije.setStrategijaNaTemeljuKupovine(nacinKupovine);

            double osnovnaCijena = strategije.izracunajCijenu(vrstaVlaka, udaljenost, false, "b");
            double konacnaCijena = strategije.izracunajCijenu(vrstaVlaka, udaljenost, jeLiSubNed, nacinKupovine);
            double popustSubotaNedjelja = jeLiSubNed ? osnovnaCijena * strategije.getPopustSuN() : 0;
            double dodatniFaktor = nacinKupovine.equalsIgnoreCase("wm") ? strategije.getPopustWebMob()
                    : (nacinKupovine.equalsIgnoreCase("v") ? strategije.getUvecanjeUVlaku() : 0);

            System.out.println("Konačna cijena: " + konacnaCijena + " €");

            Karta karta = new Karta(oznakaVlaka, ruta, datumPutovanja, osnovnaCijena, popustSubotaNedjelja, dodatniFaktor >= 0 ? 0 : osnovnaCijena * dodatniFaktor, 
            		dodatniFaktor > 0 ? osnovnaCijena * dodatniFaktor : 0, konacnaCijena, nacinKupovine);
            
            context.getPovijestKupovina().dodajKupovinu(karta);

            System.out.println("Kupovina uspješno evidentirana:");
            System.out.println(karta);

        } catch (Exception e) {
            System.out.println("Greška u obradi komande: " + e.getMessage());
        }
    }


    public static boolean jeSubotaIliNedjelja(String datumPutovanja) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        LocalDate datum = LocalDate.parse(datumPutovanja, formatter);
        DayOfWeek dan = datum.getDayOfWeek();
        return dan == DayOfWeek.SATURDAY || dan == DayOfWeek.SUNDAY;
    }
}
