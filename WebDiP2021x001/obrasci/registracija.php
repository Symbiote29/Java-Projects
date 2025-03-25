<?php 

$direktorij = dirname(getcwd());

include '../baza.class.php';
include '../sesija.class.php';

session_start();

$potrebnaUloga = 1;

//Sesija::kreirajSesiju();

if(!isset($_SESSION["uloga"])){
    Sesija::kreirajKorisnika("gost");
}

if($_SESSION["uloga"] < $potrebnaUloga){
    header("Location: ./prijava.php");
}

if(isset($_POST['register'])){

    $veza = new Baza();
    $veza->spojiDB();

    $korimeP = $_POST['korime'];

    
    $imeP = $_POST['ime'];
    $prezimeP = $_POST['prezime'];
    $grodP = $_POST['grod'];
    $emailP = $_POST['email'];
    $lozP = $_POST['loz'];
    $plozP = $_POST['ploz'];
    $datum = date('Y-m-d H:i:s');
    $secretkey = "6LdM4GcgAAAAADDndJVclIXbFcztwLE8X-V-vJJc";

    $_SESSION['korime'] = $korimeP;

    $uvjetiKoristenja = $_POST['uvjetiKoristenja'];
    $zapamtiMe = $_POST['zapamtiMe'];
    $popuniPodatke = $_POST['popuniPodatke'];
    $redoslijedPrikazivanja = $_POST['redoslijedPrikazivanja'];

    $sql = "SELECT * FROM dz4_korisnik WHERE ". "Korisnicko_ime = '{$korimeP}'";
    $result = $veza->selectDB($sql);

    $user = mysqli_fetch_array($result);


    $znakovi = "/[!#$%&]/";
    $rgxgrod = "/^\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$/";
    $rgxemail = "/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.+-]+\.com$/";

    //1
    if(strlen($lozP) < 5){
        exit(json_encode("short"));
    }

    //2
    elseif(!preg_match($znakovi, $lozP)){
        exit(json_encode("znakovi"));
    }

    //3
    elseif($lozP != $plozP){
        exit(json_encode("iste"));
    }

    //4
    elseif(!preg_match($rgxemail, $emailP)){
        exit(json_encode("m"));
    }

    //5
    elseif(!preg_match($rgxgrod, $grodP)){
        exit(json_encode("d"));
    }


    elseif($korimeP == "" || $imeP == "" || $prezimeP == "" || $lozP == "" || $grodP == "" || $emailP == ""){
        exit(json_encode("error"));
    }

    elseif($user[5] == $korimeP){
        exit(json_encode("exists"));
    }
    else{
        $mail_to = $emailP;
        //$mail_to = "filipantunovic29@gmail.com";
        //$mail_to = "filipantunovic29@gmail.com";
        $mail_from = "From: fantunovi@foi.hr";
        $mail_subject = "Email aktivacijski kod";

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
        $_SESSION['kod'] = $mail_body;

        mail($mail_to, $mail_subject, $mail_body, $mail_from);
        $upit = "INSERT INTO dz4_korisnik (Ime, Prezime, Godina_rodenja, Email, Korisnicko_ime, Lozinka, PotvrdaLozinke, UlogaKorisnika, Kolacici_idKolacici, BrojPokusaja, Blokiran, otp, verify, datumRegistracije)
            VALUES ('$imeP','$prezimeP','$grodP','$emailP','$korimeP','$lozP','$plozP','2','1','0','0','0','1','$datum')";
        $veza->selectDB($upit);
        exit(json_encode("success"));
        // if (mail($mail_to, $mail_subject, $mail_body, $mail_from)) {
        //     $upit = "INSERT INTO dz4_korisnik (Ime, Prezime, Godina_rodenja, Email, Korisnicko_ime, Lozinka, PotvrdaLozinke, UlogaKorisnika, Kolacici_idKolacici, BrojPokusaja, Blokiran, otp, verify, datumRegistracije)
        //     VALUES ('$imeP','$prezimeP','$grodP','$emailP','$korimeP','$lozP','$plozP','2','1','0','0','0','1','$datum')";
        //     $veza->selectDB($upit);
        //     exit(json_encode("success"));
        // }
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
    <link href="../css/registracija.css" rel="stylesheet">
    <!-- <script src="../javascript/fantunovi.js"></script> -->
    <script src="https://www.google.com/recaptcha/api.js" async defer></script>
    <title>Registracija</title>
</head>
<body>
    <header>
        <a href="#proba2"><h1 class="o1">Obrazac za registraciju</h1></a>
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
                        // echo '<a href="./obrazac.php">Obrazac</a>'."<br>";
                        echo '<a href="../popis.php">Popis</a>'."<br>";
                        echo '<a href="../kosarica.php">Kosarica</a>'."<br>";
                        echo '<a href="../narudzba.php">Narudzba</a>'."<br>";
                        echo '<a href="../kosarica.php">Kosarica</a>'."<br>";
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
        <div class="formaZaRegistraciju">
            <form id = "f2" action="./registracija.php" method="POST" enctype="multipart/form-data">
                <h2 class="registriranje"><u>Forma za registraciju</u></h2>

                <label for="ime" class="imeA">Ime: </label>
                <input type="text" id="ime" autofocus><br><br>

                <label for="prezime" class="prezimeA">Prezime: </label>
                <input type="text" id="prezime"><br><br>

                <label for="grod" class="grodA">Godina rođenja: </label>
                <input type="text" id="grod" onchange="gr(this.value)"><br><br>

                <script>
                
                function gr(val){
                    var rgxgrod = /^\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$/;
                    var g = document.getElementById('grod').value;
                    var f = rgxgrod.test(g);
                    if(!f){
                        alert("Datum je krivog formata");
                    }
                }

                </script>

                <label for="email" class="emailA">Email: </label>
                <input type="email" id="email" placeholder="ldap@gmail.com" value="" onchange="mail(this.value)"><br><br>
                
                <script>
                function mail(val){
                    var mail = document.getElementById('email');
                    var rgxemail = new RegExp("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.+-]+\.com");
                    if(!rgxemail.test(mail.value)){
                        alert("Email mora sadrzavati @ i zavrsavati sa .com");
                    }
                }
                </script>

                <label for="korime" class="korimeA">Korisničko ime: </label>
                <input type="text" id="korime"><br><br>

                <label for="loz" class="lozA">Lozinka: </label>
                <input type="password" id="loz" value="" onchange="test(this.value)"><br><br>

                <script>
                function test(val){
                    var l = document.getElementById('loz');
                    var rgxloz = new RegExp("(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{5,}");
                    if(l.value.length < 5){
                        alert("Lozinka mora imati najmanje 5 znakova");
                    }
                    if(!rgxloz.test(l.value)){
                        alert("Lozinka mora sadrzavati minimalno 5 znakova, 1 broj i jedan specijalni znak");
                    }
                }
                </script>

                <label for="ploz" class="plozA">Potvrda lozinke: </label>
                <input type="password" id="ploz" onchange="iste(this.value)"><br><br>

                <script>
                function iste(val){
                    var l = document.getElementById('loz');
                    var pl = document.getElementById('ploz');
                    if(l.value != pl.value){
                        alert("Lozinka i potvrda lozinke moraju biti iste");
                    }
                }
                </script>

                <label for="cookies" class="cookies">Dozvola korištenja kolačića: </label>
                <br>

                <input type="hidden" value = "0">
                <input type="checkbox" id="uvjetiKoristenja" value="1">
                <label for="uvjetiKoristenja"> Uvjeti koristenja</label><br>

                <input type="hidden" value = "0">
                <input type="checkbox" id="zapamtiMe" value="1">
                <label for="zapamtiMe"> Zapamti me</label><br>

                <input type="hidden" value = "0">
                <input type="checkbox" id="popuniPodatke" value="1">
                <label for="popuniPodatke"> Popuni podatke</label><br>

                <input type="hidden" value = "0">
                <input type="checkbox" id="redoslijedPrikazivanja" value="1">
                <label for="redoslijedPrikazivanja"> Redoslijed prikazivanja</label><br><br>

                <br> <br>
                <div class="g-recaptcha" data-sitekey="6LdM4GcgAAAAAIKoX1pJYdaeBE7qwZhVTl9dg3Ks"></div>
            </form>
            <button id="register" form="f2" class = "registracija" type="button" value="Registriraj">Registriraj se</button>
        </div>
    <footer>
        <img src="/materijali/htmlValidator.png" width="60" height="60" alt="html logo">
        <p>&copy; 2022 <a href="mailto:filipantunovic29@gmail.com">Filip Antunović</a></p>
        <img src="/materijali/cssValidator.png" width="60" height="60" alt="css logo">
    </footer>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            $('#register').click(function(){
                var korime = $("#korime").val();
                var ime = $("#ime").val();
                var prezime = $("#prezime").val();
                var grod = $("#grod").val();
                var email = $("#email").val();
                var loz = $("#loz").val();
                var ploz = $("#ploz").val();
                var uvjetiKoristenja = $("#uvjetiKoristenja").val();
                var zapamtiMe = $("#zapamtiMe").val();
                var popuniPodatke = $("#popuniPodatke").val();
                var redoslijedPrikazivanja = $("#redoslijedPrikazivanja").val();
                $.ajax({
                    method: "POST",
                    url: "registracija.php",
                    data:{
                        register: 1,
                        korime: korime,
                        ime: ime,
                        prezime: prezime,
                        grod: grod,
                        email: email,
                        korime: korime,
                        loz: loz,
                        ploz: ploz,
                        uvjetiKoristenja: uvjetiKoristenja,
                        zapamtiMe: zapamtiMe,
                        popuniPodatke: popuniPodatke,
                        redoslijedPrikazivanja: redoslijedPrikazivanja
                    },
                    dataType: "JSON",
                    success: function(response){
                        if(response == "success"){
                            alert("Dobro korisnicko ime");
                            window.location.replace("./emailaktivacija.php");
                        }
                        else if(response == "error"){
                            alert("Niste popunili sve kucice");
                        }
                        else if(response == "exists"){
                            alert("Korisnik s unesenim korisnickim imenom vec postoji");
                        }
                        else if(response == "short"){
                            alert("Lozinka treba imati minimalno 5 znakova");
                        }
                        else if(response == "znakovi"){
                            alert("Lozinka mora sadrzavati jedan od specijalnih znakova");
                        }
                        else if(response == "iste"){
                            alert("Lozinka i potvrda lozinke moraju biti iste");
                        }
                        else if(response == "m"){
                            alert("Email mora zavrsavati sa .com");
                        }
                        else if(response == "d"){
                            alert("Datum je krivog formata\nTocan format je 2020-01-01");
                        }
                    }
                })
            })
        });
    </script>

</body>
</html>