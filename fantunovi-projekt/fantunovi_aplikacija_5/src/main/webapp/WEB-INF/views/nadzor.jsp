<%@page import="org.foi.nwtis.fantunovi.aplikacija_5.podaci.JsonSadrzaj"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Nadzor komande</title>
</head>
<body>

<%

JsonSadrzaj sadrzaj = (JsonSadrzaj) request.getAttribute("status");

%>

<form action="" method="POST">
	<input type="hidden" id="komanda" name="komanda" value="STATUS">
	<button type="submit">Status</button>
</form>

<form action="" method="POST">
	<input type="hidden" id="komanda" name="komanda" value="KRAJ">
	<button type="submit">Kraj</button>
</form>

<form action="" method="POST">
	<input type="hidden" id="komanda" name="komanda" value="INIT">
	<button type="submit">Init</button>
</form>

<form action="" method="POST">
	<input type="hidden" id="komanda" name="komanda" value="PAUZA">
	<button type="submit">Pauza</button>
</form>

<form action="" method="POST">
	<input type="hidden" id="komanda" name="komanda" value="DA">
	<button type="submit">Info da</button>
</form>

<form action="" method="POST">
	<input type="hidden" id="komanda" name="komanda" value="NE">
	<button type="submit">Info ne</button>
</form>

<%

if(sadrzaj != null){

%>

<%=sadrzaj.getOpis() %> 
<%} %>

</body>
</html>