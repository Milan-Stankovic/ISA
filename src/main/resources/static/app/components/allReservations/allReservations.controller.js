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
    }


})();






