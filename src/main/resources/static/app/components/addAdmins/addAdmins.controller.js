(function () {
    'use strict';

    angular
		.module('app')
		.controller('addAdminsController', addAdminsController);

    addAdminsController.$inject = ['$location', '$scope', '$rootScope', '$cookies', '$window', '$http', '$timeout'];
    function addAdminsController($location, $scope, $rootScope, $cookies, $window, $http, $timeout) {
    	var aac = this;
    	
        var init = function (){
        	$scope.showDone=false;
        	$scope.showSthWentWrong=false;
        	$scope.eshowDone=false;
        	$scope.eshowSthWentWrong=false;
        	$scope.blankField=false;
        	$scope.emptyField="";
        	$scope.eblankField=false;
        	$scope.eemptyField="";
        	$scope.seller_options = [];
        	$scope.type_options = ['FanZone', 'Regular'];
        	$scope.pozBio = [];
        	$scope.item = {"username": "",
	        				"pass": "cinema123",
	        				"tipAdmina": "Regular",
	        				"pozBio": [],
	        				"email": ""
	        				};
        	
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
        	var regUser={};
        	regUser = $cookies.get('user');
        	/*$http({
                method: 'GET',
                url: 'http://localhost:8096/admin/'+regUser,
              }).then(function successCallback(response) {
              		if(response.data.tip!="SYS")
              			$location.path('/home');
              });*/
        	$scope.pb = [];
        	$http({
                method: 'GET',
                url: 'http://localhost:8096/pb'
              }).then(function successCallback(response) {
              		var pozBio = response.data;
              		for(var i=0; i<pozBio.length; i++){
              			$scope.pb.push(pozBio[i]);
              		}
              });

        };
        init();
        
        aac.addToList = function(id){
        	var index = $scope.pozBio.indexOf(id);
        	if(index!=-1){
        		$scope.pozBio.splice(index, 1);
        	}else{
            	$scope.pozBio.push(id);        		
        	}
        }
       
        aac.add = function(){
        	if($scope.item.username==""){
        		aac.blankField("Userame");
        		return;
        	}

        	if($scope.item.email=="" || $scope.item.email==undefined){
        		aac.blankField("Email");
        		return;
        	}
        	var data = {"username": $scope.item.username,
        				"pass": "cinema123",
        				"tipAdmina": "POZBI",
        				"pozBio": $scope.pozBio,
        				"email": $scope.item.email
        				};
        	if($scope.item.tipAdmina=='FanZone')
        		data.pozBio="FAN";
        	$http({
                method: 'POST',
                url: 'http://localhost:8096/admins',
                data: data
              }).then(function successCallback(response) {
            	  aac.showDone();
            	  $scope.item.username="";
            	  $scope.item.email="";
            	  $scope.item.tipAdmina="Regular";
              });
        		
        }	 
        
        aac.edit = function(){
        	if($scope.bs.goldTreshold=="" || $scope.bs.silverTreshold=="" || $scope.bs.bronzeTreshold=="" ||
        			!parseInt($scope.bs.goldTreshold) || !parseInt($scope.bs.silverTreshold) || !parseInt($scope.bs.bronzeTreshold)){
        		aac.eblankField("Some Treshold");
        		return;
        	}
        	if($scope.bs.bronzePopust=="" || $scope.bs.silverPopust=="" || $scope.bs.goldPopust=="" ||
        		!parseInt($scope.bs.goldPopust) || !parseInt($scope.bs.silverPopust) || !parseInt($scope.bs.bronzePopust)){
        		aac.eblankField("Some Discount");
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
            	  aac.eshowDone();  
              });
        	
        	
        }
        
		 aac.eshowSthWentWrong = function() {
		      $scope.eshowSthWentWrong = true;
		      $timeout(function() {
		    	  $scope.eshowSthWentWrong = false;
		      }, 3000);    
 		 }
		 aac.showSthWentWrong = function() {
		      $scope.showSthWentWrong = true;
		      $timeout(function() {
		    	  $scope.showSthWentWrong = false;
		      }, 3000);    
		 }
 		aac.showDone = function() {
		      $scope.showDone = true;
		      $timeout(function() {
		    	  $scope.showDone = false;
		      }, 3000);    
		 }
		 aac.eshowDone = function() {
		      $scope.eshowDone = true;
		      $timeout(function() {
		    	  $scope.eshowDone = false;
		      }, 3000);    
		 }
		 aac.blankField = function(field){
			 $scope.blankField = true;
			 $scope.emptyField = field;
		      $timeout(function() {
		    	  $scope.blankField = false;
					 $scope.emptyField = "";
		      }, 3000);    
		 }
		 aac.eblankField = function(field){
			 $scope.eblankField = true;
			 $scope.eemptyField = field;
		      $timeout(function() {
		    	  $scope.eblankField = false;
					 $scope.eemptyField = "";
		      }, 3000);    
		  }
    }

})();