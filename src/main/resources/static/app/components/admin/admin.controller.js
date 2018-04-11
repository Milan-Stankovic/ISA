(function() {
    'use strict';

    angular
        .module('app')
        .controller('adminController', adminController);

    adminController.$inject = ['$location', '$scope', '$rootScope', '$http', '$window', '$cookies'];

    function adminController($location, $scope, $rootScope, $http, $window, $cookies) {
        var ac = this;

        var init = function () {

        };
        init();

        $scope.indexFunc = function () {
            $location.path("home");
        }
    }
})();