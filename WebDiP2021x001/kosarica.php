<?php

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


$veza = new Baza();
$veza->spojiDB();


$sql = "SELECT * FROM narudzba WHERE korisnik_id = '1'";

$result = $veza->selectDB($sql);

$datum = date("Y-m-d H:i:s");

if(isset($_POST['payButton'])){
    if(!empty($_POST['vrstaPlacanja'])){
        $vrsta = $_POST['vrstaPlacanja'];
    }
    $z = [];
    foreach($_SESSION['pivka'] as $item){
        array_push($z, $item);
        //echo $item.",";
    }
    $string = implode(', ',$z);
    $sql4 = "SELECT idracun FROM racun WHERE racun_stavke = '$string'";
    $idRacun = $veza->selectDB($sql4);

    $sql5 = "INSERT INTO narudzba (narudzba_stavke, narudzba_status, racunStatus, narudzba_datum, racun_idracun, ukupniIznosRacuna, placeniIznosRacuna, idkorisnik, idPivnice) 
    VALUES('$string', 'U skladistu', 'Nije placeno' '$datum', '9', '50 HRK', '0 HRK', 2, 1)";
    $veza->selectDB($sql5);
}

?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="./css/kosarica.css" rel="stylesheet">
    <script type="text/javascript" src="./javascript/fantunovi.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    <title>Kosarica</title>
</head>
<body>
<header>
        <a href="#proba2"><h1 class="o1">Kosarica</h1></a>
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
        <h1>Vasa kosarica</h1><br><br>
        <button id ="pay" class="pay" type="button" onclick='show()'>Payment</button>
        <button id ="backToOrder" class="backToOrder" type="submit" onclick='window.location.replace("./narudzba.php")'>Back to order</button>
        <div class="narudzba">
            <?php
            
            if(!empty($_POST["beer"])){
                echo '<h2>You have selected the following beers:</h2><br><br>';
                $polje = array();
                
                foreach($_POST["beer"] as $b){
                    echo '<h3>'.$b.'</h3>';
                    array_push($polje, $b);
                    //array_push($_SESSION['pivka'], $polje);
                    
                    //array_push($_SESSION['pivka'],$b);
                    //$_SESSION['pivka'] =$b.',';
                    
                    ?>
                    <br>
                    <img src="./materijali/<?php echo $b; ?>.jpg" name="test2"><br><br><br>
                    <?php
                }
                $_SESSION['pivka'] = $polje;
                // $p = [];
                // array_push($p, $_SESSION['pivka']);
                // print_r($p);

                // //$p = $_SESSION['pivka'];
                // $_SESSION['beers'] = [];
                // $t = [];
                // foreach($_SESSION['pivka'] as $item => $value){
                //     array_push($t, $value);
                //     //echo $value.",";
                // }
                // $_SESSION['beers'] = $t;
                // echo '<h2>Status narudzbe: </h2>'?><h2>Otvoreno</h2>
                <?php
            }
            else{
                echo '<h2>Nemate stavki u vasoj kosarici</h2><br>';
            }
            
            ?>
            <br>
        </div>
        <form id="payment" method="POST">
            <div id="placanje" class="paymentType">
                <label for="nacinPlacanja" class="nacinPlacanja">Nacin placanja</label>

                <select id="c" name="vrstaPlacanja">
                    <option value="Placeno u potpunosti">U potpunosti</option>
                    <option value="Djelomicno placeno">Djelomicno</option>
                </select>

                <select id="nacinPlacanja" name="nacinPlacanja">
                    <option value="gotovina">Gotovina</option>
                    <option value="kartica">Kartica</option>
                    <option value="paypal">PayPal</option>
                    <option value="crypto">Crypto</option>
                </select>

                <button form="payment" id="payBtn" class="payButton" name="payButton">Pay</button>
            </div>
        </form>
        
    </div>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script>
        function show(){
            var x = document.getElementById('placanje');
            if(x.style.display === "none"){
                x.style.display = "block";
            }
            else{
                x.style.display = "none";
            }
        }
    </script>
    <!-- <script type="text/javascript">
         $(document).ready(function(){
            <?php
            $z = [];
            foreach($_SESSION['pivka'] as $item){
                array_push($z, $item);
                echo $item.",";
            }
            $string = implode(', ',$z);
            //echo $string;
            ?>
            $("#payBtn").click(function(){
                var t = $string;
                alert("Vasa narudzba je uspjesna");
            })
        })
    </script> -->
</body>
</html>