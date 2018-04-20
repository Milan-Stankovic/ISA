(function() {
    'use strict';




    angular
        .module('app')
        .directive('ngConfirmClick', [
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


        .controller('adminController', adminController).directive('fileUpload', fileUpload);

    fileUpload.$inject = ['$parse'];

    function fileUpload($parse) {

        var directive = {
            restrict: 'A',
            link: function (scope, element, attrs) {
                var model = $parse(attrs.fileUpload);
                var modelSetter = model.assign;

                element.bind('change', function (event) {
                    scope.$apply(function () {

                        scope.myFile = event.target.files[0];
                        event.preventDefault();
                    });
                });

            }
        };
        return directive;

    }


    adminController.$inject = ['$location', '$scope', '$rootScope', '$http', '$window', '$cookies', '$document','$state'];



    function adminController($location, $scope, $rootScope, $http, $window, $cookies, $document,$state) {



        var ac = this;
        $scope.adminId;

        var init = function () {



            var regUser={};
            $scope.adminId= $cookies.get('id');
            regUser=$cookies.get('user');
            $http({
                method: 'GET',
                url: 'http://localhost:8096/admin/'+regUser,
            }).then(function successCallback(response) {
                if(response.data.tip!="POZBI")
                    $location.path('/home');;
            }, function errorCallback(response) {
                alert("Error occured check connection");
                $location.path('/home');
            });


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



        $scope.go = function(state) {


            $state.go(state, {"id" : $scope.adminId} );
        }

        $scope.goOneClick = function(state, param1) {
            $state.go(state, {"admin" : true, "pbId" : param1} );

        }
        $scope.goCenovnik = function(state, param1,param2) {
            $state.go(state, {"salaId":param1, "salaName":param2} );

        }


        $scope.goOne = function(state, param1, param2,param3, param4, param5, param6) {

            $state.go(state, {
                "id" : $scope.adminId,
                "dName" :param1,
                "pTime" :param2,
                "dId" :param3,
                "pId": param4,
                "bpId" : param5,
                "pPrice" :param6
            } );
        }


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
        $scope.allDogadjaji = false;

        //#$cookies.get("id");

        $scope.pickSala = function (edit) {
            $scope.izvestaj = false;
            $scope.allProjekcije = false;
            $scope.sala = true;
            $scope.pozoriste = false;
            $scope.bioskop = false;
            $scope.sedista = false;
            $scope.dogadjaj = false;
            $scope.projekcija = false;
            $scope.karte = false;
            $scope.bpOne = false;
            $scope.editOneSala=true;
            $scope.allDogadjaji = false;
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
            $scope.izvestaj = false;
            $scope.sala = false;
            $scope.pozoriste = true;
            $scope.bioskop = false;
            $scope.sedista = false;
            $scope.dogadjaj = false;
            $scope.projekcija = false;
            $scope.karte = false;
            $scope.bpOne = false;
            $scope.allDogadjaji = false;
            $scope.allProjekcije = false;


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
        $scope.bpDogadjaj=[];

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
            $scope.allDogadjaji = false;
            $scope.allProjekcije = false;
            $scope.izvestaj = false;


        }

        $scope.pickIzvestaj = function(){
            $scope.sala = false;
            $scope.pozoriste = false;
            $scope.bioskop = false;
            $scope.sedista = false;
            $scope.dogadjaj = false;
            $scope.projekcija = false;
            $scope.karte = false;
            $scope.bpOne = false;
            $scope.allDogadjaji = false;
            $scope.allProjekcije = false;
            $scope.izvestaj = true;

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
            $scope.allDogadjaji = false;
            $scope.allProjekcije = false;
            $scope.izvestaj = false;


        }

        $scope.pickDogadjaj = function (edit) {

                $scope.izvestaj = false;
                $scope.sala = false;
                $scope.pozoriste = false;
                $scope.bioskop = false;
                $scope.sedista = false;
                $scope.dogadjaj = true;
                $scope.projekcija = false;
                $scope.karte = false;
                $scope.bpOne = false;
                $scope.allDogadjaji = false;
                $scope.allProjekcije = false;

            if(!edit){
                $scope.dogadjajEdit = false;
                $scope.newDogadjaName="";
                $scope.newDogadjajOpis="";
                $scope.newDogadjajReziser="";
                $scope.newDogadjajTrajanje="";
                $scope.newDogadjajBodovi="";
                $scope.newDogadjajGlumci="";
                this.myFile=null;
            }


        }

        $scope.pickProjekcija = function (edit) {

            $scope.sala = false;
            $scope.pozoriste = false;
            $scope.bioskop = false;
            $scope.sedista = false;
            $scope.dogadjaj = false;
            $scope.projekcija = true;
            $scope.karte = false;
            $scope.bpOne = false;
            $scope.allDogadjaji = false;
            $scope.allProjekcije = false;
            $scope.izvestaj = false;
            if(!edit){
                $scope.newProjekcijaCena = "";
                $scope.newProjekcijaDate = "";
                $scope.newProjekcijaAktivan = false;
                $scope.newProjekcijaSala = null;

            }


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
            $scope.allDogadjaji = false;
            $scope.allProjekcije = false;
            $scope.izvestaj = false;


        }

        $scope.pickBpOne = function (id) {



            $http({
                method: 'GET',
                url: 'http://localhost:8096/pb/'+id,
            }).then(function successCallback(response) {
                $scope.bp = response.data;

            }, function errorCallback(response) {
                alert("Error")

            });



            $http({
                method: 'GET',
                url: 'http://localhost:8096/pb/sale/'+id,
            }).then(function successCallback(response) {
                $scope.bpSale = response.data;


            }, function errorCallback(response) {
                alert("Error")

            });



            $scope.sala = false;
            $scope.pozoriste = false;
            $scope.bioskop = false;
            $scope.sedista = false;
            $scope.dogadjaj = false;
            $scope.projekcija = false;
            $scope.karte = false;
            $scope.bpOne = true;
            $scope.allDogadjaji = false;
            $scope.allProjekcije = false;
            $scope.izvestaj = false;


        }

        $scope.getProjections= function(koji){
            switch (koji) {
                case 0:
                    $scope.eventProjekcije = $scope.activeProjekcijeFiltered;
                    break;
                case 1:
                    $scope.eventProjekcije = $scope.inactiveProjekcijeFiltered;
                    break;

                case 2:
                    $scope.eventProjekcije = $scope.allProjekcijeFiltered;
                    break;

                default :
                    $scope.eventProjekcije = $scope.allProjekcijeFiltered;
            }
        }

        $scope.getEvents = function(koji){
            switch (koji) {
                case 0:
                    $scope.bpDogadjaj = $scope.currentDogadjajiFilter;
                    break;
                case 1:
                    $scope.bpDogadjaj = $scope.upcomingDogadjajiFilter;
                    break;

                case 2:
                    $scope.bpDogadjaj = $scope.endedDogadjajiFitler;
                    break;
                case 3:
                    $scope.bpDogadjaj = $scope.allDogadjajiFilter;
                    break;

                default :
                    $scope.bpDogadjaj = $scope.currentDogadjajiFilter;
            }
        }


        $scope.changeCheckbox= function(){
            $scope.newProjekcijaAktivan = !$scope.newProjekcijaAktivan;
        }


        $scope.allDogadjajiFilter=[];
        $scope.currentDogadjajiFilter=[];
        $scope.upcomingDogadjajiFilter=[];
        $scope.endedDogadjajiFitler=[];

        var filterCurrent = function(dogadjaji){
            $scope.currentDogadjajiFilter=[];
            for(var i in dogadjaji){
                if(dogadjaji[i].status == "CURRENT")
                    $scope.currentDogadjajiFilter.push(dogadjaji[i]);
            }
        }
        var filterUpcoming = function(dogadjaji){
            $scope.upcomingDogadjajiFilter=[];
            for(var i in dogadjaji){
                if(dogadjaji[i].status == "UPCOMING")
                    $scope.upcomingDogadjajiFilter.push(dogadjaji[i]);
            }
        }
        var filterEnded = function(dogadjaji){
            $scope.endedDogadjajiFitler=[];
            for(var i in dogadjaji){
                if(dogadjaji[i].status == "ENDED")
                    $scope.endedDogadjajiFitler.push(dogadjaji[i]);
            }
        }

        $scope.allProjekcije = false;
        $scope.inactiveProjekcijeFiltered=[];
        $scope.activeProjekcijeFiltered=[];
        $scope.allProjekcijeFiltered=[];
        $scope.eventProjekcije = [];

        var filterActive = function(projekcije){
            $scope.activeProjekcijeFiltered=[];
            for(var i in projekcije){

                projekcije[i].vreme=formatDate(projekcije[i].vreme);

                if(projekcije[i].aktivna) {
                    $scope.activeProjekcijeFiltered.push(projekcije[i]);
                }
            }
        }

        var filterInactive = function(projekcije){
            $scope.inactiveProjekcijeFiltered=[];
            for(var i in projekcije){
                if(!projekcije[i].aktivna)
                    $scope.inactiveProjekcijeFiltered.push(projekcije[i]);
            }
        }


        var formatDate = function(dateTemp){

            var date = new Date(dateTemp);
            var day = date.getDate();
            var month = date.getMonth();
            var year = date.getFullYear();
            var hour = date.getHours();
            var min = date.getMinutes();
            var sec = date.getSeconds();
            if(hour<10)
                hour='0'+hour;
            if(min<10)
                min='0'+min;
            if(day<10)
                day='0'+day;
            if(month<10)
                month='0'+month;
            if(sec<10)
                sec = '0'+sec;

            return day+'-'+month+'-'+year+' '+hour+':'+min+':'+sec;

        }


        $scope.showProjections = function(bpId,dogadjajId,newDogadjaName){

            $scope.pickDogadjaj(true);
            $scope.dogadjaj = false;

            $scope.dogadjajId = dogadjajId;
            $scope.newDogadjaName = newDogadjaName;
            $scope.bpId=bpId;


            $http({
                method: 'GET',
                url: 'http://localhost:8096/d/projekcije/'+dogadjajId,
            }).then(function successCallback(response) {
                $scope.allProjekcijeFiltered= response.data;
                $scope.eventProjekcije = response.data;
                filterActive($scope.eventProjekcije);
                filterInactive($scope.eventProjekcije);
                $scope.allProjekcije = true;

            }, function errorCallback(response) {
                alert("Error while getting Projections");


            });






        }

        $scope.pickAllDogadjaji = function(id, naziv){

            $scope.sala = false;
            $scope.pozoriste = false;
            $scope.bioskop = false;
            $scope.sedista = false;
            $scope.dogadjaj = false;
            $scope.projekcija = false;
            $scope.karte = false;
            $scope.izvestaj = false;

            $scope.bpId=id;
            $scope.bpName=naziv;


            $http({
                method: 'GET',
                url: 'http://localhost:8096/pb/dogadjaj/'+id,
            }).then(function successCallback(response) {
                $scope.allDogadjajiFilter= response.data;
                $scope.bpDogadjaj = response.data;
                filterCurrent($scope.bpDogadjaj);
                filterEnded($scope.bpDogadjaj);
                filterUpcoming($scope.bpDogadjaj);
                $scope.bpOne = false;
                $scope.allDogadjaji = true;


            }, function errorCallback(response) {
                alert("Error getting Events");
                $scope.pickBpOne(id);


            });


        }



        $scope.dogadjajEdit = false;
        $scope.projekcijaId=[];




        $scope.editProjection = function(projekcijaId, dogadjajId, dogadjajName, bpId, bpName){

            $http({
                method: 'GET',
                url: 'http://localhost:8096/projekcija/'+projekcijaId,
            }).then(function successCallback(response) {
                var projekcija = response.data;
                $scope.projekcijaId = projekcija.id;
                $scope.editProjekcija=true;
                $scope.dogadjajId = dogadjajId;
                $scope.bpName=bpName;
                $scope.bpId = bpId;
                $scope.dogadjajNaziv= dogadjajName;
                console.log(projekcija);
                $scope.newProjekcijaCena =projekcija.cena;
                $scope.newProjekcijaSala =projekcija.sala;
                $scope.newProjekcijaDate= formatDate(projekcija.vreme);
                $scope.newProjekcijaAktivan = projekcija.aktivna;
                getSale(bpId);
                $scope.pickProjekcija(true);

            }, function errorCallback(response) {
                alert("Error getting Event");


            });

        }
        $scope.newProjekcijaAktivan = false;

        $scope.editDogadjaj = function(dogadjajId, bpNaziv, bpId){

            $scope.newProjekcijaAktivan = false;

            $http({
                method: 'GET',
                url: 'http://localhost:8096/d/'+dogadjajId,
            }).then(function successCallback(response) {
                var dogadjaj = response.data;
                $scope.dogadjajEdit = true;
                $scope.dogadjajId = dogadjajId;
                pickZanr(dogadjaj.zanr);
                $scope.newDogadjaName = dogadjaj.naziv;
                $scope.newDogadjajOpis=dogadjaj.opis;
                $scope.newDogadjajReziser=dogadjaj.reziser;
                $scope.newDogadjajTrajanje=dogadjaj.trajanje;
                $scope.newDogadjajBodovi=dogadjaj.donosiBodova;
                $scope.newDogadjajGlumci=dogadjaj.glumciStr;
                $scope.dogadjaSlika = dogadjaj.slika;
                switchStatus(dogadjaj.status);
                $scope.bpId=bpId;
                $scope.bpName=bpNaziv;
                $scope.pickDogadjaj(true);

            }, function errorCallback(response) {
                alert("Error getting Event");
                $scope.pickBpOne(bpId);


            });

        }

        $scope.editChange = function(){
            $scope.edit = !$scope.edit;
        }

        $scope.pozoristaAdmin = {};
        $scope.bioskopiAdmin = {};

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

        getPozorista();
        getBioskopi();



        $scope.dogadjajStatusi = [
            "UPCOMING",
            "ENDED",
            "CURRENT"
        ];

        var switchStatus = function(status) {
            switch (status) {
                case "UPCOMING":
                    $scope.statusDogadjaja = $scope.dogadjajStatusi[0];
                    break;
                case "ENDED":
                    $scope.statusDogadjaja = $scope.dogadjajStatusi[1];
                    break;

                case "CURRENT":
                    $scope.statusDogadjaja = $scope.dogadjajStatusi[2];
                    break;

                default :
                    $scope.statusDogadjaja = $scope.dogadjajStatusi[0];
            }
        }

        var pickZanr = function(zanr){

            switch(zanr) {
                case "HOROR":
                    $scope.newDogadjajZanr = $scope.zanr[0];
                    break;
                case "COMEDY":
                    $scope.newDogadjajZanr = $scope.zanr[1];
                    break;
                case "THRILLER":
                    $scope.newDogadjajZanr = $scope.zanr[2];
                    break;
                case "ACTION":
                    $scope.newDogadjajZanr = $scope.zanr[3];
                    break;
                case "ADVENTURE":
                    $scope.newDogadjajZanr = $scope.zanr[4];
                    break;
                case "CRIME":
                    $scope.newDogadjajZanr = $scope.zanr[5];
                    break;
                case "DRAMA":
                    $scope.newDogadjajZanr = $scope.zanr[6];
                    break;
                case "FANTASY":
                    $scope.newDogadjajZanr = $scope.zanr[7];
                    break;
                case "FICTION":
                    $scope.newDogadjajZanr = $scope.zanr[8];
                    break;
                case "MISTERY":
                    $scope.newDogadjajZanr = $scope.zanr[9];
                    break;
                case "MUSICAL":
                    $scope.newDogadjajZanr = $scope.zanr[10];
                    break;
                case "SATIRE":
                    $scope.newDogadjajZanr = $scope.zanr[11];
                    break;
                case "DOCUMENTARY":
                    $scope.newDogadjajZanr = $scope.zanr[12];
                    break;
                default:
                    $scope.newDogadjajZanr = null;
            }
        }







        $scope.indexFunc = function () {
            $location.path("home");
        }

        $scope.bpName = "";


        $scope.addSala = function(id, name){
            $scope.bpName=name;
            $scope.bpId = id;
            $scope.pickSala(false);

        }

        $scope.dogadjajNaziv="";




        var getSale = function(id){

            $http({
                method: 'GET',
                url: 'http://localhost:8096/pb/sale/'+id,
            }).then(function successCallback(response) {
                $scope.saleProjekcija = response.data;

            }, function errorCallback(response) {
                alert("Error getting Auditoriums for a Projection")

            });

        }


        $scope.addProjection = function(bpId, id, name){


            $scope.editProjekcija=false;


            $http({
                method: 'GET',
                url: 'http://localhost:8096/pb/sale/'+bpId, // FALI MI DEO ZA ID
            }).then(function successCallback(response) {
                $scope.saleProjekcija = response.data;
                $scope.dogadjajId=id;
                $scope.bpId = bpId;
                $scope.dogadjajNaziv = name;

                $scope.pickProjekcija(false);

            }, function errorCallback(response) {
                alert("Error getting Auditoriums for a Projection")

            });



        }


        $scope.addDogadjaj = function(id, name){
            $scope.bpName=name;
            $scope.bpId = id;
            $scope.newProjekcijaAktivan = false;
            $scope.pickDogadjaj(false);

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
                    return 1;
                if (a.id < b.id)
                    return -1;
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


        $scope.salaId=-1;

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
                $scope.salaId = id;

                drawEditGrid($scope.oneSala.sedista,  $scope.newSalaBR,  $scope.newSalaBS);

                $scope.pickSala(true);





            }, function errorCallback(response) {
                alert("Error getting Auditorium")

            });


        }



        $scope.editProjekcija = false;

        $scope.saveProjection = function(bpId,dogadjajId,newProjekcijaCena,newProjekcijaSala,newProjekcijaDate, menjaj, bpNaziv, newProjekcijaAktivan){

            var moze=false;
            if(bpId)
                if(dogadjajId)
                    if(newProjekcijaCena)
                        if(newProjekcijaSala)
                            if(newProjekcijaDate)
                                if(newProjekcijaCena>0)
                                    moze=true;
            if(moze){

                console.log("Aktivan ?");
                console.log($scope.newProjekcijaAktivan);
                console.log(newProjekcijaAktivan);

                var newProjekcijaDTO = {};
                newProjekcijaDTO = {
                    "cena": newProjekcijaCena,
                    "sala": newProjekcijaSala.id,
                    "date": newProjekcijaDate,
                    "ustanova": bpId,
                    "dogadjaj": dogadjajId,
                    "aktivna" : newProjekcijaAktivan
                }

                if(!menjaj){

                    $http({
                        method: 'POST',
                        url: 'http://localhost:8096/d/projekcija',
                        data: newProjekcijaDTO
                    }).then(function successCallback(response) {
                        //  location.reload(); Moze ovako clear
                        alert("Projection added sucessfully");
                        $scope.editDogadjaj(dogadjajId,bpNaziv, bpId);

                    }, function errorCallback(response) {
                        alert("Error occured while adding projection");
                    });


                }else{

                    $http({
                        method: 'PUT',
                        url: 'http://localhost:8096/projekcija/'+$scope.projekcijaId,
                        data: newProjekcijaDTO
                    }).then(function successCallback(response) {
                        //  location.reload(); Moze ovako clear
                        alert("Projection edited sucessfully");
                        $scope.editDogadjaj(dogadjajId,bpNaziv, bpId);

                    }, function errorCallback(response) {
                        alert("Error occured while editing projection");
                    });


                }

            }






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

        $scope.deleteProjekcija= function(projekcijaId, bpId, dogadjajId, dogadjajName){
            if(projekcijaId)
                if(bpId)
                    if(dogadjajId){
                        $http({
                            method: 'DELETE',
                            url: 'http://localhost:8096/dogadjaj/' +dogadjajId+
                            '/projekcija/delete/'+projekcijaId
                        }).then(function successCallback(response) {
                            //  location.reload(); Moze ovako clear
                            alert("Projection deleted sucessfully");
                            $scope.showProjections(bpId, dogadjajId, dogadjajName);



                        }, function errorCallback(response) {
                            alert("Error occured while deleting projection");
                        });

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



        $scope.deleteDogadjaj= function(id, pbId){
            if(id)
                if(pbId){
                    $http({
                        method: 'DELETE',
                        url: 'http://localhost:8096/d/delete/'+id
                    }).then(function successCallback(response) {
                        //  location.reload(); Moze ovako clear
                        alert("Event deleted sucessfully");
                        $scope.pickBpOne(pbId);



                    }, function errorCallback(response) {
                        alert("Error occured while deleting event");
                    });
                }
        }







        $scope.addNewEvent = function (newDogadjaName, newDogadjajOpis,newDogadjaZanr, newDogadjajReziser,newDogadjajTrajanje,newDogadjajBodovi,newDogadjajGlumci, bpId, edit,newDogadjajStatus) {


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
                                                    if(this.myFile || edit)
                                                        provera=true;


                if(provera) {

                    var fd = new FormData();
                   // console.log(this.myFile);
                    fd.append('file', this.myFile);
                    fd.append('data', 'string');
                   // console.log(fd);

                    $scope.statusDogadjaja = newDogadjajStatus;
                    switchStatus(newDogadjajStatus);


                    var dogadjajDTO = {
                        "naziv" : newDogadjaName,
                        "trajanje" : newDogadjajTrajanje,
                        "zanr" : newDogadjaZanr,
                        "opis" : newDogadjajOpis,
                        "reziser": newDogadjajReziser,
                        "donosiBodova" : newDogadjajBodovi,
                        "glumciStr" : newDogadjajGlumci,
                        "pbId": bpId,
                        "status": $scope.statusDogadjaja,
                        "slika" : $scope.dogadjaSlika ///Ako bude bug zbog ovoga je
                    }

                    if(edit){

                        if(this.myFile){

                            $http({
                                method:'POST',
                                url: 'http://localhost:8096/upload',
                                transformRequest: angular.identity,
                                headers: {'Content-Type': undefined},
                                data : fd
                            }).then(function successCallback(response){
                                var lokacija = response.data.rezultat;
                                if(lokacija != "FAILED"){
                                    dogadjajDTO.slika=lokacija;


                                    $http({
                                        method:'PUT',
                                        url: 'http://localhost:8096/d/'+ $scope.dogadjajId,
                                        data : dogadjajDTO
                                    }).then(function successCallback(response){
                                        $scope.pickBpOne(bpId);
                                        alert("Event edited sucessfully");
                                    }, function errorCallback(response){
                                        alert("Error occured while editing event");
                                    } )


                                }else
                                    alert("Error occured while editing event");

                            }, function errorCallback(response){
                                alert("Error occured while editing event");
                            } )
                        }else{

                            $http({
                                method:'PUT',
                                url: 'http://localhost:8096/d/'+ $scope.dogadjajId,
                                data : dogadjajDTO
                            }).then(function successCallback(response){
                                $scope.pickBpOne(bpId);
                                alert("Event edited sucessfully");
                            }, function errorCallback(response){
                                alert("Error occured while editing event");
                            } )


                        }



                    }else{

                        $http({
                            method:'POST',
                            url: 'http://localhost:8096/upload',
                            transformRequest: angular.identity,
                            headers: {'Content-Type': undefined},
                            data : fd
                        }).then(function successCallback(response){
                            var lokacija = response.data.rezultat;
                            if(lokacija != "FAILED"){
                                dogadjajDTO.slika=lokacija;
                                $http({
                                    method:'POST',
                                    url: 'http://localhost:8096/d',
                                    data : dogadjajDTO
                                }).then(function successCallback(response){
                                    $scope.pickBpOne(bpId);
                                    alert("Event added sucessfully");
                                })


                            }else
                                alert("Error occured while adding event");

                        }, function errorCallback(response){
                            alert("Error occured while adding event");
                        } )


                    }
                }
                else
                    alert("You must enter all of the fields to save");

        }

    }
})();