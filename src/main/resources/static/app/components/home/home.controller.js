(function () {
    'use strict';

    angular
		.module('app')
		.controller('homeController', homeController);

    function homeController() {
        var hc = this;
        hc.home = "Home";
    }


})();