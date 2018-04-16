(function() {
    'use strict';

    angular
        .module('app').directive('ngConfirmClick', [
        function(){
            return {
                link: function (scope, element, attr) {
                    var msg = attr.ngConfirmClick || "Are you sure?";
                    var clickAction = attr.confirmedClick;
                    element.bind('click',function (event) {
                        if ( window.confirm(msg) ) {
                            scope.$eval(clickAction)
                        }
                    });
                }
            };
        }])

        .controller('adminController', adminController);

    adminController.$inject = ['$location', '$scope', '$rootScope', '$http', '$window', '$cookies', '$document'];



    function adminController($location, $scope, $rootScope, $http, $window, $cookies, $document) {

        $scope.sala = false;
        $scope.pozoriste = false;
        $scope.bioskop = true;
        $scope.sedista = false;
        $scope.dogadjaj = false;
        $scope.projekcija = false;
        $scope.karte = false;
        $scope.edit = true;
        $scope.bpOne = false;
        $scope.editOneSala = false;
        $scope.bp = [];

        //#$cookies.get("id");

        $scope.pickSala = function (edit) {
            $scope.sala = true;
            $scope.pozoriste = false;
            $scope.bioskop = false;
            $scope.sedista = false;
            $scope.dogadjaj = false;
            $scope.projekcija = false;
            $scope.karte = false;
            $scope.bpOne = false;
            $scope.editOneSala=true;
            if(!edit){
                $scope.editOneSala=false;
                $scope.newSalaName = "";
                $scope.newSalaBR = "";
                $scope.newSalaBS = "";
                $scope.items=[];
                $scope.split_items=[];
                $scope.duzina=[];
                $scope.editOneSala = false;

            }


           // if(edit)


        }


        $scope.pickPozoriste = function () {
            $scope.sala = false;
            $scope.pozoriste = true;
            $scope.bioskop = false;
            $scope.sedista = false;
            $scope.dogadjaj = false;
            $scope.projekcija = false;
            $scope.karte = false;
            $scope.bpOne = false;


        }

        $scope.items = [];
        $scope.split_items=[];
        $scope.showGrid = false;
        $scope.duzina = [];




        $scope.drawGrid = function(newSalaBR, newSalaBS){
            var b = false;
            if(newSalaBR)
                if(newSalaBS)
                    if(newSalaBR>0 && newSalaBR<76 )
                        if(newSalaBS>0 && newSalaBS<101)
                            b= true;


            if(b)
                initGrid(newSalaBR, newSalaBS);
            else {
                $scope.showGrid = false;
                $scope.items = [];
                $scope.split_items = [];
                $scope.duzina = [];
                }
        }



        $scope.divs = {};
        var initGrid = function (i,j) {
            $scope.items = [];
            $scope.split_items = [];
            $scope.duzina = [];

            for (var z = 0; z < i*j; z++) {
                $scope.items.push({
                    id: z,
                    checked: false,
                    type : "REGULAR"
                });
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



        }





        $scope.lastChecked = null;
        $scope.lastMouseChecked = null;
        $scope.futureCheckState = false;

        $scope.select = function(item, $event, tip) {
            if ($event.shiftKey && $scope.lastChecked) {
                var start = $scope.items.indexOf(item);
                var end = $scope.items.indexOf($scope.lastChecked);
                var toBeChecked = $scope.items.slice(Math.min(start, end), Math.max(start, end) + 1);
                angular.forEach(toBeChecked, function(ii) {
                    var id ="";
                    id+="#"+ii.id;

                    switch(tip) {
                        case "REGULAR":
                            $(id).removeClass(); // Cist jquerry ali ono ali isto kao dole samo removeClass ne radi
                            angular.element(document.getElementById(ii.id)).addClass("clear");
                            ii.type="REGULAR";
                            break;
                        case "VIP":
                            $(id).removeClass();
                            angular.element(document.getElementById(ii.id)).addClass("clearBlue");
                            ii.type="VIP";
                            break;

                        case "LOVEBOX":
                            $(id).removeClass();
                            angular.element(document.getElementById(ii.id)).addClass("clearRed");
                            ii.type="LOVEBOX";
                            break;

                        case "BALCONY":
                            $(id).removeClass();
                            angular.element(document.getElementById(ii.id)).addClass("clearGreen");
                            ii.type="BALCONY";
                            break;

                        case "TAKEN":
                            $(id).removeClass();
                            angular.element(document.getElementById(ii.id)).addClass("clearWhite");
                            ii.type="TAKEN";
                            break;
                        default:
                            angular.element(document.getElementById(ii.id)).removeClass();
                            angular.element(document.getElementById(ii.id)).addClass("clear");
                            ii.type="REGULAR";
                    }


                    ii.checked = $scope.futureCheckState;
                    ii.type= tip;
                });
            }

            var id ="";
            id+="#"+item.id;

            switch(tip) {
                case "REGULAR":
                    $(id).removeClass();
                    angular.element(document.getElementById(item.id)).addClass("clear");
                    item.type="REGULAR";
                    break;
                case "VIP":
                    $(id).removeClass();
                    angular.element(document.getElementById(item.id)).addClass("clearBlue");
                    item.type="VIP";
                    break;

                case "LOVEBOX":
                    $(id).removeClass();
                    angular.element(document.getElementById(item.id)).addClass("clearRed");
                    item.type="LOVEBOX";
                    break;

                case "BALCONY":
                    $(id).removeClass();
                    angular.element(document.getElementById(item.id)).addClass("clearGreen");
                    item.type="BALCONY";
                    break;

                case "TAKEN":
                    $(id).removeClass();
                    angular.element(document.getElementById(item.id)).addClass("clearWhite");
                    item.type="TAKEN";
                    break;
                default:
                    $(id).removeClass();
                    angular.element(document.getElementById(item.id)).addClass("clear");
                    item.type="REGULAR";
            }
            $scope.lastChecked = item;
        };
        $scope.mouseSelect = function(item, $event, start, tip) {
            if (start) {

                $scope.lastMouseChecked = item;
                $scope.futureCheckState = !item.checked;

            } else if (item !== $scope.lastMouseChecked) {
                $scope.lastChecked = $scope.lastMouseChecked;
                var ev = {
                    shiftKey: true
                };
                select(item, ev, tip);
            }
        };



        $scope.hoverIn = function(box){

            box.checked = 1-box.checked;
        }

        $scope.pickBioskop = function () {

            $scope.sala = false;
            $scope.pozoriste = false;
            $scope.bioskop = true;
            $scope.sedista = false;
            $scope.dogadjaj = false;
            $scope.projekcija = false;
            $scope.karte = false;
            $scope.bpOne = false;


        }

        $scope.pickSedista = function () {

            $scope.sala = false;
            $scope.pozoriste = false;
            $scope.bioskop = false;
            $scope.sedista = true;
            $scope.dogadjaj = false;
            $scope.projekcija = false;
            $scope.karte = false;
            $scope.bpOne = false;


        }

        $scope.pickDogadjaj = function () {

            $scope.sala = false;
            $scope.pozoriste = false;
            $scope.bioskop = false;
            $scope.sedista = false;
            $scope.dogadjaj = true;
            $scope.projekcija = false;
            $scope.karte = false;
            $scope.bpOne = false;


        }

        $scope.pickProjekcija = function () {

            $scope.sala = false;
            $scope.pozoriste = false;
            $scope.bioskop = false;
            $scope.sedista = false;
            $scope.dogadjaj = false;
            $scope.projekcija = true;
            $scope.karte = false;
            $scope.bpOne = false;


        }
        $scope.bpSale = {};

        $scope.pickKarta = function () {

            $scope.sala = false;
            $scope.pozoriste = false;
            $scope.bioskop = false;
            $scope.sedista = false;
            $scope.dogadjaj = false;
            $scope.projekcija = false;
            $scope.karte = true;
            $scope.bpOne = false;


        }

        $scope.pickBpOne = function (id) {

            $http({
                method: 'GET',
                url: 'http://localhost:8096/pb/'+id,
            }).then(function successCallback(response) {
                $scope.bp = response.data;

            }, function errorCallback(response) {
                alert("Greska kod admin get one BP")

            });

            $http({
                method: 'GET',
                url: 'http://localhost:8096//pb/sale/'+id,
            }).then(function successCallback(response) {
                $scope.bpSale = response.data;


            }, function errorCallback(response) {
                alert("Greska kod admin get one BP")

            });

            $scope.sala = false;
            $scope.pozoriste = false;
            $scope.bioskop = false;
            $scope.sedista = false;
            $scope.dogadjaj = false;
            $scope.projekcija = false;
            $scope.karte = false;
            $scope.bpOne = true;


        }



        $scope.editChange = function(){
            $scope.edit = !$scope.edit;
        }

        $scope.pozoristaAdmin = {};
        $scope.bioskopiAdmin = {};

        var getPozorista = function () {
            $http({
                method: 'GET',
                url: 'http://localhost:8096/admin/p/6', // FALI MI DEO ZA ID
            }).then(function successCallback(response) {
                $scope.pozoristaAdmin = response.data;

            }, function errorCallback(response) {
                alert("Greska kod admin pozorista")

            });
        }

        getPozorista();

        var getBioskopi = function () {
            $http({
                method: 'GET',
                url: 'http://localhost:8096/admin/b/6', // FALI MI DEO ZA ID
            }).then(function successCallback(response) {
                $scope.bioskopiAdmin = response.data;

            }, function errorCallback(response) {
                alert("Greska kod admin bioskopi")

            });
        }
        getBioskopi();





        var ac = this;

        var init = function () {

            $scope.zanr = [
                "HOROR",
                "COMEDY",
                "THRILLER",
                "ACTION",
                "ADVENTURE",
                "CRIME",
                "DRAMA",
                "FANTASY",
                "FICTION",
                "MISTERY",
                "MUSICAL",
                "SATIRE",
                "DOCUMENTARY"

            ];

            $scope.tipSedista =[
                "REGULAR",
                "VIP",
                "LOVEBOX",
                "BALCONY",
                "TAKEN"
            ];

            $scope.class = "clear";

        };
        init();

        $scope.indexFunc = function () {
            $location.path("home");
        }

        $scope.bpName = "";


        $scope.addSala = function(id, name){
            $scope.bpName=name;
            $scope.bpId = id;
            $scope.pickSala(false);

        }

        $scope.oneSala =[];


        var drawEditGrid= function(sedista, brRed, brSed){

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
                    checked: true,
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


        $scope.editSala = function(id, name, idBp){
            $http({
                method: 'GET',
                url: 'http://localhost:8096/sala/' +id,
            }).then(function successCallback(response) {
                $scope.oneSala = response.data;
                $scope.newSalaName = $scope.oneSala.ime;
                $scope.newSalaBR = $scope.oneSala.brRed;
                $scope.newSalaBS = $scope.oneSala.brSedista;
                $scope.bpName = name;
                $scope.bpId=idBp;

                drawEditGrid($scope.oneSala.sedista,  $scope.newSalaBR,  $scope.newSalaBS);

                $scope.pickSala(true);





            }, function errorCallback(response) {
                alert("Greska kod dobijanja sale za edit")

            });


        }

        $scope.saveSala = function(newSalaName, newSalaBR, newSalaBS, edit ){
            var b = true;
            if($scope.bpName != "")
                if($scope.bpId)
                    if($scope.bpId)
                        if(newSalaName)
                            if(newSalaName!="")
                                if(newSalaBR)
                                    if(newSalaBR>0 && newSalaBR<76)
                                        if(newSalaBS)
                                            if(newSalaBS>0 && newSalaBS<101)
                                                if($scope.items.length>0)
                                                    b=true;
            if(b) {
                if(edit){

                    var newSalaDTO = {};
                    newSalaDTO = {
                        "brRed": newSalaBR,
                        "brSedista": newSalaBS,
                        "ime": newSalaName,
                        "ustanova": $scope.bpId,
                        "sedista": $scope.items
                    }


                    // console.log(newSalaDTO);
                    $http({
                        method: 'PUT',
                        url: 'http://localhost:8096/sala/edit/'+$scope.oneSala.id,
                        data: newSalaDTO
                    }).then(function successCallback(response) {
                        //  location.reload(); Moze ovako clear
                        alert("Auditorium edited sucessfully");
                        $scope.pickBpOne($scope.bpId);


                    }, function errorCallback(response) {
                        alert("Error occured while editing auditorium");
                    });


                }else {

                    var newSalaDTO = {};
                    newSalaDTO = {
                        "brRed": newSalaBR,
                        "brSedista": newSalaBS,
                        "ime": newSalaName,
                        "ustanova": $scope.bpId,
                        "sedista": $scope.items
                    }


                    // console.log(newSalaDTO);
                    $http({
                        method: 'POST',
                        url: 'http://localhost:8096/sala/add',
                        data: newSalaDTO
                    }).then(function successCallback(response) {
                        //  location.reload(); Moze ovako clear
                        alert("Auditorium added sucessfully");
                        $scope.pickBpOne($scope.bpId);


                    }, function errorCallback(response) {
                        alert("Error occured while adding auditorium");
                    });

                }


            }




        }

        $scope.deleteSala = function(id, pbId){

            if(id)
                if(pbId){

                    $http({
                        method: 'DELETE',
                        url: 'http://localhost:8096/sala/delete/'+pbId+'/'+id
                    }).then(function successCallback(response) {
                        //  location.reload(); Moze ovako clear
                        alert("Auditorium deleted sucessfully");
                        $scope.pickBpOne(pbId);



                    }, function errorCallback(response) {
                        alert("Error occured while deleting auditorium");
                    });
                }



        }



        $scope.addNewEvent = function (newDogadjaName, newDogadjajOpis,newDogadjaZanr, newDogadjajReziser,newDogadjajTrajanje,newDogadjajBodovi,newDogadjajGlumci) {

            var form = document.getElementById("myForm");

            var provera = false;
            if(newDogadjajGlumci)
                if(newDogadjaName)
                    if(newDogadjaZanr)
                        if(newDogadjajReziser)
                            if(newDogadjajOpis)
                                if(newDogadjajTrajanje)
                                    if(newDogadjajTrajanje>0)
                                        if(newDogadjajBodovi)
                                            if(newDogadjajBodovi>=0)
                                                provera=true;

            if(provera) {
                var dogadjajDTO = {
                    "naziv" : newDogadjaName,
                    "trajanje" : newDogadjajTrajanje,
                    "zanr" : newDogadjaZanr,
                    "opis" : newDogadjajOpis,
                    "reziser": newDogadjajReziser,
                    "prosecnaOcena" : 0,
                    "brojOcena" : 0,
                    "donosiBodova" : newDogadjajBodovi,
                    "glumciStr" : newDogadjajGlumci
                }

               // console.log(dogadjajDTO)

                $http({
                    method:'POST',
                    url: 'http://localhost:8096/d',
                    data: dogadjajDTO
                }).then(function successCallback(response){
                  //  location.reload(); Moze ovako clear
                    alert("Event added sucessfully");


                }, function errorCallback(response){
                    alert("Error occured while adding event");
                } );
            }
        }






    }
})();