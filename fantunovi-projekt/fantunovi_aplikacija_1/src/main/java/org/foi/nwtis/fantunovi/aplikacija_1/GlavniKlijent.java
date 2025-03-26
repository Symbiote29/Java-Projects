package org.foi.nwtis.fantunovi.aplikacija_1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GlavniKlijent {

	public static void main(String[] args) throws NumberFormatException, InterruptedException {
		GlavniKlijent gk = new GlavniKlijent();

		gk.posaljiZahtjev(spojiArgumente(args), "localhost", 8000);

	}

	/**
	 * Metoda koja sluzi za razdvajanje zahtjeva kojeg korisnik unese
	 * 
	 * @param zahtjev
	 * @return
	 */
	private static String[] razdvojiArgumente(String zahtjev) {
		return zahtjev.trim().split("\\s+");
	}

	/**
	 * Metoda koja sluzi za slanje zahtjeva prema glavnom posluzitelju
	 * 
	 * @param zahtjev     (zahtjev koji korisnik unosi)
	 * @param adresa      (adresa iz konfiguracijske datoteke)
	 * @param mreznaVrata (mrezna vrata iz konfiguracijske datoteke)
	 * @throws InterruptedException
	 */
	private static void posaljiZahtjev(String zahtjev, String adresa, int mreznaVrata) throws InterruptedException {
		try {
			System.out.println("korisnikov zahtjev: " + zahtjev);
			var mreznaUticnica = new Socket(adresa, mreznaVrata);
			var citac = new BufferedReader(
					new InputStreamReader(mreznaUticnica.getInputStream(), Charset.forName("UTF-8")));
			var pisac = new BufferedWriter(
					new OutputStreamWriter(mreznaUticnica.getOutputStream(), Charset.forName("UTF-8")));

			pisac.write(zahtjev);
			pisac.flush();
			mreznaUticnica.shutdownOutput();

			var poruka = new StringBuilder();
			while (true) {
				var red = citac.readLine();
				if (red == null)
					break;

				Logger.getGlobal().log(Level.INFO, red);
				poruka.append(red);
			}
			//Logger.getGlobal().log(Level.INFO, "odgovor:" + poruka.toString());
			mreznaUticnica.shutdownInput();
			mreznaUticnica.close();
			citac.close();

		} catch (IOException e) {
			Logger.getGlobal().log(Level.INFO, "Greska prilikom slanja zahtjeva");
		}
	}

	/**
	 * Metoda koja sluzi za spajanje argumenata koje korisnik unese sa razmakom medu
	 * njima
	 * 
	 * @param argumenti (zahtjev unesen od strane korisnika koji je prijasnje
	 *                  splitan po ';' i stavljen u array
	 * @return
	 */
	private static String spojiArgumente(String[] argumenti) {
		
		String argum = "";
	    if (argumenti.length > 1) {
	      for (int i = 0; i < argumenti.length; i++) {
	        if (i > 0) {
	        	argum += " ";
	        }
	        argum += argumenti[i];
	      }
	    } else {
	    	argum = argumenti[0];
	    }
	    System.out.println("Saljem komandu: " + argumenti);
	    
		return argum;
	}

	/**
	 * Metoda koja provjerava je li zahtjev zadovoljava oblik u kojem treba biti:
	 * npr -k pero -l 123456 -a localhost -v 8000 -t 0 --kraj
	 * 
	 * @param zahtjev
	 * @return
	 */
	private String provjeriZahtjev(String zahtjev) {
		String regex = "-k\\s[\\w-]{3,10}\\s-l\\s[\\w#!-]{3,10}\\s-a\\s((\\b(?:\\d{1,3}\\.){3}\\d{1,3}\\b)|([\\w.\\-]+))\\s-v\\s(8\\d{3}|9\\d{3}|[1-9]\\d{3,3})\\s-t\\s\\d+\\s((--meteo\\s[\\w-]+)|(--makstemp\\s[\\w-]+)|(--maksvlaga\\s[\\w-]+)|(--makstlak\\s[\\w-]+)|(--alarm\\s'[\\w ]+')|(--udaljenost\\s'[\\w ]+'\\s'[\\w ]+')|(--udaljenost\\sspremi)|(--kraj))";
		Pattern uzorak = Pattern.compile(regex);
		Matcher mKorisnickiZahtjev = uzorak.matcher(zahtjev);

		if (mKorisnickiZahtjev.find()) {
			return mKorisnickiZahtjev.group();
		} else {
			return null;
		}
	}

}
