package org.foi.nwtis.fantunovi.aplikacija_1;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.foi.nwtis.Konfiguracija;
import org.foi.nwtis.KonfiguracijaApstraktna;
import org.foi.nwtis.NeispravnaKonfiguracija;

public class PokretacPosluzitelja {

	public static void main(String[] args) {
		var pokretac = new PokretacPosluzitelja();
		if (!pokretac.provjeriArgumente(args)) {
			Logger.getLogger(PokretacPosluzitelja.class.getName()).log(Level.SEVERE, "Nije upisan naziv datoteke!");
			return;
		}

		try {
			var konf = pokretac.ucitajPostavke(args[0]);
			var glavniPosluzitelj = new GlavniPosluzitelj(konf);
			glavniPosluzitelj.pokreniPosluzitelja();
		} catch (NeispravnaKonfiguracija e) {
			Logger.getLogger(PokretacPosluzitelja.class.getName()).log(Level.SEVERE,
					"Pogreška kod učitavanja postavki iz datoteke!" + e.getMessage());
		}
	}

	/**
	 * Metoda za provjeru ulaznih argumenata
	 * 
	 * @param args
	 * @return
	 */
	public boolean provjeriArgumente(String[] args) {
		return args.length == 1 ? true : false;
	}

	/**
	 * Metoda za ucitavanje konfiguracijske datoteke
	 * 
	 * @param nazivDatoteke
	 * @return
	 * @throws NeispravnaKonfiguracija
	 */
	Konfiguracija ucitajPostavke(String nazivDatoteke) throws NeispravnaKonfiguracija {
		return KonfiguracijaApstraktna.preuzmiKonfiguraciju(nazivDatoteke);
	}

}
