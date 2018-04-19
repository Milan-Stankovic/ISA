(function () {
    'use strict';

    angular
		.module('app')
		.controller('invitationController', invitationController);

    invitationController.$inject = ['$location', '$scope', '$rootScope','$http', '$cookies', '$timeout', '$window'];
    function invitationController($location, $scope, $rootScope, $http, $cookies, $timeout, $window) {
        var ic = this;
        ic.home = "Home";
        $scope.logged=false;
        $scope.user;
        $scope.rez;
        var userID;
        var rezID;
        var init = function (){
            $location.path("login")
            $scope.message=""
            var part = window.location.href.substring(0, window.location.href.indexOf("/event"));
            var new_str = part.split("/event")[0];
            userID = /[^/]*$/.exec(new_str)[0];
            rezID = /[^/]*$/.exec(window.location.href)[0];

            if($cookies.get('user')!=undefined && $cookies.get('user')!=userID){
                $cookies.remove('user');
                $cookies.remove('id');
                $window.location.reload();
            }else if($cookies.get('user')==undefined){
                $scope.logged=false;
            }else{
                $scope.logged=true;
            }



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
            getUstanova();



          }, function errorCallback(response) {
              console.log("Greska kod GET rez");
          });

        }
        getRez();


        $scope.accept = function() {


           $http({
              method: 'GET',
              url: 'http://localhost:8096/#!/api/poziv/' + $scope.rez.id

            }).then(function successCallback(response) {
                 $scope.pozivi = response.data;
                 console.log($scope.pozivi[0])
                 /*$scope.message = "Invitation accepted!";
                $timeout(function() {
                   $location.path("home");
                  }, 4000);*/


              }, function errorCallback(response) {
                  console.log("Greska kod GET rez");
                  $scope.message = "Invitation not accepted!";
              });


        }

        $scope.decline = function() {

            $http({
              method: 'GET',
              url: 'http://localhost:8096/#!/api/user/invitation/decline/'+ userID +'/event/' +rezID

            }).then(function successCallback(response) {

                 $scope.message = "Invitation declined!";
                 $timeout(function() {
                   $location.path("home");
                  }, 4000);


              }, function errorCallback(response) {
                  console.log("Greska kod GET rez");
                  $scope.message = "Invitation not accepted!";
              });


                }

        var getUstanova = function(){
        $http({
            method: 'GET',
            url: 'http://localhost:8096/sala/ustanova/' + $scope.proj.sala.id
        }).then(function successCallback(response) {
            $scope.salaUstanova = response.data;
        }, function errorCallback(response) {


       });


        }

    }

})();