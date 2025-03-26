<%@page import="org.foi.nwtis.rest.podaci.LetAviona"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Polasci letova</title>
</head>
<body>

<a href="http://webpredmeti:8080/fantunovi_zadaca_2_wa_2/"><%= "Pocetna" %></a><br><br>

<table border="1">
		<thead>
			<tr style="font-family: Arial; font-size: 15px;">
				<th>ICAO24</th>
				<th>FIRST SEEN</th>
				<th>EST DEPARTURE AIRPORT</th>
				<th>LAST SEEN</th>
				<th>EST ARRIVAL AIRPORT</th>
				<th>CALL SIGN</th>
				<th>EST DEPARTURE AIRPORT HORIZ DISTANCE</th>
				<th>EST DEPARTURE AIRPORT VERT DISTANCE</th>
				<th>EST ARRIVAL AIRPORT HORIZ DISTANCE</th>
				<th>EST ARRIVAL AIRPORT VERT DISTANCE</th>
				<th>DEPARTURE AIRPORT CANDIDATES COUNT</th>
				<th>ARRIVAL AIRPORT CANDIDATES COUNT</th>
				<th>SPREMI</th>
			</tr>
		</thead>
		<tbody> 
			<%
			java.util.List<LetAviona> letovi = (java.util.List<LetAviona>) request.getAttribute("letovi");
			for (LetAviona l : letovi) {
			%>
			<tr>
				<td><%= l.getIcao24() %></td>
				<td><%= l.getFirstSeen() %></td>
				<td><%= l.getEstDepartureAirport() %></td>
				<td><%= l.getLastSeen() %></td>
				<td><%= l.getEstArrivalAirport() %></td>
				<td><%= l.getCallsign() %></td>
				<td><%= l.getEstDepartureAirportHorizDistance() %></td>
				<td><%= l.getEstDepartureAirportVertDistance() %></td>
				<td><%= l.getEstArrivalAirportHorizDistance() %></td>
				<td><%= l.getEstArrivalAirportVertDistance() %></td>
				<td><%= l.getDepartureAirportCandidatesCount() %></td>
				<td><%= l.getArrivalAirportCandidatesCount() %></td>
				<td>
				<form method="POST" action="spremi">
					<input type="hidden" name="icao24" value="<%= l.getIcao24() %>">
					<button type="submit">Spremi</button>
				</form>
			</td>
			</tr>
			<%
				}
			%>
		</tbody>
	</table>

</body>
</html>