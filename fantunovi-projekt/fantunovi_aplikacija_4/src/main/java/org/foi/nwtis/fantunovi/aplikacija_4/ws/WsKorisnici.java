package org.foi.nwtis.fantunovi.aplikacija_4.ws;

import org.foi.nwtis.Konfiguracija;
import org.foi.nwtis.podaci.Korisnik;
import org.foi.nwtis.podaci.PogresnaAutentikacija;

import jakarta.annotation.Resource;
import jakarta.inject.Inject;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.servlet.ServletContext;

import java.sql.ResultSet;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebService(serviceName = "korisnici")
public class WsKorisnici {

	@Inject
	  private ServletContext kontekst;
	
	@Resource(lookup = "java:app/jdbc/nwtis_bp")
	  javax.sql.DataSource ds;
	
	public boolean serverPokrenut() {
		Konfiguracija konfig = (Konfiguracija) kontekst.getAttribute("konfig");
	    String serverAddress = konfig.dajPostavku("aplikacija_1.adresa");
	    int serverPort = Integer.parseInt(konfig.dajPostavku("aplikacija_1.mreznaVrata"));

	    try (Socket socket = new Socket()) {
	        socket.connect(new InetSocketAddress(serverAddress, serverPort), 1000); // 1000 milliseconds timeout
	        System.out.println("Server radi");
	        return true;
	    } catch (IOException e) {
	    	Logger.getGlobal().log(Level.SEVERE, "Server nije pokrenut" + e.getMessage());
	        return false;
	    }
	}
	
	@WebMethod
	public List<Korisnik> dajKorisnike(@WebParam String korisnik, @WebParam String lozinka, @WebParam String traziImeKorisnika,
			@WebParam String traziPrezimeKorisnika) throws PogresnaAutentikacija{
		
		 Konfiguracija konfig = (Konfiguracija) kontekst.getAttribute("konfig");
		 String kor = konfig.dajPostavku("OpenSkyNetwork.korisnik");
		 String loz = konfig.dajPostavku("OpenSkyNetwork.lozinka");
		
		if(!provjeraKorisnika(korisnik,lozinka)) {
			throw new PogresnaAutentikacija();
		}
		
		boolean filtriran = false;

	    List<Korisnik> korisnici = new ArrayList<Korisnik>();
	    
	    StringBuilder query = new StringBuilder();
	    query.append("SELECT IME, PREZIME, KORISNICKO_IME, LOZINKA, EMAIL FROM KORISNIK WHERE");

	    if (traziImeKorisnika != null && !traziImeKorisnika.isEmpty()) {
	        query.append(" IME LIKE '%").append(traziImeKorisnika).append("%'");
	        filtriran = true;
	    }

	    if (traziPrezimeKorisnika != null && !traziPrezimeKorisnika.isEmpty()) {
	        if (filtriran) {
	            query.append(" OR");
	        }
	        query.append(" PREZIME LIKE '%").append(traziPrezimeKorisnika).append("%'");
	        filtriran = true;
	    }

	    vracanjeListeTrazenihKorisnika(korisnici, query);

	    return korisnici;
	}

	private void vracanjeListeTrazenihKorisnika(List<Korisnik> korisnici, StringBuilder query) {
		PreparedStatement stmt = null;
	    try (var con = ds.getConnection()) {
	      stmt = con.prepareStatement(query.toString());

	      ResultSet rs = stmt.executeQuery();
	      while (rs.next()) {
	        String ime = rs.getString("IME");
	        String prezime = rs.getString("PREZIME");
	        String korIme = rs.getString("KORISNICKO_IME");
	        String lozinkaUpit = rs.getString("LOZINKA");
	        String email = rs.getString("EMAIL");

	        var noviKorisnik = new Korisnik(korIme, ime, prezime, lozinkaUpit, email);
	        korisnici.add(noviKorisnik);
	      }
	      rs.close();
	    } catch (SQLException e) {
	      e.printStackTrace();
	      Logger.getGlobal().log(Level.SEVERE, e.getMessage());
	    } finally {
	      try {
	        if (stmt != null && !stmt.isClosed())
	          stmt.close();
	      } catch (SQLException e) {
	        Logger.getGlobal().log(Level.SEVERE, e.getMessage());
	      }
	    }
	}
	
