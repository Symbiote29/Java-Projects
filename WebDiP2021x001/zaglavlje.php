<?php

require "$direktorij/baza.class.php";
require "$direktorij/dnevnik.class.php";
require "$direktorij/sesija.class.php";

// porketanje sesije
//Sesija::kreirajSesiju();

// "$direktorij/templates"
// "$direktorij/templates_c"
// "$direktorij/cache"
// "$direktorij/configs"

// $smarty = new Smarty();
// $smarty->setTemplateDir("$direktorij/templates")
//         ->setCompileDir("$direktorij/templates_c")
//         ->setPluginsDir(SMARTY_PLUGINS_DIR)
//         ->setCacheDir("$direktorij/cache")
//         ->setConfigDir("$direktorij/configs");
// $smarty->configLoad('konfiguracija.conf');
// $razina = $smarty->getConfigVars('razina_izvjestavanja');
//error_reporting(constant($razina));

// obrisi sesiju GET parametrom 'obrisi'
// dodati link u navigaciju za brisanje sesije
// if (isset($_GET["obrisi"])) {
//     Sesija::obrisiSesiju();
// }

// vrijeme i virtualno vrijeme (na početku je isto)
// treba spremiti i učitati iz konfiguracije 
// $vrijeme_servera = time();
// $vrijeme_sustava = time();

// // pomak iz konfiguracije
// if (isset($_GET["primjeni"])) {
//     $pomak = $smarty->getConfigVars('pomak');
//     $vrijeme_sustava = $vrijeme_servera + ($pomak * 60 * 60);
// }

/* /**
 * dnevnik
 * @param string $tekst
 * Funkcija prima tekst i dodaje zapis u dnevnik.log u formatu: "d.m.y. hⓂ️s, tekst"
 */
// function dnevnik($tekst) {
//     global $direktorij;
//     $sada = date('d.m.Y. H:i:s');
//     $fp = fopen("$direktorij/izvorne_datoteke/dnevnik.log", "a+");
//     // potrebno je dozvoliti pisanje u datoteku (chmod 777 nazivDatoteke)
//     fwrite($fp, $sada);
//     fwrite($fp, ", ");
//     fwrite($fp, $tekst);
//     fwrite($fp, "\n");
//     fclose($fp);
// }

/* /**
 * vrati_zapis
 * @param string $naziv
 * Funkcija prima naziv datoteke i vraća istu u asocijativnom polju gdje je ključ datum a tekst vrijednost
 /

function vrati_zapis($naziv) {
    $fp = fopen($naziv, "r");
    $rezultat = fread($fp, filesize($naziv));
    fclose($fp);

    $polje = explode("\n", $rezultat);

    //zbog NULL vrijednosti na početku i kraju
    for ($i = 1; $i < count($polje) - 1; $i++) {
        $kljuc = explode(", ", $polje [$i]);
        $ascPolje[$kljuc[0]] = $kljuc [1];
    }
    return $ascPolje;
}

// ispis polja
function ispis_polja($polje) {
    global $noviRedak;
    $ispis = '';
    foreach ($polje as $k => $v) {
        $ispis .= "k:$k => v:$v $noviRedak";
    }
    return $ispis;
}

// Zapis u dnevnik uz pomoću klase
/
  $dnevnik = new Dnevnik();
  $dnevnik->spremiDnevnik("Testni Text u log!");
 */
// if (isset($_COOKIE)) {
//     if (isset($_GET['obrisi']) && $_GET['obrisi'] == "kolacic") {
//         // brisanje koristeći get parametar obrisi
//         // brisanje kolačiča pod nazivom autenticiran
//         unset($_COOKIE['autenticiran']);
//     }
// }

// postavi pomak vremena
// if (isset($_GET['postavi'])) {
//     header("Location: http://barka.foi.hr/WebDiP/pomak_vremena/vrijeme.html%22");
// }

// // preuzmi trenutni pomak u XML formatu i ispiši vritualno vrijeme // treba procitati iz konfiguracije
// if (isset($_GET['virtualno'])) {
//     $url = "http://barka.foi.hr/WebDiP/pomak_vremena/pomak.php?format=xml";

//     if (!($fp = fopen($url, 'r'))) {
//         echo 'Problem: nije moguće otvoriti url:' . $url;
//         exit;
//     }
//     $xml_string = fread($fp, 10000);
//     fclose($fp);

//     $domdoc = new DOMDocument;
//     $domdoc->loadXML($xml_string);

//     $params = $domdoc->getElementsByTagName('brojSati');
//     $sati = 0;

//     if ($params != NULL) {
//         $sati = $params->item(0)->nodeValue;
//     }

//     // postavimo vritualno vrijeme
//     $vrijeme_sustava = $vrijeme_servera + ($sati * 60 * 60);
//     // TODO spremiti u konfiguracijsku datoteku ili tablicu baze podataka
// }


//$smarty->assign('naslov', $naslov);
//$smarty->assign('putanja', $putanja);

//$smarty->display('zaglavlje.tpl');
//$smarty->assign('vrijeme_sustava', $vrijeme_sustava);