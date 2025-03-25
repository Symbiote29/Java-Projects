<?php

include './baza.class.php';

session_start();

$potrebnaUloga = 2;

if(!isset($_SESSION["uloga"])){
    Sesija::kreirajKorisnika("gost");
}

if($_SESSION["uloga"] < $potrebnaUloga){
    header("Location: .obrasci/prijava.php");
}

$veza = new Baza();
$veza->spojiDB();

$usr = $_SESSION["user"];
$sql = "SELECT * FROM dz4_korisnik WHERE Korisnicko_ime = '$usr'";
$res = $veza->selectDB($sql);

$r = mysqli_fetch_array($res);

$sql2 = "SELECT * FROM narudzba WHERE idkorisnik = '$r[0]'";


$result = $veza->selectDB($sql2);

$veza->zatvoriDB();

?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="./css/kosarica.css" rel="stylesheet">
    <title>Povijest narudzbi</title>
</head>
<body>
    <header>
            <a href="#proba2"><h1 class="o1">Obrazac za narucivanje</h1></a>
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
    <div class="formaZaPrijavu">
        <h1>Povijest narudzbi</h1><br><br>
        <?php
            if(mysqli_num_rows($result) > 0){
                foreach($result as $c){
                    ?>
                    <div class="kosarica">
                        <h3><?php echo $c['narudzba_stavke']; ?></h3>
                        <p class="desc"><?php echo $c['narudzba_status']; ?></p>
                        <p class="price"><?php echo $c['racunStatus']; ?></p>
                        <p class="ocjena"><?php echo $c['narudzba_datum']; ?></p>
                        <p class="ocjena"><?php echo $c['racun_idracun']; ?></p>
                        <p class="ocjena"><?php echo $c['idkorisnik']; ?></p>
                    </div><br>
                    <?php
                }
            }
        ?>
        
        <button id ="backToOrder" class="backToOrder" type="submit" onclick='window.location.replace("./narudzba.php")'>Back to order</button>
    </div>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    <script type="text/javascript">
         $(document).ready(function(){
            $("#backToOrder").on("click", function(){
                window.location.href = "./narudzba.php";
            })
        })
    </script>
</body>
</html>