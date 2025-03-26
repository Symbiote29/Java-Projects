package org.foi.uzdiz.chainofresponsibility;

import java.util.ArrayList;
import java.util.List;

public class ProcesorKomandi {
	private final List<HandlerKomandi> handleri = new ArrayList<>();
	private final ContextKomandi context;
	
	public ProcesorKomandi(ContextKomandi context) {
		this.context = context;
	}
	
	public void dodajHandlera(HandlerKomandi handler) {
		handleri.add(handler);
	}
	
	public void procesirajKomandu(String komanda) {
	    for (HandlerKomandi handler : handleri) {
	        if (handler.canHandle(komanda)) {
	            handler.handle(komanda, context);
	            return;
	        }
	    }
	    System.out.println("Nepoznata komanda: " + komanda);
	}
}
