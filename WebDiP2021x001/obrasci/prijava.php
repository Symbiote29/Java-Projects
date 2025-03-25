<?php

//$putanja = dirname($_SERVER['REQUEST_URI'], 2);
$direktorij = dirname(getcwd());

include '../baza.class.php';
include '../sesija.class.php';

session_start();

$potrebnaUloga = 1;

Sesija::kreirajSesiju();
//var_dump($_SESSION);

if(!isset($_SESSION["uloga"])){
    Sesija::kreirajKorisnika("gost");
}

if($_SESSION["uloga"] < $potrebnaUloga){
    header("Location: prijava.php");
}


if(isset($_POST['login'])){
    $veza = new Baza();
    $veza->spojiDB();

    $korime = $_POST['korime'];
    $loz = $_POST['loz'];
    //$z1 = $_POST['zp'];

    $sql = "SELECT * FROM dz4_korisnik WHERE Korisnicko_ime = '$korime' AND Lozinka = '$loz'";
    $result = $veza->selectDB($sql);

    $sql4 = "SELECT * FROM dz4_korisnik WHERE Korisnicko_ime = '$korime'";
    $res4 = $veza->selectDB($sql4);
    $kor = mysqli_fetch_array($res4);

    $row = mysqli_fetch_array($result);
    $userres = mysqli_query($veza->spojiDB(), $sql);

    if($row > 0 && $row[11] == '0' && $row[12] != '0'){
        $_SESSION["user"] = $row[5];
        $_SESSION["pass"] = $row[6];

        Sesija::kreirajKorisnika($row[5], $row[8]);
        
        //header("Location: prijava.php");

        // if($z1 == 'on'){
        //     setcookie("autentificiran", $korisnickoime, false, '/', false);
        //     setcookie("korime", $korime, time()+ (10*365*24*60*60));
        //     echo $_COOKIE['korime'];
        //     setcookie("loz", $_POST["loz"], time()+ (10*365*24*60*60));
        // }
        // else{
        //     if(isset($_COOKIE["korime"])){
        //         setcookie("korime", "");
        //     }
        //     if(isset($_COOKIE["loz"])){
        //         setcookie("loz", "");
        //     }
            
        // }
        //var_dump($_POST);
        exit(json_encode("success"));
    }
    elseif($row > 0 && $row[11] == '1'){
        exit(json_encode("blockedEntry"));
    }
    elseif($row > 0 && $row[12] == '0'){
        exit(json_encode("nijeaktivirano"));
    }
    if($kor > 0){
        if($kor[10] == 5){
            $sql6 = "UPDATE dz4_korisnik SET BrojPokusaja = '0', Blokiran = '1' WHERE Korisnicko_ime = '$kor[5]'";
            $veza->selectDB($sql6);
            exit(json_encode("blocked"));
        }
        elseif($kor[10] < 5){
            $nPokusaja = 5;
            $bp = $kor[10] + 1;
            $sql5 = "UPDATE dz4_korisnik SET BrojPokusaja = '$bp' WHERE Korisnicko_ime = '$kor[5]'";
            $veza->selectDB($sql5);
            exit(json_encode("Pogresno korisnicko ime ili lozinka\n Preostali broj Pokusaja je: ".(5 - $kor[10])));
        }
    }
}

