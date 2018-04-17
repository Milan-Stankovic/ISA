(function () {
    'use strict';

    angular
		.module('app')
		.controller('projekcijaController', projekcijaController);

    projekcijaController.$inject = ['$location', '$scope', '$rootScope','$http', '$cookies', '$sce'];
    function projekcijaController($location, $scope, $rootScope, $http, $cookies, $sce) {
        var prc = this;
        prc.home = "Home";
        var id1;
        var id2;
        var init = function (){
            var part = window.location.href.substring(0, window.location.href.indexOf("/projekcije"));
            var new_str = part.split("/projekcije")[0];
            id1 = /[^/]*$/.exec(new_str)[0];
            id2 = /[^/]*$/.exec(window.location.href)[0];

        };
        init();

        $scope.trustSrc = function(src) {
            return $sce.trustAsResourceUrl(src);
        }
        console.log(window.location.href);

        console.log("id1=" + id1 + "id2=" +id2);

        $http({
            method: 'GET',
            url: 'http://localhost:8096/pb/' + id1 + "/projekcije/" + id2,
        }).then(function successCallback(response) {
            $scope.proj = response.data;
             console.log("projekcija: " + $scope.proj.dogadjaj.naziv)

             $http({
                      method: 'GET',
                      url: 'http://localhost:8096/sala/ustanova/' + $scope.proj.sala.id
                     }).then(function successCallback(response) {
                     $scope.salaUstanova = response.data;
                     }, function errorCallback(response) {
                                   alert("Greska kod get ustanova")

                               });

        }, function errorCallback(response) {
            alert("Greska kod get projekcije")

        });





        $http({
            method: 'GET',
            url: 'http://localhost:8096/pb/' + id1,
        }).then(function successCallback(response) {
            $scope.ustanova = response.data;
            console.log("ustanova: " + $scope.ustanova.naziv)

        }, function errorCallback(response) {
            alert("Greska kod get ustanove")

        });
        $scope.rezervisi = function(proj){
            $location.path('/pozoristeBioskop/' + id1 + '/projekcije/' + id2 + "/reservation")
        }

    }


})();