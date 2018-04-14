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
            

            $scope.hshowDone=false;
            $scope.hshowSthWentWrong=false;
            $scope.hblankField=false;
            $scope.hemptyField="";
            $scope.seatType_options = ["regular", "balcony", "vip", "lovebox", "taken"];
            $scope.hall={"ime":"",
            			"brSedista":"",
            			"brRed":"",
            			"ustanova": 0};
	    	
        	$scope.adding=true;
        	$scope.type_options = ["theatre", "cinema"];
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
    }

})();