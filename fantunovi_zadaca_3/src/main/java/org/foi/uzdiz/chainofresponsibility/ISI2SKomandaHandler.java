package org.foi.uzdiz.chainofresponsibility;

public class ISI2SKomandaHandler implements HandlerKomandi{
	@Override
    public boolean canHandle(String komanda) {
        return komanda.startsWith("ISI2S");
    }

    @Override
    public void handle(String komanda, ContextKomandi context) {
        String[] parts = komanda.split(" - ");
        if (parts.length == 2) {
            String pocetnaStanica = parts[0].substring(6).trim();
            String zavrsnaStanica = parts[1].trim();

            context.getPrugaManager().ispisiPut(pocetnaStanica, zavrsnaStanica);
        } else {
            System.out.println("Neispravna sintaksa. Koristite: ISI2S polaznaStanica - odredisnaStanica");
        }
    }
}
