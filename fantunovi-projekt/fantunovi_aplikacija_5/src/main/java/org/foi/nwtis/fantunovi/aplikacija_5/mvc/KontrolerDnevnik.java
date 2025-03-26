package org.foi.nwtis.fantunovi.aplikacija_5.mvc;

import org.foi.nwtis.fantunovi.aplikacija_5.podaci.Dnevnik;
import org.foi.nwtis.fantunovi.aplikacija_5.rest.RestKlijentDnevnik;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.mvc.View;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Controller
@Path("dnevnik")
@RequestScoped
public class KontrolerDnevnik {

	@Inject
	  private Models model;
	
	@GET
	@View("dnevnik.jsp")
	public void getDnevnik() {}
	
	
	@GET
	@Path("")
	@View("dnevnik.jsp")
	public void getDnevnikInfo() {
		try {
			RestKlijentDnevnik rkd = new RestKlijentDnevnik();
			var dnevnik = rkd.getDnevnik();
			
			model.put("dnevnik", dnevnik);
		}catch (Exception e) {
		      e.printStackTrace();
	    }
	}
}
