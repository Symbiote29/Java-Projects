package org.foi.nwtis.fantunovi.aplikacija_5.slusaci;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.foi.nwtis.Konfiguracija;
import org.foi.nwtis.KonfiguracijaApstraktna;
import org.foi.nwtis.NeispravnaKonfiguracija;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

@jakarta.servlet.annotation.WebListener
public class WebListener implements ServletContextListener{
	public static ServletContext servletContext;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		servletContext = sce.getServletContext();
		String datoteka = servletContext.getRealPath(servletContext.getInitParameter("konfiguracija2"));
		Konfiguracija konf = null;
		try {
			konf = KonfiguracijaApstraktna.preuzmiKonfiguraciju(datoteka);
		}catch(NeispravnaKonfiguracija e) {
			Logger.getGlobal().log(Level.SEVERE, "Greska");
			return;
		}
		servletContext.setAttribute("konfig2", konf);
		ServletContextListener.super.contextInitialized(sce);
		System.out.println("Konfiguracija uspješno učitana!");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		ServletContextListener.super.contextDestroyed(sce);
	}

}
