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
import org.foi.nwtis.podaci.Dnevnik;
import org.foi.nwtis.podaci.Lokacija;
import org.foi.nwtis.rest.podaci.LetAviona;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.RequestScoped;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("dnevnik")
@RequestScoped
public class RestDnevnik {
	@Resource(lookup = "java:app/jdbc/nwtis_bp")
	  javax.sql.DataSource ds;
	  private Aplikacija_1_konekcija app1 = new Aplikacija_1_konekcija();
	  @Context
	  ServletContext servletContext;
	  Konfiguracija konfig = (Konfiguracija) Aplikacija_2_slusac.servletContext.getAttribute("konfig");
	  
	  @GET
	  @Produces(MediaType.APPLICATION_JSON)
	  public Response osnovnaPutanjaDnevnik(@QueryParam("vrsta") String vrsta, @QueryParam("odBroja") Integer odBroja, @QueryParam("broj") Integer broj) {
		  
		  String query = "";
		  
		  if(vrsta == null) {
			  query = "SELECT * FROM DNEVNIK LIMIT ? OFFSET ?";
		  }
		  else {
			  query = "SELECT * FROM DNEVNIK WHERE VRSTAAPLIKACIJE = ? LIMIT ? OFFSET ?";
		  }
		  
		  List<Dnevnik> dnevnik = new ArrayList<>();
		  PreparedStatement stmt = null;
		    try (Connection con = ds.getConnection();) {
		    	stmt = con.prepareStatement(query);
		    	int limit = broj == null ? 20 : broj;
		    	int pocetak = odBroja == null ? 1 : odBroja - 1;
		    	if(vrsta != null) {
		    		stmt.setString(1, vrsta);
		    		stmt.setInt(2, limit);
			    	stmt.setInt(3, pocetak);
		    	}
		    	else {
		    		stmt.setInt(1, limit);
			    	stmt.setInt(2, pocetak);
		    	}
		    	ResultSet rs = stmt.executeQuery();

		    	while (rs.next()) {
		    		String zahtjev = rs.getString("ZAHTJEV");
		    		String put = rs.getString("PUT");
		    		String vrstaAplikacije = rs.getString("VRSTAAPLIKACIJE");
		    		String vrijeme = rs.getString("VRIJEME");
		    	Dnevnik dnv = new Dnevnik(zahtjev, put, vrstaAplikacije, vrijeme);
		    	dnevnik.add(dnv);
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
		    String podaci = gson.toJson(dnevnik);

		    return Response.ok().entity(podaci).build();
	  }
	  @POST
	  @Consumes(MediaType.APPLICATION_JSON)
	  @Produces(MediaType.APPLICATION_JSON)
	  public Response spremiDnevnike(Dnevnik dnv) {
		  
		  String query = "INSERT INTO DNEVNIK (ZAHTJEV, PUT, VRSTAAPLIKACIJE, VRIJEME) VALUES (?, ?, ?, CURRENT_TIMESTAMP)";
			
			PreparedStatement stmt = null;
		    try (Connection con = ds.getConnection();) {
		      stmt = con.prepareStatement(query);
		      stmt.setString(1, dnv.getZahtjev());
		      stmt.setString(2, dnv.getPut());
		      stmt.setString(3, dnv.getVrstaAplikacije());
		      stmt.executeUpdate();
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

		    return Response.ok().entity("Dnevnik je dodan u bazu").build();
		  	
	  }
}
