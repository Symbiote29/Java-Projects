package org.foi.uzdiz.chainofresponsibility;

import java.util.ArrayList;
import java.util.List;

import org.foi.uzdiz.builderstanica.Stanica;
import org.foi.uzdiz.factorypruga.Pruga;
import org.foi.uzdiz.statePrugeStanice.IspravnaState;
import org.foi.uzdiz.statePrugeStanice.KvarState;
import org.foi.uzdiz.statePrugeStanice.State;
import org.foi.uzdiz.statePrugeStanice.TestiranjeState;
import org.foi.uzdiz.statePrugeStanice.ZatvorenaState;

public class PSP2SKomandaHandler implements HandlerKomandi {

    @Override
    public boolean canHandle(String komanda) {
        return komanda.trim().toUpperCase().startsWith("PSP2S");
    }

    @Override
    public void handle(String komanda, ContextKomandi context) {
        String[] dijelovi = komanda.split(" - ");
        if (dijelovi.length < 4) {
            System.out.println("Neispravna sintaksa. Koristite: PSP2S <oznakaPruge> - <pocetnaStanica> - <krajnjaStanica> - <status>");
            return;
        }

        try {
            String oznakaPruge = dijelovi[0].split(" ")[1].trim();
            String pocetnaStanica = dijelovi[1].trim();
            String krajnjaStanica = dijelovi[2].trim();
            String statusPruge = dijelovi[3].trim().toUpperCase();

            Pruga pruga = context.getPrugaManager().pronadiPrugu(oznakaPruge);
            if (pruga == null) {
                System.out.println("Pruga s oznakom " + oznakaPruge + " ne postoji.");
                return;
            }

            List<String> staniceIzmedu = dohvatiSveStaniceIzmedu(context, pruga, pocetnaStanica, krajnjaStanica);
            int brojStanicaIzmedu = staniceIzmedu.size() - 2;

            if (brojStanicaIzmedu == 1) {
                if (imaPreklapanjeRelacija(context, pruga, pocetnaStanica, krajnjaStanica)) {
                    System.out.println("Ne možete dodati relaciju jer dolazi do preklapanja s postojećim relacijama.");
                    return;
                }
            }

            List<Stanica> sveStanice = context.getPrugaManager().getZsDatoteka().getStaniceZaPrugu(pruga.getOznaka());

            boolean jeObrnutiSmjer = sveStanice.indexOf(pocetnaStanica) > sveStanice.indexOf(krajnjaStanica);
            if (jeObrnutiSmjer) {
                System.out.println("Smjer je obrnuti.");
            } else {
                System.out.println("Smjer je normalni.");
            }

            if (brojStanicaIzmedu > 1) {
                if (jeObrnutiSmjer) {
                    String relacijaObrnutiSmjer = pocetnaStanica + "-" + krajnjaStanica;

                    if (!pruga.getStatusRelacija().containsKey(relacijaObrnutiSmjer)) {
                        postaviStatusPremaState(pruga, statusPruge, krajnjaStanica, pocetnaStanica, relacijaObrnutiSmjer, "O");
                    } else {
                        System.out.println("Status za obrnuti smjer je već postavljen.");
                    }
                } else {
                    String relacijaNormalniSmjer = pocetnaStanica + "-" + krajnjaStanica;

                    if (!pruga.getStatusRelacija().containsKey(relacijaNormalniSmjer)) {
                        postaviStatusPremaState(pruga, statusPruge, pocetnaStanica, krajnjaStanica, relacijaNormalniSmjer, "N");
                    } else {
                        System.out.println("Status za normalni smjer je već postavljen.");
                    }
                }
            } else {
                String relacijaNormalniSmjer = pocetnaStanica + "-" + krajnjaStanica;
                String relacijaObrnutiSmjer = krajnjaStanica + "-" + pocetnaStanica;

                if (brojStanicaIzmedu == 1) {
                    postaviStatusPremaState(pruga, statusPruge, pocetnaStanica, krajnjaStanica, relacijaNormalniSmjer, "N");
                    postaviStatusPremaState(pruga, statusPruge, krajnjaStanica, pocetnaStanica, relacijaObrnutiSmjer, "O");
                } else {
                    if (!pruga.getStatusRelacija().containsKey(relacijaNormalniSmjer) && !pruga.getStatusRelacija().containsKey(relacijaObrnutiSmjer)) {
                        postaviStatusPremaState(pruga, statusPruge, pocetnaStanica, krajnjaStanica, relacijaNormalniSmjer, "N");
                        postaviStatusPremaState(pruga, statusPruge, krajnjaStanica, pocetnaStanica, relacijaObrnutiSmjer, "O");
                    } else {
                        System.out.println("Ne možete postaviti status jer dolazi do preklapanja.");
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Greška u obradi komande: " + e.getMessage());
        }
    }

    private boolean imaPreklapanjeZaKolosijek(Pruga pruga, String pocetnaStanica, String krajnjaStanica) {
        String relacijaNormalniSmjer = pocetnaStanica + "-" + krajnjaStanica;
        String relacijaObrnutiSmjer = krajnjaStanica + "-" + pocetnaStanica;

        return pruga.getStatusRelacija().containsKey(relacijaNormalniSmjer) || pruga.getStatusRelacija().containsKey(relacijaObrnutiSmjer);
    }


    private void postaviStatusPremaState(Pruga pruga, String statusPruge, String pocetnaStanica, String krajnjaStanica, String relacija, String smjer) {
        State stanje = null;
        
        switch (statusPruge) {
            case "K":
                stanje = new KvarState();
                break;
            case "I":
                stanje = new IspravnaState();
                break;
            case "T":
                stanje = new TestiranjeState();
                break;
            case "Z":
                stanje = new ZatvorenaState();
                break;
            default:
                System.out.println("Neispravan status pruge.");
                return;
        }

        if (stanje != null) {
            stanje.promijeniStatus(pruga, pocetnaStanica, krajnjaStanica);
            pruga.getStatusRelacija().put(relacija, stanje);
            
            if (smjer.equals("N")) {
                System.out.println("Status pruge između " + pocetnaStanica + " i " + krajnjaStanica + " (normalni smjer) postavljen na: " + statusPruge);
            } else if (smjer.equals("O")) {
                System.out.println("Status pruge između " + pocetnaStanica + " i " + krajnjaStanica + " (obrnuti smjer) postavljen na: " + statusPruge);
            }
        }
    }

    private boolean isObrnutiSmjer(List<Stanica> sveStanice, String pocetnaStanica, String krajnjaStanica) {
        Stanica pocetna = null;
        Stanica krajnja = null;

        for (Stanica stanica : sveStanice) {
            if (stanica.getNaziv().equals(pocetnaStanica)) {
                pocetna = stanica;
            }
            if (stanica.getNaziv().equals(krajnjaStanica)) {
                krajnja = stanica;
            }
        }

        if (pocetna == null || krajnja == null) {
            System.out.println("Stanica nije pronađena.");
            return false;
        }

        int pocetnaIndex = sveStanice.indexOf(pocetna);
        int krajnjaIndex = sveStanice.indexOf(krajnja);

        return pocetnaIndex > krajnjaIndex;
    }

    
    private boolean imaPreklapanjeRelacija(ContextKomandi context, Pruga pruga, String pocetnaStanica, String krajnjaStanica) {
        List<String> noveStanice = dohvatiSveStaniceIzmedu(context, pruga, pocetnaStanica, krajnjaStanica);

        for (String relacija : pruga.getStatusRelacija().keySet()) {
            String[] staniceRelacije = relacija.split("-");
            String postojecaPocetna = staniceRelacije[0].trim();
            String postojecaKrajnja = staniceRelacije[1].trim();

            List<String> postojecaStanice = dohvatiSveStaniceIzmedu(context, pruga, postojecaPocetna, postojecaKrajnja);

            if (provjeriPreklapanje(postojecaStanice, noveStanice)) {
                System.out.println("Preklapanje između: " + pocetnaStanica + " i " + krajnjaStanica +
                                   " s postojećom relacijom: " + postojecaPocetna + " - " + postojecaKrajnja);
                return true;
            }
        }

        return false;
    }

    private boolean provjeriPreklapanje(List<String> postojecaStanice, List<String> noveStanice) {
        for (int i = 1; i < postojecaStanice.size() - 1; i++) {
            String postojecaStanica = postojecaStanice.get(i);
            if (noveStanice.contains(postojecaStanica)) {
                return true;
            }
        }
        return false;
    }


    private List<String> dohvatiSveStaniceIzmedu(ContextKomandi context, Pruga pruga, String pocetnaStanica, String krajnjaStanica) {
    	List<Stanica> sveStanice = context.getPrugaManager().getZsDatoteka().getStaniceZaPrugu(pruga.getOznaka());
        List<String> staniceIzmedu = new ArrayList<>();
     
        boolean dodavanje = false;

        for (Stanica stanica : sveStanice) {
            String nazivStanice = stanica.getNaziv();

            if (nazivStanice.equals(pocetnaStanica)) {
                dodavanje = true;
            }
            if (dodavanje) {
                staniceIzmedu.add(nazivStanice);
            }
            if (nazivStanice.equals(krajnjaStanica)) {
                break;
            }
        }
        
        if (staniceIzmedu.isEmpty() || !staniceIzmedu.contains(krajnjaStanica)) {
            List<String> obrnutimRedoslijedom = new ArrayList<>();
            dodavanje = false;
            for (int i = sveStanice.size() - 1; i >= 0; i--) {
                Stanica stanica = sveStanice.get(i);
                String nazivStanice = stanica.getNaziv();

                if (nazivStanice.equals(pocetnaStanica)) {
                    dodavanje = true;
                }
                if (dodavanje) {
                    obrnutimRedoslijedom.add(nazivStanice);
                }
                if (nazivStanice.equals(krajnjaStanica)) {
                    break;
                }
            }
            return obrnutimRedoslijedom;
        }

        return staniceIzmedu;
    }


}
