<?php

$putanja = dirname($_SERVER['REQUEST_URI'], 2);
$direktorij = dirname(getcwd());

include './baza.class.php';
include './sesija.class.php';

session_start();

$potrebnaUloga = 1;

if(!isset($_SESSION["uloga"])){
    Sesija::kreirajKorisnika("gost");
}

if($_SESSION["uloga"] < $potrebnaUloga){
    header("Location: .obrasci/prijava.php");
}

$veza = new Baza();
$veza->spojiDB();

if(isset($_POST['filterDate'])){
    $pivnica = $_POST['pivnica'];
    $o = $_POST['od'];
    $d = $_POST['do'];
    $sql2 = "SELECT * FROM narudzba WHERE narudzba_datum BETWEEN '$o' AND '$d' AND idPivnice = '$pivnica'";
    $res = $veza->selectDB($sql2);

    $sql3 = "SELECT idPivnice, COUNT(*) AS c, narudzba_datum FROM narudzba WHERE 
        narudzba_datum BETWEEN '$o' AND '$d' GROUP BY idPivnice";
    $res2 = $veza->selectDB($sql3);
}

?>

<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Rang lista</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="css/style.css" rel="stylesheet">
        <script type="text/javascript" src="./javascript/fantunovi.js"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.js"></script>
        <script src="https://code.jquery.com/ui/1.13.1/jquery-ui.js"></script>
    </head>
    <body>
        <header class="header">
            <a href="#proba2"><h1 class="p1">Rang lista</h1></a>
            <img class = "img-logo" src="./materijali/logo.png" alt="logo">
            <a href="#proba1"><img class = "img-izbornik" id=" Left" src="./materijali/izbornik.png" alt="logo"></a>
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
                        echo '<a href="./ranglista.php">Rang lista</a>'."<br>";
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
        <main>
            <figure class = "prviblok">
            </figure>
            
            <div class="card-body">
                <form method="POST">
                    <div class="row">

                        <!-- <div class="col-md-4">
                            <label >Unesite ID pivnice</label>
                            <input type="text" name="pivnica" class="form-control">
                        </div> -->
                        <div class="col-md-4">
                            
                            <label for="">Od datum</label>
                            <input type="date" name="od" class="form-control">
                        </div>
                        <div class="col-md-4">
                            <label for="">Do datum</label>
                            <input type="date" name="do" class="form-control">
                        </div>
                        <br>
                        <div class="col-md-4">
                            <label for="">Click me</label>
                            <button id="filterDate" name="filterDate" type="submit" class="btn btn-primary px-4">Pretrazi</button>
                        </div>
                    </div>
                    <br><br>

                    <!-- <table class="ranglista" border="2">
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
                    </table> -->
                    <table class="ranglista2" border="2">
                        <tr>
                            <th>ID pivnice</th>
                            <th>Broj narucenih piva</th>
                            <th>Datum narudzbe</th>
                        </tr>
                        <?php
                        if(mysqli_num_rows($res2) > 0){
                            while($podaci = mysqli_fetch_assoc($res2)){
                                echo '
                                    <tr>
                                        <td>'.$podaci['idPivnice'].'</td>
                                        <td>'.$podaci['c'].'</td>
                                        <td>'.$podaci['narudzba_datum'].'</td>
                                    </tr>
                                ';
                            }
                        }
                        ?>
                    </table>

                </form>
            </div>

            <div class="col-md-12">
                <div class="card">
                    <div class="card-body">
                        <div class="row">
                            
                        </div>
                    </div>
                </div>
            </div>
        </main>
        <script
            src="https://code.jquery.com/jquery-3.6.0.js"
            integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk="
            crossorigin="anonymous">
        </script>
        <!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js" 
        integrity="sha512-uto9mlQzrs59VwILcLiRYeLKPPbS/bT71da/OEBYEwcdNUk8jYIy+D176RYoop1Da+f9mvkYrmj5MCLZWEtQuA==" 
        crossorigin="anonymous" referrerpolicy="no-referrer"></script> -->       

        <footer>
            <img src="./materijali/htmlValidator.png" width="60" height="60" alt="html logo">
            <p>&copy; 2022 <a href="mailto:filipantunovic29@gmail.com">Filip Antunović</a></p>
            <img src="./materijali/cssValidator.png" width="60" height="60" alt="css logo">
        </footer>
    </body>
</html>