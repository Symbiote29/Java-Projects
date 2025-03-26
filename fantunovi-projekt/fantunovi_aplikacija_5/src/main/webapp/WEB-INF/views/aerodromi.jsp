<%@page import="org.foi.nwtis.Konfiguracija"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Pregled aerodroma</title>
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
			java.util.List<org.foi.nwtis.podaci.Aerodrom> aerodromi = (java.util.List<org.foi.nwtis.podaci.Aerodrom>) request.getAttribute("aerodromi");
			for (org.foi.nwtis.podaci.Aerodrom aerodrom : aerodromi) {
			%>
			<tr>
				<td><a href="icao/?icao=<%= aerodrom.getIcao() %>"><%= aerodrom.getIcao() %></a></td>
				<td><%= aerodrom.getNaziv() %></td>
				<td><%= aerodrom.getDrzava() %></td>
			</tr>
			<%
				}
			%>
		</tbody>
	</table>
</body>
</html>