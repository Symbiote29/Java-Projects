<?php

//$putanja = dirname($_SERVER['REQUEST_URI'], 2);
$direktorij = dirname(getcwd());

//include './zaglavlje.php';

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

if(isset($_POST['order'])){
    $veza = new Baza();
    $veza->spojiDB();

    if(isset($_POST['beer[]'])){
        $piva = $_POST['beer[]'];

        $selected = $_POST['beer[]'];

        header("Location: ./kosarica.php");
    }
    else{
        echo 'please select a value';
    }

    $veza->zatvoriDB();
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
    <link href="./css/narudzba.css" rel="stylesheet">
    <title>Narucivanje</title>
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
        <?php
        //print_r($_COOKIE);
        ?>
        <form id="f1" method="post" action="./kosarica.php">
            <h2 class="narucivanje"><u>Forma za narucivanje</u></h2>
            <br><br>

            <label for="">Odaberite zeljeno pivo: </label><br><br>

            <input type="checkbox" id="Guiness" value="Guiness" name="beer[]">
            <label for="Guiness">Guiness</label><br>

            <input type="checkbox" id="Karlovacko" value="Karlovacko" name="beer[]">
            <label for="Karlovacko">Karlovacko</label><br>

            <input type="checkbox" id="Ozujsko" value="Ozujsko" name="beer[]">
            <label for="Ozujsko">Ozujsko</label><br>

            <input type="checkbox" id="Hill438" value="Hill438" name="beer[]">
            <label for="Hill438">Hill438</label><br>

            <input type="checkbox" id="Fireball" value="Fireball" name="beer[]">
            <label for="Fireball">Fireball</label><br>

            <br> <br>
            <br><br>
        </form>
        
        <button form="f1" id="order" class="prijava" type="submit" value="naruci">Naruci</button>
    </div>
    <footer>
        <img src="./materijali/htmlValidator.png" width="60" height="60" alt="html logo">
        <p>&copy; 2022 <a href="mailto:filipantunovic29@gmail.com">Filip Antunović</a></p>
        <img src="./materijali/cssValidator.png" width="60" height="60" alt="css logo">
    </footer>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
</body>
</html>