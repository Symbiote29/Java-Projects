package org.foi.nwtis.fantunovi.aplikacija_3.dretve;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.foi.nwtis.Konfiguracija;
import org.foi.nwtis.fantunovi.aplikacija_3.zrna.JmsPosiljatelj;
import org.foi.nwtis.podaci.Aerodrom;
import org.foi.nwtis.podaci.Lokacija;
import org.foi.nwtis.rest.klijenti.NwtisRestIznimka;
import org.foi.nwtis.rest.klijenti.OSKlijent;
import org.foi.nwtis.rest.podaci.LetAviona;

public class SakupljacLetovaAviona extends Thread{

	private JmsPosiljatelj jms;
	private Konfiguracija konfig;
	private long trajanje;
	private boolean krajRada;
	//private long longDanOd;
	//private long longDanDo;
	private String longDanOd;
	private String longDanDo;
	private ArrayList<String> aerodromiLetovi;
	OSKlijent OSKlijent;
	javax.sql.DataSource ds;
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
	private Date obradaDatum = null;
	private Date datumDo = null;
	private Date zadnjiLetDatum = null;
	private long zadnjiLetUnix = 0;
	
	public SakupljacLetovaAviona(JmsPosiljatelj jms, Konfiguracija konfig, javax.sql.DataSource ds) {
		this.jms = jms;
		this.konfig = konfig;
		this.ds = ds;
		krajRada = false;
		this.longDanOd = this.konfig.dajPostavku("preuzimanje.od");
		this.longDanDo = this.konfig.dajPostavku("preuzimanje.do");
		aerodromiLetovi = new ArrayList<String>();
		this.OSKlijent = new OSKlijent(konfig.dajPostavku("OpenSkyNetwork.korisnik"), konfig.dajPostavku("OpenSkyNetwork.lozinka"));
	}
	
	@Override
	public synchronized void start() {
		super.start();
	}

	@Override
	public void run() {
		trajanje = Integer.parseInt(konfig.dajPostavku("ciklus.trajanje")) * 1000;
		long zadnjiUnos = dohvatiZadnjiUnos();
		long vrijeme = 0;
		long spavanje = 0;
		
		try {
			obradaDatum = pretvoriUsekunde(longDanOd);
			datumDo = pretvoriUsekunde(longDanDo);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		zadnjiLetUnix = dohvatiZadnjiUnos();
		zadnjiLetDatum = new Date(zadnjiLetUnix * 1000);
		int dohvaceniLetovi = 0;
		
		getAerodromIcao();
		
		while(!krajRada) {
			vrijeme = System.currentTimeMillis();
			
//			if(obradaDatum.before(datumDo) || (!obradaDatum.before(datumDo) && !obradaDatum.after(datumDo))) {
//				if(zadnjiLetDatum.before(obradaDatum)) {
//					dohvaceniLetovi = dohvatiLetove(obradaDatum, aerodromiLetovi);
//				}else {
//					Logger.getGlobal().log(Level.INFO,
//				              "Datum obrade je veći od posljednjeg datuma u BP, datum se preskače.");
//				}
//			}
			
			if(obradaDatum.before(datumDo)) {
				if(zadnjiLetDatum.before(obradaDatum)) {
					dohvaceniLetovi = dohvatiLetove(obradaDatum, aerodromiLetovi);
				}else {
					Logger.getGlobal().log(Level.INFO,"Datum se preskače.");
				}
			}
			
			obradaDatum = new Date(obradaDatum.getTime() + 86400000);
			
			String brojLetova = "Broj_dohvacenih_letova_=_" + dohvaceniLetovi;
			
	        System.out.println("Poruka: " + brojLetova);
	        boolean poslana = this.jms.saljiPoruku(brojLetova);
			
			spavanje = this.trajanje - (System.currentTimeMillis() - vrijeme);
			
			if (spavanje <= 0) {
				spavanje = 0;
		    }
			System.out.println("Poruka je poslana: " + poslana);
			
	        try {
	          Thread.sleep(spavanje * 1000);
	        } catch (InterruptedException e) {
	          e.printStackTrace();
	        }
			
			zadnjiUnos += 86400;
		}
	}

	private void getAerodromIcao() {
		String query = "SELECT * FROM AERODROMI_LETOVI";
		
		PreparedStatement stmt = null;
	    try (Connection con = ds.getConnection();) {
	    	stmt = con.prepareStatement(query);
	    	ResultSet rs = stmt.executeQuery();

	    	while (rs.next()) {
	    		String icao = rs.getString("ICAO");
	    		aerodromiLetovi.add(icao);
	    	}

	    } catch (SQLException e) {
	    	e.printStackTrace();
	    } finally {
	    	try {
	    		if (stmt != null && !stmt.isClosed())
	    			stmt.close();
	    	} catch (SQLException e) {
	    		e.printStackTrace();
	    	}
	    }
	}

	private long dohvatiZadnjiUnos() {
		long zadnjiUnos = 0;
		String query = "SELECT * FROM LETOVI_POLASCI ORDER BY FIRSTSEEN DESC";
		
		 PreparedStatement stmt = null;
	    try (Connection con = ds.getConnection();) {
	    	stmt = con.prepareStatement(query);
	    	ResultSet rs = stmt.executeQuery();

	    	while (rs.next()) {
	    		zadnjiUnos = rs.getLong("FIRSTSEEN");

	    	}

	    } catch (SQLException e) {
	    	e.printStackTrace();
	    } finally {
	    	try {
	    		if (stmt != null && !stmt.isClosed())
	    			stmt.close();
	    	} catch (SQLException e) {
	    		e.printStackTrace();
	    	}
	    }
		
	    return zadnjiUnos;
	}
	
	private Date pretvoriUsekunde(String dan) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		
		return formatter.parse(dan);
	}

