package org.foi.nwtis.fantunovi.aplikacija_5.mvc;

import jakarta.enterprise.context.RequestScoped;
import jakarta.mvc.Controller;
import jakarta.mvc.View;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Controller
@Path("")
@RequestScoped
public class KontrolerIzbornik {

	@GET
	@View("index.jsp")
	public void pocetak() {}

}
