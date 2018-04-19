(function () {
    'use strict';

    angular
		.module('app')
		.controller('regKorHomeController', regKorHomeController);

    regKorHomeController.$inject = ['$location', '$scope', '$rootScope','$http', '$cookies', '$sce'];
    function regKorHomeController($location, $scope, $rootScope, $http, $cookies, $sce) {
        var rhc = this;
        var user;
        rhc.home = "Home";

        var init = function (){
            console.log("trazim admira, path: " + 'http://localhost:8096/admin/' + $cookies.get('user'));
            $http({
              method: 'GET',
              url: 'http://localhost:8096/admin/' + $cookies.get('user')

            }).then(function successCallback(response) {
                user = response.data;
                 if(user!=undefined && user.hasOwnProperty('tip')){

                       console.log("admir")
                       $location.path("/admin");

                 }else {
                    console.log("not admir")
                    $location.path("/regKorHome");
                 }

              }, function errorCallback(response) {
                  console.log("Greska kod GET user");
              });

        	//$location.path("/login");
        };
        init();



        $scope.trustSrc = function(src) {
            return $sce.trustAsResourceUrl(src);
        }

        $scope.svaPozorista = {};
        $scope.sviBioskopi = {};

        var getPozorista = function () {
            $http({
                method: 'GET',
                url: 'http://localhost:8096/p',
            }).then(function successCallback(response) {
                $scope.svaPozorista = response.data;
                console.log("sva pozorista: " + $scope.svaPozorista.length)
            }, function errorCallback(response) {
                alert("Greska kod pozorista")

            });
        }

        var getMovies = function () {
            $http({
                method: 'GET',
                url: 'http://localhost:8096/b',
            }).then(function successCallback(response) {
                $scope.sviBioskopi = response.data;
                console.log("svi bioskopi: " + $scope.sviBioskopi.length)

            }, function errorCallback(response) {
                alert("Greska kod bioskopa")

            });
        }

        getMovies();
        getPozorista();


        $scope.rezervacije = [];
        var getRez = function () {
            $http({
                method: 'GET',
                url: 'http://localhost:8096/api/user/reservations/' + $cookies.get('user'),
            }).then(function successCallback(response) {
                $scope.rezervacije = response.data;
                console.log("sve rezervacije: " + $scope.rezervacije.length)

            }, function errorCallback(response) {
                console.log("Greska kod rezervacija, mozda je admin pa ih nema")

            });
        }

        getRez();

        $scope.visited=[];
        $scope.pozorista = [];
        $scope.bioskopi = [];
        var user = $cookies.get('user');
        var getVisited = function () {
            $http({
                method: 'GET',
                url: 'http://localhost:8096/api/user/visited/' + user,
            }).then(function successCallback(response) {
                $scope.visited = response.data;
                for(var i = 0; i < $scope.visited.length; i++){

                    if($scope.visited[i].tip=="POZORISTE"){

                        $scope.pozorista.push($scope.visited[i]);
                    }
                    else{

                        $scope.bioskopi.push($scope.visited[i]);
                    }

                }

            }, function errorCallback(response) {
                alert("Greska kod pozorista")

            });


        }
        getVisited();




        $scope.otvori = function(id){
            $http({
                method: 'GET',
                url: 'http://localhost:8096/pb/' + id,
            }).then(function successCallback(response) {
                $scope.pozoristeBioskop = response.data;
                $location.path("/pozoristeBioskop/" + id)

            }, function errorCallback(response) {
                alert("Greska kod pozorista")

            });
        }

        $scope.showOnlyBioskopi=function(){
            $rootScope.showBioskopi=true;
            $rootScope.showPozorista=false;

        }

        $scope.showOnlyPozorista=function(){
            $rootScope.showBioskopi=false;
            $rootScope.showPozorista=true;
        }

        $scope.showAll=function(){
            $rootScope.showBioskopi=false;
            $rootScope.showPozorista=false;

        }
    }


})();