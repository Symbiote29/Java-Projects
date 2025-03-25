<?php

include './baza.class.php';
include './sesija.class.php';

session_start();

$potrebnaUloga = 3;

if(!isset($_SESSION["uloga"])){
    Sesija::kreirajKorisnika("gost");
}

if($_SESSION["uloga"] < $potrebnaUloga){
    header("Location: ./obrasci/prijava.php");
}

$veza = new Baza();
$veza->spojiDB();

$c = $_SESSION["uloga"];
$d = $_SESSION["user"];

$sql5 = "SELECT * FROM dz4_korisnik WHERE Korisnicko_ime = '$d'";
$res5 = $veza->selectDB($sql5);

$us = mysqli_fetch_array($res5);

$id = $us[0];

$sql = "SELECT * FROM popispiva pp, dz4_korisnik d WHERE pp.moderator_id = d.idKorisnik AND d.idKorisnik = '$id'";
$res = $veza->selectDB($sql);


if(isset($_POST['dodaj'])){

    $novoPivoIme = $_POST['novaPiva2'];
    $cijena = $_POST['cijenaPive2'];
    $ocjena = $_POST['ocjenaPive2'];
    $pivnicaID = $_POST['idPivnice'];
    $moderatorID = $_POST['moderatori'];

    $sql2 = "SELECT * FROM popispiva WHERE nazivPive = '$novoPivoIme'";
    $res2 = $veza->selectDB($sql2);

    if(empty($_POST['novaPiva2']) || empty($_POST['cijenaPive2']) || empty($_POST['ocjenaPive2'])){
        exit(json_encode("error"));
    }
    elseif(mysqli_num_rows($res2) > 0){
        exit(json_encode("alreadyexists"));
    }
    else{
        if($moderatorID != $id){
            exit(json_encode("wrong"));
        }
        else{
            $sql = "INSERT INTO popispiva (nazivPive, cijena, ocjena, pivnica_id, moderator_id) 
            VALUES ('$novoPivoIme', '$cijena', '$ocjena', '$pivnicaID', '$moderatorID')";
            $veza->selectDB($sql);
            exit(json_encode("success"));
        }
    }
}

if(isset($_POST['azuriraj'])){

    $ponovoPiva = $_POST['azuriranoPonovoPivo'];
    $azuriranaPiva = $_POST['azuriranaPiva2'];
    $azuriranaCijena = $_POST['azuriranaCijenaPive2'];
    $azuriranaOcjena = $_POST['azuriranaOcjenaPive2'];
    $azuriraniIDpivnice = $_POST['azuriraniIdPivnice'];
    $azuriraniIDmoderatora = $_POST['moderatori2'];

    $sql3 = "SELECT * FROM popispiva WHERE nazivPive = '$ponovoPiva'";
    $res3 = $veza->selectDB($sql3);

    if(empty($_POST['azuriranoPonovoPivo']) || empty($_POST['azuriranaPiva2']) || empty($_POST['azuriranaCijenaPive2']) || empty($_POST['azuriranaOcjenaPive2'])
        || empty($_POST['azuriraniIdPivnice']) || empty($_POST['moderatori2'])){
        exit(json_encode("error"));
    }
    elseif(mysqli_num_rows($res3) == 0){
        exit(json_encode("ex"));
    }
    else{
        if($azuriraniIDmoderatora != $id){
            exit(json_encode("wrong"));
        }
        else{
            $sql = "UPDATE popispiva SET nazivPive = '$azuriranaPiva', cijena = '$azuriranaCijena', ocjena = '$azuriranaOcjena', 
            pivnica_id = '$azuriraniIDpivnice', moderator_id = '$azuriraniIDmoderatora' WHERE nazivPive = '$ponovoPiva'";
            $veza->selectDB($sql);
            exit(json_encode("success"));
        }
    }
}

