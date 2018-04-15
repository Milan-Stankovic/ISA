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
              			if(response.data.tip!="FAN"){
                  			$location.path('/home');              				
              			}
              				$scope.regUser=response.data;
              				$http({
              	                method: 'GET',
              	                url: 'http://localhost:8096/fanAdmin/'+$scope.regUser.id+'/pb'
              	              }).then(function successCallback(response) {
              	                   var items = response.data;
              	                   if (items!=null && items!=undefined){
              	                	   for(var i=0; i<items.length; i++){
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
        			"naziv":"",
        			"opis":"",
        		    "slika":"",
        			"cena":0,
        			"pozBioID":0,
        			"admin":$cookies.get('user')
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
        		var postData = {
           			"naziv": $scope.item.naziv,
           			"opis": $scope.item.opis,
           			"cena": parseFloat($scope.item.cena),
           			"slika": "",
           			"postavio":$cookies.get('user'),
           			"preuzeti": $scope.item.preuzeti.id
        		};
            	$http({
                    method: 'POST',
                    url: 'http://localhost:8096/rekviziti/zvanicni',
                    headers: {'Content-Type': 'application/json'},
                    data: postData                
                  }).then(function successCallback(response) {
                       var items = response.data;
                       if (items==null || items==undefined){
                    	   aeic.showSthWentWrong();
                       }
                       else{
                    	   $scope.item_options.push(items);
                    	   aeic.showDone();
                    	   $scope.item = {
                       			"naziv": "",
                       			"opis": "",
                       			"cena": 0,
                       			"slika": "",
                       			"postavio": $cookies.get('user'),
                       			"preuzeti": {}
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
	        		var postData = {
	           			"naziv": $scope.item.naziv,
	           			"opis": $scope.item.opis,
	           			"cena": parseFloat($scope.item.cena),
	           			"slika": "",
	           			"postavio":$cookies.get('user'),
	           			"preuzeti": $scope.item.preuzeti.id,
	        		};
	        		postData.slika = response.data.slika;
	            	$http({
	                    method: 'POST',
	                    url: 'http://localhost:8096/rekviziti/zvanicni',
	                    headers: {'Content-Type': 'application/json;charset=UTF-8'},
	                    data: postData
	                  }).then(function successCallback(response) {
	                       var items = response.data;
	                       if (items==null || items==undefined){
	                    	   aeic.showSthWentWrong();
	                       }
	                       else{
	                    	   $scope.item_options.push(items);
	                    	   aeic.showDone();
	                    	   $scope.item = {
	                       			"naziv": "",
	                       			"opis": "",
	                       			"cena": 0,
	                       			"slika": "",
	                       			"postavio": $cookies.get('user'),
	                       			"preuzeti": {}
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
           			"naziv": $scope.eitem.naziv,
           			"opis": $scope.eitem.opis,
           			"cena": parseFloat($scope.eitem.cena),
           			"slika": $scope.eitem.slika,
           			"postavio":$cookies.get('user'),
           			"preuzeti": $scope.eitem.preuzeti.id,
        		};
            	$http({
                    method: 'PUT',
                    url: 'http://localhost:8096/rekviziti/zvanicni/'+$scope.itemToEdit.id,
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
                       			"naziv": "",
                       			"opis": "",
                       			"cena": 0,
                       			"slika": "",
                       			"postavio": $cookies.get('user'),
                       			"preuzeti": "",
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
	           			"naziv": $scope.eitem.naziv,
	           			"opis": $scope.eitem.opis,
	           			"cena": parseFloat($scope.eitem.cena),
	           			"slika": "",
	           			"admin":$cookies.get('user'),
	           			"preuzeti": $scope.eitem.preuzeti.id
	        		};
	            	data.slika = response.data.slika;
	            	$http({
	                    method: 'PUT',
	                    url: 'http://localhost:8096/rekviziti/zvanicni/'+$scope.itemToEdit.id,
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
	                       			"naziv":"",
	                    			"opis":"",
	                    		    "slika":"",
	                    			"cena":0,
	                    			"pozBioID":0,
	                    			"admin":$cookies.get('user')
	                    	   };
	                       }
	                   });
	            	});    
	        	}
        }
        
        aeic.setEditFields = function(){
        	if($scope.itemToEdit==undefined)
        		return;
        	$scope.eitem = $scope.itemToEdit;
        	for(var i=0; i<$scope.seller_options.length; i++){
        		if($scope.seller_options[i].id==$scope.itemToEdit.id){
        			$scope.eitem.preuzeti = $scope.seller_options[i];
        			return;
        		}
        	}
        	
        }
        
        aeic.deleteItem = function(){
        	$scope.eitem = $scope.itemToEdit;
        	$http({
                method: 'DELETE',
                url: 'http://localhost:8096/rekviziti/zvanicni/'+$scope.itemToEdit.id
              }).then(function successCallback(response) {
            	  var index = $scope.item_options.indexOf($scope.eitem);
            	  $scope.item_options.splice(index, 1);
            	  $scope.item_options="";
            	  $scope.eitem = {
              			"naziv":"",
	           			"opis":"",
	           		    "slika":"",
	           			"cena":0,
	           			"pozBioID":0,
	           			"admin":$cookies.get('user')
	           	   };
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