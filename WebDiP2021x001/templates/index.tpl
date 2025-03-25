{config_load file="konfiguracija.conf"}

<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Početna stranica</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="css/style.css" rel="stylesheet">
    </head>
    <body>
        <header class="header">
            <a href="#proba2"><h1 class="p1">Početna stranica</h1></a>
            <img class = "img-logo" src="materijali/logo.png" alt="logo">
            <a href="#proba1"><img class = "img-izbornik" id="btnLeft" src="../materijali/izbornik.png" alt="logo"></a>
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
                        <a href="popis.php" target="_blank">Popis</a>
                    </li>
                    <li>
                        <a href="era.php" target="_blank">Era</a>
                    </li>
                    <li>
                        <a href="navigacijski.php" target="_blank">Navigacijski</a>
                    </li>
                </ul>
            </div>
        </header>
        <main>
            <figure class = "prviblok">
            </figure>
            <div class="drugiblok">
                <ul>
                    <li class="prvi">
                        <a href="https://9anime.se/watch/one-piece-100?ep=2142" target="_blank"><h3>One piece</h3></a>
                        <p><i>Datum: </i><time datetime="2022-03-20"><i>20.Ožujka</i></time></p>
                        <p><b>No. 1 Anime in the world</b></p>
                        <p>Prati priču Monkey D. Luffija, mladića napravljenog od gume, koji inspiriran svojim idolom iz detinstvo 'Red Haired' Shanksa
                            odlazi na put kako bi pronašao blago iz legendi - 'One Piece'
                        </p>
                        <a class = "link" href="https://9anime.se/watch/one-piece-100?ep=2142" target="_blank">Link na članak</a>
                    </li>
                    <li class="drugi">
                        <a href="https://9anime.se/watch/bleach-806?ep=13793" target="_blank"><h3>Bleach</h3></a>
                        <p><i>Datum: </i><time datetime="2022-03-20"><i>20.Ožujka</i></time></p>
                        <p><b>2nd best Anime in the world</b></p>
                        <p>Prati priču mladog nadobudnog tinejdžera Ichiga Kurosakija koji je nakon što je dobio moći 'Soul Reapera'
                            se bori protiv negativaca i pokušava ostvariti sudbinu svoju roditelja
                        </p>
                        <a class = "link" href="https://9anime.se/watch/bleach-806?ep=13793" target="_blank">Link na članak</a>
                    </li>
                    <li class="treci">
                        <a href="https://9anime.se/watch/naruto-677?ep=12352" target="_blank"><h3>Naruto</h3></a>
                        <p><i>Datum: </i><time datetime="2022-03-20"><i>20.Ožujka</i></time></p>
                        <p><b>3rd best Anime in the world</b></p>
                        <p>Prati priču mladog dječaka Naruta Uzumakija, mladog nindže koji svojim radom i trudom pokušava dobiti 
                            priznanje od seoskih starješina te sanja kako će jednog dana postati 'Hokage'
                        </p>
                        <a class = "link" href="https://9anime.se/watch/naruto-677?ep=12352" target="_blank">Link na članak</a>
                    </li>
                </ul>
            </div>
            <div class="treciblok">
                <ul>
                    <li>Ime: Filip</li>
                    <li>Prezime: Antunović</li>
                    <li>Email: filipantunovic29@gmail.com</li>
                    <li>Broj iksice: 0016143851</li>
                    <li>Slika lica: </li>
                    <li class="zivotopis">Životopis: 
                        <p>Moje ime je Filip Antunović, imam 21 godinu te dolazim
                            iz jednog lijepog grada na dalmatinskoj obali
                            i popularnog turističkog odredišta, Makarske. <br>
                            FOI sam upisao 2019. godine jer me
                            zanima programiranje te sam se kroz godine 
                            dosta poboljšao u tome. <br>Trenutno sam treća godina
                            na fakultetu te, nakon što ove godine završim
                            preddiplomski studij, planiram dalje nastaviti
                            obrazovanje na jednom od diplomskih studija 
                            koje FOI trenutno nudi.
                        </p>
                    </li>
                </ul>
                <img class = "lice" src="materijali/slikalica.jpg" alt="profilna">
            </div>
        </main>
        <footer>
            <img src="/materijali/htmlValidator.png" width="60" height="60" alt="html logo">
            <p>&copy; 2022 <a href="mailto:filipantunovic29@gmail.com">Filip Antunović</a></p>
            <img src="/materijali/cssValidator.png" width="60" height="60" alt="css logo">
        </footer>
    </body>
</html>