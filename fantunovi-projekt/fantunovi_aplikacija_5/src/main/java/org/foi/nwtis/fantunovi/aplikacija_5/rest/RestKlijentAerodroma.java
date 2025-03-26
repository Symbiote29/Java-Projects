package org.foi.nwtis.fantunovi.aplikacija_5.rest;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.foi.nwtis.Konfiguracija;
import org.foi.nwtis.fantunovi.aplikacija_5.podaci.JsonSadrzaj;
import org.foi.nwtis.fantunovi.aplikacija_5.podaci.Udaljenost;
import org.foi.nwtis.fantunovi.aplikacija_5.podaci.UdaljenostAerodrom;
import org.foi.nwtis.fantunovi.aplikacija_5.podaci.UdaljenostAerodromDrzava;
import org.foi.nwtis.fantunovi.aplikacija_5.podaci.UdaljenostAerodromKlasa;
import org.foi.nwtis.fantunovi.aplikacija_5.rest.RestKlijentNadzor.RestKKlijent;
import org.foi.nwtis.fantunovi.aplikacija_5.slusaci.WebListener;
import org.foi.nwtis.podaci.Aerodrom;
import org.foi.nwtis.rest.podaci.LetAviona;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import com.google.gson.JsonArray;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;

public class RestKlijentAerodroma {
	
  public RestKlijentAerodroma() {}

  	/**
  	 * metoda koja sluzi za instanciranje RestKKlijenta i vracanje vrijednosti o svim aerodromima
  	 * @param odBroja
  	 * @param broj
  	 * @return
  	 */
  public List<Aerodrom> getAerodromi(int odBroja, int broj) {
    RestKKlijent rc = new RestKKlijent();
    Aerodrom[] json_Aerodromi = rc.getAerodromi(odBroja, broj);
    List<Aerodrom> aerodromi;
    if (json_Aerodromi == null) {
      aerodromi = new ArrayList<>();
    } else {
      aerodromi = Arrays.asList(json_Aerodromi);
    }
    rc.close();
    return aerodromi;
  }
  
  /**
   * metoda koja sluzi za instanciranje RestKKlijenta i vracanje vrijednosti o udaljenosti izmedu
   * icao-a i svih ostalih aerodroma
   * @param icao
   * @param odBroja
   * @param broj
   * @return
   */
  public List<Udaljenost> getUdaljenostAerodroma(String icao, int odBroja, int broj){
	    RestKKlijent rc = new RestKKlijent();
	    Udaljenost[] json_udaljenosti = null;
	    System.out.println("rca test");
		try {
			json_udaljenosti = rc.getUdaljenostiSvihAerodroma(icao, odBroja, broj);
			for(Udaljenost u: json_udaljenosti) {
				System.out.println("tu smo: " + u);
			}
		} catch (ClientErrorException e) {
			e.printStackTrace();
		}
	    List<Udaljenost> udaljenosti = new ArrayList<>();
	    udaljenosti = Arrays.asList(json_udaljenosti);
	    //udaljenosti = new ArrayList<>(Arrays.asList(json_udaljenosti));
	    for(Udaljenost u : udaljenosti) {
	    	System.out.println(u);
	    }
	    rc.close();
	    
	    return udaljenosti;
	  }
  
  /**
   * metoda koja sluzi za instanciranje RestKKlijent i vracanje podataka o udaljenosti izmedu 2 aerodroma
   * @param icaoOd
   * @param icaoDo
   * @return
   */
  public List<UdaljenostAerodrom> getUdaljenost2Aerodroma(String icaoOd, String icaoDo){
	    RestKKlijent rc = new RestKKlijent();
	    UdaljenostAerodrom[] json_udaljenosti = null;
		try {
			json_udaljenosti = rc.udaljenost2Aerodroma(icaoOd, icaoDo);
		} catch (ClientErrorException e) {
			e.printStackTrace();
		}
	    List<UdaljenostAerodrom> udaljenosti;
	    udaljenosti = new ArrayList<>(Arrays.asList(json_udaljenosti));
	    rc.close();
	    
	    return udaljenosti;
	  }
  
