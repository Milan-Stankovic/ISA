(function () {
    'use strict';

    angular
		.module('app')
		.controller('addEditItemController', addEditItemController);

    addEditItemController.$inject = ['$location', '$scope', '$rootScope', '$cookies', '$window', '$http'];
    function addEditItemController($location, $scope, $rootScope, $cookies, $window, $http) {
    	var aeic = this;
    	
        var init = function (){
        	//proveri da li je admin koji ima pristup ovome
        };
        init();
       
   

    }

})();