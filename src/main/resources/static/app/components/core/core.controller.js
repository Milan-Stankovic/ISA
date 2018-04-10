(function () {
    'use strict';

    angular
		.module('app')
		.controller('coreController', coreController);

    coreController.$inject = ['$location', '$scope', '$rootScope', '$cookies'];
    function coreController($location, $scope, $rootScope,$cookies) {
    	var cc = this;
        var init = function (){
        };
        init();
        
    }

})();