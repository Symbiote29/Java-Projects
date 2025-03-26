<%@page import="org.foi.nwtis.fantunovi.aplikacija_5.podaci.Udaljenost"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Pregled udaljenosti svih aerodroma</title>
</head>
<body>

<a href="http://webpredmeti:8080/fantunovi_zadaca_2_wa_2/"><%= "Pocetna" %></a><br><br>

<table border="1">
		<thead>
			<tr>
				<th>ICAO</th>
				<th>Udaljenost</th>
			</tr>
		</thead>
		<tbody> 
			<%
			java.util.List<Udaljenost> udaljenosti = (java.util.List<Udaljenost>) request.getAttribute("udaljenosti");
			if(udaljenosti != null){
			for (Udaljenost udaljenost : udaljenosti) {
			%>
			<tr>
				<td><%= udaljenost.icaoTo() %></td>
				<td><%= udaljenost.distTot() %></td>
			</tr>
			<%
				}
			}
			%>
		</tbody>
	</table>
</body>
</html>