(function () {
    'use strict';

    angular
		.module('app')
		.controller('allReservationsController', allReservationsController);

    allReservationsController.$inject = ['$location', '$scope', '$rootScope','$http', '$cookies', '$sce'];
    function allReservationsController($location, $scope, $rootScope, $http, $cookies, $sce) {
        var arc = this;
        var user;
        arc.home = "Home";
        $scope.pozivi = [];
        var init = function (){
            if(!$cookies.get('user'))
                $location.path("login")

        }
        init();

        $scope.rate = function (rez, ambRating, eveRating, pozivId) {
            console.log(rez);
            console.log(ambRating);
            console.log(eveRating);
            console.log(pozivId);
        }

        //OVDE DOBIJA SVE POZIVE I PRIHVACENE I KOJE TREBA DA PRIHVATI BEZ ODBIJENIH
        var getRez = function () {
            $http({
                method: 'GET',
                url: 'http://localhost:8096/api/user/resPozivi/' + $cookies.get('user'),
            }).then(function successCallback(response) {
            //OVO SU SVI POZIVI KOJE JE NAPRAVIO SAM ILI VEC PRIHVATIO
                $scope.pozivi = response.data;

           /*     var korId = $cookies.get('id');
                for(var i=0; i<$scope.rezervacije.length; i++){
                    for(var j=0; j<$scope.inv.length; j++){
                        if($scope.rezervacije[i].id==$scope.inv[j].rezervacija.id){ // Skrnavo ali nema druge...
                            if($scope.inv[j].rezervacija.osoba.id == korId) {
                                $scope.rezervacije[i].pozivId = $scope.inv[j].id;
                                $scope.rezervacije[i].ocenaA = $scope.inv[j].ocenaAmbijenta;
                                $scope.rezervacije[i].ocenaF = $scope.inv[j].ocenaFilma;
                                $scope.rezervacije[i].edit = true;
                                if($scope.rezervacije[i].ocenaA)
                                    if($scope.rezervacije[i].ocenaF)
                                        $scope.rezervacije[i].edit = false;

                                break;
                            }
                        }
                    }NE RADI NI OVO
                }
                */




                console.log("sve pozivi: " + $scope.pozivi.length)

            }, function errorCallback(response) {
                console.log("Greska kod bioskopa")

            });
                }

                getRez();

/*

        var getAccRez = function () {
        $http({
            method: 'GET',
            url: 'http://localhost:8096/api/user/invAccepted/' + $cookies.get('user'),
        }).then(function successCallback(response) {
            $scope.invAcc = response.data;
            console.log("sve invAcc: " + $scope.invAcc.length)

        }, function errorCallback(response) {
            alert("Greska kod invAcc")

        });
            }

            getAccRez();
*/

        var getUstanova = function(rez){
            $scope.sedista = rez.projekcija.sala.sedista;
            $scope.redova = rez.projekcija.sala.brRed;
            $scope.kolona = rez.projekcija.sala.brSedista;
            if($scope.sedista[0].red==$scope.redova-1)
                                $scope.sedista.reverse();

            $scope.groups = $scope.sedista.map( function(e,i){
                                 return i%$scope.kolona===0 ? $scope.sedista.slice(i,i+$scope.kolona) : null;
                             })
                             .filter(function(e){ return e; });
            $http({
                  method: 'GET',
                  url: 'http://localhost:8096/sala/ustanova/' + rez.projekcija.sala.id
                 }).then(function successCallback(response) {
                 $scope.salaUstanova = response.data;
                 }, function errorCallback(response) {
                               alert("Greska kod get ustanova")

                           });

        }

        $scope.accept = function(rez){
            document.getElementsByClassName(rez.id)[0].style.display = "none";
            $scope.answer(rez, true);
        }
        $scope.answer = function(rez, isAccepted){
            if(isAccepted){
                $http({
                    method: 'POST',
                    url: 'http://localhost:8096/api/rezervacija/acc/' + $cookies.get('user'),
                    data: rez.id
                  }).then(function successCallback(response) {
                       $scope.pozivi = response.data;

                       console.log("aj sad accept: " + $scope.pozivi)

                       }
                   );
            }else{
                 $http({
                    method: 'POST',
                    url: 'http://localhost:8096/api/rezervacija/decl/' + $cookies.get('user'),
                    data: rez.id
                  }).then(function successCallback(response) {
                       $scope.pozivi = response.data;

                       console.log("aj sad accept: " + $scope.pozivi)

                       }
                   );
            }


        }


        $scope.updateView = function(){
             if($scope.pozivi!=undefined){
                for(var i = 0; i < $scope.pozivi.length; i++){
                    if($scope.pozivi[i].status=="CEKA")
                        document.getElementsByClassName($scope.pozivi[i].rezervacija.id)[0].style.display = "inline";
                }
             }

        }
        $scope.details = function(rez){
                if(document.getElementById(rez.id + " details").style.display=="none"){
                    getUstanova(rez);
                    document.getElementById(rez.id + " details").style.display = "block";
                }
                else
                    document.getElementById(rez.id + " details").style.display="none";


        }


        $scope.cancel = function(rez){
            var currentTime = new Date();
            var sad = currentTime/1000;
            var a = new Date(sad*1000);
            var ayear = a.getFullYear();
            var amonth = a.getMonth()+1;
            var adate = a.getDate();
            var ahour = a.getHours();
            var amin = a.getMinutes();

            var epoch_date = rez.projekcija.vreme/1000;
            var b = new Date(epoch_date*1000);
            var byear = a.getFullYear();
            var bmonth = a.getMonth()+1;
            var bdate = a.getDate();
            var bhour = a.getHours();
            var bmin = a.getMinutes()+30;//ovo+30 obrisati!
            if(byear-ayear>=0)
                if(bmonth-amonth>=0)
                    if(bdate-adate>=0)
                        if(bhour-ahour>=0)
                            if(bmin-amin>=30){
                                $scope.answer(rez, false);
                                return;
                            }

            alert("Canceling is not available.")
            return;

        }
    }


})();






