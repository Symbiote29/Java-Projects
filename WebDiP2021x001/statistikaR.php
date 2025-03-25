<?php

include './baza.class.php';
include './sesija.class.php';

session_start();

$potrebnaUloga = 2;


if(!isset($_SESSION["uloga"])){
    Sesija::kreirajKorisnika("gost");
}

if($_SESSION["uloga"] < $potrebnaUloga){
    header("Location: ./obrasci/prijava.php");
}


$veza = new Baza();
$veza->spojiDB();


// OVO ISPOD TRIAN FIXAT

$user = $_SESSION['user'];

$sql2 = "SELECT * FROM dz4_korisnik WHERE Korisnicko_ime = '$user'";
$res2 = $veza->selectDB($sql2);

$row = mysqli_fetch_array($res2);

$userID = $row[0];

$sql = "SELECT * FROM narudzba n WHERE n.idkorisnik = '$userID' ORDER BY n.narudzba_stavke ASC";
$res = $veza->selectDB($sql);

$sql2 = "SELECT narudzba_stavke, COUNT(*) AS c FROM narudzba WHERE idkorisnik = '$userID' GROUP BY narudzba_stavke ASC";
$res2 = $veza->selectDB($sql2);

$sql3 = "SELECT * FROM novosti GROUP BY nazivPiva ASC";
$res3 = $veza->selectDB($sql3);
?>

<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Vlastita statistika</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="./css/style.css" rel="stylesheet">
    </head>
    <body>
        <header class="statistikaR">
            <a href="#proba2"><h1 class="o1">Vlastita statistika</h1></a>
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
        <table class="statistikaNarudzbi" border="2">
            <tr>
                <th>ID</th>
                <th>Stavke narudzbe</th>
                <th>Status narudzbe</th>
                <th>Status racuna</th>
                <th>Datum narudzbe</th>
                <th>ID racuna</th>
                <th>ID korisnika</th>
            </tr>
            <?php
            if(mysqli_num_rows($res) > 0){
                while($podaci = mysqli_fetch_assoc($res)){
                    echo '
                        <tr>
                            <td>'.$podaci['idnarudzba'].'</td>
                            <td>'.$podaci['narudzba_stavke'].'</td>
                            <td>'.$podaci['narudzba_status'].'</td>
                            <td>'.$podaci['racunStatus'].'</td>
                            <td>'.$podaci['narudzba_datum'].'</td>
                            <td>'.$podaci['racun_idracun'].'</td>
                            <td>'.$podaci['idkorisnik'].'</td>
                        </tr>
                    ';
                }
            }
            ?>
        </table>
        <table class="statistikaNarudzbi2" border="2">
        <tr>
                <th>Naziv piva</th>
                <th>Broj narudzbi</th>
            </tr>
            <?php
            if(mysqli_num_rows($res2) > 0){
                while($podaci = mysqli_fetch_assoc($res2)){
                    echo '
                        <tr>
                            <td>'.$podaci['narudzba_stavke'].'</td>
                            <td>'.$podaci['c'].'</td>
                        </tr>
                    ';
                }
            }
            ?>
        </table>
        <table class="statistikaNarudzbi3" border="2">
        <tr>
                <th>ID novosti</th>
                <th>Naziv piva</th>
                <th>Novost</th>
                <th>Datum novosti</th>
            </tr>
            <?php
            if(mysqli_num_rows($res3) > 0){
                while($podaci = mysqli_fetch_assoc($res3)){
                    echo '
                        <tr>
                            <td>'.$podaci['novosti_id'].'</td>
                            <td>'.$podaci['nazivPiva'].'</td>
                            <td>'.$podaci['novost'].'</td>
                            <td>'.$podaci['datumNovosti'].'</td>
                        </tr>
                    ';
                }
            }
            ?>
        </table>
        <button id="gnrtBtn" name="gnrtBtn" onclick="window.print()">Generate pdf</button>
    </body>
</html>