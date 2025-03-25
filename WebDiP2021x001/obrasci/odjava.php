<?php

// session_start();
// session_unset();
// session_destroy();

// header("Location: index.php");

// exit();

$putanja = dirname($_SERVER['REQUEST_URI'], 2);
$direktorij = dirname(getcwd());

//include '../zaglavlje.php';
include '../sesija.class.php';
session_start();
//Sesija::kreirajSesiju();
var_dump($_SESSION);

if(isset($_SESSION["korisnik"]) || isset($_SESSION["uloga"]) ){
            unset($_SESSION["korisnik"]);
            unset($_SESSION["uloga"]);
            header("Location: prijava.php");
            //obrasci/
            exit();
}

if(isset($_GET["submit"])) {
    if (isset($_SESSION["korisnik"]) || isset($_SESSION["uloga"])) {
        unset($_SESSION["korisnik"]);
        unset($_SESSION["uloga"]);
        echo "Uspješna odjava";
        header("Location: prijava.php");
        //header("Location: $putanja/index.php");
    }
}
else{
        echo "Neuspješna odjava";
        header("Location: prijava.php");
        //header("Location: $putanja/index.php");
    
}

Sesija::obrisiSesiju();
//header("Location: $putanja/index.php");
?>