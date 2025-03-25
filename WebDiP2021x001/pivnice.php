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

$sql = "SELECT * FROM pivnica";
$res = $veza->selectDB($sql);

if(isset($_POST['dodaj'])){
    $pivnica = $_POST['novaPivnica'];
    $adresa = $_POST['adresaPivnice'];
    $getMod = $_POST['mods'];
    
    $sql2 = "SELECT * FROM pivnica WHERE nazivPivnice = '$pivnica'";
    $res2 = $veza->selectDB($sql2);

    if(mysqli_num_rows($res2) > 0){
        exit(json_encode("ex"));
    }
    elseif(mysqli_num_rows($res2) == 0 && empty($_POST['novaPivnica']) || empty($_POST['adresaPivnice']) || empty($_POST['mods'])){
        exit(json_encode("error"));
    }
    else{
        $sql = "INSERT INTO pivnica (nazivPivnice, adresaPivnice, moderatorID) VALUES ('$pivnica', '$adresa', '$getMod')";
        $veza->selectDB($sql);
        exit(json_encode("success"));
    }
}

if(isset($_POST['azuriraj'])){

    $ponovoPivnica = $_POST['ponovoPiv'];
    $azuriranaPivnica = $_POST['azuriranaPivnica'];
    $azuriranaAdresa = $_POST['azuriranaAdresa'];
    $azuriraniMods = $_POST['azuriraniMods'];

    $sql3 = "SELECT * FROM pivnica WHERE nazivPivnice = '$ponovoPivnica'";
    $res3 = $veza->selectDB($sql3);

    if(empty($_POST['ponovoPiv']) || empty($_POST['azuriranaPivnica']) || empty($_POST['azuriranaAdresa']) || empty($_POST['azuriraniMods'])){
        exit(json_encode("error"));
    }
    elseif(mysqli_num_rows($res3) == 0){
        exit(json_encode("doesntexist"));
    }
    else{
        $sql = "UPDATE pivnica SET nazivPivnice = '$azuriranaPivnica', adresaPivnice = '$azuriranaAdresa', moderatorID = '$azuriraniMods' 
            WHERE nazivPivnice = '$ponovoPivnica'";
        $veza->selectDB($sql);
        exit(json_encode("success"));
    }
}

if(isset($_POST['obrisi'])){
    $pivnicaZaBrisanje = $_POST['brisiPivnicu'];

    $sql4 = "SELECT * FROM pivnica where nazivPivnice = '$pivnicaZaBrisanje'";
    $res4 = $veza->selectDB($sql4);

    if(empty($_POST['brisiPivnicu'])){
        exit(json_encode("error"));
    }
    elseif(mysqli_num_rows($res4) == 0){
        exit(json_encode("ex"));
    }
    else{
        $sql = "DELETE FROM pivnica WHERE nazivPivnice = '$pivnicaZaBrisanje'";
        $veza->selectDB($sql);
        exit(json_encode("success"));
    }
}

?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="./css/style.css" rel="stylesheet">
    <title>Pivnica</title>
