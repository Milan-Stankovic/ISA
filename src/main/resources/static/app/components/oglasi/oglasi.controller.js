(function () {
    'use strict';

    angular
		.module('app')
		.controller('oglasiController', oglasiController);

    oglasiController.$inject = ['$location', '$scope', '$rootScope', '$cookies', '$window', '$http', '$timeout'];
    function oglasiController($location, $scope, $rootScope, $cookies, $window, $http, $timeout) {
    	var oc = this;
    	
        var init = function (){
        	$scope.ashowDone=false;
        	$scope.ashowSthWentWrong=false;
        	$scope.ablankField=false;
        	$scope.aemptyField="";  
        	var today = new Date();
        	var dd = today.getDate();
        	var mm = today.getMonth()+1; //January is 0!
        	var yyyy = today.getFullYear();
        	 if(dd<10){
        	        dd='0'+dd
        	    } 
        	    if(mm<10){
        	        mm='0'+mm
        	    } 

        	today = yyyy+'-'+mm+'-'+dd;
        	$scope.today = today;
        	/*	var date = new Date();
        	$scope.items=[{
        		"slika":"",
        		"naziv":"naziv",
        		"opis": "iaos",
        		"cena": 100,
        		"datum": date     		
        	}];*/
        	var regUser={};
        	regUser = $cookies.get('user');
        	$http({
                method: 'GET',
                url: 'http://localhost:8096/api/user/'+regUser,
              }).then(function successCallback(response) {
              		/*if(response.data=="")
              			$location.path('/home');*/
              });

        };
        init();
        
       
        oc.add = function(){
        	if($scope.it.naziv==""){
        		oc.ablankField("Title");
        		return;
        	}
        	if($scope.it.opis==""){
        		oc.ablankField("Description");
        		return;
        	}
        	if($scope.it.datum==undefined ){
        		oc.ablankField("Description");
        		return;
        	}
        	var data = {"username": $scope.item.username,
        				"pass": "default",
        				"tipAdmina": "SYS",
        				"pozBio": [],
        				"email": $scope.item.email
        				};
        	$http({
                method: 'POST',
                url: 'http://localhost:8096/admins',
                data: data
              }).then(function successCallback(response) {
            	  oc.showDone();
            	  $scope.item.username="";
            	  $scope.item.email="";
              });
        		
        }	 
        oc.makeOffer = function(id){
        	
        }
        oc.ashowSthWentWrong = function() {
		      $scope.ashowSthWentWrong = true;
		      $timeout(function() {
		    	  $scope.ashowSthWentWrong = false;
		      }, 3000);    
		 }
        oc.ashowDone = function() {
		      $scope.ashowDone = true;
		      $timeout(function() {
		    	  $scope.ashowDone = false;
		      }, 3000);    
		 }
        oc.ablankField = function(field){
			 $scope.ablankField = true;
			 $scope.aemptyField = field;
		      $timeout(function() {
		    	  $scope.ablankField = false;
					 $scope.aemptyField = "";
		      }, 3000);    
		 }
    }

})();