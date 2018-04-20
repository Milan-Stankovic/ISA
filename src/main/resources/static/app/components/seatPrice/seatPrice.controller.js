(function () {
    'use strict';

    angular
        .module('app')
        .controller('seatPriceController', seatPriceController);

    seatPriceController.$inject = ['$location', '$scope', '$rootScope','$http', '$window', '$cookies', '$stateParams', '$state', '$timeout'];//mozda tu jos onaj docs
    function seatPriceController($location, $scope, $rootScope, $http, $window, $cookies, $stateParams, $state, $timeout) {
        var spc= this;

        var init = function (){
            $scope.pbId = $stateParams.pbId;
            $scope.salaId = $stateParams.salaId;


            var regUser={};
            regUser=$cookies.get('user');


            $scope.adminId =$cookies.get('id');
            $http({
                method: 'GET',
                url: 'http://localhost:8096/admin/' + regUser,
            }).then(function successCallback(response) {
                if (response.data.tip != "POZBI")
                    $location.path('/home');

            }, function errorCallback(response) {
                alert("Error occured check connection");
                $location.path('/home');
            });


            $scope.tipSedista =[
                "REGULAR",
                "VIP",
                "LOVEBOX",
                "BALCONY",
                "TAKEN"
            ];

        }




    }
})();