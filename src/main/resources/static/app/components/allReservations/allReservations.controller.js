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


        var accept = function(rez){
            console.log('http://localhost:8096/poziv/user/' + $cookies.get('user'))
            console.log(rez)
            $http({
                  method: 'POST',
                  url: 'http://localhost:8096/#!/poziv/user/' + $cookies.get('user'),
                   data: rez
              }).then(function successCallback(response) {
                  $scope.rezultat = response.data;
                  console.log("rez: " + $scope.rezultat)

              }, function errorCallback(response) {
                  alert("Greska kod accept")

              });

        }
        $scope.updateView = function(){
         if($scope.inv!=undefined){
            for(var i = 0; i < $scope.inv.length; i++){
                document.getElementsByClassName($scope.inv[0].id)[0].innerText ="Accept"
                document.getElementsByClassName($scope.inv[0].id)[0].value ="Accept"
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

            if(document.getElementsByClassName(rez.id)[0].value=="Accept"){

                accept(rez);
            }
            else{

                if(document.getElementById(rez.id + " details").style.display=="none")
                    document.getElementById(rez.id + " details").style.display = "block";
                else
                    document.getElementById(rez.id + " details").style.display="none";
            }

        }
    }


})();






