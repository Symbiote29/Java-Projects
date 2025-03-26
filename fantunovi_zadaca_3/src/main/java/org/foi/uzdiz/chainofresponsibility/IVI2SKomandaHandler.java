package org.foi.uzdiz.chainofresponsibility;

public class IVI2SKomandaHandler implements HandlerKomandi{
	@Override
    public boolean canHandle(String komanda) {
        return komanda.trim().toUpperCase().startsWith("IVI2S");
    }

    @Override
    public void handle(String komanda, ContextKomandi context) {
	    if (komanda.length() <= 6) {
	        System.out.println("Greška: Kriva komanda. Očekuje se 'IVI2S polazna stanica - odredišna stanica - dan - odVr - doVr - prikaz'.");
	        return;
	    }
	
	    String strippedCommand = komanda.substring(6).trim();
	    String[] dijeloviKomande = strippedCommand.split(" - ");
	
	    if (dijeloviKomande.length < 6) {
	        System.out.println("Greška: Kriva komanda. Očekuje se 6 dijelova komande: polazna stanica - odredišna stanica - dan - odVr - doVr - prikaz.");
	        return;
	    }
	
	    String polaznaStanica = dijeloviKomande[0].trim();
	    String odredisnaStanica = dijeloviKomande[1].trim();
	    String dan = dijeloviKomande[2].trim();
	    String odVr = dijeloviKomande[3].trim();
	    String doVr = dijeloviKomande[4].trim();
	    String prikaz = dijeloviKomande[5].trim();
	
	    if (!odVr.matches("\\d{1,2}:\\d{2}") || !doVr.matches("\\d{1,2}:\\d{2}")) {
	        System.out.println("Greška: Vrijeme mora biti u formatu HH:MM.");
	        return;
	    }
	
	    context.getRaspored().ispisStanicaVoznogRedaNaOdredenDan(polaznaStanica, odredisnaStanica, dan, odVr, doVr);
    }
}
