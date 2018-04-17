(function () {
    'use strict';

    angular
		.module('app')
		.controller('homeController', homeController);

    homeController.$inject = ['$location', '$scope', '$rootScope','$http', '$cookies', '$sce'];
    function homeController($location, $scope, $rootScope, $http, $cookies, $sce) {
        var hc = this;
        hc.home = "Home";
        var user;
        var init = function (){

            if($cookies.get('user'))
                $location.path("regKorHome")
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

    }


})();