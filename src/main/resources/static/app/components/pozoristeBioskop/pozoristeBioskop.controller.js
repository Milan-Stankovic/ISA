(function () {
    'use strict';

    angular
		.module('app')
		.controller('pozoristeBioskopController', pozoristeBioskopController);

    pozoristeBioskopController.$inject = ['$location', '$scope', '$rootScope','$http', '$cookies', '$sce','$state'];
    function pozoristeBioskopController($location, $scope, $rootScope, $http, $cookies, $sce,$state) {
        var pbc = this;
        pbc.home = "Home";
        var id;
        $scope.date;


        $scope.go = function(pbId){
            $state.go('core.userOneClick',{"admin":false, "pbId" : pbId} );
        }

        $scope.filteredProj=[];
        var init = function (){

            id = /[^/]*$/.exec(window.location.href)[0];
        };
        init();

        $scope.trustSrc = function(src) {
            return $sce.trustAsResourceUrl(src);
        }


        $scope.pozorista = {};
        $scope.bioskopi = {};

        var getPozorista = function () {
            $http({
                method: 'GET',
                url: 'http://localhost:8096/p',
            }).then(function successCallback(response) {
                $scope.pozorista = response.data;

            }, function errorCallback(response) {
                alert("Greska kod pozorista")

            });
        }

        var getMovies = function () {
            $http({
                method: 'GET',
                url: 'http://localhost:8096/b',
            }).then(function successCallback(response) {
                $scope.bioskopi = response.data;


            }, function errorCallback(response) {
                alert("Greska kod bioskopa")

            });
        }

        getMovies();
        getPozorista();

        console.log(window.location.href);



        $http({
            method: 'GET',
            url: 'http://localhost:8096/pb/' + id,
        }).then(function successCallback(response) {
            $scope.pb = response.data;
        }, function errorCallback(response) {
            alert("Greska kod pozorista")

        });

        $http({
            method: 'GET',
            url: 'http://localhost:8096/pb/' + id + '/projekcije',
        }).then(function successCallback(response) {
            $scope.projekcije = response.data;

            if($scope.projekcije===undefined)
                $scope.projekcije = [];
            else $scope.projekcije.sort(function(a, b) { return a.date - b.date });
            $scope.filteredProj=$scope.projekcije;
            console.log("br porjekcija: " + $scope.projekcije.length)

        }, function errorCallback(response) {
            alert("Greska kod pozorista")

        });


        $scope.otvoriProj = function(proj){
            $location.path('/pozoristeBioskop/' + id + '/projekcije/' + proj)
        }

        $scope.rezervisi = function(proj){
            $location.path('/pozoristeBioskop/' + id + '/projekcije/' + proj + "/reservation")
        }


        $scope.odabranDatum = function(){

                $scope.currentTime = new Date();
            var sad = $scope.currentTime/1000;
            var tren = new Date(sad*1000);
            if($scope.date!=undefined && $scope.date!=""){

                $scope.filteredProj = [];
                var epoch_date = $scope.date/1000;
                var a = new Date(epoch_date*1000);
                // Hours part from the timestamp
                var year = a.getFullYear();
                var month = a.getMonth()+1;
                var date = a.getDate();

                if(tren.getFullYear()>year || tren.getMonth()+1>month || tren.getDate()>date) {
                    console.log("picked past date, no reservations allowed")
                    $scope.filteredProj = [];
                    return;
                }

                var hour = a.getHours();
                var min = a.getMinutes();
                var sec = a.getSeconds();
                console.log("start date: " + date + "."+month+"."+year+ " time:" +hour+":"+min+":"+sec)
                var z = a;
                z.setHours(23);
                z.setMinutes(59);
                z.setSeconds(59);
                var zhour = a.getHours();
                var zmin = a.getMinutes();
                var zsec = a.getSeconds();
                console.log("end date: " + date + "."+month+"."+year+ " time:" +zhour+":"+zmin+":"+zsec)

                var j = 0;
                var odProjekcije;
                while(j < $scope.projekcije.length){
                    odProjekcije = $scope.projekcije[j].vreme ;
                    console.log(odProjekcije)
                    epoch_date = odProjekcije/1000;
                    var b = new Date(epoch_date*1000);
                    // Hours part from the timestamp
                     var pyear = b.getFullYear();
                     var pmonth = a.getMonth()+1;
                     var pdate = b.getDate();
                     var phour = b.getHours();
                     var pmin = b.getMinutes();
                     var psec = b.getSeconds();
                    console.log("PROJ date: " + pdate + "."+pmonth+"."+pyear+ " time:" +phour+":"+pmin+":"+psec)


                    if(year-pyear==0 && month-pmonth==0 && date-pdate==0 && hour-phour<=0 && zhour-phour>=0){
                        $scope.filteredProj.push($scope.projekcije[j]);
                    }
                    j++;
                    console.log(j + ">=" + $scope.projekcije.length)
                    if(j>=$scope.projekcije.length){

                         break;
                    }

                }
            }else
                $scope.filteredProj = $scope.projekcije;
        }
    }


})();