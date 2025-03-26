<%@page import="org.foi.nwtis.fantunovi.aplikacija_5.podaci.Dnevnik"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dnevnik</title>
</head>
<body>

<%

Dnevnik[] sadrzaj = (Dnevnik[]) request.getAttribute("dnevnik");

%>

<%

if(sadrzaj != null){
	for(Dnevnik d : sadrzaj){
		d.getZahtjev();
		d.getPut();
		d.getVrstaAplikacije();
		d.getVrijeme();
	}
}
else{
	System.out.println("No data to display");
}

%>

</body>
</html>