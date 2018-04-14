(function () {
    'use strict';

    angular
		.module('app')
		.controller('adminModeController', adminModeController);

    adminModeController.$inject = ['$location', '$scope', '$rootScope', '$cookies', '$window', '$http'];
    function adminModeController($location, $scope, $rootScope, $cookies, $window, $http) {
    	var amc = this;
    	
        var init = function (){
        	var regUser={};
        	regUser = $cookies.get('user');
        /*	$http({
                method: 'GET',
                url: 'http://localhost:8096/admin/'+regUser,
              }).then(function successCallback(response) {
              		if(response.data.tip!="SYS")
              			$location.path('/home');
              });*/
//ADD theatre/cinema
        	$scope.tcshowDone=false;
            $scope.tcshowSthWentWrong=false;
            $scope.tcblankField=false;
            $scope.tcemptyField="";
        	$scope.type_options = ["theatre", "cinema"];
        	$scope.tc={"naziv":"",
        				"opis":"",
        				"adresa":"",
        				"tipUstanove":"theatre"};
//EDIT theatre/cinema
        	$scope.eshowDone=false;
            $scope.eshowSthWentWrong=false;
            $scope.eblankField=false;
            $scope.eemptyField="";
        	$scope.e={"naziv":"",
        			 "opis":"",
        		     "adresa":"",
        			 "tipUstanove":""};
//treshold
            $scope.tshowDone=false;
            $scope.tshowSthWentWrong=false;
            $scope.tblankField=false;
            $scope.temptyField="";
            $scope.bs ={"goldTreshold":"",
    				"silverTreshold":"",
    				"bronzeTreshold":"",
    				"goldPopust":"",
    				"silverPopust":"",
    				"bronzePopust":""        			
    				};
	    	$http({
	            method: 'GET',
	            url: 'http://localhost:8096/bodSkala'
	          }).then(function successCallback(response) {
	        	  if(response.data!="")
	        		  $scope.bs =response.data;
	          });
            
//hall
            $scope.hshowDone=false;
            $scope.hshowSthWentWrong=false;
            $scope.hblankField=false;
            $scope.hemptyField="";
            $scope.seatType_options = ["regular", "balcony", "vip", "lovebox", "taken"];
            $scope.hall={"ime":"",
            			"brSedista":"",
            			"brRed":"",
            			"ustanova": 0,
            			"tipSedista": "regular"};
//admin	    	
            $scope.ashowDone=false;
            $scope.ashowSthWentWrong=false;
            $scope.ablankField=false;
            $scope.aemptyField="";
            $scope.admType_options = ["fanZone", "for Theatre/Cinema"];
            $scope.adm={"resp": "",
            			"username": "",
            			"email":"",
            			"pass":"theatre123",
            			"tipAdmina":"fanZone"};
            
            
        	$http({
                method: 'GET',
                url: 'http://localhost:8096/pb'
              }).then(function successCallback(response) {
            	  $scope.pb = response.data;
                }
              );

        };
        init();
       
        amc.editTreshold = function(){
        	if($scope.bs.goldTreshold=="" || $scope.bs.silverTreshold=="" || $scope.bs.bronzeTreshold=="" ||
        			!parseInt($scope.bs.goldTreshold) || !parseInt($scope.bs.silverTreshold) || !parseInt($scope.bs.bronzeTreshold)){
        		aac.tblankField("Some Treshold");
        		return;
        	}
        	if($scope.bs.bronzePopust=="" || $scope.bs.silverPopust=="" || $scope.bs.goldPopust=="" ||
        		!parseInt($scope.bs.goldPopust) || !parseInt($scope.bs.silverPopust) || !parseInt($scope.bs.bronzePopust)){
        		aac.tblankField("Some Discount");
        		return;
        	}
        	var d = new Date();
        	var n = d.getTime();
        	var data = { "id": 0,
        			"goldTreshold":parseInt($scope.bs.goldTreshold),
    				"silverTreshold":parseInt($scope.bs.silverTreshold),
    				"bronzeTreshold":parseInt($scope.bs.bronzeTreshold),
    				"goldPopust":parseInt($scope.bs.goldPopust),
    				"silverPopust":parseInt($scope.bs.silverPopust),
    				"bronzePopust":parseInt($scope.bs.bronzePopust),
    				"datum": n
    				};
        	$http({
                method: 'POST',
                url: 'http://localhost:8096/bodSkala',
                data: data
              }).then(function successCallback(response) {
            	  amc.tshowDone();  
              });
        	
        	
        }
        
        amc.addHall = function(){
        	
        }
        
        amc.addAdmin = function(){
        	
        }
        
        amc.addTC = function(){
        	
        }
        amc.editTC = function(){
        	
        }
        amc.setTCToEdit = function(){
        	
        }
        
        
        amc.tcshowDone = function() {
		      $scope.tcshowDone = true;
		      $timeout(function() {
		    	  $scope.tcshowDone = false;
		      }, 3000);    
		 }
		 amc.tcblankField = function(field){
			 $scope.tcblankField = true;
			 $scope.tcemptyField = field;
		      $timeout(function() {
		    	  $scope.tcblankField = false;
					 $scope.tcemptyField = "";
		      }, 3000);    
		  }
		 amc.tcshowSthWentWrong = function() {
		      $scope.tcshowSthWentWrong = true;
		      $timeout(function() {
		    	  $scope.tcshowSthWentWrong = false;
		      }, 3000);    
		 }
		 amc.eshowDone = function() {
		      $scope.eshowDone = true;
		      $timeout(function() {
		    	  $scope.eshowDone = false;
		      }, 3000);    
		 }
		 amc.eblankField = function(field){
			 $scope.eblankField = true;
			 $scope.eemptyField = field;
		      $timeout(function() {
		    	  $scope.eblankField = false;
					 $scope.eemptyField = "";
		      }, 3000);    
		  }
		 amc.eshowSthWentWrong = function() {
		      $scope.eshowSthWentWrong = true;
		      $timeout(function() {
		    	  $scope.eshowSthWentWrong = false;
		      }, 3000);    
		 }
        amc.tshowDone = function() {
		      $scope.tshowDone = true;
		      $timeout(function() {
		    	  $scope.tshowDone = false;
		      }, 3000);    
		 }
		 amc.tblankField = function(field){
			 $scope.tblankField = true;
			 $scope.temptyField = field;
		      $timeout(function() {
		    	  $scope.tblankField = false;
					 $scope.temptyField = "";
		      }, 3000);    
		  }
		 amc.tshowSthWentWrong = function() {
		      $scope.tshowSthWentWrong = true;
		      $timeout(function() {
		    	  $scope.tshowSthWentWrong = false;
		      }, 3000);    
		 }
		 amc.hshowDone = function() {
		      $scope.hshowDone = true;
		      $timeout(function() {
		    	  $scope.hshowDone = false;
		      }, 3000);    
		 }
		 amc.hblankField = function(field){
			 $scope.hblankField = true;
			 $scope.hemptyField = field;
		      $timeout(function() {
		    	  $scope.hblankField = false;
					 $scope.hemptyField = "";
		      }, 3000);    
		  }
		 amc.hshowSthWentWrong = function() {
		      $scope.hshowSthWentWrong = true;
		      $timeout(function() {
		    	  $scope.hshowSthWentWrong = false;
		      }, 3000);    
		 }
		 amc.ashowDone = function() {
		      $scope.ashowDone = true;
		      $timeout(function() {
		    	  $scope.ashowDone = false;
		      }, 3000);    
		 }
		 amc.ablankField = function(field){
			 $scope.ablankField = true;
			 $scope.aemptyField = field;
		      $timeout(function() {
		    	  $scope.ablankField = false;
					 $scope.aemptyField = "";
		      }, 3000);    
		  }
		 amc.ashowSthWentWrong = function() {
		      $scope.ashowSthWentWrong = true;
		      $timeout(function() {
		    	  $scope.ashowSthWentWrong = false;
		      }, 3000);    
		 }
    }

})();