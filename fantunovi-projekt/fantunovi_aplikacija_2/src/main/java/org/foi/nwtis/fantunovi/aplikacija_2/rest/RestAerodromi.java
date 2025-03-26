package org.foi.nwtis.fantunovi.aplikacija_2.rest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.foi.nwtis.Konfiguracija;
import org.foi.nwtis.fantunovi.aplikacija_2.konekcija.Aplikacija_1_konekcija;
import org.foi.nwtis.fantunovi.aplikacija_2.slusaci.Aplikacija_2_slusac;
import org.foi.nwtis.podaci.Aerodrom;
import org.foi.nwtis.podaci.IcaoUdaljenosti;
import org.foi.nwtis.podaci.Lokacija;
import org.foi.nwtis.podaci.NajduljiPutDrzave;
import org.foi.nwtis.podaci.Udaljenost;

import com.google.gson.Gson;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.RequestScoped;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("aerodromi")
@RequestScoped
public class RestAerodromi {

  @Resource(lookup = "java:app/jdbc/nwtis_bp")
  javax.sql.DataSource ds;
  private Aplikacija_1_konekcija app1 = new Aplikacija_1_konekcija();
  @Context
  ServletContext servletContext;
  Konfiguracija konfig = (Konfiguracija) Aplikacija_2_slusac.servletContext.getAttribute("konfig");

