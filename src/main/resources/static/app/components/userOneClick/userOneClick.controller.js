(function () {
    'use strict';

    angular
        .module('app')
        .controller('userOneClickController', userOneClickController);

    userOneClickController.$inject = ['$location', '$scope', '$rootScope','$http', '$window', '$cookies', '$stateParams', '$state', '$timeout'];//mozda tu jos onaj docs
    function userOneClickController($location, $scope, $rootScope, $http, $window, $cookies, $stateParams, $state, $timeout) {

        var uoc = this;

        var init = function (){

            var isAdmin = $stateParams.admin;

            $scope.pbId = $stateParams.pbId;

            console.log(isAdmin);
            console.log($scope.pbId);

            var regUser={};
            regUser=$cookies.get('user');


            if(!$cookies.get('user'))
                $location.path('/home');

            if(isAdmin) {
                $scope.adminId =$cookies.get('id');
                $http({
                    method: 'GET',
                    url: 'http://localhost:8096/admin/' + regUser,
                }).then(function successCallback(response) {
                    if (response.data.tip != "POZBI")
                        $location.path('/home');

                }, function errorCallback(response) {
                    alert("Error occured check connection");
                    $location.path('/home');
                });
            }


            var getReservations = function () {

                $http({
                    method: 'GET',
                    url: 'http://localhost:8096/api/quickRezervacije/'+$scope.pbId,
                }).then(function successCallback(response) {
                    $scope.reservations = response.data;
                    console.log($scope.reservations);

                }, function errorCallback(response) {
                    alert("Error getting reservations")

                });

            }

            var getAdminReservations = function () {

                $http({
                    method: 'GET',
                    url: 'http://localhost:8096/api/quickRezervacije/'+$scope.adminId+ '/admin/'+$scope.pbId,
                }).then(function successCallback(response) {
                    $scope.reservations = response.data;
                    console.log($scope.reservations);

                }, function errorCallback(response) {
                    alert("Error getting reservations")

                });

            }


            if(isAdmin){
                $scope.adminId= $cookies.get('id');
                getAdminReservations();
            }else
                getReservations();





        };
        init();

        var setUserReservation = function(pozivId, rezervacijaID){


            var afterDTO={
                "userId" : $cookies.get('id'),
                "pozivId" : pozivId,
                "rezervacijaID" : rezervacijaID
            };



            $http({
                method: 'PUT',
                url: 'http://localhost:8096/api/user',
                data:afterDTO
            }).then(function successCallback(response) {

                for(var i =0; i< $scope.reservations.length; i++){
                    if($scope.reservations[i].id == rezervacijaID){
                        $scope.reservations.splice(i, 1);
                        break;
                    }

                }

            }, function errorCallback(response) {
                alert("Error making reservation")

            });

        }

        $scope.reserve=function(reserveId){

            $http({
                method: 'GET',
                url: 'http://localhost:8096/oneClick/' +reserveId+'/user/'+$cookies.get('id')
            }).then(function successCallback(response) {
                var rezultat = response.data;

                if(rezultat.message =="ERROR"){
                    alert("The reservation was already taken, better luck next time !");
                }else {
                    alert("You got the reservation !");
                    setUserReservation(rezultat.pozivId, reserveId);
                }



            }, function errorCallback(response) {
                alert("Error making reservation");

            });

        }

        $scope.delete = function (rezervacijaId) {


            $http({
                method: 'DELETE',
                url: 'http://localhost:8096/api/rezervacija/delete/'+rezervacijaId
            }).then(function successCallback(response) {
                alert("Reservation deleted");

                for(var i =0; i< $scope.reservations.length; i++){
                    if($scope.reservations[i].id == rezervacijaId){
                        $scope.reservations.splice(i, 1);
                        break;
                    }

                }





            }, function errorCallback(response) {
                alert("Error deleting reservation");

            });



        }



    }

})();