$(document).ready(function () {
  //alert("Dokument učitan!");
  naslov = $(document).find("title").text();
  console.log(naslov);
  switch (naslov) {
      case "Obrazac":
          //$("#poruke").html('<p>JavaScript program!</p>');
          /* 
           var blok = $('<p>JavaScript program!</p>');
           blok.append('<p>jQuery </p>')
           blok.append('<p>jQuery Autocomplete</p>')
           blok.append('<p>jQuery DataTables</p>')
           $("#poruke").html(blok);
          */

          greske = true;

          $("#korime").keyup(function (event) {
              //console.log($("#korime").val());
              $.ajax({
                  //url: 'http://barka.foi.hr/WebDiP/2021/materijali/zadace/dz3/korisnik.php?korisnik=' + $("#korime").val(),
                  url: 'http://barka.foi.hr/WebDiP/2021/materijali/zadace/dz3/korisnik.php',
                  type: 'GET',
                  data: { 'korisnik': $("#korime").val() },
                  dataType: 'xml',
                  success: function (xml) {
                      $(xml).find('korisnik').each(function () {
                          console.log(($(this).text()));
                          //console.log(typeof ($(this).text()));
                          //$(this).attr('nazivAtributa');
                          //$(this).text();
                          if ($(this).text() === "1") {
                              $("#korime").attr("style", "border-color:green");
                              greske = false;
                          } else {
                              $("#korime").attr("style", "border-color:red");
                              greske = true;
                          }
                      });
                  },
                  error: function (xhr, status, error) {
                      alert("Pogreške: " + error.responseText);
                  }
              });
          });
          $("#lozinka").keyup(function (event) {
            /* *
             8-20 znakova,
             najmanje 1 alfanumerički znak,
             najmanje 1 broj ili specijalni znak i
             ne smiju se više od 3 znaka ponavljati
             */
            var lozinka = $("#lozinka").val();
            var re = new RegExp((/^(?!.*(.)\1{3})((?=.*[\d])(?=.*[A-Za-z])|(?=.*[^\w\d\s])(?=.*[A-Za-z])).{8,20}$/));
            var ok = re.test(lozinka);
            if (!ok) {
                //console.log("Tekst se ne poklapa s predloškom!");
                $("#lozinka").attr("style", "border-color:red");
                greske = true;
            } else {
                //console.log("OK!");
                $("#lozinka").attr("style", "border-color:green");
                greske = false;
            }
        });

        $("#obrazac").submit(function (event) {
            if (greske) {
                event.preventDefault();
            }
        });
        break;
        case "Multimedija":
            //console.log($("#trazi").val());
            $(".grid-item").hide();
            $("#prazno").hide();
            $("#trazi").click(function (event) {
                $(".grid-item")
                .hide()
                .filter(":contains('" + $("input[name='unos']").val() + "')")
                .show();
                if(!$(".grid-item").is(":visible")){
                    $("#prazno").show();
                }
                else{
                    $("#prazno").hide();
                }
            });

            var pretraga = new Array();

            $.getJSON("json/search.json",
                function (data) {
                    $.each(data, function (key, val) {
                        //console.log(val);
                        pretraga.push(val);
                    });
                });

            $('#unos').autocomplete({
                source: pretraga
            });

            break;
        case "Popis":
            var tablica = $('<table id="tablica2" class="display">');
                tablica.append('<thead><tr><th>Ime</th><th>Prezime</th><th>Email</th></tr></thead>');
            
            $.getJSON('json/users.json', function (data) {
                var tbody = $("<tbody>");
                for (i = 0; i < data.length; i++) {
                    var red = "<tr>";
                    red += "<td>" + data[i].name + "</td>";
                    red += "<td>" + data[i].surname + "</td>";
                    red += "<td>" + data[i].email + "</td>";
                    red += "</tr>";
                    tbody.append(red);
                }
                tbody.append("</tbody>");
                tablica.append(tbody);
                $('#content').html(tablica);
                $('#tablica2').dataTable();
            });
            // $('#tablica').dataTable(
            //     {
            //         "aaSorting": [[0, "asc"], [1, "asc"]],
            //         "bPaginate": true,
            //         "bLengthChange": true,
            //         "bFilter": true,
            //         "bSort": true,
            //         "bInfo": true,
            //         "bAutoWidth": true
            //     });
        break;
        case 'Registracija':
            jquery(function(){
                $(".ime").keyup(function () {
                    var VAL = this.value;
            
                    var ime = new RegExp('^(?!\d| ){1}([a-z]|){1}([a-z]\d){1,25}');
            
                    if (ime.test(VAL)) {
                        alert('Uspjesno ste unijeli ime');
                    }
                });
            });
        break;
                    //https://api.jquery.com/jQuery.ajax/
                    //var service = 'http://localhost/DistributedDataSystem/Service.svc/';

            //         $(document).ready(function(){
            //             $.get("https://api.jquery.com/jQuery.ajax/"),function(data,status){
            //                 console.log(JSON.parse(data));
            //                 var parsedData = JSON.parse(data);
            //             }
            //             $.each(parsedData, function(i, item){

            //             });
            //             data.array.forEach(function(dt){
            //                 $("#tdata").append("<tr>" +
            //                 "<td>"+ dt.ID_korisnika+"</td>"+
            //                 "<td>"+dt.Korisnicko_ime+"</td>"+
            //                 "<td>"+td.Tip+"</td>"+
            //                 "<td>"+td.Status+"</td>"+
            //                 "<td>"+td.Neuspjesne_prijave+"</td>"+
            //                 "<td>"+td.Datum_i_vrijeme_blokiranja+"</td>"
            //                 +"</tr>"
            //                 );
            //             });
                        
            //             jQuery.support.cors = true;

            //             $.ajax(
            //             {
            //                 type: "GET",
            //                 //url: 'https://api.jquery.com/jQuery.ajax/',
            //                 url: 'https://barka.foi.hr/WebDiP/2021/materijali/zadace/dz3/userNameSurname.php?all',
            //                 data: "{}",
            //                 contentType: "application/json; charset=utf-8",
            //                 dataType: "json",
            //                 cache: false,
            //                 success: function (data) {
                                
            //                 var trHTML = '';
                                    
            //                 $.each(data, function (i, item) {
                                
            //                     trHTML += '<tr><td>' + item.ID_korisnika + '</td><td>' +
            //                     item.Korisnicko_ime + '</td></td>' +
            //                     item.Tip +'</td><td>'+ item.status + 
            //                     '</td><td>' + item.Neuspjesne_prijave 
            //                     +'</td><td>' +item.Datum_i_vrijeme_blokiranja + '</td></tr>';
            //                 });
                            
            //                 $('#tablica').append(trHTML);
                            
            //                 },
                            
            //                 error: function (msg) {
                                
            //                     alert(msg.responseText);
            //                 }
            //             });
            //         })


            //         $('#tablica').DataTable();

            //         $('#json').click(function () {
            //             var tablica = $('<table id="tablica2" class="display">');
            //             tablica.append('<thead><tr><th>Ime</th><th>Prezime</th><th>Email</th></tr></thead>');
        
            //             $.getJSON('json/users.json', function (data) {
            //                 var tbody = $("<tbody>");
            //                 for (i = 0; i < data.length; i++) {
            //                     var red = "<tr>";
            //                     red += "<td>" + data[i].name + "</td>";
            //                     red += "<td>" + data[i].surname + "</td>";
            //                     red += "<td>" + data[i].email + "</td>";
            //                     red += "</tr>";
            //                     tbody.append(red);
            //                 }
            //                 tbody.append("</tbody>");
            //                 tablica.append(tbody);
            //                 $('#content').html(tablica);
            //                 $('#tablica2').dataTable();
            //             });
            //         });
            //         break;
        
            //     default:
            //         alert("Stranica ne postoji!");
            //         break;
            }
        });