  public List<UdaljenostAerodrom> getIzracunaj1Udaljenosti2Aerodroma(String icaoOd, String icaoDo){
	  RestKKlijent rc = new RestKKlijent();
	    UdaljenostAerodrom[] json_udaljenosti = null;
		try {
			json_udaljenosti = rc.izracunajUdaljenostiDvaAerodroma1(icaoOd, icaoDo);
			for(UdaljenostAerodrom a : json_udaljenosti) {
			}
		} catch (ClientErrorException e) {
			e.printStackTrace();
		}
	    List<UdaljenostAerodrom> udaljenosti;
	    udaljenosti = new ArrayList<>(Arrays.asList(json_udaljenosti));
	    rc.close();
	    
	    return udaljenosti;
  }
  
  public List<UdaljenostAerodromKlasa> getIzracunajUdaljenostDva (String icaoDo, String drzava, float udaljenost){
	  RestKKlijent rc = new RestKKlijent();
	  UdaljenostAerodromKlasa[] json_udaljenosti = null;
	  
	  try {
			json_udaljenosti = rc.izracunajUdaljenostiDvaAerodroma(icaoDo, drzava, udaljenost);
			for(UdaljenostAerodromKlasa a : json_udaljenosti) {
			}
		} catch (ClientErrorException e) {
			e.printStackTrace();
		}
	    List<UdaljenostAerodromKlasa> udaljenosti;
	    udaljenosti = new ArrayList<>(Arrays.asList(json_udaljenosti));
	    rc.close();
	  
	  return udaljenosti;
  }
  
  public JsonSadrzaj getIzracunatuVrijednost2Aerodroma(String icaoOd, String icaoDo){
	  	RestKKlijent rc = new RestKKlijent();
	    JsonSadrzaj a = rc.izracunajUdaljenostiDvaAerodroma(icaoOd, icaoDo);
	    rc.close();
	    return a;
	  }
  
  /**
   * metoda koja sluzi za intanciranje RestKKlijenta i vracanje podataka o najduljem putu preko neke 
   * drzave za zadani icao
   * @param icao
   * @return
   */
  public List<UdaljenostAerodromDrzava> getNajduljiPutDrzava(String icao){
	    RestKKlijent rc = new RestKKlijent();
	    UdaljenostAerodromDrzava[] json_udaljenosti = null;
		try {
			System.out.println("woo hoo");
			json_udaljenosti = rc.getAerodromNajduljiPutDrzava(icao);
			System.out.println("rca radi");
		} catch (ClientErrorException e) {
			e.printStackTrace();
		}
	    List<UdaljenostAerodromDrzava> udaljenosti;
	    udaljenosti = new ArrayList<>(Arrays.asList(json_udaljenosti));
	    rc.close();
	    
	    return udaljenosti;
	  }

  public List<Aerodrom> getAerodromi() {
    return this.getAerodromi(1, 20);
  }

  public Aerodrom getAerodrom(String icao) {
    RestKKlijent rc = new RestKKlijent();
    Aerodrom k = rc.getAerodrom(icao);
    rc.close();
    return k;
  }

  static class RestKKlijent {

    private final WebTarget webTarget;
    private final Client client;
    private static final String BASE_URI = "http://200.20.0.4:8080/fantunovi_aplikacija_2/api";

    public RestKKlijent() {
      client = ClientBuilder.newClient();
      webTarget = client.target(BASE_URI).path("aerodromi");
    }

