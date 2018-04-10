(function () {
    'use strict';

    angular
		.module('app',['ngCookies'])
		.controller('loginController', loginController);

    loginController.$inject = ['$location', '$scope', '$rootScope','$http', '$window', '$cookies'];
    function loginController($location, $scope, $rootScope, $http, $localStorage, $window, $cookies) {
        var hc = this;
        init = function (){
        	
        };
        init();
    }


})();