if(isset($_POST['zapamti'])){
    $veza = new Baza();
    $veza->spojiDB();

    $korime = $_POST['korime'];
    $sql2 = "SELECT * FROM dz4_korisnik WHERE Korisnicko_ime = '$korime'";
    $res2 = $veza->selectDB($sql2);

    $user = mysqli_fetch_array($res2);

    if($user[5] == $korime){
        $mail_to = $user[4];
        //$mail_to = "filipantunovic29@gmail.com";
        //$mail_to = "filipantunovic29@gmail.com";
        $mail_from = "From: fantunovi@foi.hr";
        $mail_subject = "Promjena lozinke";
    
        function generateRandomString($length = 25) {
            $characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
            $charactersLength = strlen($characters);
            $randomString = '';
            for ($i = 0; $i < $length; $i++) {
                $randomString .= $characters[rand(0, $charactersLength - 1)];
            }
            return $randomString;
        }
        $myRandomString = generateRandomString(5);
        $mail_body = $myRandomString;
        $_SESSION['novaLozinka'] = $mail_body;
    
        mail($mail_to, $mail_subject, "Nova lozinka: ".$mail_body, $mail_from);

        $sql3 = "UPDATE dz4_korisnik SET Lozinka = '$myRandomString', PotvrdaLozinke = '$myRandomString' WHERE Korisnicko_ime = '$korime '";
        $veza->selectDB($sql3);

        exit(json_encode("success"));
    }
    else{
        exit(json_encode("error"));
    }
}
?>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="autor" content="Filip Antunović">
    <meta name="Datum početka izrade" content="19.3.2022">
    <link href="../css/prijava.css" rel="stylesheet">
    <script type="text/javascript" src="../javascript/fantunovi.js"></script>
    <title>Prijava</title>
