package org.foi.nwtis.fantunovi.aplikacija_5.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.foi.nwtis.fantunovi.aplikacija_5.podaci.Dnevnik;
import org.foi.nwtis.podaci.Aerodrom;

import com.google.gson.Gson;

import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;

public class RestKlijentDnevnik {
	
	public List<Dnevnik> getDnevnik() {
	    RestKKlijent rc = new RestKKlijent();
	    Dnevnik[] dn = rc.getDnevnik();
	    List<Dnevnik> dnevnici;
	    
	    if (dn == null) {
	    	dnevnici = new ArrayList<>();
	    } else {
	    	dnevnici = Arrays.asList(dn);
	    }
	    rc.close();
	    
	    rc.close();
	    return dnevnici;
	  }
	
	static class RestKKlijent {

	    private final WebTarget webTarget;
	    private final Client client;
	    private static final String BASE_URI = "http://200.20.0.4:8080/fantunovi_aplikacija_2/api";

	    public RestKKlijent() {
	      client = ClientBuilder.newClient();
	      webTarget = client.target(BASE_URI).path("dnevnik");
	    }
	    
	    public Dnevnik[] getDnevnik() throws ClientErrorException {
	        WebTarget resource = webTarget;
	        Invocation.Builder request = resource.request(MediaType.APPLICATION_JSON);
	        if (request.get(String.class).isEmpty()) {
	          return null;
	        }
	        Gson gson = new Gson();
	        Dnevnik[] sadrzaj = gson.fromJson(request.get(String.class), Dnevnik[].class);
	        return sadrzaj;
	      } 
	    
	    public void close() {
		      client.close();
		    }
	}
	
}
