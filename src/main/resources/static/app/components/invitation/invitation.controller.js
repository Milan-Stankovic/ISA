(function () {
    'use strict';

    angular
		.module('app')
		.controller('invitationController', invitationController);

    invitationController.$inject = ['$location', '$scope', '$rootScope','$http', '$cookies', '$sce'];
    function invitationController($location, $scope, $rootScope, $http, $cookies, $sce) {
        var ic = this;
        ic.home = "Home";

        $scope.user;
        $scope.rez;
        var userID;
        var rezID;
        var init = function (){
            var part = window.location.href.substring(0, window.location.href.indexOf("/event"));
            var new_str = part.split("/event")[0];
            userID = /[^/]*$/.exec(new_str)[0];
            rezID = /[^/]*$/.exec(window.location.href)[0];


            $http({
              method: 'GET',
              url: 'http://localhost:8096/api/user/'+userID

            }).then(function successCallback(response) {
                $scope.user = response.data;

              }, function errorCallback(response) {
                  console.log("Greska kod GET user");
              });




        };
        init();

        var getRez = function(){
        $http({
          method: 'GET',
          url: 'http://localhost:8096/api/rezervacija/'+rezID

        }).then(function successCallback(response) {
            $scope.rez = response.data;
            $scope.proj = $scope.rez.projekcija;
            console.log($scope.proj)
            getUstanova();

          }, function errorCallback(response) {
              console.log("Greska kod GET rez");
          });

        }
        getRez();
        $scope.accept = function() {

        }

        var getUstanova = function(){
        $http({
            method: 'GET',
            url: 'http://localhost:8096/sala/ustanova/' + $scope.proj.sala.id
        }).then(function successCallback(response) {
            $scope.salaUstanova = response.data;
        }, function errorCallback(response) {
           alert("Greska kod get ustanova")

       });


        }

    }

})();