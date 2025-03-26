package org.foi.nwtis.fantunovi.aplikacija_3.slusaci;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.foi.nwtis.Konfiguracija;
import org.foi.nwtis.KonfiguracijaApstraktna;
import org.foi.nwtis.NeispravnaKonfiguracija;
import org.foi.nwtis.fantunovi.aplikacija_3.dretve.SakupljacLetovaAviona;
import org.foi.nwtis.fantunovi.aplikacija_3.zrna.JmsPosiljatelj;

import jakarta.annotation.Resource;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class Slusac implements ServletContextListener{
	public static ServletContext servletContext;
	
	@Inject
	JmsPosiljatelj posiljatelj;
	
	@Resource(lookup = "java:app/jdbc/nwtis_bp")
	javax.sql.DataSource ds;
	
	private SakupljacLetovaAviona sakupljac;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		servletContext = sce.getServletContext();
		String datoteka = servletContext.getRealPath(servletContext.getInitParameter("konfiguracija"));
		System.out.println("datoteka " + datoteka);
		Konfiguracija konf = null;
		try {
			konf = KonfiguracijaApstraktna.preuzmiKonfiguraciju(datoteka);
		}catch(NeispravnaKonfiguracija e) {
			Logger.getGlobal().log(Level.SEVERE, "Greska");
			return;
		}
		servletContext.setAttribute("konfig", konf);
		ServletContextListener.super.contextInitialized(sce);
		System.out.println("Konfiguracija uspješno učitana!");
		
		sakupljac = new SakupljacLetovaAviona(posiljatelj, konf, ds);
		sakupljac.start();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		sakupljac.interrupt();
		ServletContextListener.super.contextDestroyed(sce);
	}
}
