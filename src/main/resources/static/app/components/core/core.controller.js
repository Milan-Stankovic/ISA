(function () {
    'use strict';

    angular
		.module('app')
		.controller('coreController', coreController);

    coreController.$inject = ['$location', '$scope', '$rootScope'];
    function coreController($location, $scope, $rootScope) {
    	var cc = this;
        var init = function (){
        };
        init();
        
    }

})();