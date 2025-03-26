package org.foi.nwtis.fantunovi.aplikacija_1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.foi.nwtis.Konfiguracija;

public class MrezniRadnik extends Thread {

	protected Socket mreznaUticnica;
	protected Konfiguracija konf;
	private int ispis = 0;
	protected GlavniPosluzitelj posluzitelj;

	public MrezniRadnik(Socket mreznaUticnica, Konfiguracija konfig, GlavniPosluzitelj posluzitelj) {
		super();
		this.mreznaUticnica = mreznaUticnica;
		this.konf = konfig;
		this.posluzitelj = posluzitelj;
	}

	@Override
	public void interrupt() {
		super.interrupt();
	}

	@Override
	public synchronized void start() {
		super.start();
	}

	@Override
	public void run() {
		try {
			var citac = new BufferedReader(
					new InputStreamReader(this.mreznaUticnica.getInputStream(), Charset.forName("UTF-8")));
			var pisac = new BufferedWriter(
					new OutputStreamWriter(this.mreznaUticnica.getOutputStream(), Charset.forName("UTF-8")));
			var poruka = new StringBuilder();

			while (true) {
				var red = citac.readLine();
				if (red == null)
					break;

				if (this.posluzitelj.getIspis() == 1) {
					Logger.getGlobal().log(Level.INFO, red);
				}
				poruka.append(red);
			}
			this.mreznaUticnica.shutdownInput();
			String odgovor = this.obradiZahtjev(poruka.toString());

			pisac.write(odgovor);
			pisac.flush();
			this.mreznaUticnica.shutdownOutput();
			this.mreznaUticnica.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			this.posluzitelj.smanjiBrojRadnika();
			this.posluzitelj.izbrisiMreznogRadnika(this);
		}
	}

	/**
	 * Metoda u kojoj se svaki zahtjev koji je prosljeden od strane korisnika
	 * provjerava tako da zadovolji oblik u kojem treba biti
	 * 
	 * @param poruka (zahtjev koji je korisnik poslao)
	 * @return
	 */
	private String obradiZahtjev(String zahtjev) {
		String odgovor = "";
		String regex = "(?<komanda>(?<status>STATUS)|(?<kraj>KRAJ)|(?<init>INIT)|(?<pauza>PAUZA)|(?<info>INFO "
				+ "(?<infoParam>(DA|NE)))|(?<udaljenost>UDALJENOST (?<koordinate>(?<koordinate1>(?<gpsSirina1>-?\\d+\\.\\d+) "
				+ "(?<gpsDuzina1>-?\\d+\\.\\d+)) (?<koordinate2>(?<gpsSirina2>-?\\d+\\.\\d+) (?<gpsDuzina2>-?\\d+\\.\\d+)))))$";
		
		Pattern uzorak = Pattern.compile(regex);
		Matcher matcher = uzorak.matcher(zahtjev);

		System.out.println("zahtjev: " + zahtjev);
		if(matcher.matches()) {
			if(matcher.group("status") != null) {
				odgovor = "OK " + this.posluzitelj.getStatus();
				this.posluzitelj.povecajBrojZahtjeva();
			}
			else if(matcher.group("kraj") != null) {
				try {
					odgovor = "OK";
					posluzitelj.posluzitelj.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
			else if(matcher.group("init") != null) {
				if(this.posluzitelj.getStatus() == 1) {
					odgovor = "ERROR 02 (Posluzitelj je već aktivan)";
				}
				else {
					this.posluzitelj.setStatus(1);
					this.posluzitelj.setBrojacZahtjeva(0);
					odgovor = "OK";
				}
			}
			else if(matcher.group("pauza") != null) {
				if(this.posluzitelj.getStatus() == 0) {
					odgovor = "ERROR 01 (Posluzitelj je pauziran)";
				}
				else {
					this.posluzitelj.setStatus(0);
					odgovor = "OK " + this.posluzitelj.getBrojacZahtjeva();
				}
			}
			else if(matcher.group("info") != null && matcher.group("infoParam") != null) {
				if(this.posluzitelj.getStatus() == 0) {
					odgovor = "ERROR 01 (Posluzitelj je pauziran)";
				}
				else {
					if(this.posluzitelj.getIspis() == 0 && matcher.group("infoParam").equals("NE")) {
						odgovor = "ERROR 04 (Posluzitelj je postavljen da ne ispisuje)";
					}
					else if (this.posluzitelj.getIspis() == 1 && matcher.group("infoParam").equals("NE")){
						this.posluzitelj.setIspis(0);
						this.posluzitelj.povecajBrojZahtjeva();
						odgovor = "OK";
					}
					else if (this.posluzitelj.getIspis() == 0 && matcher.group("infoParam").equals("DA")){
						this.posluzitelj.setIspis(1);
						this.posluzitelj.povecajBrojZahtjeva();
						odgovor = "OK";
					}
					else if (this.posluzitelj.getIspis() == 1 && matcher.group("infoParam").equals("DA")){
						odgovor = "ERROR 03 (Posluzitelj je postavljen da ispisuje)";
					}
				}
			}
			else if(matcher.group("udaljenost") != null) {
				if(this.posluzitelj.getStatus() == 0) {
					odgovor = "ERROR 01 (Posluzitelj je pauziran)";
				}
				else {
					double sirina1 = Double.parseDouble(matcher.group("gpsSirina1"));
			        double duzina1 = Double.parseDouble(matcher.group("gpsDuzina1"));
			        double sirina2 = Double.parseDouble(matcher.group("gpsSirina2"));
			        double duzina2 = Double.parseDouble(matcher.group("gpsDuzina2"));
			        
			        double udaljenost = izracunajUdaljenost(sirina1, duzina1, sirina2, duzina2);
			        odgovor = "OK " + udaljenost;
			        this.posluzitelj.povecajBrojZahtjeva();
				}
			}
		}
		else {
			odgovor = "ERROR 05 (Neispravni format)";
		}
		
		return odgovor;
	}
	
	private static final double RADIUS_ZEMLJE = 6371.0; // Radius zemlje u kilometrima
    
    public static double izracunajUdaljenost(double sirina1, double duzina1, double sirina2, double duzina2) {
        double razlikaSirina = Math.toRadians(sirina2 - sirina1);
        double razlikaDuzina = Math.toRadians(duzina2 - duzina1);
        
        double a = Math.sin(razlikaSirina / 2) * Math.sin(razlikaSirina / 2) +
                   Math.cos(Math.toRadians(sirina1)) * Math.cos(Math.toRadians(sirina2)) *
                   Math.sin(razlikaDuzina / 2) * Math.sin(razlikaDuzina / 2);
        
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        
        double udaljenost = RADIUS_ZEMLJE * c;
        return udaljenost;
    }

}
