(function () {
    'use strict';

    angular
		.module('app')
		.controller('homeController', homeController);

    homeController.$inject = ['$location', '$scope', '$rootScope'];
    function homeController($location, $scope, $rootScope) {
        var hc = this;
        init = function (){
        	
        };
        init();
    }


})();