if(isset($_POST['obrisi'])){
    $pivaZaBrisanje = $_POST['pivaZaObrisati'];

    $sql4 = "SELECT * FROM popispiva WHERE nazivPive = '$pivaZaBrisanje'";
    $res4 = $veza->selectDB($sql4);

    if(empty($_POST['pivaZaObrisati'])){
        exit(json_encode("error"));
    }
    elseif(mysqli_num_rows($res4) == 0){
        exit(json_encode("doesntExist"));
    }
    else{
        $sql = "DELETE FROM popispiva WHERE nazivPive = '$pivaZaBrisanje'";
        $veza->selectDB($sql);
        exit(json_encode("success"));
    }
}

?>

<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Popis piva</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="./css/style.css" rel="stylesheet">
    </head>
    <body>
        <header class="popispivaH">
            <a href="#proba2"><h1 class="o1">Popis piva</h1></a>
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
        <table class="popispiva" border="2">
            <tr>
                <th>ID</th>
                <th>Naziv pive</th>
                <th>Cijena</th>
                <th>Ocjena</th>
                <th>ID pivnice</th>
                <th>ID moderatora</th>
            </tr>
            <?php
            if(mysqli_num_rows($res) > 0){
                while($podaci = mysqli_fetch_assoc($res)){
                    echo '
                        <tr>
                            <td>'.$podaci['popisPiva_id'].'</td>
                            <td>'.$podaci['nazivPive'].'</td>
                            <td>'.$podaci['cijena'].'</td>
                            <td>'.$podaci['ocjena'].'</td>
                            <td>'.$podaci['pivnica_id'].'</td>
                            <td>'.$podaci['moderator_id'].'</td>
                        </tr>
                    ';
                }
            }
            ?>
        </table>
        <form action="./popispiva.php" method="POST">
            <div class="dodavanjePiveForma">
                <button id="dodajPivuUpopis" class="dodajPivu" type="submit">Dodaj pivu u popis</button>
           
                <div class="nP">
                    <label for="novaPiva" class="imePive2">Unesite ime nove pive</label>
                    <input type="text" id="novaPiva2">

                    <label for="cijenaPive" class="cijenap2">Unesite cijenu pive</label>
                    <input type="text" id="cijenaPive2">

                    <label for="ocjenaPive2" class="ocjPive2">Unesite ocjenu pive</label>
                    <input type="text" id="ocjenaPive2">

                    <label for="idPivnice" class="idP">Unesite ID pivnice</label>
                    <input type="text" id="idPivnice">

                    <div class="mod">
                        <label for="moderatori" class="mod_id">Unesite ID moderatora:</label>
                        <input type="text" id="moderatori">
                    </div>
                </div>
            </div>
            
        </form>

        <form action="./popispiva.php">
            <div class="azuriranjePivaForma">
                <button id="azurirajPopisPiva" class="azrP" type="submit">Azuriraj popis piva</button>

                <div class="ponPivo">
                    <label for="azuriranoPonovoPivo" class="ponovoImePiva">Unesite ime pive koju zelite promijeniti</label>
                    <input type="text" id="azuriranoPonovoPivo">

                    <label for="azuriranaPiva2" class="azuriranoImePiva2">Unesite novo ime pive</label>
                    <input type="text" id="azuriranaPiva2" class="azurPiva2">

                    <label for="azuriranaCijenaPive2" class="azuriranacijenap2">Unesite novu/istu cijenu pive</label>
                    <input type="text" id="azuriranaCijenaPive2">

                    <label for="azuriranaOcjenaPive2" class="azuriranaocjPive2">Unesite novu/istu ocjenu pive</label>
                    <input type="text" id="azuriranaOcjenaPive2">

                    <label for="azuriraniIdPivnice" class="azuriraniIdPiv">Unesite novi/isti ID pivnice</label>
                    <input type="text" id="azuriraniIdPivnice">

                    <div class="mod2">
                        <label for="moderatori2" class="mod_id2">Unesite ID moderatora:</label>
                        <input type="text" id="moderatori2">
                    </div>
                </div>
            </div>
        </form>

        <form action="./popispiva.php">
            <div class="formaZaBrisanje">
                <button id="obrisiPivuIzPopisa" class="obrPivo" type="submit">Obrisi pivu iz popisa</button>
                <div class="bPivaIzPopisa">
                    <label for="brisiPivoIzPopisa" class="brPizPop">Unesite ime pive koju zelite obrisati</label>
                    <input type="text" id="brisiPivoIzPopisa" class="obrisiPizPop">
                </div>
            </div>
        </form>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
        <script type="text/javascript">
            $(document).ready(function(){
                $("#dodajPivuUpopis").click(function(){
                    var novaPiva2 = $("#novaPiva2").val();
                    var cijenaPive2 = $("#cijenaPive2").val();
                    var ocjenaPive2 = $("#ocjenaPive2").val();
                    var idPivnice = $("#idPivnice").val();
                    var moderatori = $("#moderatori").val();
                    $.ajax({
                        method: "POST",
                        url: "popispiva.php",
                        data:{
                            dodaj: 1,
                            novaPiva2: novaPiva2,
                            cijenaPive2: cijenaPive2,
                            ocjenaPive2: ocjenaPive2,
                            idPivnice: idPivnice,
                            moderatori: moderatori
                        },
                        dataType: "JSON",
                        success: function(response){
                            if(response == "success"){
                                alert("Piva je uspjesno dodana");
                            }
                            else if(response == "error"){
                                alert("Niste popunili sva polja");
                            }
                            else if(response == "wrong"){
                                alert("Unijeli ste krivog moderatora");
                            }
                            else{
                                alert("Ta piva vec postoji");
                            }
                        }
                    })
                })
                $("#azurirajPopisPiva").click(function(){
                    var azuriranoPonovoPivo = $("#azuriranoPonovoPivo").val();
                    var azuriranaPiva2 = $("#azuriranaPiva2").val();
                    var azuriranaCijenaPive2 = $("#azuriranaCijenaPive2").val();
                    var azuriranaOcjenaPive2 = $("#azuriranaOcjenaPive2").val();
                    var azuriraniIdPivnice = $("#azuriraniIdPivnice").val();
                    var moderatori2 = $("#moderatori2").val();
                    $.ajax({
                        method: "POST",
                        url: "popispiva.php",
                        data:{
                            azuriraj: 1,
                            azuriranoPonovoPivo: azuriranoPonovoPivo,
                            azuriranaPiva2: azuriranaPiva2,
                            azuriranaCijenaPive2: azuriranaCijenaPive2,
                            azuriranaOcjenaPive2: azuriranaOcjenaPive2,
                            azuriraniIdPivnice: azuriraniIdPivnice,
                            moderatori2: moderatori2
                        },
                        dataType: "JSON",
                        success: function(response){
                            if(response == "success"){
                                alert("Piva je uspjesno azurirana");
                            }
                            else if(response == "error"){
                                alert("Niste popunili sva polja");
                            }
                            else if(response == "wrong"){
                                alert("Unijeli ste krivog moderatora");
                            }
                            else{
                                alert("Piva koju zelite azurirati ne postoji");
                            }
                        }
                    })
                })
                $("#obrisiPivuIzPopisa").click(function(){
                    var pivaZaObrisati = $("#brisiPivoIzPopisa").val();
                    $.ajax({
                        method: "POST",
                        url: "popispiva.php",
                        data:{
                            obrisi: 1,
                            pivaZaObrisati: pivaZaObrisati
                        },
                        dataType: "JSON",
                        success: function(response){
                            if(response == "success"){
                                alert("Piva je obrisana iz popisa");
                            }
                            else if(response == "error"){
                                alert("Niste unijeli pivu koju zelite izbrisati iz popisa");
                            }
                            else{
                                alert("Piva koju zelite izbrisati iz popisa ne postoji");
                            }
                        }
                    })
                })
            })
        </script>
    </body>
</html>