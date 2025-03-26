<%@page import="org.foi.nwtis.fantunovi.aplikacija_5.podaci.UdaljenostAerodrom"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Izracunaj udaljenost1 dva aerodroma</title>
</head>
<body>

<%
java.util.List<UdaljenostAerodrom> aerodromi = (java.util.List<UdaljenostAerodrom>) request.getAttribute("izracunajUdaljenostiJedanDvaAerodroma");

if (aerodromi == null || aerodromi.isEmpty()) {
%>
    <p>No data in the array.</p>
<% 
} else {
%>
    <table border="1">
        <thead>
            <tr>
                <th>Drzava</th>
                <th>Km</th>
            </tr>
        </thead>
        <tbody> 
            <%
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
<% 
}
%>

</body>
</html>