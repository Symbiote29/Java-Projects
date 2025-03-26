package org.foi.nwtis.fantunovi.aplikacija_2.rest;

import java.util.ArrayList;

import org.foi.nwtis.Konfiguracija;
import org.foi.nwtis.fantunovi.aplikacija_2.konekcija.Aplikacija_1_konekcija;
import org.foi.nwtis.fantunovi.aplikacija_2.slusaci.Aplikacija_2_slusac;
import org.foi.nwtis.podaci.IcaoUdaljenosti;
import org.foi.nwtis.podaci.JsonSadrzaj;
import org.foi.nwtis.podaci.Lokacija;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.RequestScoped;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("nadzor")
@RequestScoped
public class RestNadzor {
	
	@Resource(lookup = "java:app/jdbc/nwtis_bp")
	  javax.sql.DataSource ds;
	  private Aplikacija_1_konekcija app1 = new Aplikacija_1_konekcija();
	  @Context
	  ServletContext servletContext;
	  Konfiguracija konfig = (Konfiguracija) Aplikacija_2_slusac.servletContext.getAttribute("konfig");
	  
	  public int provjeraOdgovora(String odgovor) {
		  if(odgovor.contains("ERROR")) {
			  return 400;
		  }
		  else {
			  return 200;
		  }
	  }
	  
	  @GET
	  @Produces(MediaType.APPLICATION_JSON)
	  public Response osnovnaPutanjaNadzor() {
		  var odgovor = app1.posaljiZahtjev(konfig.dajPostavku("aplikacija_1.adresa"), Integer.parseInt(konfig.dajPostavku("aplikacija_1.mreznaVrata")), "STATUS");
		  	
		  JsonSadrzaj sadrzaj = new JsonSadrzaj(provjeraOdgovora(odgovor), odgovor);
		
		  if(sadrzaj.getStatus() == 200) {
			  Gson gson = new Gson();
			  String podaci = gson.toJson(sadrzaj);

			  return Response.ok().entity(podaci).build();
		  }
		  else {
			  Gson gson = new Gson();
			  String podaci = gson.toJson(sadrzaj);

			  return Response.status(Response.Status.BAD_REQUEST).entity(podaci).build();
		  }
		    
	  }
	  
	  @GET
	  @Path("{komanda}")
	  @Produces(MediaType.APPLICATION_JSON)
	  public Response odrediKomandu(@PathParam("komanda") String komanda) {
		  
		  	var odgovor = app1.posaljiZahtjev(konfig.dajPostavku("aplikacija_1.adresa"), Integer.parseInt(konfig.dajPostavku("aplikacija_1.mreznaVrata")), komanda);
		  	
		  	JsonSadrzaj sadrzaj = new JsonSadrzaj(provjeraOdgovora(odgovor), odgovor);
		  	
		  	if(sadrzaj.getStatus() == 200) {
				  Gson gson = new Gson();
				  String podaci = gson.toJson(sadrzaj);

				  return Response.ok().entity(podaci).build();
			  }
			  else {
				  Gson gson = new Gson();
				  String podaci = gson.toJson(sadrzaj);

				  return Response.status(Response.Status.BAD_REQUEST).entity(podaci).build();
			  }
	  }
	  
	  @GET
	  @Path("INFO/{vrsta}")
	  @Produces(MediaType.APPLICATION_JSON)
	  public Response komandaVrsta(@PathParam("vrsta") String vrsta) {
		  
		  	var odgovor = app1.posaljiZahtjev(konfig.dajPostavku("aplikacija_1.adresa"), Integer.parseInt(konfig.dajPostavku("aplikacija_1.mreznaVrata")), "INFO " + vrsta);
		  	
		  	JsonSadrzaj sadrzaj = new JsonSadrzaj(provjeraOdgovora(odgovor), odgovor);
		  	
		  	if(sadrzaj.getStatus() == 200) {
				  Gson gson = new Gson();
				  String podaci = gson.toJson(sadrzaj);

				  return Response.ok().entity(podaci).build();
			  }
			  else {
				  Gson gson = new Gson();
				  String podaci = gson.toJson(sadrzaj);

				  return Response.status(Response.Status.BAD_REQUEST).entity(podaci).build();
			  }
	  }
	 
}
