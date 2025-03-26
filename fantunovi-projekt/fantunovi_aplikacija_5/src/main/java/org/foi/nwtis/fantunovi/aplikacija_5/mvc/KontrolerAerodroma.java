/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package org.foi.nwtis.fantunovi.aplikacija_5.mvc;

import org.foi.nwtis.Konfiguracija;
import org.foi.nwtis.fantunovi.aplikacija_5.podaci.JsonSadrzaj;
import org.foi.nwtis.fantunovi.aplikacija_5.podaci.UdaljenostAerodrom;
import org.foi.nwtis.fantunovi.aplikacija_5.rest.RestKlijentAerodroma;
import org.foi.nwtis.fantunovi.aplikacija_5.slusaci.WebListener;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.mvc.View;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;

/**
 *
 * @author NWTiS
 */
@Controller
@Path("aerodromi")
@RequestScoped
public class KontrolerAerodroma {

	private ServletContext context = WebListener.servletContext;
	private Konfiguracija[] konf = (Konfiguracija[]) context.getAttribute("konfig");
	
  @Inject
  private Models model;

  @GET
  @Path("")
  @View("izbornikAerodromi.jsp")
  public void pocetak() {}

  /**
   * GET metoda koja sluzi za instanciranje RestKlijentAerodroma i vracanje podataka o svim aerodromima
   */
  @GET
  @Path("svi")
  @View("aerodromi.jsp")
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

  /**
   * GET metoda koja sluzi za instanciranje RestKlijentAerodroma i vracanje podataka o jednom aerodromu
   * @param icao
   */
  @GET
  @Path("icao")
  @View("aerodrom.jsp")
  public void getAerodrom(@QueryParam("icao") String icao) {
    try {
      RestKlijentAerodroma rca = new RestKlijentAerodroma();
      var aerodrom = rca.getAerodrom(icao);
      model.put("aerodrom", aerodrom);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * GET metoda koja sluzi za instanciranje RestKlijentAerodroma i vracanje podataka 
   * o udaljenosti izmedu dva aerodroma
   * @param icaoOd
   * @param icaoDo
   */
  @GET
  @Path("{icaoOd}/{icaoDo}")
  @View("aerodromiUdaljenosti.jsp")
  public void getAerodromiUdaljenost(@PathParam("icaoOd") String icaoOd,
		  @PathParam("icaoDo") String icaoDo) {
	  try {
	      RestKlijentAerodroma rca = new RestKlijentAerodroma();
	      System.out.println(icaoOd + " " + icaoDo);
	      var udaljenosti = rca.getUdaljenost2Aerodroma(icaoOd, icaoDo);
	      model.put("udaljenosti2Aerodroma", udaljenosti);
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
  }
  
  @GET
  @Path("{icaoOd}/izracunaj/{icaoDo}")
  @View("izracunajUdaljenostiDvaAerodroma.jsp")
  public void izracunajUdaljenostiDvaAerodroma(@PathParam("icaoOd") String icaoOd, @PathParam("icaoDo") String icaoDo) {
	  try {
	      RestKlijentAerodroma rca = new RestKlijentAerodroma();
	      JsonSadrzaj udaljenosti = rca.getIzracunatuVrijednost2Aerodroma(icaoOd, icaoDo);
	      //System.out.println("rca: " + udaljenosti.toString());
	      model.put("izracunajUdaljenostiDvaAerodroma", udaljenosti);
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
  }
  
  @GET
  @Path("{icaoOd}/udaljenost1/{icaoDo}")
  @View("izracunajUdaljenostiJedanDvaAerodroma.jsp")
  public void izracunajUdaljenostiJedanDvaAerodroma(@PathParam("icaoOd") String icaoOd, @PathParam("icaoDo") String icaoDo) {
	  try {
	      RestKlijentAerodroma rca = new RestKlijentAerodroma();
	      var udaljenosti = rca.getIzracunaj1Udaljenosti2Aerodroma(icaoOd, icaoDo);
	      model.put("izracunajUdaljenostiJedanDvaAerodroma", udaljenosti);
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
  }
  
  @GET
  @Path("{icaoDo}/udaljenost2")
  @View("izracunajUdaljenostDva.jsp")
  public void izracunajUdaljenostDVa(@PathParam("icaoDo") String icaoDo, @QueryParam("drzava") @DefaultValue("MK") String drzava ,
		  @QueryParam("udaljenost") @DefaultValue("900") float udaljenost ) {
	  try {
	      RestKlijentAerodroma rca = new RestKlijentAerodroma();
	      var udaljenosti = rca.getIzracunajUdaljenostDva(icaoDo, drzava, udaljenost);
	      model.put("izracunajUdaljenostDva", udaljenosti);
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
  }
  
  /**
   * GET metoda koja sluzi za instanciranjea RestKlijentAerodroma i vracanje podataka o pregledu udaljenosti
   * svih aerodroma s pocetnog icao-a
   * @param icao
   * @param odBroja
   * @param broj
   */
  @GET
  @Path("udaljenostiSvihAerodroma")
  @View("pregledUdaljenostiSvihAerodroma.jsp")
  public void getUdaljenostiSvihAerodroma(@QueryParam("icao") String icao, 
		  @QueryParam("odBroja") @DefaultValue("1") int odBroja, @QueryParam("broj") @DefaultValue("20") int broj) {
	  System.out.println("mvc test");
	  try {
	      RestKlijentAerodroma rca = new RestKlijentAerodroma();
	      var udaljenosti = rca.getUdaljenostAerodroma(icao, odBroja, broj);
	      model.put("udaljenosti", udaljenosti);
	      System.out.println(udaljenosti);
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
  }

  /**
   * GET metoda koja sluzi za instanciranjea RestKlijentAerodroma i vracanje podataka o najduljoj udaljenosti
   * predenoj preko neke drzave
   * @param icao
   */
  @GET
  @Path("icao/najduljiPutDrzave")
  @View("pregledAerodromNajduljiPutDrzave.jsp")
  public void getNajduljiPutDrzave(@PathParam("icao") String icao) {
	  try {
	      RestKlijentAerodroma rca = new RestKlijentAerodroma();
	      var najduljiPut = rca.getNajduljiPutDrzava(icao);
	      model.put("najdulji", najduljiPut);
	      System.out.println("Im here");
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
  }
  
}
