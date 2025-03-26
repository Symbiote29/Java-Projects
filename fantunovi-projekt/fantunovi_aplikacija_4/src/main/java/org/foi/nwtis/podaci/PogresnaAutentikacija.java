package org.foi.nwtis.podaci;

import java.io.Serializable;

public class PogresnaAutentikacija extends Exception implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public PogresnaAutentikacija() {
	    super("Autentikacija nije uspješna!");
	  }
	
}
