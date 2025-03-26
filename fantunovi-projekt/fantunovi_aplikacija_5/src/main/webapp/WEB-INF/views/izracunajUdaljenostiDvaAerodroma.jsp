<%@page import="org.foi.nwtis.fantunovi.aplikacija_5.podaci.JsonSadrzaj"%>
<%@page import="org.foi.nwtis.fantunovi.aplikacija_5.podaci.UdaljenostAerodrom"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Izracunaj udaljenost izmedu 2 aerodroma</title>
</head>
<body>


<%

JsonSadrzaj sadrzaj = (JsonSadrzaj) request.getAttribute("izracunajUdaljenostiDvaAerodroma");

System.out.println("vrijednost: " + sadrzaj);

%>

<%
  if (sadrzaj != null) {
%>
    <h2><%= sadrzaj.getOpis() %></h2>
<%
  } else {
%>
    <h2>No data available</h2->
<%
  }
%>

</body>
</html>