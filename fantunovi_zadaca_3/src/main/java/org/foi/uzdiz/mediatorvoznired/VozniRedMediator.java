package org.foi.uzdiz.mediatorvoznired;

import org.foi.uzdiz.builderstanica.Stanica;
import org.foi.uzdiz.compositevoznired.Etape;
import org.foi.uzdiz.compositevoznired.Vlak;

public interface VozniRedMediator {
	void obavijestiKorisnikeOVoznomRedu(String vlakOznaka);
    void obavijestiStaniceOEtapi(Vlak vlak, Etape etapa);
    void posaljiPoruku(String message, Stanica stanica);
}
