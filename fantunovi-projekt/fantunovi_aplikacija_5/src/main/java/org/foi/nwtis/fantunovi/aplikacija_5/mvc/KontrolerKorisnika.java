package org.foi.nwtis.fantunovi.aplikacija_5.mvc;

import org.foi.nwtis.Konfiguracija;
import org.foi.nwtis.fantunovi.aplikacija_5.rest.RestKlijentAerodroma;
import org.foi.nwtis.fantunovi.aplikacija_5.slusaci.WebListener;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.mvc.View;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Controller
@Path("korisnici")
@RequestScoped
public class KontrolerKorisnika {

	private ServletContext context = WebListener.servletContext;
	private Konfiguracija[] konf = (Konfiguracija[]) context.getAttribute("konfig");
	
	@Inject
	  private Models model;
	
	 @GET
	  @Path("")
	  @View("izbornikKorisnici.jsp")
	  public void pocetak() {}
	 
	 @GET
	  @Path("svi")
	  @View("dajKorisnike.jsp")
	  public void getAerodromi() {
	    try {
	      RestKlijentAerodroma rca = new RestKlijentAerodroma();
	      var aerodromi = rca.getAerodromi();
	      model.put("aerodromi", aerodromi);
	      model.put("datoteka", konf);
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	  }
}