    /**
     * metoda u kojoj se odreduje putanja preko koje ce se dohvatiti podaci za udaljenost izmedu 2 aerodroma
     * i vratiti gore u RestKlijentAerodroma i nazad u kontroler
     * @param icaoOd
     * @param icaoDo
     * @return
     */
	public UdaljenostAerodrom[] udaljenost2Aerodroma(String icaoOd, String icaoDo) {
    	WebTarget resource = webTarget;
    	resource = resource.path(icaoOd + "/" + icaoDo);
    	System.out.println(resource.getUri());
        Invocation.Builder request = resource.request(MediaType.APPLICATION_JSON);
        if (request.get(String.class).isEmpty()) {
          return null;
        }
        
        Gson gson = new Gson();
        UdaljenostAerodrom[] udaljenost = null;
		try {
			
			JsonElement element = JsonParser.parseString(request.get(String.class));
			JsonArray array = element.getAsJsonArray();
			udaljenost = new UdaljenostAerodrom[array.size()];

			for (int i = 0; i < array.size(); i++) {
				JsonObject obj = array.get(i).getAsJsonObject();
				String drzava = obj.get("drzava").getAsString();
				float km = obj.get("km").getAsFloat();
				udaljenost[i] = new UdaljenostAerodrom(drzava, km);
			}
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
        return udaljenost;
	}

	public JsonSadrzaj izracunajUdaljenostiDvaAerodroma(String icaoOd, String icaoDo) {
		WebTarget resource = webTarget;
    	resource = resource.path(icaoOd + "/izracunaj/" + icaoDo);
    	System.out.println(resource.getUri());
        Invocation.Builder request = resource.request(MediaType.APPLICATION_JSON);
        if (request.get(String.class).isEmpty()) {
          return null;
        }
        
        Gson gson = new Gson();
        JsonSadrzaj sadrzaj = null;
		try {
			System.out.println("we are here: " + request.get(String.class));
			
			String response = request.get(String.class);
	        String jsonSubstring = response.substring(response.indexOf("OK ") + 3);
	        sadrzaj = new JsonSadrzaj();
	        sadrzaj.setOpis(jsonSubstring);
	        System.out.println("tu sad: " + sadrzaj.toString());
			
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
        return sadrzaj;
	}
	
	public UdaljenostAerodrom[] izracunajUdaljenostiDvaAerodroma1(String icaoOd, String icaoDo) {
		WebTarget resource = webTarget;
    	resource = resource.path(icaoOd + "/udaljenost1/" + icaoDo);
    	System.out.println(resource.getUri());
        Invocation.Builder request = resource.request(MediaType.APPLICATION_JSON);
        if (request.get(String.class).isEmpty()) {
          return null;
        }
        
        Gson gson = new Gson();
        //JsonSadrzaj sadrzaj = null;
        UdaljenostAerodrom[] udaljenosti = null;
        System.out.println("json response: " + request.get(String.class));
        try {
        	JsonElement element = JsonParser.parseString(request.get(String.class));
			JsonArray array = element.getAsJsonArray();
			udaljenosti = new UdaljenostAerodrom[array.size()];

			for (int i = 0; i < array.size(); i++) {
				JsonObject obj = array.get(i).getAsJsonObject();
				String icaoTo = obj.get("icaoTo").getAsString();
				float distTot = obj.get("distTot").getAsFloat();
				udaljenosti[i] = new UdaljenostAerodrom(icaoTo, distTot);
			}
			
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
        
        return udaljenosti;
	}
	
	public UdaljenostAerodromKlasa[] izracunajUdaljenostiDvaAerodroma (String icaoDo, String drzava, float udaljenost) {
		WebTarget resource = webTarget;
    	resource = resource.path(icaoDo + "/udaljenost2");
    	System.out.println(resource.getUri());
        Invocation.Builder request = resource.request(MediaType.APPLICATION_JSON);
        if (request.get(String.class).isEmpty()) {
          return null;
        }
        
        Gson gson = new Gson();
        UdaljenostAerodromKlasa[] udaljenosti = null;
        try {
        	JsonElement element = JsonParser.parseString(request.get(String.class));
			JsonArray array = element.getAsJsonArray();
			udaljenosti = new UdaljenostAerodromKlasa[array.size()];

			for (int i = 0; i < array.size(); i++) {
				JsonObject obj = array.get(i).getAsJsonObject();
				String icao = obj.get("icaoTo").getAsString();
				//String drz = obj.get("drzava").getAsString();
				float max = obj.get("distTot").getAsFloat();
				udaljenosti[i] = new UdaljenostAerodromKlasa(icao, max);
			}
			
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
        
        return udaljenosti;
	}
	
	/**
	 *  metoda u kojoj se odreduje putanja preko koje ce se dohvatiti podaci za najdulji put drzave za icao
     * i vratiti gore u RestKlijentAerodroma i nazad u kontroler
	 * @param icao
	 * @return
	 */
	public UdaljenostAerodromDrzava[] getAerodromNajduljiPutDrzava(String icao) {
    	WebTarget resource = webTarget;
    	resource = resource.path(icao + "/najduljiPutDrzave");
    	System.out.println(resource.getUri());
        Invocation.Builder request = resource.request(MediaType.APPLICATION_JSON);
        System.out.println("rezultat: " + request.get(String.class));
        if (request.get(String.class).isEmpty()) {
          return null;
        }
        
        Gson gson = new Gson();
        UdaljenostAerodromDrzava[] udaljenost = null;
		try {
			JsonElement element = JsonParser.parseString(request.get(String.class));
			JsonArray array = element.getAsJsonArray();
			udaljenost = new UdaljenostAerodromDrzava[array.size()];

			for (int i = 0; i < array.size(); i++) {
				JsonObject obj = array.get(i).getAsJsonObject();
				String icaoFrom = obj.get("icao").getAsString();
				String drzava = obj.get("drzava").getAsString();
				float max = obj.get("max").getAsFloat();
				udaljenost[i] = new UdaljenostAerodromDrzava(icaoFrom, drzava, max);
			}
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
        return udaljenost;
	}

	/**
	 * metoda u kojoj se odreduje putanja preko koje ce se dohvatiti podaci udaljenosti izmedu svih aerodroma i icao-a
     * i vratiti gore u RestKlijentAerodroma i nazad u kontroler
	 * @param icao
	 * @param odBroja
	 * @param broj
	 * @return
	 * @throws ClientErrorException
	 */
	public Udaljenost[] getUdaljenostiSvihAerodroma(String icao, int odBroja, int broj) throws ClientErrorException{
    	WebTarget resource = webTarget;
    	resource = resource.path(icao + "/udaljenosti").queryParam("odBroja", odBroja).queryParam("broj", broj);
        Invocation.Builder request = resource.request(MediaType.APPLICATION_JSON);
        System.out.println("rc test");
        System.out.println("resource: " + resource.getUri());
        if (request.get(String.class).isEmpty()) {
          return null;
        }
        Gson gson = new Gson();
        Udaljenost[] udaljenosti = null;
        System.out.println("json response: " + request.get(String.class));
		try {
			JsonElement element = JsonParser.parseString(request.get(String.class));
			JsonArray array = element.getAsJsonArray();
			udaljenosti = new Udaljenost[array.size()];

			for (int i = 0; i < array.size(); i++) {
				JsonObject obj = array.get(i).getAsJsonObject();
				String icaoTo = obj.get("icaoTo").getAsString();
				float distTot = obj.get("distTot").getAsFloat();
				udaljenosti[i] = new Udaljenost(icaoTo, distTot);
			}
			
			//udaljenost = gson.fromJson(request.get(String.class), Udaljenost[].class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
        return udaljenosti;
	}

	/**
	 *  metoda u kojoj se odreduje putanja preko koje ce se dohvatiti svi aerodromi
     * i vratiti gore u RestKlijentAerodroma i nazad u kontroler
	 * @param odBroja
	 * @param broj
	 * @return
	 * @throws ClientErrorException
	 */
	public Aerodrom[] getAerodromi(int odBroja, int broj) throws ClientErrorException {
      WebTarget resource = webTarget;

      Invocation.Builder request = resource.request(MediaType.APPLICATION_JSON);
      if (request.get(String.class).isEmpty()) {
        return null;
      }
      Gson gson = new Gson();
      Aerodrom[] aerodromi = gson.fromJson(request.get(String.class), Aerodrom[].class);
      
      return aerodromi;
    }

	/**
	 * metoda u kojoj se odreduje putanja preko koje ce se dohvatiti podaci za jedan aerodrom
     * i vratiti gore u RestKlijentAerodroma i nazad u kontroler
	 * @param icao
	 * @return
	 * @throws ClientErrorException
	 */
    public Aerodrom getAerodrom(String icao) throws ClientErrorException {
      WebTarget resource = webTarget;
      resource = resource.path(java.text.MessageFormat.format("{0}", new Object[] {icao}));
      Invocation.Builder request = resource.request(MediaType.APPLICATION_JSON);
      if (request.get(String.class).isEmpty()) {
        return null;
      }
      Gson gson = new Gson();
      Aerodrom aerodrom = gson.fromJson(request.get(String.class), Aerodrom.class);
      return aerodrom;
    } 

    public void close() {
      client.close();
    }
  }



}