</head>
<body>
    <header class="pivniceH">
        <a href="#proba2"><h1 class="o1">Pivnice</h1></a>
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
    <table class="tablicaPivnica" border="2">
            <tr>
                <th>ID</th>
                <th>Naziv pivnice</th>
                <th>Adresa pivnice</th>
                <th>Moderator</th>
            </tr>
            <?php
            if(mysqli_num_rows($res) > 0){
                while($podaci = mysqli_fetch_assoc($res)){
                    echo '
                        <tr>
                            <td>'.$podaci['pivnica_id'].'</td>
                            <td>'.$podaci['nazivPivnice'].'</td>
                            <td>'.$podaci['adresaPivnice'].'</td>
                            <td>'.$podaci['moderatorID'].'</td>
                        </tr>
                    ';
                }
            }
            ?>
        </table>
        <form action="./pivnice.php" method="POST">
            <button id="dodajPivnicu" class="dp" type="submit">Dodaj pivnicu</button>

            <div class="novoImePivnice">
                <label for="novaPivnica" class="imePivnice">Unesite ime nove pivnice</label>
                <input type="text" id="novaPivnica">

                <label for="adresaPivnice" class="adresaPivnice">Unesite adresu pivnice</label>
                <input type="text" id="adresaPivnice">

                <label for="mods" class="modsID2">Odaberite ID moderatora:</label>
                <input type="text" id="mods">
            </div>
        </form>

        <form action="pivnice.php">
            <button id="azurirajPivnicu" class="ap" type="submit">Azuriraj pivnicu</button>
            <div class="ponovoP">
                <label for="ponovoPiv" class="ponovoImePivnice">Unesite ime pivnice koju zelite promijeniti</label>
                <input type="text" id="ponovoPiv">

                <label for="azuriranaPivnica" class="azuriranoImePiv">Unesite novo/postojece ime pivnice</label>
                <input type="text" id="azuriranaPivnica" class="azurP">

                <label for="azuriranaAdresa" class="azuriranaAdr">Unesite novu/postojecu adresu pivnice</label>
                <input type="text" id="azuriranaAdresa" class="azurAdr">

                <label for="azuriraniMods" class="modsID">Odaberite ID moderatora:</label>
                <input type="text" id="azuriraniMods">
            </div>
        </form>
        <form action="pivnice.php">
            <button id="obrisiPivnicu" class="op" type="submit">Obrisi pivnicu</button>
            <div class="brDrz">
                <label for="brisiPivnicu" class="BrisiPiv">Unesite ime pivnice koju zelite obrisati</label>
                <input type="text" id="brisiPivnicu" class="brisiP">
            </div>
        </form>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
        <script type="text/javascript">
            $(document).ready(function(){
                $("#azurirajPivnicu").click(function(){
                    var ponovoPiv = $("#ponovoPiv").val();
                    var azuriranaPivnica = $("#azuriranaPivnica").val();
                    var azuriranaAdresa = $("#azuriranaAdresa").val();
                    var azuriraniMods = $("#azuriraniMods").val();
                    $.ajax({
                        method: "POST",
                        url: "pivnice.php",
                        data:{
                            azuriraj: 1,
                            ponovoPiv: ponovoPiv,
                            azuriranaPivnica: azuriranaPivnica,
                            azuriranaAdresa: azuriranaAdresa,
                            azuriraniMods: azuriraniMods
                        },
                        dataType: "JSON",
                        success: function(response){
                            if(response == "success"){
                                alert("Pivnica je azurirana");
                            }
                            else if(response == "error"){
                                alert("Niste popunili sva polja");
                            }
                            else{
                                alert("Ne postoji pivnica koju zelite azurirati");
                            }
                        }
                    })
                })
                $("#dodajPivnicu").click(function(){
                    var novaPivnica = $("#novaPivnica").val();
                    var adresaPivnice = $("#adresaPivnice").val();
                    var mods = $("#mods").val();
                    $.ajax({
                        method: "POST",
                        url: "pivnice.php",
                        data:{
                            dodaj: 1,
                            novaPivnica: novaPivnica,
                            adresaPivnice: adresaPivnice,
                            mods: mods
                        },
                        dataType: "JSON",
                        success: function(response){
                            if(response == "success"){
                                alert("Pivnica je dodana u popis");
                            }
                            else if(response == "error"){
                                alert("Niste popunili sva polja");
                            }
                            else{
                                alert("Odabrana piva vec postoji");
                            }
                        }
                    })
                })
                $("#obrisiPivnicu").click(function(){
                    var brisiPivnicu = $("#brisiPivnicu").val();
                    $.ajax({
                        method: "POST",
                        url: "pivnice.php",
                        data:{
                            obrisi: 1,
                            brisiPivnicu: brisiPivnicu
                        },
                        dataType: "JSON",
                        success: function(response){
                            if(response == "success"){
                                alert("Pivnica je uspjesno obrisana");
                            }
                            else if(response == "error"){
                                alert("Niste unijeli pivnicu koju zelite obrisati");
                            }
                            else{
                                alert("Ne postoji pivnica koju zelite obrisati");
                            }
                        }
                    })
                })
            })
        </script>
</body>
</html>