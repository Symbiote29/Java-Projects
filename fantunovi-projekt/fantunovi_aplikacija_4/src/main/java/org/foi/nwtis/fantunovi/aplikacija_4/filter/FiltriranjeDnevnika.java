package org.foi.nwtis.fantunovi.aplikacija_4.filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.annotation.Resource;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

@WebFilter("/*")
public class FiltriranjeDnevnika implements Filter{
	
	@Resource(lookup = "java:app/jdbc/nwtis_bp")
	javax.sql.DataSource ds;
	private FilterConfig filterConfig = null;
	
	public void init(FilterConfig filterConfig) throws ServletException {
	this.filterConfig = filterConfig;
	}
	
	public void destroy() {
	this.filterConfig = null;
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpReq = (HttpServletRequest) request;
		
		String zahtjev = httpReq.getMethod();
		String put = httpReq.getRequestURI();
		
		String query = "INSERT INTO DNEVNIK (ZAHTJEV, PUT, VRSTAAPLIKACIJE, VRIJEME) VALUES (?, ?, ?, CURRENT_TIMESTAMP)";
		
		System.out.println("zahtjev " + zahtjev);
		System.out.println("put " + put);
		
		PreparedStatement stmt = null;
	    try (Connection con = ds.getConnection();) {
	      stmt = con.prepareStatement(query);
	      stmt.setString(1, zahtjev);
	      stmt.setString(2, put);
	      stmt.setString(3, "AP4");
	      stmt.executeUpdate();
	    } catch (SQLException e) {
	      e.printStackTrace();
	    } finally {
	      try {
	        if (stmt != null && !stmt.isClosed())
	          stmt.close();
	      } catch (SQLException e) {
	        e.printStackTrace();
	      }
	    }
	    chain.doFilter(request, response);
	}
}
