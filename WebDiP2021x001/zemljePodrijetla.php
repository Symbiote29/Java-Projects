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

$sql = "SELECT * FROM zemlja_podrijetla_piva";
$res = $veza->selectDB($sql);


if(isset($_POST['dodaj'])){

    $drzava = $_POST['novaDrzava'];
    $sql2 = "SELECT * FROM zemlja_podrijetla_piva WHERE nazivDrzave = '$drzava'";
    $res2 = $veza->selectDB($sql2);

    if(mysqli_num_rows($res2) > 0){
        exit(json_encode("ex"));
        
    }
    // elseif(mysqli_num_rows($res2) == 0 && empty($_POST['novaDrzava'])){
    //     exit(json_encode("error"));
    // }
    elseif(empty($_POST['novaDrzava'])){
        exit(json_encode("error"));
    }
    else{
        $sql = "INSERT INTO zemlja_podrijetla_piva (nazivDrzave) VALUES ('$drzava')";
        $veza->selectDB($sql);
        exit(json_encode("success"));
    }
}

if(isset($_POST['azuriraj'])){
    $ponovoDrzava = $_POST['ponovoDrz'];
    $azuriranaDrzava = $_POST['azuriranaDrz'];
    $sql3 = "SELECT * FROM zemlja_podrijetla_piva WHERE nazivDrzave = '$ponovoDrzava'";
    $res3 = $veza->selectDB($sql3);

    if(empty($_POST['ponovoDrz']) || empty($_POST['azuriranaDrz'])){
        exit(json_encode("error"));
    }
    elseif(mysqli_num_rows($res3) == 0){
        exit(json_encode("ex"));
    }
    else{
        $sql = "UPDATE zemlja_podrijetla_piva SET nazivDrzave = '$azuriranaDrzava' WHERE nazivDrzave = '$ponovoDrzava'";
        $veza->selectDB($sql);
        exit(json_encode("success"));
    }
}

if(isset($_POST['brisi'])){
    $drzavaZaBrisanje = $_POST['brisiDrzavu'];

    $sql4 = "SELECT * FROM zemlja_podrijetla_piva WHERE nazivDrzave = '$drzavaZaBrisanje'";
    $res4 = $veza->selectDB($sql4);

    if(empty($drzavaZaBrisanje)){
        exit(json_encode("error"));
    }
    elseif(mysqli_num_rows($res4) == 0){
        exit(json_encode("ex"));
    }
    else{
        $sql = "DELETE FROM zemlja_podrijetla_piva WHERE nazivDrzave = '$drzavaZaBrisanje'";
        $veza->selectDB($sql);
        exit(json_encode("success"));
    }
}

?>

<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Zemlje podrijetla</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="./css/style.css" rel="stylesheet">
    </head>
    <body>
        <header class="zemljeH">
            <a href="#proba2"><h1 class="o1">Zemlje podrijetla</h1></a>
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
        <table class="tablicaDrzava" border="2">
            <tr>
                <th>ID</th>
                <th>Zemlja</th>
            </tr>
            <?php
            if(mysqli_num_rows($res) > 0){
                while($podaci = mysqli_fetch_assoc($res)){
                    echo '
                        <tr>
                            <td>'.$podaci['idzemlja_podrijetla_piva'].'</td>
                            <td>'.$podaci['nazivDrzave'].'</td>
                        </tr>
                    ';
                }
            }
            ?>
        </table>
        <form action="./zemljePodrijetla.php" method="POST">
            <button id="dodajDrzavu" class="dd" type="submit">Dodaj Drzavu</button>
            <label for="novaDrzava" class="imeDrzave">Unesite ime nove drzave</label>
            <input type="text" id="novaDrzava">
        </form>
        <form action="./zemljePodrijetla.php">
            <button id="azurirajDrzavu" class="aa" type="submit">Azuriraj Drzavu</button>
            <div class="ponovoD">
                <label for="ponovoDrz" class="ponovoImeDrz">Unesite ime drzave koju zelite promijeniti</label>
                <input type="text" id="ponovoDrz">
            </div>
            <div class="azuriranaD">
                <label for="azuriranaDrz" class="azuriranoImeDrz">Unesite ime nove drzave</label>
                <input type="text" id="azuriranaDrz" class="azurD">
            </div>
        </form>
        <form action="./zemljePodrijetla.php">
            <button id="obrisiDrz" class="od" type="submit">Obrisi Drzavu</button>
            <div class="brDrz">
                <label for="brisiDrzavu" class="brisiDrz">Unesite ime drzave koju zelite obrisati</label>
                <input type="text" id="brisiDrzavu" class="brisiD">
            </div>
        </form>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
        <script type="text/javascript">
            $(document).ready(function(){
                $("#dodajDrzavu").click(function(){
                    var novaDrzava = $("#novaDrzava").val();
                    $.ajax({
                        method: "POST",
                        url: "zemljePodrijetla.php",
                        data:{
                            dodaj: 1,
                            novaDrzava: novaDrzava
                        },
                        dataType: "JSON",
                        success: function(response){
                            if(response == "success"){
                                alert("Piva je uspjesno dodana");
                            }
                            else if(response == "error"){
                                alert("Niste unijeli drzavu");
                            }
                            else{
                                alert("Ta drzava vec postoji");
                            }
                        }
                    })
                })
                $("#azurirajDrzavu").click(function(){
                    var ponovoDrz = $("#ponovoDrz").val();
                    var azuriranaDrz = $("#azuriranaDrz").val();
                    $.ajax({
                        method: "POST",
                        url: "zemljePodrijetla.php",
                        data:{
                            azuriraj: 1,
                            ponovoDrz: ponovoDrz,
                            azuriranaDrz: azuriranaDrz
                        },
                        dataType: "JSON",
                        success: function(response){
                            if(response == "success"){
                                alert("Piva je uspjesno azurirana");
                            }
                            else if(response == "error"){
                                alert("Niste popunili sva polja");
                            }
                            else{
                                alert("Ne postoji piva koju zelite azurirati");
                            }
                        }
                    })
                })
                $("#obrisiDrz").click(function(){
                    var brisiDrzavu = $("#brisiDrzavu").val();
                    $.ajax({
                        method: "POST",
                        url: "zemljePodrijetla.php",
                        data:{
                            brisi: 1,
                            brisiDrzavu: brisiDrzavu
                        },
                        dataType: "JSON",
                        success: function(response){
                            if(response == "success"){
                                alert("Piva je uspjesno obrisana");
                            }
                            else if(response == "error"){
                                alert("Niste unijeli drzavu");
                            }
                            else{
                                alert("Drzava koju zelite obrisati ne postoji");
                            }
                        }
                    })
                })
            })
        </script>
    </body>
</html>