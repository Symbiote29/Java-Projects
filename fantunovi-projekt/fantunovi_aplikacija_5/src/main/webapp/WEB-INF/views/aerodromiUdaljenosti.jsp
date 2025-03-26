<%@page import="org.foi.nwtis.fantunovi.aplikacija_5.podaci.UdaljenostAerodrom"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>aerodromiUdaljenosti</title>
</head>
<body>

<table border="1">
		<thead>
			<tr>
				<th>Drzava</th>
				<th>Km</th>
			</tr>
		</thead>
		<tbody> 
			<%
			java.util.List<UdaljenostAerodrom> aerodromi = (java.util.List<UdaljenostAerodrom>) request.getAttribute("udaljenosti2Aerodroma");
			for (UdaljenostAerodrom aerodrom : aerodromi) {
			%>
			<tr>
				<td><%= aerodrom.drzava() %></td>
				<td><%= aerodrom.km() %></td>
			</tr>
			<%
				}
			%>
		</tbody>
	</table>

</body>
</html>