  /**
   * GET metoda koja uz pomoc query-a vraca podatke o aerodromima (icao, name, iso_country, coordinates)
   * @param odBroja
   * @param broj
   * @return
   */
  
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response dajSveAerodrome(@QueryParam("traziNaziv") String traziNaziv, @QueryParam("traziDrzavu") 
  String traziDrzavu, @QueryParam("odBroja") Integer odBroja, @QueryParam("broj") Integer broj) {
	  List<Aerodrom> aerodromi = new ArrayList<>();  
	  String query = getQuery(traziNaziv, traziDrzavu);
	  PreparedStatement stmt = null;
	    try (Connection con = ds.getConnection();) {
	    	stmt = con.prepareStatement(query);
	    	int limit = broj == null ? 20 : broj;
	    	int pocetak = odBroja == null ? 1 : odBroja - 1;
	    	stmt.setInt(1, limit);
	    	stmt.setInt(2, pocetak);
	    	ResultSet rs = stmt.executeQuery();

	    	while (rs.next()) {
	    		String icao = rs.getString("ICAO");
	    		String ime = rs.getString("NAME");
	    		String drzava = rs.getString("ISO_COUNTRY");
	    		String[] kordinate = rs.getString("COORDINATES").split(" ");
	    		String duzina = kordinate[0];
	    		String sirina = kordinate[1];
	    	Aerodrom ad = new Aerodrom(icao, ime, drzava, new Lokacija(duzina, sirina));
	    	aerodromi.add(ad);
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
	  
	    Gson gson = new Gson();
	    String podaci = gson.toJson(aerodromi);

	    return Response.ok().entity(podaci).build();
  }

  
private String getQuery(String traziNaziv, String traziDrzavu) {
	String query = "";
	String traziNzv = traziNaziv == null ? traziNaziv : null;
	String traziDrz = traziDrzavu == null ? traziDrzavu : null;
	boolean uneseniNazivDrzava = traziNaziv == null && traziDrzavu == null ? false : true;
	
	
	if(traziNaziv == null && traziDrzavu == null) {
		query = "SELECT ICAO, NAME, ISO_COUNTRY, COORDINATES FROM AIRPORTS LIMIT ? OFFSET ?";
	}
	else if(traziNaziv != null && traziDrzavu != null) {
		query = "SELECT ICAO, NAME, ISO_COUNTRY, COORDINATES FROM AIRPORTS WHERE NAME LIKE '"+traziNaziv+"%' LIMIT ? OFFSET ?"
				+ "AND ISO_COUNTRY = '"+traziDrzavu+"'";
	}
	else if(traziNaziv == null && traziDrzavu != null) {
		query = "SELECT ICAO, NAME, ISO_COUNTRY, COORDINATES FROM AIRPORTS WHERE ISO_COUNTRY = '"+traziDrzavu+"' LIMIT ? OFFSET ?";
	}
	else if(traziNaziv != null && traziDrzavu == null) {
		query = "SELECT ICAO, NAME, ISO_COUNTRY, COORDINATES FROM AIRPORTS WHERE NAME LIKE '"+traziNaziv+"%' LIMIT ? OFFSET ?";
	}
	return query;
}

  /**
   * GET metoda koja vraca podatke za pojedinacni aerodrom (icao, name, iso_country, coordinates)
   * @param icao
   * @return
   */
  @GET
  @Path("{icao}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response dajAerodrom(@PathParam("icao") String icao) {
    List<Aerodrom> aerodromi = new ArrayList<>();
    String query = "SELECT ICAO, NAME, ISO_COUNTRY, COORDINATES FROM AIRPORTS";
    
    PreparedStatement stmt = null;
    try (Connection con = ds.getConnection();) {
    	stmt = con.prepareStatement(query);
    	ResultSet rs = stmt.executeQuery();

    	while (rs.next()) {
    		String icao1 = rs.getString("ICAO");
    		String ime = rs.getString("NAME");
    		String drzava = rs.getString("ISO_COUNTRY");
    		String[] kordinate = rs.getString("COORDINATES").split(" ");
    		String duzina = kordinate[0];
    		String sirina = kordinate[1];
    	Aerodrom ad = new Aerodrom(icao1, ime, drzava, new Lokacija(duzina, sirina));
    	aerodromi.add(ad);
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

    Aerodrom aerodrom = null;
    for (Aerodrom a : aerodromi) {
      if (a.getIcao().compareTo(icao) == 0) {
        aerodrom = a;
        break;
      }
    }

    if (aerodrom == null) {
      return Response.noContent().build();
    }

    Gson gson = new Gson();
    String podaci = gson.toJson(aerodrom);
    Response odgovor = Response.ok().entity(podaci).build();

    return odgovor;
  }

  /**
   * GET metoda koja vraca udaljenost izmedu dva aerodroma
   * @param icaoFrom
   * @param icaoTo
   * @return
   */
  @GET
  @Path("{icaoOd}/{icaoDo}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response dajUdaljenostiAerodoma(@PathParam("icaoOd") String icaoFrom,
      @PathParam("icaoDo") String icaoTo) {
    var udaljenosti = new ArrayList<Udaljenost>();
    String query = "select ICAO_FROM, ICAO_TO, COUNTRY, DIST_CTRY from "
        + "AIRPORTS_DISTANCE_MATRIX where ICAO_FROM = ? AND ICAO_TO =  ?";

    PreparedStatement stmt = null;
    try (Connection con = ds.getConnection();) {
      stmt = con.prepareStatement(query);
      stmt.setString(1, icaoFrom);
      stmt.setString(2, icaoTo);
      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        String drzava = rs.getString("COUNTRY");
        float udaljenost = rs.getFloat("DIST_CTRY");
        Udaljenost u = new Udaljenost(drzava, udaljenost);
        udaljenosti.add(u);
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

    Gson gson = new Gson();
    String podaci = gson.toJson(udaljenosti);

    return Response.ok().entity(podaci).build();
  }
  
  /**
   * GET metoda koja vraca najvecu udaljenost predenu za pojedinacni icao
   * @param icao
   * @param odBroja
   * @param broj
   * @return
   */
  @GET
  @Path("{icao}/udaljenosti")
  @Produces(MediaType.APPLICATION_JSON)
  public Response dajIcaoUdaljenosti(@PathParam("icao") String icao,
	      @QueryParam("odBroja") Integer odBroja, @QueryParam("broj") Integer broj) {
	    var icUdaljenosti = new ArrayList<IcaoUdaljenosti>();
	    String query = "SELECT DISTINCT ICAO_TO, DIST_TOT FROM AIRPORTS_DISTANCE_MATRIX WHERE ICAO_FROM = ? LIMIT ? OFFSET ?";

	    PreparedStatement stmt = null;
	    try (Connection con = ds.getConnection();) {
	      stmt = con.prepareStatement(query);
	      int limit = broj == null ? 20 : broj;
	      int pocetak = odBroja == null ? 1 : odBroja;
	      stmt.setString(1, icao);
	      stmt.setInt(2, limit);
	      stmt.setInt(3, pocetak - 1);
	      ResultSet rs = stmt.executeQuery();

	      while (rs.next()) {
	    	  String icaoTo = rs.getString("ICAO_TO");
	          float maxDistance = rs.getFloat("DIST_TOT");
	          IcaoUdaljenosti u = new IcaoUdaljenosti(icaoTo, maxDistance);
	          icUdaljenosti.add(u);
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

	    Gson gson = new Gson();
	    String podaci = gson.toJson(icUdaljenosti);

	    return Response.ok().entity(podaci).build();
	  }
  
  @GET
  @Path("{icaoOd}/izracunaj/{icaoDo}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response dajUdaljenostIzracunaj(@PathParam("icaoOd") String icaoFrom,
		  @PathParam("icaoDo") String icaoTo) {
	  var icUdaljenostiIzracunaj = new ArrayList<IcaoUdaljenosti>();

	  	Lokacija lokacija1 = getCoordinates(icaoFrom);
	  	Lokacija lokacija2 = getCoordinates(icaoTo);
	  	
	  	String zahtjev = izracunajUdaljenost(lokacija1, lokacija2);
	  
	  	var odgovor = app1.posaljiZahtjev(konfig.dajPostavku("aplikacija_1.adresa"), Integer.parseInt(konfig.dajPostavku("aplikacija_1.mreznaVrata")), zahtjev);
	  	
	    Gson gson = new Gson();
	    String podaci = gson.toJson(odgovor);

	    return Response.ok().entity(podaci).build();
  }
  
	  private String izracunajUdaljenost(Lokacija lokacija1, Lokacija lokacija2) {
		return "UDALJENOST " + lokacija1.getLatitude() + "" + lokacija1.getLongitude() + " " + 
				lokacija2.getLatitude() + "" + lokacija2.getLongitude();
	}

	public Lokacija getCoordinates(String icao) {
		  Lokacija gps = null;
		  String query = "SELECT ICAO, NAME, ISO_COUNTRY, COORDINATES FROM AIRPORTS WHERE ICAO = ?";
	
		    PreparedStatement stmt = null;
		    try (Connection con = ds.getConnection();) {
		      stmt = con.prepareStatement(query);
		      stmt.setString(1, icao);
		      ResultSet rs = stmt.executeQuery();
	
		      while (rs.next()) {
		    	  var koord = rs.getString("COORDINATES").split(",");
		    	  gps = new Lokacija(koord[0], koord[1]);
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
		  return gps;
	  }
	
	@GET
	  @Path("{icaoOd}/udaljenost1/{icaoDo}")
	  @Produces(MediaType.APPLICATION_JSON)
	  public Response dajUdaljenostJedan(@PathParam("icaoOd") String icaoFrom,
			  @PathParam("icaoDo") String icaoTo) {

		  	Lokacija lokacija1 = getCoordinates(icaoFrom);
		  	Lokacija lokacija2 = getCoordinates(icaoTo);
		  	
		  	var drzava = getDrzava(icaoTo);
		  	
		  	String zahtjev = izracunajUdaljenost(lokacija1, lokacija2);
		  	
		  	var odgovor = app1.posaljiZahtjev(konfig.dajPostavku("aplikacija_1.adresa"), Integer.parseInt(konfig.dajPostavku("aplikacija_1.mreznaVrata")), zahtjev);
		  	
		  	List<Aerodrom> aerodromi = getAerodrome(drzava);
		  	
		  	float maxUdaljenost = Float.parseFloat(odgovor.split(" ")[1]);
		  	List<IcaoUdaljenosti> udaljenosti = getUdaljenosti(aerodromi, maxUdaljenost, lokacija1); 
		  
		    Gson gson = new Gson();
		    String podaci = gson.toJson(udaljenosti);

		    return Response.ok().entity(podaci).build();
	  }

	private List<IcaoUdaljenosti> getUdaljenosti(List<Aerodrom> aerodromi, float maxUdaljenost, Lokacija lokacija1) {
		
		List<IcaoUdaljenosti> udaljenosti = new ArrayList<>();
		String zahtjev;

		float odgovorUdaljenost;
		
		for(Aerodrom a : aerodromi) {
			zahtjev = izracunajUdaljenost(lokacija1, a.getLokacija());
			var odgovorAerodromi = app1.posaljiZahtjev(konfig.dajPostavku("aplikacija_1.adresa"), Integer.parseInt(konfig.dajPostavku("aplikacija_1.mreznaVrata")), zahtjev);
			odgovorUdaljenost = Float.parseFloat(odgovorAerodromi.split(" ")[1]);
			if(odgovorUdaljenost <= maxUdaljenost) {
				IcaoUdaljenosti u = new IcaoUdaljenosti(a.getIcao(), odgovorUdaljenost);
				udaljenosti.add(u);
			}
		}
		
		return udaljenosti;
	}

	private List<Aerodrom> getAerodrome(String drzava) {
		String query2 = "SELECT ICAO, NAME, ISO_COUNTRY, COORDINATES FROM AIRPORTS WHERE ISO_COUNTRY = ?";
		List<Aerodrom> aerodromi = new ArrayList<>();
		
		PreparedStatement stmt = null;
		try (Connection con = ds.getConnection();) {
		  stmt = con.prepareStatement(query2);
		  stmt.setString(1, drzava);
		  ResultSet rs = stmt.executeQuery();

		  while (rs.next()) {
			  String icao = rs.getString("ICAO");
			  	String ime = rs.getString("NAME");
	    		String drz = rs.getString("ISO_COUNTRY");
	    		var koord = rs.getString("COORDINATES").split(",");
		    	Lokacija gps = new Lokacija(koord[0], koord[1]);
	    	Aerodrom ad = new Aerodrom(icao, ime, drz, gps);
	    	aerodromi.add(ad);
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
		return aerodromi;
	}

	private String getDrzava(String icaoTo) {
		String query = "SELECT ICAO, NAME, ISO_COUNTRY, COORDINATES FROM AIRPORTS WHERE ICAO = ?";
		var drzava = "";
		
		PreparedStatement stmt = null;
		try (Connection con = ds.getConnection();) {
		  stmt = con.prepareStatement(query);
		  stmt.setString(1, icaoTo);
		  ResultSet rs = stmt.executeQuery();

		  while (rs.next()) {
			  drzava = rs.getString("ISO_COUNTRY");
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
		return drzava;
	}
	
	@GET
	  @Path("{icaoOd}/udaljenost2")
	  @Produces(MediaType.APPLICATION_JSON)
	  public Response dajUdaljenostdVA(@PathParam("icaoOd") String icaoFrom, @QueryParam("drzava") @DefaultValue("SI") String drzava ,
			  @QueryParam("udaljenost") @DefaultValue("100") float udaljenost) {

		  	Lokacija lokacija1 = getCoordinates(icaoFrom);
		  	
		  	List<Aerodrom> aerodromi = getAerodrome(drzava);
		  	
		  	List<IcaoUdaljenosti> udaljenosti = getUdaljenosti(aerodromi, udaljenost, lokacija1); 
		  
		    Gson gson = new Gson();
		    String podaci = gson.toJson(udaljenosti);

		    return Response.ok().entity(podaci).build();
	  }
  
}