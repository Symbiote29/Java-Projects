<?php

include '../baza.class.php';
include '../sesija.class.php';

session_start();

$veza = new Baza();
$veza->spojiDB();

if(isset($_POST['activate'])){
    $korime = $_POST['kor_ime'];
    $kod = $_POST['aktKod'];

    if(empty($_POST['aktKod'])){
        echo "Niste unijeli vas aktivacijski kod";
    }
    elseif($kod != $_SESSION['kod']){
        echo "Unijeli ste krivi aktivacijski kod";
    }
    else{
        $sql = "UPDATE dz4_korisnik SET otp = '$kod' WHERE Korisnicko_ime = '$korime'";
        $veza->selectDB($sql);
        echo "Vas email je aktiviran";
        header("Location: ./prijava.php");
    }
}

?>

<!DOCTYPE html>
<html lang="en">
<head>
        <title>Email aktivacija</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="../css/style.css" rel="stylesheet">
    </head>
<body>
    <h1 class="popisPivaUpivnici">Email aktivacija</h1>
    <form action="./emailaktivacija.php" method="POST">
        <button id="aktivirajEmail" class="aktiviraj" name="activate">Aktiviraj email</button>

        <div class="unosAktivacijskogKoda">
            <label for="kIme" class="k">Unesite vase korisnicko ime</label>
            <input type="text" id="kIme" name="kor_ime">

            <label for="kod" class="code">Unesite vas aktivacijski kod</label>
            <input id="kod" type="text" name="aktKod">
        </div>
    </form>

    </script>
</body>
</html>