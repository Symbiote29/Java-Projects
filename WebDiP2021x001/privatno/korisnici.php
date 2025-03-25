<?php

include '../baza.class.php';
include '../sesija.class.php';

$veza = new Baza();
$veza->spojiDB();

$sql = "SELECT Ime, Prezime, Korisnicko_ime, Lozinka, email FROM dz4_korisnik";
$res = $veza->selectDB($sql);

?>


<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Skripta</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="css/style.css" rel="stylesheet">
    </head>
    <body>
        <table>
            <tr>
                <th>Ime</th>
                <th>Prezime</th>
                <th>Korisnicko ime</th>
                <th>Lozinka</th>
                <th>Email</th>
            </tr>
            <?php
            if(mysqli_num_rows($res) > 0){
                while($podaci = mysqli_fetch_assoc($res)){
                    echo '
                        <tr>
                            <td>'.$podaci['Ime'].'</td>
                            <td>'.$podaci['Prezime'].'</td>
                            <td>'.$podaci['Korisnicko_ime'].'</td>
                            <td>'.$podaci['Lozinka'].'</td>
                            <td>'.$podaci['email'].'</td>
                        </tr>
                    ';
                }
            }
            ?>
        </table>
    </body>
</html>