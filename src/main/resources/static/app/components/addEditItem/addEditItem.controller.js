(function () {
    'use strict';

    angular
		.module('app')
		.controller('addEditItemController', addEditItemController);

    addEditItemController.$inject = ['$location', '$scope', '$rootScope', '$cookies', '$window', '$http', '$timeout'];
    function addEditItemController($location, $scope, $rootScope, $cookies, $window, $http, $timeout) {
    	var aeic = this;
    	
        var init = function (){
        	//proveri da li je admin koji ima pristup ovome
        	$scope.item = {
        			"naziv": "",
        			"opis": "",
        			"cena": "",
        			"slika": "",
        			"preuzeti": ""
        	};
        	$scope.showDone=false;
        	$scope.showSthWentWrong=false;
        	$scope.eshowDone=false;
        	$scope.eshowSthWentWrong=false;
        	$scope.blankField=false;
        	$scope.emptyField="";
        	$scope.seller_options = [];
        	$http({
                method: 'GET',
                url: 'http://localhost:8096/p'
              }).then(function successCallback(response) {
                   var items = response.data;
                   if (items!=null && items!=undefined){
                	   for(var i=0; i<items.length; i++){
                		   $scope.seller_options.push(items[i].naziv);
                	   }
                   }
                }
              );
        	$scope.item_options = [];
        	$http({
                method: 'GET',
                url: 'http://localhost:8096/rekviziti/zvanicni'
              }).then(function successCallback(response) {
                   var items0 = response.data;
                   if (items0!=null && items0!=undefined){
                	   for(var i=0; i<items0.length; i++){
                		   $scope.item_options.push(items0[i].naziv);
                	   }
                   }
                }
              );
        	
        };
        init();
       
        aeic.add = function(){
        	if($scope.item.naziv==""){
        		aeic.blankField("Name");
        		return;
        	}if($scope.item.opis==""){
        		aeic.blankField("Description");
        		return;        		
        	}if($scope.item.cena==""){
        		aeic.blankField("Price");
        		return;        		
        	}if($scope.item.preuzeti==""){
        		aeic.blankField("Seller");
        		return;        		
        	}
        	var data = $scope.item;
        	$http({
                method: 'POST',
                url: 'http://localhost:8096/rekviziti/zvanicni',
                data: data                
              }).then(function successCallback(response) {
                   var items = response.data;
                   if (items==null || items==undefined){
                	   aeic.showSthWentWrong();
                   }
                   else{
                	   aeic.showDone();
                	   $scope.item = {
                   			"naziv": "",
                   			"opis": "",
                   			"cena": "",
                   			"slika": "",
                   			"preuzeti": ""
                	   };
                   }
                }
              );
        }
        
        aeic.edit = function(){
        	
        }
        
        
        
        aeic.eshowSthWentWrong = function() {
 		      $scope.eshowSthWentWrong = true;
 		      $timeout(function() {
 		    	  $scope.eshowSthWentWrong = false;
 		      }, 3000);    
  		 };
  		aeic.showSthWentWrong = function() {
		      $scope.showSthWentWrong = true;
		      $timeout(function() {
		    	  $scope.showSthWentWrong = false;
		      }, 3000);    
		 };
		 aeic.showDone = function() {
		      $scope.showDone = true;
		      $timeout(function() {
		    	  $scope.showDone = false;
		      }, 3000);    
		 };
		 aeic.eshowDone = function() {
		      $scope.eshowDone = true;
		      $timeout(function() {
		    	  $scope.eshowDone = false;
		      }, 3000);    
		 };
		 aeic.blankField = function(field){
			 $scope.blankField = true;
			 $scope.emptyField = field;
		      $timeout(function() {
		    	  $scope.blankField = false;
					 $scope.emptyField = "";
		      }, 3000);    
		 }
        
    }

})();