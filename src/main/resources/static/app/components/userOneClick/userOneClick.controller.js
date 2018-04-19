(function () {
    'use strict';

    angular
        .module('app')
        .controller('userOneClickController', userOneClickController);

    userOneClickController.$inject = ['$location', '$scope', '$rootScope','$http', '$window', '$cookies', '$stateParams', '$state', '$timeout'];//mozda tu jos onaj docs
    function userOneClickController($location, $scope, $rootScope, $http, $window, $cookies, $stateParams, $state, $timeout) {

        var uoc = this;
        var init = function (){
            $scope.userId = $stateParams.id;
        };
        init();

    }

})();