(function () {
    'use strict';

    angular
        .module('app')
        .controller('seatPriceController', seatPriceController);

    seatPriceController.$inject = ['$location', '$scope', '$rootScope','$http', '$window', '$cookies', '$stateParams', '$state', '$timeout'];//mozda tu jos onaj docs
    function seatPriceController($location, $scope, $rootScope, $http, $window, $cookies, $stateParams, $state, $timeout) {
        var spc= this;


        $scope.regular= 0;
        $scope.vip = 0;
        $scope.love  = 0;
        $scope.balkon = 0;
        $scope.salaName="";


        var getCene = function (id) {

            $http({
                method: 'GET',
                url: 'http://localhost:8096/api/cenovnikSedista/' + id,
            }).then(function successCallback(response) {

                console.log(response.data);

                $scope.regular= response.data.doplataRegular;
                $scope.vip = response.data.doplataVIP;
                $scope.love  = response.data.doplataLoveBox;
                $scope.balkon = response.data.doplataBalcony;


            }, function errorCallback(response) {
                alert("Error geting prices");
                $location.path('/home');


            });

        }


        var init = function (){

            $scope.salaId = $stateParams.salaId;
            $scope.salaName = $stateParams.salaName;
           // console.log($scope.salaName);

            if($scope.salaId<0) {
                alert("No auditorium");
                $location.path('/home');
            }

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


            getCene($scope.salaId);


            $scope.tipSedista =[
                "REGULAR",
                "VIP",
                "LOVEBOX",
                "BALCONY",
                "TAKEN"
            ];

        }
        init();




        $scope.updatePrice = function (cena1, cena2, cena3, cena4) {

            if(cena1){
                if(cena1<0)
                    cena1 =0;
            }else{
                cena1=0;
            }

            if(cena2){
                if(cena2<0)
                    cena2 =0;
            }else{
                cena2=0;
            }

            if(cena3){
                if(cena3<0)
                    cena3 =0;
            }else{
                cena3=0;
            }

            if(cena4){
                if(cena4<0)
                    cena4 =0;
            }else{
                cena4=0;
            }



            var cenovnikDTO = {
                "doplataRegular" :cena1,
                "doplataVIP" : cena2,
                "doplataLoveBox" : cena3,
                "doplataBalcony" : cena4
            }


            console.log(cenovnikDTO);

            $http({
                method: 'PUT',
                url: 'http://localhost:8096/api/cenovnikSedista/' + $stateParams.salaId,
                data : cenovnikDTO
            }).then(function successCallback(response) {
                alert("Price changed");
                $location.path('/home');


            }, function errorCallback(response) {
                alert("Error changing price");
                $location.path('/home');

            });
        }








    }
})();