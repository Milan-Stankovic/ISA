(function () {
    'use strict';

    angular
		.module('app')
		.controller('homeController', homeController);

    homeController.$inject = ['$location', '$scope', '$rootScope','$http', '$cookies'];
    function homeController($location, $scope, $rootScope, $http, $cookies) {
        var hc = this;
        hc.home = "Home";
        var init = function (){
        	//$location.path("/login");
        };
        init();


        $scope.pozorista = {};
        $scope.bioskopi = {};

        var getPozorista = function () {
            $http({
                method: 'GET',
                url: 'http://localhost:8096/p',
            }).then(function successCallback(response) {
                $scope.pozorista = response.data;
                console.log(response.data);

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
                console.log(response.data);

            }, function errorCallback(response) {
                alert("Greska kod bioskopa")

            });
        }

        getMovies();
        getPozorista();


    }


})();