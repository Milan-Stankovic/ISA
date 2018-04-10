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
        var init = function (){
        	userCookie = $cookies.get('user');

        	if (userCookie) 
        		$scope.logged=true;
        	else $scope.logged=false;

        };
        init();
        alert("logged: " + $scope.logged + " user: " + userCookie)
        
        $scope.logout = function(){
        	$cookies.remove('user');
        	$window.location.href = 'http://localhost:8096/';
        }
    }

})();