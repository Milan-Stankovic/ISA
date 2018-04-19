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
        $scope.rezervacije = [];
        var init = function (){


        }

        var getRez = function () {
            $http({
                method: 'GET',
                url: 'http://localhost:8096/api/user/reservations/' + $cookies.get('user'),
            }).then(function successCallback(response) {
                $scope.rezervacije = response.data;
                console.log("sve rezervacije: " + $scope.rezervacije.length)

            }, function errorCallback(response) {
                alert("Greska kod bioskopa")

            });
                }

                getRez();

        var getInvRez = function () {
                    $http({
                        method: 'GET',
                        url: 'http://localhost:8096/api/user/invitations/' + $cookies.get('user'),
                    }).then(function successCallback(response) {
                        $scope.inv = response.data;
                        console.log("sve inv: " + $scope.inv.length)

                    }, function errorCallback(response) {
                        alert("Greska kod bioskopa")

                    });
                        }

                        getInvRez();
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

        var getUstanova = function(rez){
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
            $scope.answer(rez, true);
        }
        $scope.answer = function(rez, isAccepted){
            if(isAccepted){
                $http({
                    method: 'POST',
                    url: 'http://localhost:8096/api/rezervacija/acc/' + $cookies.get('user'),
                    data: rez.id
                  }).then(function successCallback(response) {
                       $scope.rezervacije = response.data;
                       console.log("aj sad accept: " + $scope.rezervacije)
                       getAccRez();
                       }
                   );
            }else{
                 $http({
                    method: 'POST',
                    url: 'http://localhost:8096/api/rezervacija/decl/' + $cookies.get('user'),
                    data: rez.id
                  }).then(function successCallback(response) {
                       $scope.rezervacije = response.data;
                       console.log("aj sad accept: " + $scope.rezervacije)
                       getAccRez();
                       }
                   );
            }

            /*console.log("usao u answer")
            var data={
                "rezID" : rez.id,
                "isAccepted" : isAccepted
            }
             $http({

                 method: 'POST',
                 url: 'http://localhost:8096/#!/api/rezervacija/inv/' + $cookies.get('user'),
                 data: data

             }).then(function successCallback(response) {
                   $scope.rezervacije = response.data;
                     console.log("aj sad accept: " + $scope.rezervacije)
                     getAccRez();

             }, function errorCallback(response) {
                 alert("Greska kod answer")

             });*/
        }


        $scope.updateView = function(){
         if($scope.inv!=undefined){
            for(var i = 0; i < $scope.inv.length; i++){
                document.getElementsByClassName($scope.inv[0].id)[0].style.display = "inline";
            }
        }
         if($scope.invAcc!=undefined){
            for(var i = 0; i < $scope.invAcc.length; i++){
                document.getElementsByClassName($scope.invAcc[0].id)[0].innerText ="Accepted";
                document.getElementsByClassName($scope.invAcc[0].id)[0].value ="Accepted";
                document.getElementsByClassName($scope.invAcc[0].id)[0].disabled = true;
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






