
var timerVariable = setInterval(countUpTimer, 1000);
var totalSeconds = 0;

function countUpTimer() {
  ++totalSeconds;
  var minutes = Math.floor((totalSeconds) / 60);
  var seconds = totalSeconds - (minutes * 60);
  if(minutes === 10 && seconds === 0){
    minutes = 0;
    seconds = 0;
    totalSeconds = 0;
  }
  document.getElementById("timer").innerHTML = minutes + ":" + seconds;
}

function reset(){
  document.getElementById('form1').reset();
  document.getElementById('opis').value = '';
}

function changeLayout(value){
  if(value === 'horizontal'){
    document.getElementById('layout').style.display = flex;
  }
  else if(value === 'vertical'){
    document.getElementById('layout').style.display = inline-block;
  }
}

const change = document.querySelector('select');

change.addEventListener('click', function(){
  document.querySelector('select').classList.add('changeDisplay');
});

function fileValidation(){
  var image = document.getElementById("naziv");
  if(typeof(image.files) != 'undefined'){
    var size = parseFloat(image.files[0].size / (1000 * 1024).toFixed(2));

    if(size > 1){
      document.getElementById("image").style.color = "red";
      document.getElementById("image").innerHTML = '*Naziv:';
      alert('Slika je veca od 1MB');
    }
    else{
      document.getElementById("image").style.color = "white;";
      document.getElementById("image").innerHTML = 'Naziv:';
      alert('Uspjesno ste uploadali sliku');
    }
  }
}

function phoneCheck(){
  var phone = [];
  phone = document.getElementById('mob').value;
  if((phone.length == 12) && (phone[0] == 0) && (phone[1] > 0 && phone[1] <= 9)
  && (phone[2] > 0 && phone[2] <= 9)
  && (phone[3] == " ")
  && (phone[4] > 0 && phone[4] <= 9)
  && (phone[5] > 0 && phone[5] <= 9)
  && (phone[6] > 0 && phone[6] <= 9)
  && (phone[7] == " ")
  && (phone[8] > 0 && phone[8] <= 9)
  && (phone[9] > 0 && phone[9] <= 9)
  && (phone[10] > 0 && phone[10] <= 9)
  && (phone[11] > 0 && phone[11] <= 9)){
    alert("broj mobitela je dobrog formata");
    document.getElementById("phone").style.color = "white";
    document.getElementById("phone").innerHTML = 'Mobitel:';
  }
  else{
    alert("broj mobitela nije dobrog formata");
    document.getElementById("phone").style.color = "red";
    document.getElementById("phone").innerHTML = '*Mobitel:';
  }
}

function dateCheck() {
  var date = [];
  date = document.getElementById("datum").value;
  var rgx = /^([0-2][0-9])\.([0-1][1-9])\.([0-9][0-9][0-9][0-9])\.\s([0-2][0-9]:[0-5][0-9]:[0-5][0-9])$/;

  if(rgx.test(date) == true){
    alert('Datum je dobrog formata!');
    document.getElementById("date").style = "white";
    document.getElementById("date").innerHTML = 'Vrijeme kreiranja:';
    return false;
  }
  else{
    document.getElementById("date").style = "color:red;";
    document.getElementById("date").innerHTML = '*Vrijeme kreiranja:'; 
    alert('Datum je krivog formata!');
    return true;
  }
}

var text3 = document.getElementById("opis");

text3.addEventListener('focusout',function(){
  var text = document.getElementById("opis").value.length;
  var text2 = document.getElementById("opis").value;
  var brojac = 0;
  var tocke = 0;

  if(text > 100){
    alert('Text ima preko 100 znakova');
  }
  else{
    for(let i = 0; i < text2.length; i++){
      if(text2.charAt(i) === ">" || text2.charAt(i) === "<" || text2.charAt(i) === "'" || text2.charAt(i) === '"'){
        brojac++;
      }
      if(text2.charAt(i) === "." && text2.charAt(i+1) === "."){
        brojac++;
        tocke++;
      }
    }
    alert('Text ima manje od 100 znakova a broj gresaka je: ' + brojac + '\n' + 'Broj duplih tocaka: ' + tocke);
    if(text2 == ''){
      document.getElementById("op1").style.color = "red";
      document.getElementById("op1").innerHTML = '*Opis:';
    }
    else if(text2 != ''){
      document.getElementById("op1").style.color = "white";
      document.getElementById("op1").innerHTML = 'Opis:';
    }
  }
});

function validateButton(){
  var file = document.forms["form1"]["naziv"].value;
  var opis = document.forms["form1"]["opis"].value;
  var datum = document.forms["form1"]["datum"].value;
  var month = document.forms["form1"]["mj"].value;
  var phone = document.forms["form1"]["mob"].value;
  var color = document.forms["form1"]["color"].value;

  if(opis === '' || file === '' || datum === '' || month === '' || phone === '' || color === ''){
    alert("Sve mora biti ispravno popunjeno");
    return false;
  }
} 

function show(){
  var x = document.getElementById('placanje');
  if(x.style.display === "none"){
    x.style.display = "block";
  }
  else{
    x.style.display = "none";
  }
}

function shortpass(){
  var pass = document.getElementById('loz');
  if(pass.length < 5){
    alert("Lozinka treba imati najmanje 5 znakova");
  }
}