package org.foi.nwtis;

import java.util.Properties;

import org.foi.nwtis.fantunovi.konfiguracije.KonfiguracijaBin;
import org.foi.nwtis.fantunovi.konfiguracije.KonfiguracijaJson;
import org.foi.nwtis.fantunovi.konfiguracije.KonfiguracijaTxt;
import org.foi.nwtis.fantunovi.konfiguracije.KonfiguracijaXml;
import org.foi.nwtis.fantunovi.konfiguracije.KonfiguracijaYaml;


/**
 * Apstraktna klasa za rad s postavkama iz konfiguracijske datoteke Implementira dio mentoda iz
 * sučelja Konfiguracija.
 */
public abstract class KonfiguracijaApstraktna implements Konfiguracija {

  /** naziv datoteke konfiguracije. */
  protected String nazivDatoteke;

  /** kolekcija postavki. */
  protected Properties postavke;

  /**
   * Konstruktor klase.
   *
   * @param nazivDatoteke naziv datoteke
   */
  public KonfiguracijaApstraktna(String nazivDatoteke) {
    this.nazivDatoteke = nazivDatoteke;
    this.postavke = new Properties();
  }

  @Override
  public Properties dajSvePostavke() {
    return this.postavke;
  }

  /**
   * Obriši sve postavke iz memorije.
   */
  @Override
  public boolean obrisiSvePostavke() {
    if (this.postavke.isEmpty())
      return false;

    this.postavke.clear();
    return true;
  }

  @Override
  public String dajPostavku(String kljuc) {
    return this.postavke.getProperty(kljuc);
  }

  @Override
  public boolean spremiPostavku(String kljuc, String vrijednost) {
    if (this.postojiPostavka(kljuc))
      return false;

    this.postavke.setProperty(kljuc, vrijednost);
    return true;
  }

  @Override
  public boolean azurirajPostavku(String kljuc, String vrijednost) {
    if (!this.postojiPostavka(kljuc))
      return false;

    this.postavke.setProperty(kljuc, vrijednost);
    return true;
  }

  @Override
  public boolean postojiPostavka(String kljuc) {
    return this.postavke.contains(kljuc);
  }

  @Override
  public boolean obrisiPostavku(String kljuc) {
    if (!this.postojiPostavka(kljuc))
      return false;

    this.postavke.remove(kljuc);
    return true;
  }

  /**
   * Spremi konfiguraciju.
   *
   * @param datoteka the datoteka
   * @throws NeispravnaKonfiguracija ako tip nije podržan ili se javi problem kod spremanja datoteke
   *         konfiguracije
   */
  public abstract void spremiKonfiguraciju(String datoteka) throws NeispravnaKonfiguracija;

  /**
   * Sprema konfiguraciju.
   *
   * @throws NeispravnaKonfiguracija ako se javi problem kod spremanja datoteke konfiguracije
   */
  public abstract void ucitajKonfiguraciju() throws NeispravnaKonfiguracija;

  /**
   * Sprema konfiguraciju pod danim nazivom datoteke.
   *
   * @throws NeispravnaKonfiguracija ako se javi problem kod spremanja datoteke konfiguracije
   */
  public void spremiKonfiguraciju() throws NeispravnaKonfiguracija {
    this.spremiKonfiguraciju(this.nazivDatoteke);
  }

  /**
   * Kreira objekt konfiguracije i sprema u datoteku pod zadanim nazivom.
   *
   * @param nazivDatoteke the naziv datoteke
   * @return objekt konfiguracije bez postavki
   * @throws NeispravnaKonfiguracija ako tip konfiguracije nije podržan ili je došlo do pogreške kod
   *         spremanja u datoteku
   */
  public static Konfiguracija kreirajKonfiguraciju(String nazivDatoteke)
      throws NeispravnaKonfiguracija {
    Konfiguracija konfig = dajKonfiguraciju(nazivDatoteke);
    konfig.spremiKonfiguraciju();
    return konfig;
  }

  /**
   * Kreira objekt konfiguraciju i u njega učitava datoteku postavki zadanog naziva.
   *
   * @param nazivDatoteke naziv datoteke
   * @return objekt konfiguracije s postavkama
   * @throws NeispravnaKonfiguracija ako tip konfiguracije nije podržan ili datoteka zadanog naziva
   *         ne postoji ili je došlo do pogreške kod čitanja datoteke
   */
  public static Konfiguracija preuzmiKonfiguraciju(String nazivDatoteke)
      throws NeispravnaKonfiguracija {
    Konfiguracija konfig = dajKonfiguraciju(nazivDatoteke);
    konfig.ucitajKonfiguraciju();
    return konfig;
  }

  /**
   * Vraća objekt konfiguracije.
   *
   * @param nazivDatoteke naziv datoteke
   * @return objekt konfiguracije
   * @throws NeispravnaKonfiguracija ako tip konfiguracije nije podržan
   */
  public static Konfiguracija dajKonfiguraciju(String nazivDatoteke)
      throws NeispravnaKonfiguracija {
    String tip = Konfiguracija.dajTipKonfiguracije(nazivDatoteke);
    return switch (tip) {
      case KonfiguracijaTxt.TIP -> new KonfiguracijaTxt(nazivDatoteke);
      case KonfiguracijaXml.TIP -> new KonfiguracijaXml(nazivDatoteke);
      case KonfiguracijaBin.TIP -> new KonfiguracijaBin(nazivDatoteke);
      case KonfiguracijaJson.TIP -> new KonfiguracijaJson(nazivDatoteke);
      case KonfiguracijaYaml.TIP -> new KonfiguracijaYaml(nazivDatoteke);
      default -> throw new NeispravnaKonfiguracija(
          "Datoteka: '" + nazivDatoteke + "' nema podržani tip datoteke.");
    };
  }

}

