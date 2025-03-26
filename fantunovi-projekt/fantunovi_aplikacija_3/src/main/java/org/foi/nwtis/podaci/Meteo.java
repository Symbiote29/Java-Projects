package org.foi.nwtis.podaci;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class Meteo {

	@Getter
	@Setter
	private String icao;
	
	@Getter
	@Setter
	private String naziv;
	
	@Getter
	@Setter
	private String drzava;
	
	private Lokacija lokacija;
	
	public Meteo() {
		
	}
}
