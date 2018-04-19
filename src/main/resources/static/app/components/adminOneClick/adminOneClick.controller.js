(function () {
    'use strict';

    angular
        .module('app')
        .controller('adminOneClickController', adminOneClickController);

    adminOneClickController.$inject = ['$location', '$scope', '$rootScope','$http', '$window', '$cookies', '$stateParams', '$state', '$timeout'];//mozda tu jos onaj docs
    function adminOneClickController($location, $scope, $rootScope, $http, $window, $cookies, $stateParams, $state, $timeout) {

        var aoc = this;




        $scope.saveOneClick = function(oneClickCena){

            if(oneClickCena)
                if(oneClickCena>0 && oneClickCena<100) {

                    var OneClickDTO = {};
                    OneClickDTO = {
                        "sedista": $scope.items,
                        "cena": oneClickCena,
                        "brojRedova" : $scope.newSalaBR,
                        "brojSedista" : $scope.newSalaBS,
                        "bpId" : $scope.bpId
                    }

                    $http({
                        method: 'POST',
                        url: 'http://localhost:8096/projekcija/zauzmi/' + $scope.projekcijaId,
                        data: OneClickDTO
                    }).then(function successCallback(response) {


                    }, function errorCallback(response) {
                        alert("Error saving seats");


                    });
                }

           // $scope.go('core.admin');


        }

        $scope.go = function(state) {
            $state.go(state, {"id" : $scope.adminId} );
        }




        $scope.select = function(item, $event) {
            if ($event.shiftKey && $scope.lastChecked) {
                var start = $scope.items.indexOf(item);
                var end = $scope.items.indexOf($scope.lastChecked);
                var toBeChecked = $scope.items.slice(Math.min(start, end), Math.max(start, end) + 1);
                angular.forEach(toBeChecked, function(ii) {
                    ii.checked = $scope.futureCheckState;
                });
            }
            $scope.lastChecked = item;
        };
        $scope.mouseSelect = function(item, $event, start) {
            if (start) {

                $scope.lastMouseChecked = item;
                $scope.futureCheckState = !item.checked;

            } else if (item !== $scope.lastMouseChecked) {
                $scope.lastChecked = $scope.lastMouseChecked;
                var ev = {
                    shiftKey: true
                };
                select(item, ev);
            }
        };





        var drawEditGrid= function(sedista, brRed, brSed, zauzetaMesta){

            var i = brRed;
            var j = brSed;

            $scope.items = [];
            $scope.split_items = [];
            $scope.duzina = [];
            var trenutniRed = 0;

            for(var z =0; z<sedista.length; z++){
                trenutniRed=Math.floor(z/brSed);
                var sediste =  sedista[z];
                var id =brSed*trenutniRed+sediste.broj;
                var type = sediste.tipSedista;

                $scope.items.push({
                    id: id,
                    checked: false,
                    type : type
                });// mozda treba sort na kraju

            }



            function compare(a,b) {
                if (a.id > b.id)
                    return -1;
                if (a.id < b.id)
                    return 1;
                return 0;
            }

            $scope.items.sort(compare);


            for(var s=0; s<zauzetaMesta.length; s++){
                var rednibr = zauzetaMesta[s].broj + zauzetaMesta[s].red*brSed;
                $scope.items[rednibr].type="TAKEN";

            }

            var temp =[];
            for (var k = 0; k < i; k++) {
                for(var m=0; m<j; m++){
                    temp.push($scope.items[k*j+m]);
                }
                $scope.split_items.push(temp);
                temp=[];
            }
            $scope.showGrid = true;
            for(var d=0; d<j; d++){
                $scope.duzina.push(d);
            }
            $scope.duzina.push(j+1);


            angular.element(function () {
                for(var z =0; z<$scope.items.length; z++){
                    var sediste =  $scope.items[z];

                   // console.log(sediste);
                    if(sediste.type != "REGULAR")
                        changeClass(sediste.id, sediste.type);
                }
            });


        }

        var changeClass = function(itemId,tip){

            var id ="";
            id+="#"+itemId;

            switch(tip) {
                case "REGULAR":
                    $(id).removeClass();
                    angular.element(document.getElementById(itemId)).addClass("clear");
                    break;
                case "VIP":
                    $(id).removeClass();
                    angular.element(document.getElementById(itemId)).addClass("clearBlue");
                    break;

                case "LOVEBOX":
                    $(id).removeClass();
                    angular.element(document.getElementById(itemId)).addClass("clearRed");
                    break;

                case "BALCONY":
                    $(id).removeClass();
                    angular.element(document.getElementById(itemId)).addClass("clearGreen");
                    break;

                case "TAKEN":
                    $(id).removeClass();
                    angular.element(document.getElementById(itemId)).addClass("clearWhite");
                    break;
                default:
                    $(id).removeClass();
                    angular.element(document.getElementById(itemId)).addClass("clear");
            }
        }




        $scope.setup=function() {


            $http({
                method: 'GET',
                url: 'http://localhost:8096/projekcija/sala/'+$scope.projekcijaId,
            }).then(function successCallback(response) {
                var sala = response.data;
                $scope.oneSala = response.data;
                $scope.newSalaName = $scope.oneSala.ime;
                $scope.newSalaBR = $scope.oneSala.brRed;
                $scope.newSalaBS = $scope.oneSala.brSedista;


                $http({
                    method: 'GET',
                    url: 'http://localhost:8096/projekcija/zauzeto/'+$scope.projekcijaId,
                }).then(function successCallback(response) {
                    var zauzetaMesta = response.data;


                    drawEditGrid($scope.oneSala.sedista,  $scope.newSalaBR,  $scope.newSalaBS, zauzetaMesta);


                }, function errorCallback(response) {
                    alert("Error getting taken seats");



                });



            }, function errorCallback(response) {
                alert("Error getting Auditorium");


            });




        }


        var init = function (){
            $scope.adminId = $stateParams.id;
            $scope.dogadjajName= $stateParams.dName;
            $scope.dogadjajId = $stateParams.dId;
            $scope.projekcijaTime = $stateParams.pTime;
            $scope.projekcijaId = $stateParams.pId;
            $scope.bpId = $stateParams.bpId;
            $scope.projekcijaCena = $stateParams.pPrice;
            $scope.setup();
        };

        init();


    }

})();