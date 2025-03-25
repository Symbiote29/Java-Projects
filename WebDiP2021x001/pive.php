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

$sql = "SELECT * FROM vrsta_piva vp, dz4_korisnik d WHERE vp.moderator_id = d.idKorisnik AND d.idKorisnik = '$id'";
$res = $veza->selectDB($sql);

//dodajPivu
if(isset($_POST['dodaj'])){
    
    $novoPivoIme = $_POST['novoPivoIme'];
    $cijena = $_POST['cijena'];
    $rokTrajanja = $_POST['roktrajanja'];
    $opis = $_POST['opis'];
    $slika = $_POST['slika'];
    $ocjena = $_POST['ocjena'];
    $receptId = $_POST['receptID'];
    $zemljaPodrijetlaID = $_POST['zemljaPodrijetlaID'];
    $mod1 = $_POST['m'];

    $sql2 = "SELECT * FROM vrsta_piva WHERE naziv = '$novoPivoIme'";
    $res2 = $veza->selectDB($sql2);

    if(mysqli_num_rows($res2) > 0){
        exit(json_encode("ex"));
    }
    elseif(mysqli_num_rows($res2) == 0 && empty($_POST['novoPivoIme']) || empty($_POST['cijena']) || empty($_POST['roktrajanja']) 
        || empty($_POST['opis']) || empty($_POST['slika']) || empty($_POST['ocjena']) 
        || empty($_POST['receptID']) || empty($_POST['zemljaPodrijetlaID']) || empty($_POST['m'])){
        exit(json_encode("wrong"));
    }
    else{
        if($mod1 != $id){
            exit(json_encode("m"));
        }
        else{
            $sql = "INSERT INTO vrsta_piva (naziv, cijena, rokTrajanja, opis, slika, ocjena, receptID, zemlja_podrijetla_id, moderator_id) 
            VALUES ('$novoPivoIme', '$cijena','$rokTrajanja','$opis','$slika','$ocjena','$receptId','$zemljaPodrijetlaID', '$mod1')";
            $veza->selectDB($sql);
            exit(json_encode("success"));
        }
    }
}

if(isset($_POST['azuriraj'])){

    $ponovoPiva = $_POST['azuriranaPiva'];
    $azuriranaPiva = $_POST['azuriranoImePive'];
    $azuriranaCijena = $_POST['azuriranaCijena'];
    $azuriraniRokTrajanja = $_POST['azuriraniRoktrajanja'];
    $azuriraniOpis = $_POST['azuriraniOpis'];
    $azuriranaSlika = $_POST['azuriranaSlika'];
    $azuriranaOcjena = $_POST['azuriranaOcjena'];
    $azurirniRecept = $_POST['azuriraniReceptID'];
    $azuriranaZemljaID = $_POST['azuriranaZemljaPodrijetlaID'];
    $mod2 = $_POST['m2'];

    $sql3 = "SELECT * FROM vrsta_piva WHERE naziv = '$ponovoPiva'";
    $res3 = $veza->selectDB($sql3);

    if(empty($_POST['azuriranaPiva']) || empty($_POST['azuriranoImePive']) || empty($_POST['azuriranaCijena']) || empty($_POST['azuriraniRoktrajanja']) 
        || empty($_POST['azuriraniOpis']) || empty($_POST['azuriranaSlika']) || empty($_POST['azuriranaOcjena']) 
        || empty($_POST['azuriraniReceptID']) || empty($_POST['azuriranaZemljaPodrijetlaID'])){
        exit(json_encode("error"));
    }
    elseif(mysqli_num_rows($res3) == 0){
        exit(json_encode("d"));
    }
    else{
        if($mod2 != $id){
            exit(json_encode("wrong"));
        }
        else{
            $sql = "UPDATE vrsta_piva SET naziv = '$azuriranaPiva', cijena = '$azuriranaCijena', rokTrajanja = '$azuriraniRokTrajanja', opis = '$azuriraniOpis',
            slika = '$azuriranaSlika', ocjena = '$azuriranaOcjena', receptID = '$azurirniRecept', zemlja_podrijetla_id = '$azuriranaZemljaID' 
            WHERE naziv = '$ponovoPiva'";
            $veza->selectDB($sql);
            exit(json_encode("success"));
        }
    }
}

if(isset($_POST['obrisi'])){
    $pivaZaBrisanje = $_POST['pivoZaObrisati'];

    $sql4 = "SELECT * FROM vrsta_piva WHERE naziv = '$pivaZaBrisanje'";
    $res4 = $veza->selectDB($sql4);

    if(empty($_POST['pivoZaObrisati'])){
        exit(json_encode("error"));
    }
    elseif(mysqli_num_rows($res4) == 0){
        exit(json_encode("d"));
    }
    else{
        $sql = "DELETE FROM vrsta_piva WHERE naziv = '$pivaZaBrisanje'";
        $veza->selectDB($sql);
        exit(json_encode("success"));
    }
}

