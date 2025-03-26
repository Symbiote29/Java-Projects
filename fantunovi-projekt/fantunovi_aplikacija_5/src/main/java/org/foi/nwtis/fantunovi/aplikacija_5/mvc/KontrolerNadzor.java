package org.foi.nwtis.fantunovi.aplikacija_5.mvc;

import org.foi.nwtis.fantunovi.aplikacija_5.podaci.JsonSadrzaj;
import org.foi.nwtis.fantunovi.aplikacija_5.rest.RestKlijentAerodroma;
import org.foi.nwtis.fantunovi.aplikacija_5.rest.RestKlijentNadzor;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.mvc.View;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Controller
@Path("nadzor")
@RequestScoped
public class KontrolerNadzor {

	@Inject
	  private Models model;
	
	@GET
	@View("nadzor.jsp")
	public void getNadzor() {}
	
	@POST
	@View("nadzor.jsp")
	public void postNadzor(@FormParam("komanda") String komanda) {
		
		RestKlijentNadzor rkn = new RestKlijentNadzor();
		JsonSadrzaj status = null;
		
		if(komanda.equals("STATUS")) {
			status = rkn.getStatus();
		}
		else if(komanda.equals("INIT")) {
			status = rkn.getKomanda(komanda);
		}
		else if(komanda.equals("KRAJ")) {
			status = rkn.getKomanda(komanda);
		}
		else if(komanda.equals("PAUZA")) {
			status = rkn.getKomanda(komanda);
		}
		else if(komanda.equals("DA")) {
			status = rkn.getInfo(komanda);
		}
		else if(komanda.equals("NE")) {
			status = rkn.getInfo(komanda);
		}
		else {
			return;
		}
		
		model.put("status", status);
	}
}
