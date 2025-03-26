package org.foi.nwtis.fantunovi.aplikacija_5.rest;

import org.foi.nwtis.fantunovi.aplikacija_5.podaci.JsonSadrzaj;
import org.foi.nwtis.fantunovi.aplikacija_5.rest.RestKlijentAerodroma.RestKKlijent;
import org.foi.nwtis.podaci.Aerodrom;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;

public class RestKlijentNadzor {

	public JsonSadrzaj getStatus() {
	    RestKKlijent rc = new RestKKlijent();
	    JsonSadrzaj a = rc.getStatus();
	    rc.close();
	    return a;
	  }
	
	public JsonSadrzaj getKomanda(String komanda) {
	    RestKKlijent rc = new RestKKlijent();
	    JsonSadrzaj a = rc.getKomanda(komanda);
	    rc.close();
	    return a;
	  }
	
	public JsonSadrzaj getInfo(String info) {
	    RestKKlijent rc = new RestKKlijent();
	    JsonSadrzaj a = rc.getInfo(info);
	    rc.close();
	    return a;
	  }
	
	static class RestKKlijent {

	    private final WebTarget webTarget;
	    private final Client client;
	    private static final String BASE_URI = "http://200.20.0.4:8080/fantunovi_aplikacija_2/api";

	    public RestKKlijent() {
	      client = ClientBuilder.newClient();
	      webTarget = client.target(BASE_URI).path("nadzor");
	    }
	    
	    public JsonSadrzaj getStatus() throws ClientErrorException {
	        WebTarget resource = webTarget;
	        Invocation.Builder request = resource.request(MediaType.APPLICATION_JSON);
	        if (request.get(String.class).isEmpty()) {
	          return null;
	        }
	        Gson gson = new Gson();
	        JsonSadrzaj sadrzaj = gson.fromJson(request.get(String.class), JsonSadrzaj.class);
	        return sadrzaj;
	      } 
	    
	    public JsonSadrzaj getKomanda(String komanda) throws ClientErrorException {
	        WebTarget resource = webTarget;
	        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[] {komanda}));
	        Invocation.Builder request = resource.request(MediaType.APPLICATION_JSON);
	        if (request.get(String.class).isEmpty()) {
	          return null;
	        }
	        Gson gson = new Gson();
	        JsonSadrzaj sadrzaj = null;
			try {
				sadrzaj = gson.fromJson(request.get(String.class), JsonSadrzaj.class);
			} catch (JsonSyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return sadrzaj;
	      } 
	    
	    public JsonSadrzaj getInfo(String info) throws ClientErrorException {
	        WebTarget resource = webTarget;
	        resource = resource.path(java.text.MessageFormat.format("INFO/{0}", new Object[] {info}));
	        Invocation.Builder request = resource.request(MediaType.APPLICATION_JSON);
	        if (request.get(String.class).isEmpty()) {
	          return null;
	        }
	        Gson gson = new Gson();
	        JsonSadrzaj sadrzaj = null;
			try {
				sadrzaj = gson.fromJson(request.get(String.class), JsonSadrzaj.class);
			} catch (JsonSyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return sadrzaj;
	      } 
	    
	    public void close() {
		      client.close();
		    }
	}

}
