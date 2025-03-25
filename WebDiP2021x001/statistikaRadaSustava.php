<?php

include './baza.class.php';
include './sesija.class.php';

session_start();

$potrebnaUloga = 4;

if(!isset($_SESSION["uloga"])){
    Sesija::kreirajKorisnika("gost");
}

if($_SESSION["uloga"] < $potrebnaUloga){
    header("Location: ./obrasci/prijava.php");
}

$veza = new Baza();
$veza->spojiDB();

$sql = "SELECT * FROM dz4_korisnik WHERE datumRegistracije >= '2022-01-01 12:12:12'";
$res = $veza->selectDB($sql);

?>

<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Statistika novo registriranih korisnika</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="./css/style.css" rel="stylesheet">
    </head>
    <body>
    <header class="statistikaRadaSustavaH">
        <a href="#proba2"><h1 class="o1">Statistika novo registriranih korisnika</h1></a>
            <a href="../index.html"><img class = "img-logo" src="./materijali/logo.png" alt="logo"></a>
            <a href="#proba1"><img class = "img-izbornik" id="btnLeft" src="./materijali/izbornik.png" alt="logo"></a>
            <div class="izbornik" id="proba2">
                <ul class="linkovi" id="proba1">
                    <?php
                    if(isset($_SESSION["uloga"]) && $_SESSION["uloga"] == 4){
                        echo '<a href="./index.php">Pocetna</a>'."<br>";
                        echo '<a href="./obrasci/registracija.php">Registracija</a>'."<br>";
                        echo '<a href="./pivnice.php">Pivnice</a>'."<br>";
                        echo '<a href="./zemljePodrijetla.php">Zemlje podrijetla</a>'."<br>";
                        // echo '<a href="./pive.php">Pive</a>'."<br>";
                        // echo '<a href="./popispiva.php">Popis piva</a>'."<br>";
                        echo '<a href="./pregledDnevnika.php">Pregled dnevnika</a>'."<br>";
                        echo '<a href="./blokiranjekorisnika.php">Blokiranje korisnika</a>'."<br>";
                        echo '<a href="./statistikaRadaSustava.php">Statistika rada sustava</a>'."<br>";
                        echo '<a href="./o_autoru.html">O autoru</a>'."<br>";
                        echo '<a href="./dokumentacija.html">Dokumentacija</a>'."<br>";
                        
                    }
                    if(isset($_SESSION["uloga"]) && $_SESSION["uloga"] == 3){
                        echo '<a href="./index.php">Pocetna</a>'."<br>";
                        echo '<a href="./obrasci/registracija.php">Registracija</a>'."<br>";
                        echo '<a href="./pive.php">Pive</a>'."<br>";
                        echo '<a href="./popispiva.php">Popis piva</a>'."<br>";
                        echo '<a href="./sveNarudzbeKorisnika.php">Sve narudzbe korisnika</a>'."<br>";
                        echo '<a href="./statistikaM.php">Statistika narucenih piva</a>'."<br>";
                        echo '<a href="./o_autoru.html">O autoru</a>'."<br>";
                        echo '<a href="./dokumentacija.html">Dokumentacija</a>'."<br>";
                    }
                    if(isset($_SESSION["uloga"]) && $_SESSION["uloga"] == 2){
                        echo '<a href="./index.php">Pocetna</a>'."<br>";
                        echo '<a href="./obrasci/registracija.php">Registracija</a>'."<br>";
                        echo '<a href="./kosarica.php">Kosarica</a>'."<br>";
                        echo '<a href="./narudzba.php">Narudzba</a>'."<br>";
                        echo '<a href="./statistikaR.php">Statistika broja novosti</a>'."<br>";
                        echo '<a href="./o_autoru.html">O autoru</a>'."<br>";
                        echo '<a href="./dokumentacija.html">Dokumentacija</a>'."<br>";
                    }
                    if(isset($_SESSION["uloga"]) && $_SESSION["uloga"] == 1){
                        echo '<a href="./index.php">Pocetna</a>'."<br>";
                        echo '<a href="./obrasci/registracija.php">Registracija</a>'."<br>";
                        echo '<a href="./o_autoru.html">O autoru</a>'."<br>";
                        echo '<a href="./dokumentacija.html">Dokumentacija</a>'."<br>";
                    }
                    if ($_SESSION["korisnik"] !="gost") {
                        echo '<a href="./obrasci/odjava.php">Odjava</a>'."<br>";
                    }
                    ?>
                </ul>
            </div>
    </header>
        <table class="statistikaRadaSustava" border="2">
            <tr>
                <th>ID</th>
                <th>Ime</th>
                <th>Prezime</th>
                <th>Godina rodenja</th>
                <th>Email</th>
                <th>Korisnicko_ime</th>
                <th>Lozinka</th>
                <th>Potvrda lozinke</th>
                <th>Uloga korisnika</th>
                <th>ID kolacica</th>
                <th>Broj pokusaja</th>
                <th>Blokiran</th>
                <th>OTP</th>
                <th>Verify</th>
                <th>Datum registracije</th>
            </tr>
            <?php
            if(mysqli_num_rows($res) > 0){
                while($podaci = mysqli_fetch_assoc($res)){
                    echo '
                        <tr>
                            <td>'.$podaci['idKorisnik'].'</td>
                            <td>'.$podaci['Ime'].'</td>
                            <td>'.$podaci['Prezime'].'</td>
                            <td>'.$podaci['Godina_rodenja'].'</td>
                            <td>'.$podaci['Email'].'</td>
                            <td>'.$podaci['Korisnicko_ime'].'</td>
                            <td>'.$podaci['Lozinka'].'</td>
                            <td>'.$podaci['PotvrdaLozinke'].'</td>
                            <td>'.$podaci['UlogaKorisnika'].'</td>
                            <td>'.$podaci['Kolacici_idKolacici'].'</td>
                            <td>'.$podaci['BrojPokusaja'].'</td>
                            <td>'.$podaci['Blokiran'].'</td>
                            <td>'.$podaci['otp'].'</td>
                            <td>'.$podaci['verify'].'</td>
                            <td>'.$podaci['datumRegistracije'].'</td>
                        </tr>
                    ';
                }
            }
            ?>
        </table>
    </body>
</html>