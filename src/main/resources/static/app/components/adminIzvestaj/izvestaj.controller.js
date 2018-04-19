(function () {
    'use strict';

    angular
        .module('app')
        .controller('adminIzvestajController', adminIzvestajController);

    adminIzvestajController.$inject = ['$location', '$scope', '$rootScope','$http', '$window', '$cookies', '$stateParams', '$state', '$timeout'];//mozda tu jos onaj docs
    function adminIzvestajController($location, $scope, $rootScope, $http, $window, $cookies, $stateParams, $state, $timeout) {

        $scope.chartData=[];
        $scope.chartDataNedeljni=[];
        $scope.chartDataMesecni=[];



        /* "chart": {
             "height": 300,
             "width": 300,
             "type": "line"
         },
         "plotOptions": {
             "series": {
                 "stacking": ""
             }
         },
         "series": [
             {
                 "name": "Days",
                 "data": [
                     3,
                     1,
                     null,
                     5,
                     2
                 ],
                 "connectNulls": true,
                 "id": "s2",
                 "type": "column",
                 "dashStyle": "LongDashDotDot",
                 "color": "#0000ff"
             }
         ],
         "title": {
             "text": "Visitors per day"
         }

         */





        var ic = this;
        $scope.pozoriste= false;
        $scope.bioskop= false;
        $scope.choseTime = false;
        $scope.showReport=false;

        var createGraphWeek = function (graf, dan, mesec, godina) {

            $scope.chartDataNedeljni=[];

            $scope.chartConfigNedeljni =
                {
                    chart: {
                        "height": 375,
                        "width": 375
                    },
                    title: {
                        text: 'Number of visitors per week'
                    },

                    xAxis: {
                        type: 'datetime'
                        // minRange: 14 * 24 * 3600000 // fourteen days
                    },
                    legend: {
                        enabled: false
                    },
                    plotOptions: {
                        series: {
                            minPointLength: 3,
                            connectNulls: true
                        }
                    },
                    series: [{
                        type: 'column',
                        name: 'Number of visitors',
                        pointInterval: 7 * 24 * 3600 * 1000,
                        pointStart: Date.UTC(2018,3,17),
                        data: [
                            [1],
                            [2],
                            [8],
                            [9]]
                    }],
                    credits:{enabled:false}

                };

            for(var i =0; i<graf.length; i++)
                $scope.chartDataNedeljni.push(graf[i]);


            $timeout(function() {
                $scope.chartConfigNedeljni.series[0].data = $scope.chartDataNedeljni;
                $scope.chartConfigNedeljni.series[0].pointStart= Date.UTC(godina,mesec,dan);
            });


        }

        var createGraphMont = function (graf, dan, mesec, godina) {

            $scope.chartDataMesecni=[];

            $scope.chartConfigMesecni =
                {
                    chart: {
                        "height": 375,
                        "width": 375
                    },
                    title: {
                        text: 'Number of visitors per month'
                    },

                    xAxis: {
                        type: 'datetime'
                        // minRange: 14 * 24 * 3600000 // fourteen days
                    },
                    legend: {
                        enabled: false
                    },
                    plotOptions: {
                        series: {
                            minPointLength: 3,
                            connectNulls: true
                        }
                    },
                    series: [{
                        type: 'column',
                        name: 'Number of visitors',
                        pointInterval: 30 * 24 * 3600 * 1000,
                        pointStart: Date.UTC(2018,3,17),
                        data: [
                            [1],
                            [2],
                            [8],
                            [9]]
                    }]

                };

            for(var i =0; i<graf.length; i++)
                $scope.chartDataMesecni.push(graf[i]);


            $timeout(function() {
                $scope.chartConfigMesecni.series[0].data = $scope.chartDataMesecni;
                $scope.chartConfigMesecni.series[0].pointStart= Date.UTC(godina,mesec,dan);
            });

        }


        var createGraph = function (graf, dan, mesec, godina) {


            $scope.chartData=[];

            $scope.chartConfig =
                {
                    chart: {
                        "height": 375,
                        "width": 375
                    },
                    title: {
                        text: 'Number of visitors per day'
                    },

                    xAxis: {
                        type: 'datetime'
                        // minRange: 14 * 24 * 3600000 // fourteen days
                    },
                    legend: {
                        enabled: false
                    },
                    plotOptions: {
                        series: {
                            minPointLength: 3,
                            connectNulls: true
                        }
                    },
                    series: [{
                        type: 'column',
                        name: 'Number of visitors',
                        pointInterval: 24 * 3600 * 1000,
                        pointStart: Date.UTC(2018,3,17),
                        data: [
                            [1],
                            [2],
                            [8],
                            [9]]
                    }]

                };



            for(var i =0; i<graf.length; i++)
                $scope.chartData.push(graf[i]);


            console.log($scope.chartData);


            $timeout(function() {
                $scope.chartConfig.series[0].data = $scope.chartData;
                $scope.chartConfig.series[0].pointStart= Date.UTC(godina,mesec,dan);
            });


        }


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

                    createGraph($scope.dobijenIzvestaj.grafikPosetaDnevno, $scope.dobijenIzvestaj.prviDan, $scope.dobijenIzvestaj.mesec, $scope.dobijenIzvestaj.godina);
                    createGraphWeek($scope.dobijenIzvestaj.grafikPosetaNedeljno, $scope.dobijenIzvestaj.prviDan, $scope.dobijenIzvestaj.mesec, $scope.dobijenIzvestaj.godina)
                    createGraphMont($scope.dobijenIzvestaj.grafikPosetaMesecno, $scope.dobijenIzvestaj.prviDan, $scope.dobijenIzvestaj.mesec, $scope.dobijenIzvestaj.godina);

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