	 @WebMethod
	  public Korisnik dajKorisnika(@WebParam String korisnik, @WebParam String lozinka,
	      @WebParam String traziKorisnika) throws PogresnaAutentikacija {

	    if (!provjeraKorisnika(korisnik, lozinka)) {
	      throw new PogresnaAutentikacija();
	    }

	    Korisnik trazeni = null;

	    String query = "SELECT IME, PREZIME, KORISNICKO_IME, LOZINKA, EMAIL FROM KORISNIK WHERE KORISNICKO_IME = ?";

	    PreparedStatement stmt = null;
	    try (var con = ds.getConnection()) {
	      stmt = con.prepareStatement(query.toString());
	      stmt.setString(1, traziKorisnika);

	      ResultSet rs = stmt.executeQuery();
	      while (rs.next()) {
	        String ime = rs.getString("IME");
	        String prezime = rs.getString("PREZIME");
	        String korIme = rs.getString("KORISNICKO_IME");
	        String lozinkaUpit = rs.getString("LOZINKA");
	        String email = rs.getString("EMAIL");

	        trazeni = new Korisnik(korIme, ime, prezime, lozinkaUpit, email);

	      }
	      rs.close();
	    } catch (SQLException e) {
	      e.printStackTrace();
	      Logger.getGlobal().log(Level.SEVERE, e.getMessage());
	    } finally {
	      try {
	        if (stmt != null && !stmt.isClosed())
	          stmt.close();
	      } catch (SQLException e) {
	        Logger.getGlobal().log(Level.SEVERE, e.getMessage());
	      }
	    }

	    return trazeni;
	  }
	 
	 @WebMethod
	  public boolean dodajKorisnika(@WebParam Korisnik korisnik) {

	    Boolean jeDodan = false;
	    String ime = korisnik.getIme();
	    String prezime = korisnik.getPrezime();
	    String korIme = korisnik.getKorime();
	    String lozinka = korisnik.getLozinka();
	    String email = korisnik.getEmail();

	    PreparedStatement stmt = null;
	    try (var con = ds.getConnection()) {
	      String upit = "INSERT INTO KORISNIK (IME, PREZIME, KORISNICKO_IME, LOZINKA, EMAIL) VALUES('"
	          + ime + "', '" + prezime + "', '" + korIme + "', '" + lozinka + "', '" + email + "')";

	      stmt = con.prepareStatement(upit);
	      try {
	        if (ime.isEmpty() || prezime.isEmpty() || korIme.isEmpty() || lozinka.isEmpty()) {
	          jeDodan = false;
	        } else {
	          stmt.execute();
	          jeDodan = true;
	        }
	      } catch (SQLException e) {
	      }

	    } catch (SQLException e) {
	      e.printStackTrace();
	      Logger.getGlobal().log(Level.SEVERE, e.getMessage());
	    } finally {
	      try {
	        if (stmt != null && !stmt.isClosed())
	          stmt.close();
	      } catch (SQLException e) {
	        Logger.getGlobal().log(Level.SEVERE, e.getMessage());
	      }
	    }

	    return jeDodan;
	  }
	 
	public Boolean provjeraKorisnika(String korisnik, String lozinka) {
		
		String query = "SELECT KORISNICKO_IME, LOZINKA FROM KORISNIK WHERE KORISNICKO_IME = ? AND LOZINKA = ?";
		Boolean postoji = false;
		
		 PreparedStatement stmt = null;
		    try (var con = ds.getConnection()) {
		      stmt = con.prepareStatement(query.toString());
		      stmt.setString(1, korisnik);
		      stmt.setString(2, lozinka);

		      ResultSet rs = stmt.executeQuery();
		      if (rs.next()) {
		        postoji = true;
		      }
		      rs.close();
		    } catch (SQLException e) {
		      e.printStackTrace();
		      Logger.getGlobal().log(Level.SEVERE, e.getMessage());
		    } finally {
		      try {
		        if (stmt != null && !stmt.isClosed())
		          stmt.close();
		      } catch (SQLException e) {
		        Logger.getGlobal().log(Level.SEVERE, e.getMessage());
		      }
		    }
		
		return postoji;
	}
}
