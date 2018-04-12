(function() {
    'use strict';

    angular
        .module('app')
        .controller('adminController', adminController);

    adminController.$inject = ['$location', '$scope', '$rootScope', '$http', '$window', '$cookies'];

    function adminController($location, $scope, $rootScope, $http, $window, $cookies) {

        $scope.sala = false;
        $scope.pozoriste = false;
        $scope.bioskop = true;
        $scope.sedista = false;
        $scope.dogadjaj = false;
        $scope.projekcija = false;
        $scope.karte = false;
        $scope.edit = false;
        $scope.bpOne = false;
        $scope.bp = [];


        var editChange = function(){
            $scope.edit = !$scope.edit;
        }

        $scope.pozoristaAdmin = {};
        $scope.bioskopiAdmin = {};

        var getPozorista = function () {
            $http({
                method: 'GET',
                url: 'http://localhost:8096/admin/p/5', // FALI MI DEO ZA ID
            }).then(function successCallback(response) {
                $scope.pozoristaAdmin = response.data;

            }, function errorCallback(response) {
                alert("Greska kod admin pozorista")

            });
        }

        getPozorista();

        var getBioskopi = function () {
            $http({
                method: 'GET',
                url: 'http://localhost:8096/admin/b/5', // FALI MI DEO ZA ID
            }).then(function successCallback(response) {
                $scope.bioskopiAdmin = response.data;

            }, function errorCallback(response) {
                alert("Greska kod admin bioskopi")

            });
        }
        getBioskopi();


        $scope.zanr = [
            "HOROR",
            "COMEDY",
            "THRILLER",
            "ACTION",
            "ADVENTURE",
            "CRIME",
            "DRAMA",
            "FANTASY",
            "FICTION",
            "MISTERY",
            "MUSICAL",
            "SATIRE",
            "DOCUMENTARY"

        ];


        var ac = this;

        var init = function () {

        };
        init();

        $scope.indexFunc = function () {
            $location.path("home");
        }

        $scope.addNewEvent = function (newDogadjaName, newDogadjajOpis,newDogadjaZanr, newDogadjajReziser,newDogadjajTrajanje,newDogadjajBodovi,newDogadjajGlumci) {

            var form = document.getElementById("myForm");

            var provera = false;
            if(newDogadjajGlumci)
                if(newDogadjaName)
                    if(newDogadjaZanr)
                        if(newDogadjajReziser)
                            if(newDogadjajOpis)
                                if(newDogadjajTrajanje)
                                    if(newDogadjajTrajanje>0)
                                        if(newDogadjajBodovi)
                                            if(newDogadjajBodovi>=0)
                                                provera=true;

            if(provera) {
                var dogadjajDTO = {
                    "naziv" : newDogadjaName,
                    "trajanje" : newDogadjajTrajanje,
                    "zanr" : newDogadjaZanr,
                    "opis" : newDogadjajOpis,
                    "reziser": newDogadjajReziser,
                    "prosecnaOcena" : 0,
                    "brojOcena" : 0,
                    "donosiBodova" : newDogadjajBodovi,
                    "glumciStr" : newDogadjajGlumci
                }

                console.log(dogadjajDTO)

                $http({
                    method:'POST',
                    url: 'http://localhost:8096/d',
                    data: dogadjajDTO
                }).then(function successCallback(response){
                  //  location.reload(); Moze ovako clear
                    alert("Event added sucessfully");


                }, function errorCallback(response){
                    console.log("Failed to add dogadja");
                } );
            }
        }






    }
})();