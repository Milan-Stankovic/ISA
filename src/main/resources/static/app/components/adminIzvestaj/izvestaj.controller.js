(function () {
    'use strict';

    angular
        .module('app')
        .controller('adminIzvestajController', adminIzvestajController);

    adminIzvestajController.$inject = ['$location', '$scope', '$rootScope','$http', '$window', '$cookies', '$stateParams', '$state'];//mozda tu jos onaj docs
    function adminIzvestajController($location, $scope, $rootScope, $http, $window, $cookies, $stateParams, $state) {


        $scope.chartConfig = {"chart":{"height":350,"width":350,"type":"line"},"plotOptions":{"series":{"stacking":""}},"series":[{"name":"My Super Column","data":[1,1,2,3,2],"type":"column","id":"s4"}],"title":{"text":"Test"}};
        var ic = this;
        $scope.pozoriste= false;
        $scope.bioskop= false;
        $scope.choseTime = false;
        $scope.showReport=false;


        var getAdminId = function(){
            $scope.adminId = $stateParams.id;
            console.log($scope.adminId);
        }

        $scope.pickCinema = function(){
           // getAdminId();

            $scope.showReport=false;
            $scope.bioskop= true;
            $scope.pozoriste= false;
            $scope.choseTime = false;

        }

        $scope.pickTheater = function(){
            // getAdminId();

            $scope.showReport=false;
            $scope.bioskop= false;
            $scope.pozoriste= true;
            $scope.choseTime = false;

        }

        $scope.bpId=null;
        $scope.bpName="";

        $scope.izvestaj = function(bpId,  bpName){
            $scope.bpId=bpId;
            $scope.bpName = bpName;
            $scope.choseTime = true;
            $scope.showReport=false;
            $scope.bioskop= false;
            $scope.pozoriste= false;
        }

        $scope.saveIzvestaj = function(newDateOd, newDateDo){

            var b=false;
            if(newDateDo)
                if(newDateDo)
                    if($scope.adminId)
                        if($scope.bpId)
                            if(newDateDo.length>0)
                                if(newDateOd.length>0)
                                    if($scope.adminId>0)
                                        if($scope.bpId>0)
                                            b=true;

            if(b) {
                var reportDTO = {};
                reportDTO = {
                    "from": newDateOd,
                    "to": newDateDo,
                    "adminId": $scope.adminId,
                    "pbId": $scope.bpId
                }
                console.log(reportDTO);

                $http({
                    method: 'POST',
                    url: 'http://localhost:8096/fullIzvestaj',
                    data: reportDTO
                }).then(function successCallback(response) {
                    console.log(response.data);
                    $scope.dobijenIzvestaj= response.data;
                    $scope.choseTime = false;
                    $scope.bioskop= false;
                    $scope.pozoriste= false;
                    $scope.showReport = true;

                }, function errorCallback(response) {
                    alert("Error occured while making report");
                });
            }



        }

        $scope.go = function(state) {
            $state.go(state);
        }

        var init = function (){
            $scope.adminId = $stateParams.id;
        };
        init();


        var getPozorista = function () {
            $http({
                method: 'GET',
                url: 'http://localhost:8096/admin/p/'+$scope.adminId, // FALI MI DEO ZA ID
            }).then(function successCallback(response) {
                $scope.pozoristaAdmin = response.data;

            }, function errorCallback(response) {
                alert("Error getting cinemas")

            });
        }

        getPozorista();

        var getBioskopi = function () {
            $http({
                method: 'GET',
                url: 'http://localhost:8096/admin/b/'+$scope.adminId, // FALI MI DEO ZA ID
            }).then(function successCallback(response) {
                $scope.bioskopiAdmin = response.data;

            }, function errorCallback(response) {
                alert("Error getting cinemas")

            });
        }
        getBioskopi();


    }

})();