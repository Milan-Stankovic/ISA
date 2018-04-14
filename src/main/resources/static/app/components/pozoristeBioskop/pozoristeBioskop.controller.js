(function () {
    'use strict';

    angular
		.module('app')
		.controller('pozoristeBioskopController', pozoristeBioskopController);

    pozoristeBioskopController.$inject = ['$location', '$scope', '$rootScope','$http', '$cookies', '$sce'];
    function pozoristeBioskopController($location, $scope, $rootScope, $http, $cookies, $sce) {
        var pbc = this;
        pbc.home = "Home";
        var id;
        var init = function (){

            id = /[^/]*$/.exec(window.location.href)[0];
        };
        init();

        $scope.trustSrc = function(src) {
            return $sce.trustAsResourceUrl(src);
        }


        $scope.pozorista = {};
        $scope.bioskopi = {};

        var getPozorista = function () {
            $http({
                method: 'GET',
                url: 'http://localhost:8096/p',
            }).then(function successCallback(response) {
                $scope.pozorista = response.data;

            }, function errorCallback(response) {
                alert("Greska kod pozorista")

            });
        }

        var getMovies = function () {
            $http({
                method: 'GET',
                url: 'http://localhost:8096/b',
            }).then(function successCallback(response) {
                $scope.bioskopi = response.data;


            }, function errorCallback(response) {
                alert("Greska kod bioskopa")

            });
        }

        getMovies();
        getPozorista();

        console.log(window.location.href);



        $http({
            method: 'GET',
            url: 'http://localhost:8096/pb/' + id,
        }).then(function successCallback(response) {
            $scope.pb = response.data;
        }, function errorCallback(response) {
            alert("Greska kod pozorista")

        });

        $http({
            method: 'GET',
            url: 'http://localhost:8096/pb/' + id + '/projekcije',
        }).then(function successCallback(response) {
            $scope.projekcije = response.data;

            if($scope.projekcije===undefined)
                $scope.projekcije = [];

            console.log("br porjekcija: " + $scope.projekcije.length)

        }, function errorCallback(response) {
            alert("Greska kod pozorista")

        });

        $scope.otvoriProj = function(proj){
            $location.path('/pozoristeBioskop/' + id + '/projekcije/' + proj)
        }
    }


})();