?>

<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Pive</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="./css/style.css" rel="stylesheet">
    </head>
    <body>
        <header class="piveH">
            <a href="#proba2"><h1 class="o1">Pive</h1></a>
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
        <table class="tablicaPiva" border="2">
            <tr>
                <th>ID</th>
                <th>Naziv pive</th>
                <th>Cijena</th>
                <th>Rok trajanja</th>
                <th>Opis</th>
                <th>Slika</th>
                <th>Ocjena</th>
                <th>ID recepta</th>
                <th>ID zemlje podrijetla</th>
                <th>ID moderatora</th>
            </tr>
            <?php
            if(mysqli_num_rows($res) > 0){
                while($podaci = mysqli_fetch_assoc($res)){
                    echo '
                        <tr>
                            <td>'.$podaci['idvrsta_piva'].'</td>
                            <td>'.$podaci['naziv'].'</td>
                            <td>'.$podaci['cijena'].'</td>
                            <td>'.$podaci['rokTrajanja'].'</td>
                            <td>'.$podaci['opis'].'</td>
                            <td>'.$podaci['slika'].'</td>
                            <td>'.$podaci['ocjena'].'</td>
                            <td>'.$podaci['receptID'].'</td>
                            <td>'.$podaci['zemlja_podrijetla_id'].'</td>
                            <td>'.$podaci['moderator_id'].'</td>
                        </tr>
                    ';
                }
            }
            ?>
        </table>
        <form action="pive.php" method="POST">
            <button id="dodajPivu" class="dodajPivu" type="submit">Dodaj Pivu</button>
            
            <div class="nP">
                <label for="novaPiva" class="imePive">Unesite ime nove pive</label>
                <input type="text" id="novaPiva">

                <label for="cijenaPive" class="cijenap">Unesite cijenu pive</label>
                <input type="text" id="cijenaPive">

                <label for="rokTrajanja" class="rokPive">Unesite rok trajanja pive</label>
                <input type="text" id="rokTrajanja">

                <label for="opisPive" class="opisP">Unesite opis pive</label>
                <input type="text" id="opisPive">

                <label for="slikaPive" class="slPive">Unesite sliku pive</label>
                <input type="text" id="slikaPive">

                <label for="ocjenaPive" class="ocjPive">Unesite ocjenu pive</label>
                <input type="text" id="ocjenaPive">

                <label for="receptPive" class="rcptPive">Unesite ID recepta pive</label>
                <input type="text" id="receptPive">

                <label for="zemljaPodrijetlaPive" class="zpPive">Unesite ID zemlje podrijetla pive</label>
                <input type="text" id="zemljaPodrijetlaPive">

                <label for="mod1" class="m1">Unesite ID moderatora</label>
                <input type="text" id="mod1">
            </div>
            
            
        </form>
        <form action="pive.php" method="POST">
            <button id="azurirajPivu" class="azrP" type="submit">Azuriraj Pivu</button>

            <div class="ponPivo">
                <label for="ponovoPivo" class="ponovoImePiva">Unesite ime pive koju zelite promijeniti</label>
                <input type="text" id="ponovoPivo">

                <label for="azuriranaPiva" class="azuriranoImePiva">Unesite novo ime pive</label>
                <input type="text" id="azuriranaPiva" class="azurPiva">

                <label for="azuriranaCijenaPive" class="azuriranacijenap">Unesite novu/istu cijenu pive</label>
                <input type="text" id="azuriranaCijenaPive">

                <label for="azuriraniRokTrajanja" class="azuriranirokPive">Unesite novi/isti rok trajanja pive</label>
                <input type="text" id="azuriraniRokTrajanja">

                <label for="azuriraniOpisPive" class="azuriraniopisP">Unesite novi/isti opis pive</label>
                <input type="text" id="azuriraniOpisPive">

                <label for="azuriranaSlikaPive" class="azuriranaslPive">Unesite novu/istu sliku pive</label>
                <input type="text" id="azuriranaSlikaPive">

                <label for="azuriranaOcjenaPive" class="azuriranaocjPive">Unesite novu/istu ocjenu pive</label>
                <input type="text" id="azuriranaOcjenaPive">

                <label for="azuriraniReceptPive" class="azuriranircptPive">Unesite novi/isti ID recepta pive</label>
                <input type="text" id="azuriraniReceptPive">

                <label for="azuriranaZemljaPodrijetlaPive" class="azuriranizpPive">Unesite novi/isti ID zemlje podrijetla pive</label>
                <input type="text" id="azuriranaZemljaPodrijetlaPive">
                
                <label for="mod2" class="m2">Unesite ID moderatora</label>
                <input type="text" id="mod2">
            </div>
            <div class="aPiva">
                
            </div>
            
        </form>
        <form action="pive.php" method="POST">
            <button id="obrisiPivo" class="obrPivo" type="submit">Obrisi Pivu</button>
            <div class="bPiva">
                <label for="brisiPivo" class="brPivo">Unesite ime pive koju zelite obrisati</label>
                <input type="text" id="brisiPivo" class="obrisiP">
            </div>
        </form>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
        <script type="text/javascript">
            $(document).ready(function(){
                $("#dodajPivu").click(function(){
                    var novoPivoIme = $("#novaPiva").val();
                    var cijena = $("#cijenaPive").val();
                    var roktrajanja = $("#rokTrajanja").val();
                    var opis = $("#opisPive").val();
                    var slika = $("#slikaPive").val();
                    var ocjena = $("#ocjenaPive").val();
                    var receptID = $("#receptPive").val();
                    var zemljaPodrijetlaID = $("#zemljaPodrijetlaPive").val();
                    var m = $("#mod1").val();
                    $.ajax({
                        method: "POST",
                        url: "pive.php",
                        data:{
                            dodaj: 1,
                            novoPivoIme: novoPivoIme,
                            cijena: cijena,
                            roktrajanja: roktrajanja,
                            opis: opis,
                            slika: slika,
                            ocjena: ocjena,
                            receptID: receptID,
                            zemljaPodrijetlaID: zemljaPodrijetlaID,
                            m: m
                        },
                        dataType: "JSON",
                        success: function(response){
                            if(response == "success"){
                                alert("Piva je dodana");
                            }
                            else if(response == "ex"){
                                alert("Ta piva vec postoji");
                            }
                            else if(response == "wrong"){
                                alert("Niste popunili sva polja");
                            }
                            else{
                                alert("Unijeli ste krivog moderatora");
                            }
                        }
                    })  
                })
                $("#azurirajPivu").click(function(){
                    var azuriranaPiva = $("#ponovoPivo").val();
                    var azuriranoImePive = $("#azuriranaPiva").val();
                    var azuriranaCijena = $("#azuriranaCijenaPive").val();
                    var azuriraniRoktrajanja = $("#azuriraniRokTrajanja").val();
                    var azuriraniOpis = $("#azuriraniOpisPive").val();
                    var azuriranaSlika = $("#azuriranaSlikaPive").val();
                    var azuriranaOcjena = $("#azuriranaOcjenaPive").val();
                    var azuriraniReceptID = $("#azuriraniReceptPive").val();
                    var azuriranaZemljaPodrijetlaID = $("#azuriranaZemljaPodrijetlaPive").val();
                    var m2 = $("#mod2").val();
                    $.ajax({
                        method: "POST",
                        url: "pive.php",
                        data:{
                            azuriraj: 1,
                            azuriranaPiva: azuriranaPiva,
                            azuriranoImePive: azuriranoImePive,
                            azuriranaCijena: azuriranaCijena,
                            azuriraniRoktrajanja: azuriraniRoktrajanja,
                            azuriraniOpis: azuriraniOpis,
                            azuriranaSlika: azuriranaSlika,
                            azuriranaOcjena: azuriranaOcjena,
                            azuriraniReceptID: azuriraniReceptID,
                            azuriranaZemljaPodrijetlaID: azuriranaZemljaPodrijetlaID,
                            m2: m2
                        },
                        dataType: "JSON",
                        success: function(response){
                            if(response == "success"){
                                alert("Piva je azurirana");
                            }
                            else if(response == "error"){
                                alert("Niste popunili sva polja");
                            }
                            else if(response == "wrong"){
                                alert("Unijeli ste krivog moderatora");
                            }
                            else{
                                alert("Ne postoji piva koju zelite azurirati");
                            }
                        }
                    })
                })
                $("#obrisiPivo").click(function(){
                    var pivoZaObrisati = $("#brisiPivo").val();
                    $.ajax({
                        method: "POST",
                        url: "pive.php",
                        data:{
                            obrisi: 1,
                            pivoZaObrisati: pivoZaObrisati
                        },
                        dataType: "JSON",
                        success: function(response){
                            if(response == "success"){
                                alert("Piva je obrisana");
                            }
                            else if(response == "error"){
                                alert("Niste unijeli pivu koju zelite izbrisati");
                            }
                            else{
                                alert("Ne postoji piva koju zelite izbrisati");
                            }
                        }
                    })
                })
            })
        </script>
    </body>
</html>