	private int dohvatiLetove(Date datum, ArrayList<String> aerodromiLetovi2) {
	    var letovi = new ArrayList<LetAviona>();
	    int brojLetova = 0;
	    long unixVrijeme = datum.getTime() / 1000;
	    long unixVrijeme2 = unixVrijeme + 86400;
	    
	    try(Connection con = ds.getConnection()){
	    	String query = "INSERT INTO LETOVI_POLASCI (ICAO24, FIRSTSEEN, ESTDEPARTUREAIRPORT, LASTSEEN,ESTARRIVALAIRPORT, "
	                + "CALLSIGN, ESTDEPARTUREAIRPORTHORIZDISTANCE, ESTDEPARTUREAIRPORTVERTDISTANCE, ESTARRIVALAIRPORTHORIZDISTANCE, "
	                + "ESTARRIVALAIRPORTVERTDISTANCE, DEPARTUREAIRPORTCANDIDATESCOUNT, ARRIVALAIRPORTCANDIDATESCOUNT, STORED) "
	                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW)";
	    	
	    	PreparedStatement stmt = con.prepareStatement(query);
	    	
	    	for(String aerodrom : aerodromiLetovi2) {
		    	try {
		  	      	letovi = (ArrayList<LetAviona>) OSKlijent.getDepartures(aerodrom, (int)unixVrijeme, (int)unixVrijeme2);
		  	      System.out.println("Preuzimanje letova za aerodrom: " + aerodrom + " " + " na datum: " + datum);
		  	      for (LetAviona let : letovi) {
		  	          dodajLet(stmt, let);
		  	          brojLetova++;
		  	        }
		  	    } catch (NwtisRestIznimka e) {
		  	      System.out.println("Nema letova za aerodrom: " + aerodrom + " " + e.toString());
		  	    }
		    }
	    	
	    } catch (SQLException e1) {
			e1.printStackTrace();
		}

	    return brojLetova;
	}

	private void dodajLet(PreparedStatement stmt, LetAviona let) throws SQLException{

	    stmt.setString(1, let.getIcao24());
	    stmt.setInt(2, let.getFirstSeen());
	    stmt.setString(3, let.getEstDepartureAirport());
	    stmt.setInt(4, let.getLastSeen());
	    stmt.setString(5, let.getEstArrivalAirport());
	    stmt.setString(6, let.getCallsign());
	    stmt.setInt(7, let.getEstDepartureAirportHorizDistance());
	    stmt.setInt(8, let.getEstDepartureAirportVertDistance());
	    stmt.setInt(9, let.getEstArrivalAirportHorizDistance());
	    stmt.setInt(10, let.getEstArrivalAirportVertDistance());
	    stmt.setInt(11, let.getDepartureAirportCandidatesCount());
	    stmt.setInt(12, let.getArrivalAirportCandidatesCount());
	    stmt.executeUpdate();
	}
	
}
