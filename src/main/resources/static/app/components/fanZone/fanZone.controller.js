(function () {
    'use strict';

    angular
		.module('app')
		.controller('fanZoneController', fanZoneController);

    fanZoneController.$inject = ['$location', '$scope', '$rootScope', '$cookies', '$window'];
    function fanZoneController($location, $scope, $rootScope, $cookies, $window) {
    	var fzc = this;
    	
        var init = function (){
        	$scope.items = [
        	  {"id" : 1,
        	    "naziv" : "naziv",
        	    "opis": "opis opis opis opis",
        		"slika": "assets/images/cinema3.jpg",
        		"preuzeti": {"naziv":"Naziv bioskopa", 
        					 "adresa": "adresa bioskopa broj5"},
        		"cena": 423.5},
    		{"id" : 2,
    	     "naziv" : "naziv2",
    	     "opis": "opis opis opis opis",
    		 "slika": "assets/images/conference2.jpg",
    		 "preuzeti": {"naziv":"Naziv bioskopa2", 
    					 "adresa": "adresa bioskopa broj5"},
    		 "cena": 423.5},
        	{"id" : 3,
    	     "naziv" : "naziv3",
    	     "opis": "opis opis opis opis",
    	     "slika": "assets/images/conference2.jpg",
    		 "preuzeti": {"naziv":"Naziv bioskopa3", 
    					 "adresa": "adresa bioskopa broj5"},
    		 "cena": 423.5}
                		
            ];

        };
        init();
        
        $scope.logout = function(){
        }
    }

})();