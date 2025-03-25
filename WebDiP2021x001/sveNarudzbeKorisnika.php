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

$sql = "SELECT * FROM narudzba WHERE racunStatus != 'Nije placeno'";
$res = $veza->selectDB($sql);

$sql2 = "SELECT * FROM narudzba WHERE racunStatus = 'Nije placeno'";
$res2 = $veza->selectDB($sql2);

$sql4 = "SELECT * FROM dz4_korisnik";
$res4 = $veza->selectDB($sql4);

if(isset($_POST['blokiraj'])){
    $blok = $_POST['blokiraniKorisnik'];

    $sql3 = "SELECT * FROM dz4_korisnik d, narudzba n WHERE d.idKorisnik = '$blok' AND n.idkorisnik = d.idKorisnik AND d.UlogaKorisnika < 3 AND d.Blokiran = 0 AND n.racunStatus = 'Nije placeno'";
    $res3 = $veza->selectDB($sql3);

    if(mysqli_num_rows($res3) > 0){
        $sql5 = "UPDATE dz4_korisnik SET Blokiran = 1 WHERE idKorisnik = '$blok'";
        $veza->selectDB($sql5);
        exit(json_encode("success"));
    }
    else{
        exit(json_encode("wrong"));
    }
}

?>

<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Sve narudzbe korisnika</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="./css/style.css" rel="stylesheet">
    </head>
    <body>
        <header class="sveNarudzbeKorisnikaH">
            <a href="#proba2"><h1 class="o1">Sve narudzbe korisnika</h1></a>
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
        <table class="tablicaNarudzbiKorisnika" border="2">
            <tr>
                <th>ID narudzbe</th>
                <th>Stavke narudzbe</th>
                <th>Status narudzbe</th>
                <th>Status racuna</th>
                <th>Datum narudzbe</th>
                <th>ID racuna</th>
                <th>Ukupni iznos racuna</th>
                <th>Placeni iznos racuna</th>
                <th>ID korisnika</th>
            </tr>
            <?php
            if(mysqli_num_rows($res) > 0){
                while($podaci = mysqli_fetch_assoc($res)){
                    if($podaci['racunStatus'] == "Djelomicno placeno"){
                        echo '
                        <tr>
                            <td style="background-color:red">'.$podaci['idnarudzba'].'</td>
                            <td style="background-color:red">'.$podaci['narudzba_stavke'].'</td>
                            <td style="background-color:red">'.$podaci['narudzba_status'].'</td>
                            <td style="background-color:red">'.$podaci['racunStatus'].'</td>
                            <td style="background-color:red">'.$podaci['narudzba_datum'].'</td>
                            <td style="background-color:red">'.$podaci['racun_idracun'].'</td>
                            <td style="background-color:red">'.$podaci['ukupniIznosRacuna'].'</td>
                            <td style="background-color:red">'.$podaci['placeniIznosRacuna'].'</td>
                            <td style="background-color:red">'.$podaci['idkorisnik'].'</td>
                        </tr>
                    ';
                    }
                    else{
                        echo '
                        <tr>
                            <td>'.$podaci['idnarudzba'].'</td>
                            <td>'.$podaci['narudzba_stavke'].'</td>
                            <td>'.$podaci['narudzba_status'].'</td>
                            <td>'.$podaci['racunStatus'].'</td>
                            <td>'.$podaci['narudzba_datum'].'</td>
                            <td>'.$podaci['racun_idracun'].'</td>
                            <td>'.$podaci['ukupniIznosRacuna'].'</td>
                            <td>'.$podaci['placeniIznosRacuna'].'</td>
                            <td>'.$podaci['idkorisnik'].'</td>
                        </tr>
                    ';
                    }
                }
            }
            ?>
        </table>
        <table class="tablicaNeplacenihKorisnika" border="2">
            <tr>
                <th>ID narudzbe</th>
                <th>Stavke narudzbe</th>
                <th>Status narudzbe</th>
                <th>Status racuna</th>
                <th>Datum narudzbe</th>
                <th>ID racuna</th>
                <th>Ukupni iznos racuna</th>
                <th>Placeni iznos racuna</th>
                <th>ID korisnika</th>
            </tr>
            <?php
            if(mysqli_num_rows($res2) > 0){
                while($podaci = mysqli_fetch_assoc($res2)){
                    echo '
                        <tr>
                            <td>'.$podaci['idnarudzba'].'</td>
                            <td>'.$podaci['narudzba_stavke'].'</td>
                            <td>'.$podaci['narudzba_status'].'</td>
                            <td>'.$podaci['racunStatus'].'</td>
                            <td>'.$podaci['narudzba_datum'].'</td>
                            <td>'.$podaci['racun_idracun'].'</td>
                            <td>'.$podaci['ukupniIznosRacuna'].'</td>
                            <td>'.$podaci['placeniIznosRacuna'].'</td>
                            <td>'.$podaci['idkorisnik'].'</td>
                        </tr>
                    ';
                }
            }
            ?>
        </table>
        <form action="./sveNarudzbeKorisnika.php">
            <button id="blokirajBtn">Blokiraj</button>
            <label for="blokiraniKorisnik" class="blokiraniTekst">Unesite ID korisnika kojeg zelite blokirati</label>
            <input type="text" id="blokiraniKorisnik">
        </form>
        <table class="tablicaKorisnika" border="2">
            <tr>
                <th>ID korisnika</th>
                <th>Ime</th>
                <th>Prezime</th>
                <th>Godina rodenja</th>
                <th>Email</th>
                <th>Korisnicko ime</th>
                <th>Lozinka</th>
                <th>Potvrda lozinke</th>
                <th>ID uloge korisnika</th>
                <th>ID kolacica</th>
                <th>Broj pokusaja</th>
                <th>Blokiran</th>
                <th>OTP</th>
                <th>Verify</th>
                <th>Datum registracije</th>
            </tr>
            <?php
            if(mysqli_num_rows($res4) > 0){
                while($podaci = mysqli_fetch_assoc($res4)){
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
        <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
        <script type="text/javascript">
            $(document).ready(function(){
                $("#blokirajBtn").click(function(){
                    var blokiraniKorisnik = $("#blokiraniKorisnik").val();
                    $.ajax({
                        method: "POST",
                        url: "sveNarudzbeKorisnika.php",
                        data:{
                            blokiraj: 1,
                            blokiraniKorisnik: blokiraniKorisnik
                        },
                        dataType: "JSON",
                        success: function(response){
                            if(response == "success"){
                                alert("Korisnik je uspjesno blokiran");
                            }
                            else{
                                alert("Taj korisnik je platio cijeli ili dio racuna");
                            }
                        }
                    })
                })
            })
        </script>
    </body>
</html>