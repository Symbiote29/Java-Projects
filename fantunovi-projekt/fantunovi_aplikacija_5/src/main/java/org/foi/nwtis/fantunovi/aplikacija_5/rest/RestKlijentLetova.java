package org.foi.nwtis.fantunovi.aplikacija_5.rest;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.foi.nwtis.rest.podaci.LetAviona;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonReader;
import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;

public class RestKlijentLetova {

	public RestKlijentLetova() {}
	
	/**
	 * Metoda koja sluzi za instanciranje RestKKlijentLetova i vracanje podataka o polascima svih letova
	 * @param icao
	 * @param dan
	 * @return
	 */
	public List<LetAviona> getPolasciLetova(String icao, String dan){
		RestKKlijentLetova rc = new RestKKlijentLetova();
	    LetAviona[] json_udaljenosti = null;
	    
		try {
			json_udaljenosti = rc.polasciLetova(icao, dan);
		} catch (ClientErrorException e) {
			e.printStackTrace();
		}
	    List<LetAviona> udaljenosti;
	    udaljenosti = new ArrayList<>(Arrays.asList(json_udaljenosti));
	    rc.close();
	    
	    return udaljenosti;
	  }
	
	/**
	 * etoda koja sluzi za instanciranje RestKKlijentLetova i vracanje podataka o polascima letova izmedu
	 * 2 aerodroma
	 * @param icaoOd
	 * @param icaoDo
	 * @param dan
	 * @return
	 */
	public List<LetAviona> getPolasciLetovaIzmeduOdabranihAerodroma(String icaoOd, String icaoDo, String dan){
		RestKKlijentLetova rc = new RestKKlijentLetova();
	    LetAviona[] json_udaljenosti = null;
	    
		try {
			json_udaljenosti = rc.polasciLetovaSjednogAerodroma(icaoOd, icaoDo, dan);
		} catch (ClientErrorException e) {
			e.printStackTrace();
		}
	    List<LetAviona> udaljenosti;
	    udaljenosti = new ArrayList<>(Arrays.asList(json_udaljenosti));
	    rc.close();
	    
	    return udaljenosti;
	  }
	
	/**
	 * metoda koja instanciranje RestKKlijentLetova i vracanje spremljene letove iz baze
	 * @return
	 */
	public List<LetAviona> getSpremljeneLetove(){
		RestKKlijentLetova rc = new RestKKlijentLetova();
	    LetAviona[] json_udaljenosti = null;
	    
		try {
			json_udaljenosti = rc.dajSpremljeneLetove();
		} catch (ClientErrorException e) {
			e.printStackTrace();
		}
	    List<LetAviona> udaljenosti;
	    udaljenosti = new ArrayList<>(Arrays.asList(json_udaljenosti));
	    rc.close();
	    
	    return udaljenosti;
	  }
	
	static class RestKKlijentLetova{
		
		private final WebTarget webTarget;
	    private final Client client;
	    private static final String BASE_URI = "http://200.20.0.4:8080/fantunovi_zadaca_2_wa_1/api";

	    public RestKKlijentLetova() {
	      client = ClientBuilder.newClient();
	      webTarget = client.target(BASE_URI).path("letovi");
	    }
	    
	    /**
	     * metoda u kojoj se odreduje putanja preko koje ce se dohvatiti spremljeni polasci letova
	     * i vratiti gore u RestKlijentLetova i nazad u kontroler
	     * @return
	     */
	    public LetAviona[] dajSpremljeneLetove() {
	    	WebTarget resource = webTarget;
	    	resource = resource.path("letovi/spremljeni");
	    	System.out.println(resource.getUri());
	    	
	        Invocation.Builder request = resource.request(MediaType.APPLICATION_JSON);
	        if (request.get(String.class).isEmpty()) {
	          return null;
	        }
	        
	        Gson gson = new Gson();
	        LetAviona[] udaljenost = null;
			try {
				
				String jsonResponse = request.get(String.class);
				System.out.println(jsonResponse);
				JsonReader reader = Json.createReader(new StringReader(jsonResponse));
				JsonArray jsonArray = reader.readArray();
				System.out.println(jsonArray);
				udaljenost = gson.fromJson(jsonArray.toString(), LetAviona[].class);
				for(LetAviona l : udaljenost) {
					System.out.println(l);
				}
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
			}
	        return udaljenost;
		}

	    /**
	     *  metoda u kojoj se odreduje putanja preko koje ce se dohvatiti polasci letova s jednog aerodroma
	     * i vratiti gore u RestKlijentLetova i nazad u kontroler
	     * @param icaoOd
	     * @param icaoDo
	     * @param dan
	     * @return
	     */
		public LetAviona[] polasciLetovaSjednogAerodroma(String icaoOd, String icaoDo, String dan) {
	    	WebTarget resource = webTarget;
	    	resource = resource.path(icaoOd + "/" + icaoDo).queryParam("dan", dan);
	    	System.out.println(resource.getUri());
	    	
	        Invocation.Builder request = resource.request(MediaType.APPLICATION_JSON);
	        if (request.get(String.class).isEmpty()) {
	          return null;
	        }
	        
	        Gson gson = new Gson();
	        LetAviona[] udaljenost = null;
			try {
				
				String jsonResponse = request.get(String.class);
				System.out.println(jsonResponse);
				JsonReader reader = Json.createReader(new StringReader(jsonResponse));
				JsonArray jsonArray = reader.readArray();
				System.out.println(jsonArray);
				udaljenost = gson.fromJson(jsonArray.toString(), LetAviona[].class);
				for(LetAviona l : udaljenost) {
					System.out.println(l);
				}
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
			}
	        return udaljenost;
		}

		/**
		 *  metoda u kojoj se odreduje putanja preko koje ce se dohvatiti svi polasci letova za jedan icao
	     * i vratiti gore u RestKlijentLetova i nazad u kontroler
		 * @param icao
		 * @param dan
		 * @return
		 */
		public LetAviona[] polasciLetova(String icao, String dan) {
	    	WebTarget resource = webTarget;
	    	resource = resource.path(icao + "/").queryParam("dan", dan);
	    	
	        Invocation.Builder request = resource.request(MediaType.APPLICATION_JSON);
	        if (request.get(String.class).isEmpty()) {
	          return null;
	        }
	        
	        Gson gson = new Gson();
	        LetAviona[] udaljenost = null;
			try {
				
				String jsonResponse = request.get(String.class);
				JsonReader reader = Json.createReader(new StringReader(jsonResponse));
				JsonArray jsonArray = reader.readArray();
				udaljenost = gson.fromJson(jsonArray.toString(), LetAviona[].class);
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
			}
	        return udaljenost;
		}
	    
	    public void close() {
	        client.close();
	      }
	}
}
