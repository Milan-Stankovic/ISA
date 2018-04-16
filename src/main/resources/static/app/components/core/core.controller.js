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
        	if (userCookie && userId)
        		$scope.logged=true;
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

        }

        $scope.showOnlyPozorista=function(){
            $rootScope.showBioskopi=false;
            $rootScope.showPozorista=true;
        }

         $scope.showAll=function(){
            $rootScope.showBioskopi=true;
            $rootScope.showPozorista=true;
        }
    }

})();