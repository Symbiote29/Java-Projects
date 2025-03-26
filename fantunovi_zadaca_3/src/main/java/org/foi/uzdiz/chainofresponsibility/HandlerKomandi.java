package org.foi.uzdiz.chainofresponsibility;

public interface HandlerKomandi {
	boolean canHandle(String komanda);
	void handle(String komanda, ContextKomandi context);
}
