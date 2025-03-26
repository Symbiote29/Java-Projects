package org.foi.uzdiz.singleton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.foi.uzdiz.factorypruga.Pruga;

public class TvrtkaZeljeznickogPrometa {
	
	private static TvrtkaZeljeznickogPrometa instanca;
	
	private List<Pruga> svePruge;
	
	private TvrtkaZeljeznickogPrometa() {
        svePruge = new ArrayList<>();
    }
	
	public static synchronized TvrtkaZeljeznickogPrometa getInstanca() {
        if (instanca == null) {
            instanca = new TvrtkaZeljeznickogPrometa();
        }
        return instanca;
    }
	
	
}
