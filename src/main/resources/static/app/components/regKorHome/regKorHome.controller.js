(function () {
    'use strict';

    angular
		.module('app')
		.controller('regKorHomeController', regKorHomeController);

    regKorHomeController.$inject = ['$location', '$scope', '$rootScope','$http', '$cookies', '$sce'];
    function regKorHomeController($location, $scope, $rootScope, $http, $cookies, $sce) {
        var rhc = this;
        var user;
        rhc.home = "Home";

        var init = function (){
            console.log("trazim admira, path: " + 'http://localhost:8096/admin/' + $cookies.get('user'));
            $http({
              method: 'GET',
              url: 'http://localhost:8096/admin/' + $cookies.get('user')

            }).then(function successCallback(response) {
                user = response.data;
                 if(user!=undefined && user.hasOwnProperty('tip')){

                       console.log("admir")
                       $location.path("/admin");
                 }else console.log("not admir")

              }, function errorCallback(response) {
                  console.log("Greska kod GET user");
              });

        	//$location.path("/login");
        };
        init();



        $scope.trustSrc = function(src) {
            return $sce.trustAsResourceUrl(src);
        }

        $scope.visited=[];
        $scope.pozorista = [];
        $scope.bioskopi = [];
        var user = $cookies.get('user');
        var getVisited = function () {
            $http({
                method: 'GET',
                url: 'http://localhost:8096/api/regKor/' + user + '/visited',
            }).then(function successCallback(response) {
                $scope.visited = response.data;
                console.log($scope.visited.length)
                for(var i = 0; i < $scope.visited.length; i++){
                    console.log("ttttttiiiip: " + $scope.visited[i].tip)
                    if($scope.visited[i].tip=="POZORISTE"){
                        console.log("pozoristeeeeeeee")
                        $scope.pozorista.push($scope.visited[i]);
                    }
                    else{
                        console.log("bioskooooooop")
                        $scope.bioskopi.push($scope.visited[i]);
                    }

                }
                 console.log($scope.pozorista.length)
                 console.log($scope.bioskopi.length)

            }, function errorCallback(response) {
                alert("Greska kod pozorista")

            });


        }
        getVisited();


    }


})();