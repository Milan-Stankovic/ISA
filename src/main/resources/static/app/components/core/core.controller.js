(function () {
    'use strict';

    angular
		.module('app')
		.controller('coreController', coreController);

    coreController.$inject = ['$location', '$scope', '$rootScope', '$cookies', '$window'];
    function coreController($location, $scope, $rootScope, $cookies, $window) {
    	var cc = this;
    	$scope.logged = false;
    	var userCookie;
    	var userId;
        var init = function (){
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
    }

})();