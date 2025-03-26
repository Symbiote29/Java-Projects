package org.foi.nwtis.fantunovi.aplikacija_2.slusaci;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.foi.nwtis.Konfiguracija;
import org.foi.nwtis.KonfiguracijaApstraktna;
import org.foi.nwtis.NeispravnaKonfiguracija;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class Aplikacija_2_slusac implements ServletContextListener{
	public static ServletContext servletContext;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		servletContext = sce.getServletContext();
		String datoteka = servletContext.getRealPath(servletContext.getInitParameter("konfiguracija"));
		Konfiguracija konf = null;
		try {
			konf = KonfiguracijaApstraktna.preuzmiKonfiguraciju(datoteka);
		}catch(NeispravnaKonfiguracija e) {
			Logger.getGlobal().log(Level.SEVERE, "Greska");
			return;
		}
		servletContext.setAttribute("konfig", konf);
		ServletContextListener.super.contextInitialized(sce);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		ServletContextListener.super.contextDestroyed(sce);
	}

}
