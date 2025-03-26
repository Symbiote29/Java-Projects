package org.foi.nwtis.fantunovi.aplikacija_2.konekcija;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Aplikacija_1_konekcija {
	
	public Aplikacija_1_konekcija() {
		
	}
	public String posaljiZahtjev(String adresa, int mreznaVrata, String naredba) {
	    var poruka = new StringBuilder();
	    String red = "";
	    try {
	      Socket mreznaUticnica = new Socket(adresa, mreznaVrata);

	      var citac = new BufferedReader(
	          new InputStreamReader(mreznaUticnica.getInputStream(), Charset.forName("UTF-8")));

	      var pisac = new BufferedWriter(
	          new OutputStreamWriter(mreznaUticnica.getOutputStream(), Charset.forName("UTF-8")));

	      String zahtjev = naredba;
	      pisac.write(zahtjev);
	      pisac.flush();
	      mreznaUticnica.shutdownOutput();

	      while (true) {
	        red = citac.readLine();
	        if (red == null) {
	          break;
	        }

	        java.util.logging.Logger.getGlobal().log(Level.INFO, red);
	        poruka.append(red);
	      }
	      mreznaUticnica.shutdownInput();
	      mreznaUticnica.close();

	    } catch (IOException e) {
	      return "ERROR (Pogreska tijekom slanje naredbe " + naredba + " : " + e.getMessage()
	          + "\nPoslano na adresu: " + adresa + " " + mreznaVrata + ")";
	    }

	    return poruka.toString();
	  }
//	public String posaljiZahtjev(String adresa, int mreznaVrata, String zahtjev) {
//		var poruka = new StringBuilder();
//		try {
//			Socket mreznaUticnica = new Socket(adresa, mreznaVrata);
//			var citac = new BufferedReader(
//					new InputStreamReader(mreznaUticnica.getInputStream(), Charset.forName("UTF-8")));
//			var pisac = new BufferedWriter(
//					new OutputStreamWriter(mreznaUticnica.getOutputStream(), Charset.forName("UTF-8")));
//			while (true) {
//				var red = citac.readLine();
//				if (red == null)
//					break;
//
//				poruka.append(red);
//			}
//			mreznaUticnica.shutdownInput();
//
//			pisac.write(zahtjev);
//			pisac.flush();
//			mreznaUticnica.shutdownOutput();
//			mreznaUticnica.close();
//		}catch (IOException e) {
//			return "Greška kod slanja zahtjeva na aplikaciju_1 " + e.getMessage();
//		}
//
//	    return poruka.toString();
//	  }
}
