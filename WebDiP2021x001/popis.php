<?php
session_start();
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="autor" content="Filip Antunović">
    <meta name="Datum početka izrade" content="19.3.2022">
    <link href="css/popis.css" rel="stylesheet">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
    <script type="text/javascript" src="https://cdn.datatables.net/v/dt/dt-1.11.5/datatables.min.js"></script>
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/dt-1.11.5/datatables.min.css"/>
    
    <script src="./javascript/fantunovi_jquery.js"></script>
    <title>Popis</title>
</head>
<body>
    <header class="header">
        <a href="#proba2"><h1>Popis</h1></a>
        <a href="../index.html"><img class = "img-logo" src="./materijali/logo.png" alt="logo"></a>
        <a href="#proba1"><img class = "img-izbornik" id="btnLeft" src="./materijali/izbornik.png" alt="logo"></a>
        <div class="izbornik" id="proba2">
            <ul class="linkovi" id="proba1">
                <li>
                    <a href="obrasci/registracija.php" target="_blank">Registracija</a>
                </li>
                <li>
                    <a href="obrasci/prijava.php" target="_blank">Prijava</a>
                </li>
                <li>
                    <a href="obrasci/obrazac.php" target="_blank">Obrasci</a>
                </li>
                <li>
                    <a href="multimedija.php" target="_blank">Multimedija</a>
                </li>
                <li>
                    <a href="era.php" target="_blank">Era</a>
                </li>
                <li>
                    <a href="navigacijski.php" target="_blank">Navigacijski</a>
                </li>
                <li>
                    <a href="./plan.html" target="_blank">Test</a>
                </li>
                <li>
                    <a href="./testbrzine.html" target="_blank">Test brzine</a>
                </li>
            </ul>
        </div>
        <h2>Welcome <?php echo $_SESSION["korime"]; ?></h2>
    </header>
    <table class ="tablica1" id="tablica"> -->
        <thead>
            <tr>
                <th>ID_korisnika</th> 
                <th>Ime</th>
                <th>Prezime</th>
                <th>Godina rodenja</th>
                <th>Email</th>
                <th>Korisnicko_ime</th>
                <th>Lozinka</th>
                <th>Tip</th>
                <th>Status</th>
                <th>Neuspjesne_prijave</th>
                <th>Datum_i_vrijeme_blokiranja</th>
            </tr>
        </thead>
        <tbody>
            <tr data-href = "obrasci/registracija.php">
                <th>1</th> 
                <th>Karlo</th>
                <th>Karlic</th>
                <th>11.3.2003</th>
                <th>kkarlic@foi.hr</th>
                <th>kkarlic</th>
                <th>karlo456</th>
                <th>Moderator</th>
                <th>aktivan</th>
                <th>1</th>
                <th>12.1.2022</th>
            </tr>
        </tbody>
        <tfoot>

        </tfoot>
    </table>
    <footer>
        <img src="./materijali/htmlValidator.png" width="60" height="60" alt="html logo">
        <p>&copy; 2022 <a href="mailto:filipantunovic29@gmail.com">Filip Antunović</a></p>
        <img src="./materijali/cssValidator.png" width="60" height="60" alt="css logo">
    </footer>

    <script type="text/javascript">
        document.addEventListener("DOMContentLoaded", () => {
            const rows = document.querySelectorAll("tr[data-href]");

            rows.forEach(row =>{
                row.addEventListener("click", () =>{
                    window.location.href = "./registracija.php";
                });
            });
        });
    </script>

</body>
</html>