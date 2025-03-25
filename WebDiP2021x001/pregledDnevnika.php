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

$sql = "SELECT * FROM dnevnik";
$res = $veza->selectDB($sql);

if(isset($_POST['pregledDatuma'])){
    $od = $_POST['od'];
    $do = $_POST['do'];

    $sql2 = "SELECT * FROM dnevnik WHERE datum_radnje BETWEEN '$od' AND '$do'";
    $res = $veza->selectDB($sql); 

    exit(json_encode("success"));
}

?>

<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Pregled dnevnika</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="./css/style.css" rel="stylesheet">
    </head>
    <body>
    <header class="dnevnikRada">
            <a href="#proba2"><h1 class="o1">Pregled dnevnika</h1></a>
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

        <?php

        if(isset($_POST['searchDate'])){
            $o = $_POST['od'];
            $d = $_POST['do'];
            $sql2 = "SELECT * FROM dnevnik WHERE datum_radnje BETWEEN '$o' AND '$d'";
            $res = $veza->selectDB($sql2);
        }

        elseif(isset($_POST['searchUser'])){
            $usr = $_POST['usr'];
            $sql3 = "SELECT * FROM dnevnik WHERE korisnik_id = '$usr'";
            $res = $veza->selectDB($sql3);
        }
        
        elseif(isset($_POST['searchAction'])){
            $act = $_POST['action'];
            $sql4 = "SELECT * FROM dnevnik WHERE tip_radnje = '$act'";
            $res = $veza->selectDB($sql4);
        }
        elseif(isset($_POST['fltr'])){
            $sql5 = "SELECT * FROM dnevnik";
            $res = $veza->selectDB($sql5);
        }
        ?>

        <form method="POST">
            <table class="dnevnik" border="2">
                <tr>
                    <th>ID dnevnika</th>
                    <th>ID korisnika</th>
                    <th>Tip radnje</th>
                    <th>Upit</th>
                    <th>Datum radnje</th>
                </tr>
                <?php
                if(mysqli_num_rows($res) > 0){
                    while($podaci = mysqli_fetch_assoc($res)){
                        echo '
                            <tr>
                                <td>'.$podaci['dnevnik_id'].'</td>
                                <td>'.$podaci['korisnik_id'].'</td>
                                <td>'.$podaci['tip_radnje'].'</td>
                                <td>'.$podaci['upit'].'</td>
                                <td>'.$podaci['datum_radnje'].'</td>
                            </tr>
                        ';
                    }
                }
                ?>
            </table>

            <form method="POST">
                <div class="dateSearch">
                    <label>Od datum</label>
                    <input type="date" name="od">

                    <label>Do datum</label>
                    <input type="date" name="do">

                    <button id="searchDate" name="searchDate">Pretrazi radnje po datumu</button>
                </div>
            </form>
            
            <form method="POST">
                <div class="userSearch">
                    <label class="usrCls">Unesite ID korisnika</label>
                    <input type="text" name="usr">

                    <button id="searchUser" name="searchUser">Pretrazi radnje po ID-u korisnika</button>
                </div>
            </form>
                
            <form method="POST">
                <div  class="actionSearch">
                    <label class="actionCls">Unesite tip radnje</label>
                    <input type="text" name="action">

                    <button id="searchAction" name="searchAction">Pretrazi po tipu radnje</button>
                </div>
            </form>
            <form method="POST">
                <button id="fltr" name="fltr">Resetiraj filtere</button>
            </form>
        </form>
    </body>
</html>