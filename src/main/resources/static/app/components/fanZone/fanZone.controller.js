(function () {
    'use strict';

    angular
		.module('app')
		.controller('fanZoneController', fanZoneController);

    fanZoneController.$inject = ['$location', '$scope', '$rootScope', '$cookies', '$window', '$http'];
    function fanZoneController($location, $scope, $rootScope, $cookies, $window, $http) {
    	var fzc = this;
    	
        var init = function (){
        	/*$scope.items = [
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
                		
            ];*/
        	
        	$http({
                method: 'GET',
                url: 'http://localhost:8096/rekviziti/zvanicni'
              }).then(function successCallback(response) {
                   var items = response.data;
                   if (items!=null && items!=undefined){
                	   $scope.items = items;
                	   for(var i=0; i<$scope.items.length; i++){
                		   $scope.items[i].push({"showDone": false, "showSthWentWrong": false});
                	   }
                   }else{
                	   $scope.items = [];
                   }
                }
              );

        };
        init();
       
        fzc.rezervisi = function(id){
        	http({
                method: 'GET',
                url: 'http://localhost:8096/admin/'+regUser,
              }).then(function successCallback(response) {
            	  if(response.data!="")
              		$scope.userID=response.data.id;
            	  else{
            		  fzc.showSthWentWrong(id);
            		  return;
            	  }
              });
        	$http({
                method: 'PUT',
                url: 'http://localhost:8096/rekviziti/'+id+'/rezervisi/'+userID
              }).then(function successCallback(response) {
	            	 result = response.data;
	         		 if(result==""){
	         			 fzc.showDone(id);
	         		 }
	         		 else
	         			 fzc.showSthWentWrong(id);	         			 
                }
              );

        }

        fzc.showDone = function(id) {
        	  for(var i=0; i<$scope.items.length; i++){
        		  if($scope.items[i].id==id){
        		      $scope.items[i].showDone = true;
        		      $timeout(function() {
        		    	  $scope.items[i].showDone = false;
        		      }, 3000);        			  
        		  }
        	  }
		 };
		 fzc.showSthWentWrong = function(id) {
       	  for(var i=0; i<$scope.items.length; i++){
       		  if($scope.items[i].id==id){
       		      $scope.items[i].showSthWentWrong = true;
       		      $timeout(function() {
       		    	  $scope.items[i].showSthWentWrong = false;
       		      }, 3000);        			  
       		  }
       	  }
		 };
    }

})();