package org.foi.nwtis.fantunovi.konfiguracije;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.foi.nwtis.Konfiguracija;
import org.foi.nwtis.KonfiguracijaApstraktna;
import org.foi.nwtis.NeispravnaKonfiguracija;

/**
 * Klasa za čitanje txt datoteke sa postavkama.
 * 
 * @author Matija Novak
 */
public class KonfiguracijaTxt extends KonfiguracijaApstraktna {

  /**
   * Konstanta TIP
   */
  public static final String TIP = "txt";

  /**
   * Konstruktor
   * 
   * @param nazivDatoteke - naziv datoteke
   */
  public KonfiguracijaTxt(String nazivDatoteke) {
    super(nazivDatoteke);
  }


  /**
   * Sprema konfiguraciju u txt formatu na disk.
   * 
   * @param datoteka - naziv datoteke
   * @throws NeispravnaKonfiguracija - iznimka u slučaju grešeke sa učitavnajem
   * @see org.foi.nwtis.KonfiguracijaApstraktna - nadklasa
   */
  @Override
  public void spremiKonfiguraciju(String datoteka) throws NeispravnaKonfiguracija {
    var putanja = Path.of(datoteka);
    var tip = Konfiguracija.dajTipKonfiguracije(datoteka);
    if (tip == null || tip.compareTo(TIP) != 0) {
      throw new NeispravnaKonfiguracija(
          "Datoteka '" + datoteka + "' nije ispravnog tipa: '" + TIP + "'");
    } else if (Files.exists(putanja)
        && (Files.isDirectory(putanja) || !Files.isWritable(putanja))) {
      throw new NeispravnaKonfiguracija(
          "Datoteka '" + datoteka + "' je direktorij ili nije moguće pisati u nju.");
    }

    try {
      this.postavke.store(Files.newOutputStream(putanja), "NWTiS matnovak 2023.");
    } catch (IOException e) {
      throw new NeispravnaKonfiguracija(
          "Datoteka '" + datoteka + "' nije moguće pisati." + e.getMessage());
    }
  }

  @Override
  public void ucitajKonfiguraciju() throws NeispravnaKonfiguracija {
    var datoteka = this.nazivDatoteke;
    var putanja = Path.of(datoteka);
    var tip = Konfiguracija.dajTipKonfiguracije(datoteka);
    if (tip == null || tip.compareTo(TIP) != 0) {
      throw new NeispravnaKonfiguracija(
          "Datoteka '" + datoteka + "' nije ispravnog tipa: '" + TIP + "'");
    } else if (Files.exists(putanja)
        && (Files.isDirectory(putanja) || !Files.isReadable(putanja))) {
      throw new NeispravnaKonfiguracija(
          "Datoteka '" + datoteka + "' je direktorij ili nije moguće čitati iz nje.");
    }

    try {
      this.postavke.load(Files.newInputStream(putanja));
    } catch (IOException e) {
      throw new NeispravnaKonfiguracija(
          "Datoteka '" + datoteka + "' nije moguće čitati." + e.getMessage());
    }

  }

}
