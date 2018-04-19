(function () {
    'use strict';

    angular
        .module('app')
        .controller('adminOneClickController', adminOneClickController);

    adminOneClickController.$inject = ['$location', '$scope', '$rootScope','$http', '$window', '$cookies', '$stateParams', '$state', '$timeout'];//mozda tu jos onaj docs
    function adminOneClickController($location, $scope, $rootScope, $http, $window, $cookies, $stateParams, $state, $timeout) {

        var aoc = this;
        var init = function (){
            $scope.adminId = $stateParams.id;
        };
        init();

    }

})();