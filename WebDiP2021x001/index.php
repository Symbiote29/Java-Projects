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
    header("Location: ./obrasci/prijava.php");
}

$veza = new Baza();
$veza->spojiDB();

//$sql = "SELECT * FROM pivo";
$sql = "SELECT * FROM vrsta_piva";

$result = $veza->selectDB($sql);

while($row = mysqli_fetch_assoc($result)){
    $nazivPiva = $row['naziv'];
    $cijena = $row['cijena'];
    $rokTrajanja = $row['rokTrajanja'];
    $opis = $row['opis'];
    $slika = $row['slika'];
    $ocjena = $row['ocjena'];
    $zemljaPodrijetlaID = $row['zemlja_podrijetla_id'];
}                           

$sql2 = "SELECT * FROM vrsta_piva";
$res = $veza->selectDB($sql2);

// $row = mysqli_fetch_array($result);
// $userres = mysqli_query($veza->spojiDB(), $sql);

//$veza->zatvoriDB();

?>

<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Početna stranica</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="./css/style.css" rel="stylesheet">
        <script type="text/javascript" src="./javascript/fantunovi.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.css" 
        integrity="sha512-aOG0c6nPNzGk+5zjwyJaoRUgCdOrfSDhmMID2u4+OIslr0GjpLKo7Xm0Ao3xmpM4T8AmIouRkqwj1nrdVsLKEQ==" 
        crossorigin="anonymous" referrerpolicy="no-referrer" />
    </head>
    <body>
        <header class="header">
            <a href="#proba2"><h1 class="p1">Početna stranica</h1></a>
            <img class = "img-logo" src="./materijali/logo.png" alt="logo">
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
                        echo '<a href="./povijestNarudzbi.php">Povijest narudzbi</a>'."<br>";
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

            <?php

            if(isset($_POST['unsortedList'])){
                //$sql2 = "SELECT * FROM vrsta_piva";
                $sql2 = "SELECT vp.idvrsta_piva, vp.naziv, vp.cijena, vp.rokTrajanja, vp.opis, vp.slika, vp.ocjena, vp.receptID, vp.zemlja_podrijetla_id,
                    vp.moderator_id, zp.nazivDrzave FROM vrsta_piva vp, zemlja_podrijetla_piva zp WHERE vp.zemlja_podrijetla_id = zp.idzemlja_podrijetla_piva";
                $res = $veza->selectDB($sql2);
            }
            elseif(isset($_POST['sortZemlje'])){
                $sql2 = "SELECT vp.idvrsta_piva, vp.naziv, vp.cijena, vp.rokTrajanja, vp.opis, vp.slika, vp.ocjena, vp.receptID, vp.zemlja_podrijetla_id,
                    vp.moderator_id, zp.nazivDrzave FROM vrsta_piva vp, zemlja_podrijetla_piva zp WHERE vp.zemlja_podrijetla_id = zp.idzemlja_podrijetla_piva 
                    ORDER BY zp.nazivDrzave";
                $res = $veza->selectDB($sql2);
            }
            elseif(isset($_POST['sortCijena'])){
                //$sql2 = "SELECT * FROM vrsta_piva ORDER BY cijena";
                $sql2 = "SELECT vp.idvrsta_piva, vp.naziv, vp.cijena, vp.rokTrajanja, vp.opis, vp.slika, vp.ocjena, vp.receptID, vp.zemlja_podrijetla_id,
                    vp.moderator_id, zp.nazivDrzave FROM vrsta_piva vp, zemlja_podrijetla_piva zp WHERE vp.zemlja_podrijetla_id = zp.idzemlja_podrijetla_piva 
                    ORDER BY vp.cijena";
                $res = $veza->selectDB($sql2);
            }
            ?>

            <form method="POST">
                <button id="unsortedList" name="unsortedList">Nesortirana lista piva</button>
                <button id="sortZemlje" name="sortZemlje">Sortiraj po zemlji podrijetla</button>
                <button id="sortCijena" name="sortCijena">Sortiraj po cijeni</button>

                <table class="svaPiva" border="2">
                    <tr>
                        <th>ID piva</th>
                        <th>Naziv piva</th>
                        <th>Cijena</th>
                        <th>Rok trajanja</th>
                        <th>Opis</th>
                        <th>Slika</th>
                        <th>Ocjena</th>
                        <th>ID recepta</th>
                        <th>ID zemlje podrijetla</th>
                        <th>Naziv drzave</th>
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
                                    <td>'.$podaci['nazivDrzave'].'</td>
                                    <td>'.$podaci['moderator_id'].'</td>
                                </tr>
                            ';
                        }
                    }
                    
                    ?>
                </table>
            </form>

            <section>

                <ul>
                    <li class="lista active" data-filter="sve">No rating</li>
                    <li class="lista" data-filter="5">5</li>
                    <li class="lista" data-filter="4">4</li>
                    <li class="lista" data-filter="3">3</li>
                    <li class="lista" data-filter="2">2</li>
                    <li class="lista" data-filter="1">1</li>
                </ul>

                <div class="pive">
                    <div class="ocjena 4">
                        <img src="./materijali/Ozujsko.jpg" width="400" height="300">
                        <div class="red">
                            <p class="cijena">Cijena - 20 Kn</p>
                        </div>
                    </div>
                    <div class="ocjena 5">
                        <img src="./materijali/Karlovacko.jpg" width="400" height="300">
                        <div class="red">
                            <p class="cijena">Cijena - 25 Kn</p>
                        </div>
                    </div>
                    <div class="ocjena 5">
                        <img src="./materijali/ZmajskoPaleAle.jfif" width="400" height="300">
                        <p class="cijena">Cijena - 30 Kn</p>
                    </div>
                    <div class="ocjena 4">
                        <img src="./materijali/ZmajskoPozoj.png" width="400" height="300">
                        <p class="cijena">Cijena - 35 Kn</p>
                    </div>
                    <div class="ocjena 3">
                        <img src="./materijali/Fakin.jpg" width="400" height="300">
                        <p class="cijena">Cijena - 30 Kn</p>
                    </div>
                    <div class="ocjena 3">
                        <img src="./materijali/Guiness.jpg" width="400" height="300">
                        <p class="cijena">Cijena - 50 Kn</p>
                    </div>
                    <div class="ocjena 2">
                        <img src="./materijali/Guiness.jpg" width="400" height="300">
                        <p class="cijena">Cijena - 50 Kn</p>
                    </div>
                    <div class="ocjena 1">
                        <img src="./materijali/AffligemTripel.jfif" width="400" height="300">
                        <p class="cijena">Cijena - 43 Kn</p>
                    </div>
                    <div class="ocjena 4">
                        <img src="./materijali/AmericanPaleAle.jpg" width="400" height="300">
                        <p class="cijena">Cijena - 27 Kn</p>
                    </div>  
                    <div class="ocjena 2">
                        <img src="./materijali/ThroatTwister.jpg" width="400" height="300">
                        <p class="cijena">Cijena - 35 Kn</p>
                    </div>
                    <div class="ocjena 1">
                        <img src="./materijali/AmericanPaleAle.jpg" width="400" height="300">
                        <p class="cijena">Cijena - 44 Kn</p>
                    </div>
                    <div class="ocjena 5">
                        <img src="./materijali/BOHBeginning.jfif" width="400" height="300">
                        <p class="cijena">Cijena - 31 Kn</p>
                    </div>
                    <div class="ocjena 4">
                        <img src="./materijali/Fireball.jpg" width="400" height="300">
                        <p class="cijena">Cijena - 22 Kn</p>
                    </div>
                    <div class="ocjena 2">
                        <img src="./materijali/BOHBeginning.jfif" width="400" height="300">
                        <p class="cijena">Cijena - 26 Kn</p>
                    </div>
                    <div class="ocjena 3">
                        <img src="./materijali/ThroatTwister.jpg" width="400" height="300">
                        <p class="cijena">Cijena - 24 Kn</p>
                    </div>
                    <div class="ocjena 4">
                        <img src="./materijali/Guiness.jpg" width="400" height="300">
                        <p class="cijena">Cijena - 25 Kn</p>
                    </div>
                    <div class="ocjena 1">
                        <img src="./materijali/Guiness.jpg" width="400" height="300">
                        <p class="cijena">Cijena - 34 Kn</p>
                    </div>
                </div>
            </section>
        </main>
        <script
            src="https://code.jquery.com/jquery-3.6.0.js"
            integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk="
            crossorigin="anonymous">
        </script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
        <script type="text/javascript">
            $(document).ready(function(){
                $('.lista').click(function(){
                    const value = $(this).attr('data-filter');
                    if(value == "sve"){
                        $('.ocjena').show('1000');
                    }
                    else{
                        $('.ocjena').not('.'+value).hide('1000');
                        $('.ocjena').filter('.'+value).show('1000');
                    }
                })
            })
        </script>        

        <footer>
            <img src="./materijali/htmlValidator.png" width="60" height="60" alt="html logo">
            <p>&copy; 2022 <a href="mailto:filipantunovic29@gmail.com">Filip Antunović</a></p>
            <img src="./materijali/cssValidator.png" width="60" height="60" alt="css logo">
        </footer>
    </body>
</html>