(function () {
    'use strict';

    angular
		.module('app')
		.controller('reservationController', reservationController);

    reservationController.$inject = ['$location', '$scope', '$rootScope','$http', '$cookies', '$window','$timeout'];
    function reservationController($location, $scope, $rootScope, $http, $cookies, $window,$timeout) {
        var resc = this;
        resc.home = "Home";
        var id1, id2;
        $scope.user;
        $scope.clicked = false;
        $scope.proj;
        $scope.sala;
        $scope.sedista;
        $scope.redova;
        $scope.kolona;
        $scope.cenovnik = [];
        $scope.loveboxDodatno = 0;
        $scope.vipDodatno = 0;
        $scope.balkonDodatno = 0;
        var skala;
        $scope.popust = 0;
        $scope.rezervisano=[];
        $scope.pozvanih=[];
        $scope.message="";
        var init = function (){

            if(!$cookies.get('user'))
                $location.path("login");
            var part = window.location.href.substring(0, window.location.href.indexOf("/projekcije"));
            var new_str = part.split("/projekcije")[0];
            id1 = /[^/]*$/.exec(new_str)[0];

            part = window.location.href.substring(0, window.location.href.indexOf("/reservation"));
            new_str = part.split("/reservation")[0];
            id2 = /[^/]*$/.exec(new_str)[0];

        };
        init();


        console.log(window.location.href);
        console.log("id1=" + id1 + "id2=" +id2);

        var getSala = function(){
         $http({
                method: 'GET',
                url: 'http://localhost:8096/pb/' + id1 + "/projekcije/" + id2,
            }).then(function successCallback(response) {
                $scope.proj = response.data;
                 console.log("projekcija: " + $scope.proj.dogadjaj.naziv)
                 $scope.zauzetaSedista = $scope.proj.zauzetaSedista;
                 console.log("ZAUZETIH: " + $scope.zauzetaSedista.length)
                 $scope.sala = $scope.proj.sala;
                 $scope.sedista = $scope.sala.sedista;
                 $scope.redova = $scope.sala.brRed;
                 $scope.kolona = $scope.sala.brSedista;
                 if($scope.sedista[0].red==$scope.redova-1)
                    $scope.sedista.reverse();
             /*    document.getElementById("columns").style.columns = $scope.kolona;*/
                 /*document.getElementById("columns").style.rows = $scope.redova;*/

                   $http({
                         method: 'GET',
                         url: 'http://localhost:8096/sala/ustanova/' + $scope.sala.id
                     }).then(function successCallback(response) {
                        $scope.salaUstanova = response.data;
                     }, function errorCallback(response) {
                                      alert("Greska kod get ustanova")

                                  });
                 console.log("OPA redova: " + $scope.redova + "kolona: " + $scope.kolona)
                 $scope.groups = $scope.sedista.map( function(e,i){
                     return i%$scope.kolona===0 ? $scope.sedista.slice(i,i+$scope.kolona) : null;
                 })
                 .filter(function(e){ return e; });

                  console.log("sala: " + $scope.sala.id)

                  $http({
                            method: 'POST',
                            url: 'http://localhost:8096/api/cenovnikSedista',
                            data: $scope.sala.id
                          }).then(function successCallback(response) {
                              $scope.cenovnik = response.data;
                              console.log("cenovnik: " + $scope.cenovnik.length);
                              if($scope.cenovnik!=undefined){
                                  for(var i = 0; i < $scope.cenovnik.length; i++){
                                      if($scope.cenovnik[i].tip=="VIP")
                                          $scope.vipDodatno = $scope.cenovnik[i].doplata;
                                        if($scope.cenovnik[i].tip=="BALCONY")
                                          $scope.balkonDodatno = $scope.cenovnik[i].doplata;
                                        if($scope.cenovnik[i].tip=="LOVEBOX")
                                          $scope.loveboxDodatno = $scope.cenovnik[i].doplata;
                                  }
                              }
                              //alert(user.userName)
                            }, function errorCallback(response) {
                                console.log("Greska kod cenovnika");
                          });


            }, function errorCallback(response) {
                alert("Greska kod get projekcije")

            });



        }
        getSala();

        var getUstanova = function(){
            $http({
                method: 'GET',
                url: 'http://localhost:8096/pb/' + id1,
            }).then(function successCallback(response) {
                $scope.ustanova = response.data;
                console.log("ustanova: " + $scope.ustanova.naziv)

            }, function errorCallback(response) {
                alert("Greska kod get ustanove")

            });
        }
        getUstanova();

        var getPrijatelji = function(){
            $http({
              method: 'GET',
              url: 'http://localhost:8096/api/user/friends/' + $cookies.get('user')

            }).then(function successCallback(response) {
                $scope.friendsList = response.data;
                console.log("prijatelja: " + $scope.friendsList.length);
                //alert(user.userName)
              }, function errorCallback(response) {
                  console.log("Greska kod GET user frineds");
            });
        }
        getPrijatelji();

        var getCookieUser = function(){
            $http({
                  method: 'GET',
                  url: 'http://localhost:8096/api/user/' + $cookies.get('user')

                }).then(function successCallback(response) {
                    $scope.user = response.data;
                    getSkala();
                    console.log("bodovi: " + $scope.user.bodovi);
                }, function errorCallback(response) {
                    console.log("Greska kod GET user");
            });
        }
        getCookieUser();
        var getSkala = function(){
            $http({
              method: 'GET',
              url: 'http://localhost:8096/bodSkala'

            }).then(function successCallback(response) {
                skala = response.data;
                if(skala!=undefined){
                    if($scope.user.bodovi >= skala.bronzeTreshold)
                        $scope.popust = skala.bronzePopust;
                    else if($scope.user.bodovi >= skala.silverTreshold)
                        $scope.popust = skala.silverPopust;
                    else if($scope.user.bodovi >= skala.goldTreshold)
                        $scope.popust = skala.goldPopust;
                }
              }, function errorCallback(response) {
                  console.log("Greska kod skale");
            });
        }


        var getSediste = function(sID){
                return $scope.sedista.filter(function(item){
                  return (item.id === sID);
                })[0];
              }

        var getIndex = function(sID){
            for (var i = 0; i < $scope.rezervisano.length ; i++) {
                if ($scope.rezervisano[i].id === sID) {

                    return i;
                }
            }
        }
        $scope.openList = function(){
            $scope.clicked = !$scope.clicked;
        }

      $scope.updateView = function(){
        if($scope.zauzetaSedista!=undefined)
            for(var i = 0; i < $scope.zauzetaSedista.length; i++){
                document.getElementsByClassName($scope.zauzetaSedista[i].id)[0].setAttribute("src", 'assets/images/zauzeto.png');
            }
        }


        $scope.dodajSediste = function(sID){
            console.log("SID: " + sID)
            var sediste;
            sediste = getSediste(sID);

            var x = document.getElementsByClassName(sID)[0].getAttribute("src");
            console.log(x)
            if(x==='assets/images/zauzeto.png'){
                return;
            }
            if(x==='assets/images/kliknuto.png'){

                if(sediste.tipSedista=="REGULAR")
                    document.getElementsByClassName(sID)[0].setAttribute("src", 'assets/images/slobodno.png');
                else if(sediste.tipSedista=="LOVEBOX")
                    document.getElementsByClassName(sID)[0].setAttribute("src", 'assets/images/lovebox.png');
                else if(sediste.tipSedista=="BALCONY")
                    document.getElementsByClassName(sID)[0].setAttribute("src", 'assets/images/balkon.png');
                else if(sediste.tipSedista=="VIP")
                    document.getElementsByClassName(sID)[0].setAttribute("src", 'assets/images/vip.png');
                else alert("aaaaaaa")
                console.log("oslobodjeno: " + sediste.id)
                $scope.rezervisano.splice(getIndex(sID), 1);
                console.log("rezervisanih sada ima: " + $scope.rezervisano.length)

            }

            else{
                document.getElementsByClassName(sID)[0].setAttribute("src", 'assets/images/kliknuto.png');
                sediste = getSediste(sID)
                console.log("dodato: " + sediste.id)
                $scope.rezervisano.push(sediste);
                console.log("rezervisanih sada ima: " + $scope.rezervisano.length)

            }


        }

        $scope.inviteFriends = function(friend){
            $scope.pozvanih.push(friend.id);
            document.getElementById("button" + friend.id).innerHTML = "Sent";
            document.getElementById("button" + friend.id).disabled=true;

            //OVIMA TREBA POSLATI INVITE AKO REZERVACIJA PRODJE USPESNO

        }

        $scope.finish = function(){
            if($scope.pozvanih.length+1>$scope.rezervisano.length){
                if($scope.pozvanih.length>0)
                    $scope.message="You didn't reserve enough seats for all invited friends. Reserve at least " + ($scope.pozvanih.length+1-$scope.rezervisano.length) + " more.";
                else
                    $scope.message="You have to reserve at least one seat.";
                return;
            }
            var sedistaID = [];
            for(var k = 0; k < $scope.rezervisano.length; k++){
                sedistaID.push($scope.rezervisano[k].id);
            }

            var rez = {
                "sedista" : sedistaID,
                "rezervisao" : $scope.user.id,
                "projekcija" : $scope.proj.id,
                "pozvani" : $scope.pozvanih,
                "popust" : $scope.popust
            }

            $http({
                method: 'POST',
                url: 'http://localhost:8096/api/reserve',
                data: rez
                }).then(function successCallback(response) {
                    $scope.message="Reservation is successful!";
                    $timeout(function() {
                               $window.location.reload();
                                          }, 3000)

                  //alert(user.userName)
                }, function errorCallback(response) {
                    console.log("Greska kod rezervacije");
                });
            //kreira pozive za svakog od prijatelja ili praznu listu stavim u rezervaciju
            //dodam cookie usera kao rzervisao
            //ddam i projekciju
            //a popust u kontroleru vidim za taj bioskop ili pozorite koliki je pa setujem
            //posalje rezervaciju da napravi

        }

    }




})();