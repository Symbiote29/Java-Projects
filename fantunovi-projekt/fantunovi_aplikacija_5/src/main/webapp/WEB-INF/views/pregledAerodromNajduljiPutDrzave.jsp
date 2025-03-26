<%@page import="org.foi.nwtis.fantunovi.aplikacija_5.podaci.UdaljenostAerodromDrzava"%>
<%@page import="org.foi.nwtis.fantunovi.aplikacija_5.podaci.Udaljenost"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Pregled aerodrom najdulji put drzave</title>
</head>
<body>

<a href="http://webpredmeti:8080/fantunovi_zadaca_2_wa_2/"><%= "Pocetna" %></a><br><br>

<table border="1">
		<thead>
			<tr>
				<th>ICAO</th>
				<th>Drzava</th>
				<th>Najveca udaljenost</th>
			</tr>
		</thead>
		<tbody> 
			<%
			java.util.List<UdaljenostAerodromDrzava> udaljenosti = (java.util.List<UdaljenostAerodromDrzava>) request.getAttribute("najdulji");
			if(udaljenosti != null){
				for (UdaljenostAerodromDrzava udaljenost : udaljenosti) {
			
			
			%>
			<tr>
				<td><%= udaljenost.icaoFrom()%></td>
				<td><%= udaljenost.drzava() %></td>
				<td><%= udaljenost.max() %></td>
			</tr>
			<%
				}
			}
			%>
		</tbody>
	</table>
</body>
</html>