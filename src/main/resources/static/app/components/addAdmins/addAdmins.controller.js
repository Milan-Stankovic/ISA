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
        	$scope.blankField=false;
        	$scope.emptyField="";
        	$scope.seller_options = [];
        	$scope.type_options = ['FanZone', 'Regular'];
        	$scope.pozBio = [];
        	$scope.item = {"username": "",
	        				"pass": "cinema123",
	        				"tipAdmina": "Regular",
	        				"pozBio": [],
	        				"email": ""
	        				};
        	
        	
        	var regUser={};
        	regUser = $cookies.get('user');
        	$http({
                method: 'GET',
                url: 'http://localhost:8096/admin/'+regUser,
              }).then(function successCallback(response) {
              		if(response.data.tip!="PRED")
              			$location.path('/home');
              });
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
        				"tipAdmina": "SYS",
        				"pozBio": $scope.pozBio,
        				"email": $scope.item.email
        				};
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
		 aac.blankField = function(field){
			 $scope.blankField = true;
			 $scope.emptyField = field;
		      $timeout(function() {
		    	  $scope.blankField = false;
					 $scope.emptyField = "";
		      }, 3000);    
		 }
    }

})();