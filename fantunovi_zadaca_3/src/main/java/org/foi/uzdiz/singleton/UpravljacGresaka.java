package org.foi.uzdiz.singleton;

import java.util.ArrayList;
import java.util.List;

public class UpravljacGresaka {
    private static UpravljacGresaka instanca;

    private static int brojacGresaka = 0;

    private static List<String> greske = new ArrayList<>();

    private UpravljacGresaka() {
    }

    public static synchronized UpravljacGresaka getInstanca() {
        if (instanca == null) {
        	instanca = new UpravljacGresaka();
        }
        return instanca;
    }

    public synchronized void dodajGresku(String greskaTest) {
    	brojacGresaka++;
        String greska = "Greska " + brojacGresaka + ": " + greskaTest;
        greske.add(greska);
    }

    public synchronized String prikaziGreske() {
        if (greske.isEmpty()) {
            return "Nema gresaka";
        }
        String errorList = String.join("\n", greske);

        return "\n" + errorList + "\n";
    }

    public synchronized void ocistiGreske() {
    	brojacGresaka = 0;
        greske.clear();
    }
}
