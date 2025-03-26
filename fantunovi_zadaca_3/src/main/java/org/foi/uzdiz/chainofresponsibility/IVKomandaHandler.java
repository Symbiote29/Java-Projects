package org.foi.uzdiz.chainofresponsibility;

public class IVKomandaHandler implements HandlerKomandi{

	@Override
	public boolean canHandle(String komanda) {
		return komanda.equalsIgnoreCase("IV");
	}

	@Override
	public void handle(String komanda, ContextKomandi context) {
		context.getZvrDatoteka().kreirajVozniRed();
	}
}
