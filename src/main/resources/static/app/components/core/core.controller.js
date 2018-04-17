(function () {
    'use strict';

    angular
		.module('app')
		.controller('coreController', coreController);

    coreController.$inject = ['$location', '$scope', '$rootScope','$http', '$cookies', '$window'];
    function coreController($location, $scope, $rootScope,$http, $cookies, $window) {
    	var cc = this;
    	$scope.logged = false;
    	var userCookie;
    	var userId;
        $rootScope.showBioskopi=true;
    	$rootScope.showPozorista=true;
    	var init = function(){
        	userCookie = $cookies.get('user');
            userId = $cookies.get('id');
        	if (userCookie && userId){
        	    $rootScope.showBioskopi=false;
                $rootScope.showPozorista=false;
                $scope.logged=true;
        	}

        	else $scope.logged=false;



        };
        init();
        console.log("logged: " + $scope.logged + " user: " + userCookie + ", id: " + userId);

        $scope.logout = function(){
        	$cookies.remove('user');
        	$cookies.remove('id');
        	$window.location.href = 'http://localhost:8096/';
        }

        $scope.showOnlyBioskopi=function(){
            $rootScope.showBioskopi=true;
            $rootScope.showPozorista=false;
            if($scope.logged)
                $location.path("regKorHome")
            else $location.path("home")

        }

        $scope.showOnlyPozorista=function(){
            $rootScope.showBioskopi=false;
            $rootScope.showPozorista=true;
            if($scope.logged)
                $location.path("regKorHome")
            else $location.path("home")

        }

         $scope.showAll=function(){

            if($scope.logged){
                $rootScope.showBioskopi=false;
                $rootScope.showPozorista=false;
                $location.path("regKorHome")
            }

            else{
                $rootScope.showBioskopi=true;
                $rootScope.showPozorista=true;
                $location.path("home")
            }

        }
    }

})();