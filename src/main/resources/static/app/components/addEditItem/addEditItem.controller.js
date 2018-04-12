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
        	$scope.seller_options = [];
        	
        	
        	//proveri da li je admin koji ima pristup ovome
        	$scope.regUser={};
        	/*$cookies.get('user');
        	$scope.regUser = JSON.parse($cookies.get('user'));
        	if($scope.regUser.tip==undefined){
        		$location.path('/home');
        	}        	
        	*/
        	
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
        	//postaviti samo za svoj pb Admin da moze da uredjuje
        	$http({
                method: 'GET',
                url: 'http://localhost:8096/pb'
              }).then(function successCallback(response) {
                   var items = response.data;
                   if (items!=null && items!=undefined){
                	   for(var i=0; i<items.length; i++){
                		   $scope.seller_options.push({"naziv":items[i].naziv, "id":items[i].id});
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
        	var pozBio = {};
        	$http({
                method: 'GET',
                url: 'http://localhost:8096/pb/'+$scope.item.preuzeti.id
              }).then(function successCallback(response) {
                pozBio = response.data;
                var file = $scope.item.slika; 
	        	if(file=="" || file==undefined){        		
	        		var data = {
	    				"id" : 0,
	           			"naziv": $scope.item.naziv,
	           			"opis": $scope.item.opis,
	           			"cena": parseFloat($scope.item.cena),
	           			"slika": "",
	           			"postavio":$scope.regUser,
	           			"preuzeti": pozBio,
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
		           			"postavio":{
		           		        "id": 4,
		           		        "userName": "admin",
		           		        "password": "admin",
		           		        "status": "AKTIVAN",
		           		        "ime": "a",
		           		        "prezime": "a",
		           		        "email": "admin",
		           		        "brojTelefona": null,
		           		        "grad": "a",
		           		        "tip": "SYS",
		           		        "mesta": []
		           		    },
		           			"preuzeti": pozBio,
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
                });
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
		 
		 function convertFileToBase64viaFileReader(url, callback){
			    var xhr = new XMLHttpRequest();
			    xhr.responseType = 'blob';
			    xhr.onload = function() {
			      var reader  = new FileReader();
			      reader.onloadend = function () {
			          callback(reader.result);
			      }
			      reader.readAsDataURL(xhr.response);
			    };
			    xhr.open('GET', url);
			    xhr.send();
			}
        
    }

})();