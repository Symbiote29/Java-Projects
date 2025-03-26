package org.foi.uzdiz.chainofresponsibility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.foi.uzdiz.builderstanica.Stanica;
import org.foi.uzdiz.factorypruga.Pruga;
import org.foi.uzdiz.statePrugeStanice.IspravnaState;
import org.foi.uzdiz.statePrugeStanice.KvarState;
import org.foi.uzdiz.statePrugeStanice.State;
import org.foi.uzdiz.statePrugeStanice.TestiranjeState;
import org.foi.uzdiz.statePrugeStanice.ZatvorenaState;

public class IRPSKomandaHandler implements HandlerKomandi {

    @Override
    public boolean canHandle(String komanda) {
        return komanda.trim().toUpperCase().startsWith("IRPS");
    }
    
    @Override
    public void handle(String komanda, ContextKomandi context) {
        String[] dijelovi = komanda.split(" ");
        
        if (!provjeriSintaksu(dijelovi)) {
            return;
        }

        State status = mapirajStringUState(dijelovi[1].trim());
        String oznakaPruge = dijelovi.length == 3 ? dijelovi[2].trim().toUpperCase() : null;

        try {
            List<Pruga> svePruge = context.getPrugaManager().getPruge();
            boolean imaRezultata = false;

            for (Pruga pruga : svePruge) {
                if (oznakaPruge != null && !pruga.getOznaka().equalsIgnoreCase(oznakaPruge)) {
                    continue;
                }

                StringBuilder ispisPruga = ispisiStatusRelacija(pruga, status, context);
                
                if (ispisPruga.length() > 0) {
                    System.out.print(ispisPruga);
                    imaRezultata = true;
                }
            }
            if (!imaRezultata) {
                System.out.println("Nijedna relacija s traženim statusom (" + status + ") nije pronađena" +
                        (oznakaPruge != null ? " na pruzi " + oznakaPruge : "") + ".");
            }
        } catch (Exception e) {
            System.out.println("Greška u obradi komande: " + e.getMessage());
        }
    }

    private boolean provjeriSintaksu(String[] dijelovi) {
        if (dijelovi.length < 2 || dijelovi.length > 3) {
            System.out.println("Neispravna sintaksa. Koristite: IRPS <status> [oznaka_pruge]");
            return false;
        }
        return true;
    }

    private StringBuilder ispisiStatusRelacija(Pruga pruga, State status, ContextKomandi context) {
        LinkedHashMap<String, State> relacije = pruga.getStatusRelacija();
        StringBuilder ispisPruga = new StringBuilder();

        boolean prugaImaStatus = false;

        for (Map.Entry<String, State> entry : relacije.entrySet()) {
            String relacija = entry.getKey();
            State statusRelacije = entry.getValue();

            if (statusRelacije.getClass().equals(status.getClass())) {
                if (!prugaImaStatus) {
                    ispisPruga.append("Relacije za prugu ").append(pruga.getOznaka()).append(":\n");
                    prugaImaStatus = true;
                }

                String[] stanice = relacija.split("-");
                String pocetnaStanica = stanice[0].trim();
                String krajnjaStanica = stanice[1].trim();
                
                if (!provjeriStanice(pocetnaStanica, krajnjaStanica, context)) {
                    System.out.println("Greška: Neispravno unesene stanice za relaciju " + relacija);
                    continue;
                }

                List<String> staniceIzmedu = dohvatStanicaIzmedu(pruga, pocetnaStanica, krajnjaStanica, context);

                ispisPruga.append("- Relacija: ").append(relacija)
                        .append(", Status: ").append(mapirajStatus(statusRelacije)).append("\n");

                if (!staniceIzmedu.isEmpty()) {
                    ispisPruga.append("  Stanice obuhvaćene kvarom pruge: ")
                            .append(String.join(", ", staniceIzmedu)).append("\n");
                }
            }
        }
        return ispisPruga;
    }

    private boolean provjeriStanice(String pocetnaStanica, String krajnjaStanica, ContextKomandi context) {
        List<Stanica> sveStanice = context.getPrugaManager().getZsDatoteka().getStanice();
        boolean pocetnaPronađena = false;
        boolean krajnjaPronađena = false;

        for (Stanica stanica : sveStanice) {
            if (stanica.getNaziv().equalsIgnoreCase(pocetnaStanica)) {
                pocetnaPronađena = true;
            }
            if (stanica.getNaziv().equalsIgnoreCase(krajnjaStanica)) {
                krajnjaPronađena = true;
            }
        }
        return pocetnaPronađena && krajnjaPronađena;
    }
    
    private List<String> dohvatStanicaIzmedu(Pruga pruga, String pocetnaStanica, String krajnjaStanica, ContextKomandi context) {
        List<Stanica> sveStanice = context.getPrugaManager().getZsDatoteka().getStaniceZaPrugu(pruga.getOznaka());
        List<String> staniceIzmedu = new ArrayList<>();

        boolean jeObrnutiSmjer = isObrnutiSmjer(sveStanice, pocetnaStanica, krajnjaStanica);

        boolean pronadjenaPocetna = false;
        List<Stanica> listaZaPretragu = jeObrnutiSmjer ? new ArrayList<>(sveStanice) : sveStanice;

        if (jeObrnutiSmjer) {
            Collections.reverse(listaZaPretragu);
        }

        for (Stanica stanica : listaZaPretragu) {
            if (stanica.getNaziv().equalsIgnoreCase(pocetnaStanica)) {
                pronadjenaPocetna = true;
                continue;
            }

            if (pronadjenaPocetna) {
                if (stanica.getNaziv().equalsIgnoreCase(krajnjaStanica)) {
                    break;
                }
                staniceIzmedu.add(stanica.getNaziv());
            }
        }
        return staniceIzmedu;
    }

    private boolean isObrnutiSmjer(List<Stanica> sveStanice, String pocetnaStanica, String krajnjaStanica) {
        int pocetnaIndex = -1;
        int krajnjaIndex = -1;
        for (int i = 0; i < sveStanice.size(); i++) {
            if (sveStanice.get(i).getNaziv().equalsIgnoreCase(pocetnaStanica)) {
                pocetnaIndex = i;
            }
            if (sveStanice.get(i).getNaziv().equalsIgnoreCase(krajnjaStanica)) {
                krajnjaIndex = i;
            }
        }

        if (pocetnaIndex == -1 || krajnjaIndex == -1) {
            System.out.println("Greška: Jedna ili obje stanice nisu pronađene.");
            return false;
        }

        boolean isObrnuti = pocetnaIndex > krajnjaIndex;
        return isObrnuti;
    }

    private String mapirajStatus(State stanje) {
        if (stanje instanceof KvarState) {
            return "U kvaru";
        } else if (stanje instanceof ZatvorenaState) {
            return "Zatvoreno";
        } else if (stanje instanceof TestiranjeState) {
            return "U testiranju";
        } else if (stanje instanceof IspravnaState) {
            return "Ispravno";
        } else {
            return "Nepoznat status";
        }
    }

    private State mapirajStringUState(String status) {
        switch (status.toUpperCase()) {
            case "K":
                return new KvarState();
            case "Z":
                return new ZatvorenaState();
            case "T":
                return new TestiranjeState();
            case "I":
                return new IspravnaState();
            default:
                throw new IllegalArgumentException("Nepoznat status: " + status);
        }
    }
}
