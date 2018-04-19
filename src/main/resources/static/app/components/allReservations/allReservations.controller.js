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
            var dat = {
                "userID" : $cookies.get('user'),
                "rezID" : rez.id
            }
            $http({
                  method: 'POST',
                  url: 'http://localhost:8096/#!/poyyyyyy/accept',
                  data: dat,

              }).then(function successCallback(response) {
                  $scope.rezultat = response.data;
                  console.log("rez: " + $scope.rezultat)

              }, function errorCallback(response) {
                  alert("Greska kod accept")

              });

        }

        var decline = function(rez){

                    $http({
                          method: 'DELETE',
                          url: 'http://localhost:8096/#!/poyyyyyyyy/' + $cookies.get('user'),
                          data: rez,
                          headers: {
                                          "Content-Type": "application/json"
                                      }
                      }).then(function successCallback(response) {
                          $scope.rezultat = response.data;
                          console.log("rez: " + $scope.rezultat)

                      }, function errorCallback(response) {
                          alert("Greska kod del")

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
            var bmin = a.getMinutes()+30;
            if(byear-ayear>=0)
                if(bmonth-amonth>=0)
                    if(bdate-adate>=0)
                        if(bhour-ahour>=0)
                            if(bmin-amin>=30){
                                decline(rez);
                                return;
                            }

            alert("Canceling is not available.")
            return;

        }
    }


})();






