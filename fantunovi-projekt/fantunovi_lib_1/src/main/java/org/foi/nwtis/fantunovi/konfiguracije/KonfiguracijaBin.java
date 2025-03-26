package org.foi.nwtis.fantunovi.konfiguracije;

import org.foi.nwtis.KonfiguracijaApstraktna;
import org.foi.nwtis.NeispravnaKonfiguracija;

/**
 * Klasa za čitanje bin datoteke sa postavkama.
 * 
 * @author Matija Novak
 */
public class KonfiguracijaBin extends KonfiguracijaApstraktna {

  /**
   * Konstanta TIP
   */
  public static final String TIP = "bin";

  /**
   * Konstruktor
   * 
   * @param nazivDatoteke - naziv datoteke
   */
  public KonfiguracijaBin(String nazivDatoteke) {
    super(nazivDatoteke);
    // TODO Auto-generated constructor stub
  }

  @Override
  public void spremiKonfiguraciju(String datoteka) throws NeispravnaKonfiguracija {
    // TODO Auto-generated method stub

  }

  @Override
  public void ucitajKonfiguraciju() throws NeispravnaKonfiguracija {
    // TODO Auto-generated method stub

  }

}
