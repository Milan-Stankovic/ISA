(function() {
    'use strict';

    angular
        .module('app')
        .controller('adminController', adminController);

    adminController.$inject = ['$location', '$scope', '$rootScope', '$http', '$window', '$cookies'];

    function adminController($location, $scope, $rootScope, $http, $window, $cookies) {

        $scope.sala = false;
        $scope.pozoriste = false;
        $scope.bioskop = false;
        $scope.sedista = false;
        $scope.dogadjaj = true;
        $scope.projekcija = false;
        $scope.karte = false;


        $scope.zanr = [
            "HOROR",
            "KOMEDIJA",
            "DRAMA",
            "TRILLER"

        ];


        var ac = this;

        var init = function () {

        };
        init();

        $scope.indexFunc = function () {
            $location.path("home");
        }
    }
})();