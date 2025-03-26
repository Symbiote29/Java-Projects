package org.foi.nwtis.fantunovi.aplikacija_1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.foi.nwtis.Konfiguracija;


/**
 * 
 * @author Filip Antunović
 *
 */

public class GlavniPosluzitelj {
	protected Konfiguracija konfig;

	protected int ispis = 0;
	protected int maxRadnika;
	protected int mreznaVrata;
	protected ServerSocket posluzitelj;
	protected int brojRadnika = 0;
	protected int status;
	protected List<MrezniRadnik> listaRadnika;
	protected int brojacZahtjeva;

	private boolean kraj;

	/**
	 * Konstruktor za dobivanje konfiguracijskih vrijednosti
	 * 
	 * @param konf Prosljeduje se instanca 'Konfiguracije' kako bi se preko nje
	 *             moglo doći do svih elemenata konfiguracije
	 */
	public GlavniPosluzitelj(Konfiguracija konf) {
		this.konfig = konf;
		this.mreznaVrata = Integer.parseInt(konf.dajPostavku("mreznaVrata"));
		this.maxRadnika = Integer.parseInt(konf.dajPostavku("maxRadnika"));
		this.status = 0;
		this.kraj = false;
		this.listaRadnika = new ArrayList<>();
		this.brojacZahtjeva = 0;
	}

	/**
	 * Metoda pomocu koje se pokrecu metode za citanje podataka o korisnicima,
	 * lokacijama i uredajima
	 */
	public void pokreniPosluzitelja() {
		try {
			pripremiPosluzitelja();
		} catch (IOException e) {
			Logger.getGlobal().log(Level.SEVERE, "ERROR 29: " + e.getMessage());
		}
	}

	/**
	 * Metoda koja sluzi za pripremu posluzitelja i dok on radi da pokrene mreznog
	 * radnika
	 * 
	 * @throws IOException
	 */
	public void pripremiPosluzitelja() throws IOException {

		this.posluzitelj = new ServerSocket(this.mreznaVrata);

		while (true) {
			if(brojRadnika <= maxRadnika) {
				povecajBrojRadnika();
				Socket veza;
				try {
					veza = posluzitelj.accept();
				} catch (SocketException e) {
					break;
				}
				MrezniRadnik mr = new MrezniRadnik(veza, konfig, this);
				listaRadnika.add(mr);
				mr.start();
			}	
		}
		
		if(listaRadnika.size() > 0) {
			for(MrezniRadnik mr : listaRadnika) {
				try {
					mr.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		posluzitelj.close();
	}

	protected synchronized void povecajBrojRadnika() {
		brojRadnika++;
	}

	protected synchronized void smanjiBrojRadnika() {
		brojRadnika--;
	}
	
	protected synchronized int getIspis() {
		return this.ispis;
	}
	protected synchronized void setIspis(int ispis) {
		this.ispis = ispis;
	}
	
	protected synchronized int getStatus() {
		return this.status;
	}
	
	protected synchronized void setStatus(int status) {
		this.status = status;
	}
	protected synchronized int getBrojacZahtjeva() {
		return this.brojacZahtjeva;
	}
	
	protected synchronized void setBrojacZahtjeva(int brojacZahtjeva) {
		this.brojacZahtjeva = brojacZahtjeva;
	}
	protected synchronized void povecajBrojZahtjeva() {
		this.brojacZahtjeva++;
	}
	
	protected synchronized void zaustaviPosluzitelja() {
		this.kraj = true;
	}
	public synchronized void izbrisiMreznogRadnika(MrezniRadnik mrezniRadnik) {
		listaRadnika.remove(mrezniRadnik);
		//System.out.println("String poslije: " + listaRadnika.size());
	  }
}
