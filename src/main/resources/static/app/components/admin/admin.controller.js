(function() {
    'use strict';

    angular
        .module('app')
        .controller('adminController', adminController);

    adminController.$inject = ['$location', '$scope', '$rootScope', '$http', '$window', '$cookies', '$document'];

    function adminController($location, $scope, $rootScope, $http, $window, $cookies, $document) {

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

        //#$cookies.get("id");

        $scope.pickSala = function () {
            $scope.sala = true;
            $scope.pozoriste = false;
            $scope.bioskop = false;
            $scope.sedista = false;
            $scope.dogadjaj = false;
            $scope.projekcija = false;
            $scope.karte = false;
            $scope.bpOne = false;
        }


        $scope.pickPozoriste = function () {
            console.log("UPAO JE ON U OVO WTF");
            $scope.sala = false;
            $scope.pozoriste = true;
            $scope.bioskop = false;
            $scope.sedista = false;
            $scope.dogadjaj = false;
            $scope.projekcija = false;
            $scope.karte = false;
            $scope.bpOne = false;


        }

        $scope.items = [];
        $scope.split_items=[];





        for (var i = 0; i < 30; i++) {
            $scope.items.push({
                id: i,
                checked: false
            });
        }

        var temp =[];
        for (var i = 0; i < 3; i++) {
            for(var j=0; j<10; j++){
                temp.push($scope.items[i*10+j]);
            }
            $scope.split_items.push(temp);
            temp=[];
        }


        $scope.lastChecked = null;
        $scope.lastMouseChecked = null;
        $scope.futureCheckState = false;

        $scope.select = function(item, $event) {
            if ($event.shiftKey && $scope.lastChecked) {
                var start = $scope.items.indexOf(item);
                var end = $scope.items.indexOf($scope.lastChecked);
                var toBeChecked = $scope.items.slice(Math.min(start, end), Math.max(start, end) + 1);
                angular.forEach(toBeChecked, function(ii) {
                    ii.checked = $scope.futureCheckState;
                });
            }
            $scope.lastChecked = item;
        };
        $scope.mouseSelect = function(item, $event, start) {
            if (start) {

                $scope.lastMouseChecked = item;
                $scope.futureCheckState = !item.checked;

            } else if (item !== $scope.lastMouseChecked) {
                $scope.lastChecked = $scope.lastMouseChecked;
                var ev = {
                    shiftKey: true
                };
                select(item, ev);
            }
        };



        $scope.hoverIn = function(box){

            box.checked = 1-box.checked;
        }

        $scope.pickBioskop = function () {

            $scope.sala = false;
            $scope.pozoriste = false;
            $scope.bioskop = true;
            $scope.sedista = false;
            $scope.dogadjaj = false;
            $scope.projekcija = false;
            $scope.karte = false;
            $scope.bpOne = false;


        }

        $scope.pickSedista = function () {

            $scope.sala = false;
            $scope.pozoriste = false;
            $scope.bioskop = false;
            $scope.sedista = true;
            $scope.dogadjaj = false;
            $scope.projekcija = false;
            $scope.karte = false;
            $scope.bpOne = false;


        }

        $scope.pickSDogadjaj = function () {

            $scope.sala = false;
            $scope.pozoriste = false;
            $scope.bioskop = false;
            $scope.sedista = false;
            $scope.dogadjaj = true;
            $scope.projekcija = false;
            $scope.karte = false;
            $scope.bpOne = false;


        }

        $scope.pickProjekcija = function () {

            $scope.sala = false;
            $scope.pozoriste = false;
            $scope.bioskop = false;
            $scope.sedista = false;
            $scope.dogadjaj = false;
            $scope.projekcija = true;
            $scope.karte = false;
            $scope.bpOne = false;


        }

        $scope.pickKarta = function () {

            $scope.sala = false;
            $scope.pozoriste = false;
            $scope.bioskop = false;
            $scope.sedista = false;
            $scope.dogadjaj = false;
            $scope.projekcija = false;
            $scope.karte = true;
            $scope.bpOne = false;


        }

        $scope.pickBpOne = function (id) {

            $http({
                method: 'GET',
                url: 'http://localhost:8096/pb/'+id,
            }).then(function successCallback(response) {
                $scope.bp = response.data;

            }, function errorCallback(response) {
                alert("Greska kod admin get one BP")

            });

            $scope.sala = false;
            $scope.pozoriste = false;
            $scope.bioskop = false;
            $scope.sedista = false;
            $scope.dogadjaj = false;
            $scope.projekcija = false;
            $scope.karte = false;
            $scope.bpOne = true;


        }



        $scope.seditChange = function(){
            $scope.edit = !$scope.edit;
        }

        $scope.pozoristaAdmin = {};
        $scope.bioskopiAdmin = {};

        var getPozorista = function () {
            $http({
                method: 'GET',
                url: 'http://localhost:8096/admin/p/6', // FALI MI DEO ZA ID
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
                url: 'http://localhost:8096/admin/b/6', // FALI MI DEO ZA ID
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