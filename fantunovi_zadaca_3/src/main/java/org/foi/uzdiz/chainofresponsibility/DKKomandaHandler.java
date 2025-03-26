package org.foi.uzdiz.chainofresponsibility;

public class DKKomandaHandler implements HandlerKomandi{
	@Override
    public boolean canHandle(String komanda) {
        return komanda.trim().toUpperCase().startsWith("DK");
    }

    @Override
    public void handle(String komanda, ContextKomandi context) {
        String korisnikInfo = komanda.substring(2).trim();

        String[] korisnikPodaci = korisnikInfo.split(" ");

        if (korisnikPodaci.length == 2) {
            String ime = cleanString(korisnikPodaci[0]);
            String prezime = cleanString(korisnikPodaci[1]);

            context.getRegistar().dodajKorisnika(ime, prezime);
        } else {
            System.out.println("Greška: Neispravna komanda. Molimo unesite ime i prezime u formatu 'DK <ime> <prezime>'.");
        }
    }

    private String cleanString(String input) {
        return input.replaceAll("[^a-zA-ZČĆŽŠĐčćžšđ]", "").trim();
    }
}
