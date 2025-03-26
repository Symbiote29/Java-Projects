<%@page import="org.foi.nwtis.Konfiguracija"%>
<%@page import="org.foi.nwtis.fantunovi.aplikacija_5.slusaci.WebListener"%>
<%@page import="jakarta.servlet.ServletContext"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.util.Properties"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Aerodrom</title>
</head>
<body>

<table border="1">
		<thead>
			<tr>
				<th>ICAO</th>
				<th>Naziv</th>
				<th>Drzava</th>
			</tr>
		</thead>
		<tbody> 
		<%
			org.foi.nwtis.podaci.Aerodrom aerodrom = (org.foi.nwtis.podaci.Aerodrom) request.getAttribute("aerodrom");
			%>
			<tr>
				<td><%= aerodrom.getIcao() %></td>
				<td><%= aerodrom.getNaziv() %></td>
				<td><%= aerodrom.getDrzava() %></td>
			</tr>
			<%
			%>
		</tbody>
	</table>
	
	<a href="${pageContext.servletContext.contextPath}/mvc/aerodromi/udaljenostiSvihAerodroma?icao=<%= aerodrom.getIcao() %>"><%= "Pregled svih udaljenosti" %></a>
	<a href="${pageContext.servletContext.contextPath}/mvc/aerodromi/<%= aerodrom.getIcao() %>/najduljiPutDrzave/"><%= "Najdulji put drzave" %></a>
</body>
</html>