</head>
<body>
    <header>
        <a href="#proba2"><h1 class="o1">Obrazac za prijavu</h1></a>
            <a href="../index.html"><img class = "img-logo" src="../materijali/logo.png" alt="logo"></a>
            <a href="#proba1"><img class = "img-izbornik" id="btnLeft" src="../materijali/izbornik.png" alt="logo"></a>
            <div class="izbornik" id="proba2">
                <ul class="linkovi" id="proba1">
                    <?php
                    if(isset($_SESSION["uloga"]) && $_SESSION["uloga"] == 4){
                        echo '<a href="../index.php">Pocetna</a>'."<br>";
                        echo '<a href="./registracija.php">Registracija</a>'."<br>";
                        echo '<a href="../pivnice.php">Pivnice</a>'."<br>";
                        echo '<a href="../zemljePodrijetla.php">Zemlje podrijetla</a>'."<br>";
                        // echo '<a href="../pive.php">Pive</a>'."<br>";
                        // echo '<a href="../popispiva.php">Popis piva</a>'."<br>";
                        echo '<a href="../pregledDnevnika.php">Pregled dnevnika</a>'."<br>";
                        echo '<a href="../blokiranjekorisnika.php">Blokiranje korisnika</a>'."<br>";
                        echo '<a href="../statistikaRadaSustava.php">Statistika rada sustava</a>'."<br>";
                        echo '<a href="../o_autoru.html">O autoru</a>'."<br>";
                        echo '<a href="../dokumentacija.html">Dokumentacija</a>'."<br>";
                        
                    }
                    if(isset($_SESSION["uloga"]) && $_SESSION["uloga"] == 3){
                        echo '<a href="../index.php">Pocetna</a>'."<br>";
                        echo '<a href="./registracija.php">Registracija</a>'."<br>";
                        echo '<a href="../pive.php">Pive</a>'."<br>";
                        echo '<a href="../popispiva.php">Popis piva</a>'."<br>";
                        echo '<a href="../sveNarudzbeKorisnika.php">Sve narudzbe korisnika</a>'."<br>";
                        echo '<a href="../statistikaM.php">Statistika rada sustava</a>'."<br>";
                        echo '<a href="../o_autoru.html">O autoru</a>'."<br>";
                        echo '<a href="../dokumentacija.html">Dokumentacija</a>'."<br>";
                    }
                    if(isset($_SESSION["uloga"]) && $_SESSION["uloga"] == 2){
                        echo '<a href="../index.php">Pocetna</a>'."<br>";
                        echo '<a href="./registracija.php">Registracija</a>'."<br>";
                        echo '<a href="../popis.php">Popis</a>'."<br>";
                        echo '<a href="../kosarica.php">Kosarica</a>'."<br>";
                        echo '<a href="../narudzba.php">Narudzba</a>'."<br>";
                        echo '<a href="../povijestNarudzbi.php">Povijest narudzbi</a>'."<br>";
                        echo '<a href="../statistikaR.php">Statistika rada sustava</a>'."<br>";
                        echo '<a href="../o_autoru.html">O autoru</a>'."<br>";
                        echo '<a href="../dokumentacija.html">Dokumentacija</a>'."<br>";
                    }
                    if(isset($_SESSION["uloga"]) && $_SESSION["uloga"] == 1){
                        echo '<a href="../index.php">Pocetna</a>'."<br>";
                        echo '<a href="../ranglista.php">Rang lista</a>'."<br>";
                        echo '<a href="./registracija.php">Registracija</a>'."<br>";
                        echo '<a href="../o_autoru.html">O autoru</a>'."<br>";
                        echo '<a href="../dokumentacija.html">Dokumentacija</a>'."<br>";
                    }
                    if ($_SESSION["korisnik"] !="gost") {
                        echo '<a href="./odjava.php">Odjava</a>'."<br>";
                    }
                    ?>
                </ul>
            </div>
    </header>
    <div class="formaZaPrijavu">
        <form id="f1" method="POST" action="prijava.php">
            <h2 class="prijavljivanje"><u>Forma za prijavu</u></h2>
            
            <label for="korime" class="korime">Korisničko ime: </label>
            <input type="text" maxlength="30" id="korime" name="korime" value = "<?php if(isset($_COOKIE['korime'])){echo $_COOKIE['korime'];}; ?>"><br><br>

            <label for="loz" class="loz">Lozinka: </label>
            <input type="password" maxlength="30" id="loz" name="loz" value = "<?php if(isset($_COOKIE['loz'])){echo $_COOKIE['loz'];}; ?>"><br><br>

            <label for="zapamti" class="zapamti">Zapamti me: </label>
            <input type="checkbox" id="zapamti" value="da">
            <br> <br>
            
            <br> <br>
        </form>
        <button id="zabButton" name="zabB" class="zabloz" type="submit">Zaboravljena lozinka?</button>
        <button form ="f1" id ="login" name = "prijaviButton" class="prijava" type="submit" value="Prijava">Prijavi se</button>
    </div>
    <footer>
        <img src="../materijali/htmlValidator.png" width="60" height="60" alt="html logo">
        <p>&copy; 2022 <a href="mailto:filipantunovic29@gmail.com">Filip Antunović</a></p>
        <img src="../materijali/cssValidator.png" width="60" height="60" alt="css logo">
    </footer>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            $("#login").click(function(){
                var korime = $("#korime").val();
                var loz = $("#loz").val();
                //var zapamti = $("#zapamti").val();

                if(korime == "" || loz == ""){
                    alert("Niste unijeli korisnicko ime i lozinku");
                } 
                else{
                    $.ajax({
                        method: "POST",
                        url: "prijava.php",
                        data:{
                            login: 1,
                            korime: korime,
                            loz: loz
                        },
                        dataType: "JSON",
                        success: function(response){
                            if(response == "success"){
                                alert("dobro je");
                                window.location.href = "../index.php";
                            }
                            else if(response == "blocked"){
                                alert("Blokirani ste, ne mozete se vise logirati");
                            }
                            else if(response == "nijeaktivirano"){
                                alert("Jos uvijek niste aktivirali email");
                            }
                            else if(response == "blockedEntry"){
                                alert("Blokirani ste, ne mozete se vise logirati");
                            }
                            else{
                                alert(response);
                            }
                        }
                    });
                }
            })
            $("#zabButton").on('click', function(){
                var zapamti = $("#zapamti").val();
                var korime = $("#korime").val();
                $.ajax({
                    method: "POST",
                    url: "prijava.php",
                    data:{
                        zapamti: 1,
                        korime: korime,
                    },
                    dataType: "JSON",
                    success: function(response){
                        if(response == "success"){
                            alert("Email je uspjesno poslan na vas email");
                        }
                        else{
                            alert("Niste unijeli korisnicko ime koje je potrebno za slanje emaila");
                        }
                    }
                })
            })
        })
    </script>
</body>
</html>