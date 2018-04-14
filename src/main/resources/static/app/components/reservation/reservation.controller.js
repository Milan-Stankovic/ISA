(function () {
    'use strict';

    angular
		.module('app')
		.controller('reservationController', reservationController);

    reservationController.$inject = ['$location', '$scope', '$rootScope','$http', '$cookies', '$sce'];
    function reservationController($location, $scope, $rootScope, $http, $cookies, $sce) {
        var resc = this;
        resc.home = "Home";
        var id1, id2;
        $scope.clicked = false;
        var init = function (){
        	//$location.path("/login");
            var part = window.location.href.substring(0, window.location.href.indexOf("/projekcije"));
            var new_str = part.split("/projekcije")[0];
            id1 = /[^/]*$/.exec(new_str)[0];

            part = window.location.href.substring(0, window.location.href.indexOf("/reservation"));
            new_str = part.split("/reservation")[0];
            id2 = /[^/]*$/.exec(new_str)[0];
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
             $scope.sala = $scope.proj.sala;
             $scope.sedista = $scope.sala.sedista;
             var zadnje = $scope.sedista[$scope.sedista.length - 1];
             $scope.redova = zadnje.red;
             $scope.kolona = zadnje.broj;
             document.getElementById("columns").style.columns = $scope.kolona;
             console.log("OPA redova: " + $scope.redova + "kolona: " + $scope.kolona)
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

        $http({
          method: 'GET',
          url: 'http://localhost:8096/api/user/friends/' + $cookies.get('user')

        }).then(function successCallback(response) {
            $scope.friendsList = response.data;
            console.log("prijatelja: " + $scope.friendsList.length);
            //alert(user.userName)
          }, function errorCallback(response) {
              console.log("Greska kod GET user frineds");
        });

        $scope.openList = function(){
            $scope.clicked = !$scope.clicked;
        }



    }




})();