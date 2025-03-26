<%@page import="org.foi.nwtis.fantunovi.aplikacija_5.podaci.UdaljenostAerodromKlasa"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Izracunaj udaljenost2</title>
</head>
<body>

<%
java.util.List<UdaljenostAerodromKlasa> aerodromi = (java.util.List<UdaljenostAerodromKlasa>) request.getAttribute("izracunajUdaljenostDva");

if (aerodromi == null || aerodromi.isEmpty()) {
%>
    <p>No data in the array.</p>
<% 
} else {
%>
    <table border="1">
        <thead>
            <tr>
                <th>Icao</th>
                <th>Km</th>
            </tr>
        </thead>
        <tbody> 
            <%
            for (UdaljenostAerodromKlasa aerodrom : aerodromi) {
            %>
            <tr>
                <td><%= aerodrom.getIcao() %></td>
                <td><%= aerodrom.getKm() %></td>
            </tr>
            <%
                }
            %>
        </tbody>
    </table>
<% 
}
%>

</body>
</html>