package org.foi.uzdiz.chainofresponsibility;

import org.foi.uzdiz.mementokupovina.Karta;

public class IKKPVKomandaHandler implements HandlerKomandi {

    @Override
    public boolean canHandle(String komanda) {
        return komanda.trim().toUpperCase().startsWith("IKKPV");
    }

    @Override
    public void handle(String komanda, ContextKomandi context) {
        String[] dijelovi = komanda.split(" ");
        
        try {
            if (dijelovi.length == 1) {
                System.out.println("+------------+------------------------------------------+-----------------+--------------+---------+--------------+------------+");
                System.out.println("| Oznaka    | Ruta                                     | Datum          | Osnovna cijena | Popust  | Konačna cijena | Način kupovine |");
                System.out.println("+------------+------------------------------------------+-----------------+--------------+---------+--------------+------------+");
                for (Karta karta : context.getPovijestKupovina().getKupovine()) {
                    System.out.println(karta);
                }
                System.out.println("+------------+------------------------------------------+-----------------+--------------+---------+--------------+------------+");
            } else if (dijelovi.length == 2) {
                try {
                    int index = Integer.parseInt(dijelovi[1].trim()) - 1;
                    if (index >= 0 && index < context.getPovijestKupovina().getKupovine().size()) {
                        Karta karta = context.getPovijestKupovina().getKupovine().get(index);
                        System.out.println("Ispis " + (index + 1) + ". kupljene karte:");
                        System.out.println("+------------+------------------------------------------+-----------------+--------------+---------+--------------+------------+");
                        System.out.println("| Oznaka    | Ruta                                     | Datum          | Osnovna cijena | Popust  | Konačna cijena | Način kupovine |");
                        System.out.println("+------------+------------------------------------------+-----------------+--------------+---------+--------------+------------+");
                        System.out.println(karta);
                        System.out.println("+------------+------------------------------------------+-----------------+--------------+---------+--------------+------------+");
                    } else {
                        System.out.println("Ne postoji karta s tim brojem.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Neispravan broj za indeks karte.");
                }
            } else {
                System.out.println("Neispravna sintaksa. Koristite: IKKPV ili IKKPV [n], gdje n je broj karte.");
            }
        } catch (Exception e) {
            System.out.println("Greška u obradi komande: " + e.getMessage());
        }
    }
}
