package org.foi.nwtis.fantunovi.aplikacija_5.mvc;

import org.foi.nwtis.fantunovi.aplikacija_5.rest.RestKlijentLetova;
import org.foi.nwtis.rest.podaci.LetAviona;

import com.google.gson.Gson;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.mvc.View;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

/**
*
* @author NWTiS
*/
@Controller
@Path("letovi")
@RequestScoped
public class KontrolerLetova {

	 @Inject
	  private Models model;

	  @GET
	  @Path("pocetak")
	  @View("index.jsp")
	  public void pocetak() {}
	
	  /**
	   * GET metoda koja sluzi za instanciranje RestKlijentLetova i vracanje letova za neki icao
	   * na neki dan
	   * @param icao
	   * @param dan
	   */
	  @GET
	  @Path("{icao}")
	  @View("polasciLetova.jsp")
	  public void getPolaskeLetova(@PathParam("icao") String icao, @QueryParam("dan") String dan) {
		  try {
		      RestKlijentLetova rkl = new RestKlijentLetova();
		      var polasciLetova = rkl.getPolasciLetova(icao, dan);
		      model.put("letovi", polasciLetova);
		      System.out.println("icao: " + icao + " |  dan: " + dan);
		      System.out.println("tu san" + polasciLetova);
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
	  }
	  
	  /**
	   * GET metoda koja sluzi za instanciranje RestKlijentLetova i vracanje letova izmedu neka dva aerodroma
	   * @param icaoOd
	   * @param icaoDo
	   * @param dan
	   */
	  @GET
	  @Path("{icaoOd}/{icaoDo}")
	  @View("PregledPolazakaSjednogAerodroma.jsp")
	  public void getPolaskeLetovaSjednogAerodroma(@PathParam("icaoOd") String icaoOd, 
			  @PathParam("icaoDo") String icaoDo, @QueryParam("dan") String dan) {
		  try {
		      RestKlijentLetova rkl = new RestKlijentLetova();
		      var polasciLetovaSjednogAerodroma = rkl.getPolasciLetovaIzmeduOdabranihAerodroma(icaoOd, icaoDo, dan);
		      model.put("letoviSJednogAerodroma", polasciLetovaSjednogAerodroma);
		      System.out.println("icaoOd: " + icaoOd + " - icaoDo: " + icaoDo + " |  dan: " + dan);
		      System.out.println("tu san" + polasciLetovaSjednogAerodroma);
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
	  }
	  
	  /**
	   * GET metoda koja sluzi za instanciranje RestKlijentLetova i vracanje spremljenih letova iz
	   * tablica letovi_polasci
	   */
	  @GET
	  @Path("letovi/spremljeni")
	  @View("pregledSpremljenihLetova.jsp")
	  public void getSpremljeneLetove() {
		  try {
		      RestKlijentLetova rkl = new RestKlijentLetova();
		      var spremljeniLetovi = rkl.getSpremljeneLetove();
		      model.put("spremljeniLetovi", spremljeniLetovi);
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
	  }
	  
}
