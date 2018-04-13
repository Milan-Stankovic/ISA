(function () {
    'use strict';

    angular
		.module('app')
		.controller('pozoristeBioskopController', pozoristeBioskopController);

    pozoristeBioskopController.$inject = ['$location', '$scope', '$rootScope','$http', '$cookies', '$sce'];
    function pozoristeBioskopController($location, $scope, $rootScope, $http, $cookies, $sce) {
        var pbc = this;
        pbc.home = "Home";
        var init = function (){


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
        var id = /[^/]*$/.exec(window.location.href)[0];


        $http({
            method: 'GET',
            url: 'http://localhost:8096/pb/' + id,
        }).then(function successCallback(response) {
            $scope.pb = response.data;
            $scope.projekcije = [];
            if($scope.pb.projekcije!=undefined)
                $scope.projekcije = $scope.pb.projekcije;

            console.log("br porjekcija: " + $scope.projekcije.length)

        }, function errorCallback(response) {
            alert("Greska kod pozorista")

        });


    }


})();