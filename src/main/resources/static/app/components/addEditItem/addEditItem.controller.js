(function () {
    'use strict';

    angular
		.module('app')
		.controller('addEditItemController', addEditItemController);

    addEditItemController.$inject = ['$location', '$scope', '$rootScope', '$cookies', '$window', '$http', '$timeout', '$stateParams'];
    function addEditItemController($location, $scope, $rootScope, $cookies, $window, $http, $timeout, $stateParams) {
    	var aeic = this;
    	
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
        	
        	
        	//proveri da li je admin koji ima pristup ovome
        	var regUser={};
        	regUser = $cookies.get('user');
        	$http({
                method: 'GET',
                url: 'http://localhost:8096/admin/'+regUser,
              }).then(function successCallback(response) {
              		if(response.data==null){
              			$location.path('/home');
              			return;
              		}else{
              		//	if(response.data.tip!="FAN")
              				$scope.regUser=response.data;
              				$http({
              	                method: 'GET',
              	                url: 'http://localhost:8096/pb'
              	              }).then(function successCallback(response) {
              	                   var items = response.data;
              	                   if (items!=null && items!=undefined){
              	                	   for(var i=0; i<items.length; i++){
              	                		//   for(var j=0;j<items[i].admini.length; j++)
              	                			  // if(items[i].admini[j]==regUser)
              	                				   $scope.seller_options.push(items[i]);
              	                	   }
              	                   }
              	                }
              	              );
              		/*	else{
                  			$location.path('/home');        
                  			return;
              			}*/
              		}
                });
        	
        	$scope.item = {
        			"id" : 0,
           			"naziv": "",
           			"opis": "",
           			"cena": "",
           			"slika": "",
           			"postavio": $scope.regUser,
           			"preuzeti": {},
           			"aktivan": true, 
           			"rezervacije": []
        	};
        	$scope.item_options = [];
        	$http({
                method: 'GET',
                url: 'http://localhost:8096/rekviziti/zvanicni'
              }).then(function successCallback(response) {
                   var items0 = response.data;
                   if (items0!=null && items0!=undefined){
                	   for(var i=0; i<items0.length; i++){
                		   $scope.item_options.push(items0[i]);
                	   }
                   }
                });
        	
        };
        init();
       
        aeic.add = function(){
        	if($scope.item.naziv==""){
        		aeic.blankField("Name");
        		return;
        	}if($scope.item.opis==""){
        		aeic.blankField("Description");
        		return;        		
        	}if($scope.item.cena=="" || !parseFloat($scope.item.cena)){
        		aeic.blankField("Price");
        		return;        		
        	}if($scope.item.preuzeti==""){
        		aeic.blankField("Seller");
        		return;        		
        	}
        	var file = $scope.item.slika; 
        	if(file=="" || file==undefined){        		
        		var data = {
    				"id" : 0,
           			"naziv": $scope.item.naziv,
           			"opis": $scope.item.opis,
           			"cena": parseFloat($scope.item.cena),
           			"slika": "",
           			"postavio":$scope.regUser,
           			"preuzeti": $scope.item.preuzeti,
           			"aktivan": true, 
           			"rezervacije": []
        		};
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
                    			"id" : 0,
                       			"naziv": "",
                       			"opis": "",
                       			"cena": "",
                       			"slika": "",
                       			"postavio": $scope.regUser,
                       			"preuzeti": {},
                       			"aktivan": true, 
                       			"rezervacije": []
                    	   };
                       }
                    });
        	}
        	//za sliku
        	else{
	        	var file = $scope.item.slika;
	        	var fileFormData = new FormData();
	            fileFormData.append('file', file);
	        	$http({
	        		method: 'POST',
	                url: 'http://localhost:8096/rekviziti/upload',
	                transformRequest: angular.identity,
	                headers: {'Content-Type': undefined},
	                data: fileFormData
	            }).then(function successCallback(response) {
	            	//za rekvizit
	        		var data = {
	    				"id" : 0,
	           			"naziv": $scope.item.naziv,
	           			"opis": $scope.item.opis,
	           			"cena": parseFloat($scope.item.cena),
	           			"slika": "",
	           			"postavio":$scope.regUser,
	           			"preuzeti": $scope.item.preuzeti,
	           			"aktivan": true, 
	           			"rezervacije": []
	        		};
	            	data.slika = response.data.slika;
	            	$http({
	                    method: 'POST',
	                    url: 'http://localhost:8096/rekviziti/zvanicni',
	                    headers: {'Content-Type': 'application/json'},
	                    data: data
	                  }).then(function successCallback(response) {
	                       var items = response.data;
	                       if (items==null || items==undefined){
	                    	   aeic.showSthWentWrong();
	                       }
	                       else{
	                    	   aeic.showDone();
	                    	   $scope.item = {
	                       			"id" : 0,
	                       			"naziv": "",
	                       			"opis": "",
	                       			"cena": "",
	                       			"slika": "",
	                       			"postavio": $scope.regUser,
	                       			"preuzeti": {},
	                       			"aktivan": true, 
	                       			"rezervacije": []
	                    	   };
	                       }
	                   });
	            	});    
	        	}
        }
 //PROVERI SVE ZA EDIT       
        aeic.edit = function(){
        	if($scope.itemToEdit==undefined)
        		return;
        	if($scope.eitem.naziv==""){
        		aeic.eblankField("Name");
        		return;
        	}if($scope.eitem.opis==""){
        		aeic.eblankField("Description");
        		return;        		
        	}if($scope.eitem.cena=="" || !parseFloat($scope.eitem.cena)){
        		aeic.eblankField("Price");
        		return;        		
        	}if($scope.eitem.preuzeti==""){
        		aeic.eblankField("Seller");
        		return;        		
        	}
        	$scope.eitem = $scope.itemToEdit;
        	var file = $scope.eitem.slika; 
        	if(file=="" || file==undefined){        		
        		var data = {
    				"id" : $scope.eitem.id,
           			"naziv": $scope.eitem.naziv,
           			"opis": $scope.eitem.opis,
           			"cena": parseFloat($scope.eitem.cena),
           			"slika": $scopee.eitem.slika,
           			"postavio":$scope.regUser,
           			"preuzeti": $scope.eitem.preuzeti,
           			"aktivan": true, 
           			"rezervacije": []
        		};
            	$http({
                    method: 'PUT',
                    url: 'http://localhost:8096/rekviziti/zvanicni',
                    data: data              
                  }).then(function successCallback(response) {
                       var items = response.data;
                       if (items==null || items==undefined){
                    	   aeic.eshowSthWentWrong();
                       }
                       else{
                    	   aeic.eshowDone();
                    	   $scope.item_options=""
                    	   $scope.eitem = {
                    			"id" : 0,
                       			"naziv": "",
                       			"opis": "",
                       			"cena": "",
                       			"slika": "",
                       			"postavio": $scope.regUser,
                       			"preuzeti": {},
                       			"aktivan": true, 
                       			"rezervacije": []
                    	   };
                       }
                    });
        	}
        	//za sliku
        	else{
	        	var file = $scope.eitem.slika;
	        	var fileFormData = new FormData();
	            fileFormData.append('file', file);
	        	$http({
	        		method: 'POST',
	                url: 'http://localhost:8096/rekviziti/upload',
	                transformRequest: angular.identity,
	                headers: {'Content-Type': undefined},
	                data: fileFormData
	            }).then(function successCallback(response) {
	            	//za rekvizit
	        		var data = {
	    				"id" : $scope.eitem.id,
	           			"naziv": $scope.eitem.naziv,
	           			"opis": $scope.eitem.opis,
	           			"cena": parseFloat($scope.eitem.cena),
	           			"slika": "",
	           			"postavio":$scope.regUser,
	           			"preuzeti": $scope.eitem.preuzeti,
	           			"aktivan": true, 
	           			"rezervacije": []
	        		};
	            	data.slika = response.data.slika;
	            	$http({
	                    method: 'PUT',
	                    url: 'http://localhost:8096/rekviziti/zvanicni',
	                    headers: {'Content-Type': 'application/json'},
	                    data: data
	                  }).then(function successCallback(response) {
	                       var items = response.data;
	                       if (items==null || items==undefined){
	                    	   aeic.eshowSthWentWrong();
	                       }
	                       else{
	                    	   aeic.eshowDone();
	                    	   $scope.item_options="";
	                    	   $scope.eitem = {
	                       			"id" : 0,
	                       			"naziv": "",
	                       			"opis": "",
	                       			"cena": "",
	                       			"slika": "",
	                       			"postavio": $scope.regUser,
	                       			"preuzeti": {},
	                       			"aktivan": true, 
	                       			"rezervacije": []
	                    	   };
	                       }
	                   });
	            	});    
	        	}
        }
        
        aeic.setEditFields = function(){
        	$scope.eitem = $scope.itemToEdit;        	
        }
        
        aeic.deleteItem = function(){
        	$scope.eitem = $scope.itemToEdit;
        	$http({
                method: 'DELETE',
                url: 'http://localhost:8096/rekviziti/zvanicni/'+$scope.itemToEdit.id
              }).then(function successCallback(response) {
                   
              });
        }
        
        aeic.eshowSthWentWrong = function() {
 		      $scope.eshowSthWentWrong = true;
 		      $timeout(function() {
 		    	  $scope.eshowSthWentWrong = false;
 		      }, 3000);    
  		 }
  		aeic.showSthWentWrong = function() {
		      $scope.showSthWentWrong = true;
		      $timeout(function() {
		    	  $scope.showSthWentWrong = false;
		      }, 3000);    
		 }
		 aeic.showDone = function() {
		      $scope.showDone = true;
		      $timeout(function() {
		    	  $scope.showDone = false;
		      }, 3000);    
		 }
		 aeic.eshowDone = function() {
		      $scope.eshowDone = true;
		      $timeout(function() {
		    	  $scope.eshowDone = false;
		      }, 3000);    
		 }
		 aeic.blankField = function(field){
			 $scope.blankField = true;
			 $scope.emptyField = field;
		      $timeout(function() {
		    	  $scope.blankField = false;
					 $scope.emptyField = "";
		      }, 3000);    
		 }
		 aeic.eblankField = function(field){
			 $scope.eblankField = true;
			 $scope.eemptyField = field;
		      $timeout(function() {
		    	  $scope.eblankField = false;
					 $scope.eemptyField = "";
		      }, 3000);    
		  }
    }
})();