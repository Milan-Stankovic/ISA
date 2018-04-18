(function () {
    'use strict';

    angular
		.module('app')
		.controller('mojiOPController', mojiOPController);

    mojiOPController.$inject = ['$location', '$scope', '$rootScope', '$cookies', '$window', '$http', '$timeout'];
    function mojiOPController($location, $scope, $rootScope, $cookies, $window, $http, $timeout) {
    	var mopc = this;
    	
        var init = function (){
        	$scope.showSales=true;
        	$scope.regUser = $cookies.get('user');
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

        	today =yyyy+'-'+mm+'-'+dd; 
        	$scope.today = today;
        	var regUser={};
        	$scope.mySales = [];
        	$scope.myOffers = [];
        	$http({
                method: 'GET',
                url: 'http://localhost:8096/api/user/'+$scope.regUser,
              }).then(function successCallback(response) {
              		/*if(response.data=="")
              			$location.path('/home');*/
            	  $scope.regUserID = response.data.id;

              	  $http({
                      method: 'GET',
                      url: 'http://localhost:8096/rekviziti/mojiOglasi/'+$scope.regUserID,
                    }).then(function successCallback(response) {
                    		var all = response.data;
                    		$scope.mySales = response.data;
                    		for(var i=0; i<all.length; i++){
                    			$scope.mySales[i].showEdit=false;
                    			$scope.mySales[i].showOffers=false;
                    			$scope.mySales[i].kraj = mopc.parseDate(all[i].kraj);
                    			$scope.mySales[i].krajDate = new Date(all[i].kraj);
                    		}
                    });
	              	$http({
	                    method: 'GET',
	                    url: 'http://localhost:8096/rekviziti/mojePonude/'+$scope.regUser,
	                  }).then(function successCallback(response) {
	                  		var all = response.data;
	                  		$scope.myOffers = response.data;
	                  		for(var i=0; i<all.length; i++){
	                  			$scope.myOffers[i].showDone=false;
	                  			$scope.myOffers[i].showSthWentWrong=false;
	                  			$scope.myOffers[i].suma = mopc.findSuma(all[i]);
	                  			$scope.myOffers[i].kraj = mopc.parseDate(all[i].kraj);
	                  			
	                  		}
	                  });
              });
        };
        init();
        
        mopc.findSuma  = function(item){
    	  for(var i=0; i<item.licitacija.length; i++){
    		  if(item.licitacija[i].ponudio==$scope.regUser){
    			  return item.licitacija[i].suma;
    		  }
    	  }
    	  return '?';
      }
        mopc.parseDate = function(d){
        	var date = new Date(d);
			var day = date.getDate();
			day = day = (day < 10) ? ("0" + day) : day;
			var month = date.getMonth() + 1;
			month = (month < 10) ? ("0" + month) : month;
			return (day + "-" + month + "-" + date.getFullYear());
        }
        
        mopc.makeNewOffer = function(item){
        	if(!parseFloat(item.novaPonuda)){
        		return;
        	}
        	var data = {"idRekvizita": item.id,
        				"username": $cookies.get('user'),
        				"cena": parseFloat(item.novaPonuda)
        		};
        	$http({
        		method: 'PUT',
        		url: 'http://localhost:8096/rekviziti/polovni/ponuda',
        		data: data
              }).then(function successCallback(response) {
            	  if(response.data==""){
            		  mopc.showDone(item.id, parseFloat(item.novaPonuda));
            		  item.suma=item.novaPonuda;
            		  item.novaPonuda="";
            	  }
            	  else{
            		  mopc.showSthWentWrong(item.id);
            	  }
              });	 
        }
        mopc.acceptOffer = function(item, offer){
        	
        }
        mopc.editItem = function(item){
        	item.showEdit=true;
        }

        mopc.goBack = function(item){
        	item.showEdit=false;
        	
        }
        mopc.checkOffers = function(item){
        	//treba samo da odradi toggle da se vide ponude
        	
        }
        mopc.saveChanges = function(item){
        	//cuva izmene oglasa
        }
        mopc.toggleSales = function(){
        	$scope.showSales=true;
        }
        mopc.toggleOffers = function(){
        	$scope.showSales=false;
        }
        mopc.ashowSthWentWrong = function() {
		      $scope.ashowSthWentWrong = true;
		      $timeout(function() {
		    	  $scope.ashowSthWentWrong = false;
		      }, 3000);    
		 }
        mopc.ashowDone = function() {
		      $scope.ashowDone = true;
		      $timeout(function() {
		    	  $scope.ashowDone = false;
		      }, 3000);    
		 }
        mopc.ablankField = function(field){
			 $scope.ablankField = true;
			 $scope.aemptyField = field;
		      $timeout(function() {
		    	  $scope.ablankField = false;
					 $scope.aemptyField = "";
		      }, 3000);    
		 }
        mopc.showDone = function(id, suma) {
      	  var regI; 
      	  for(var i=0; i<$scope.myOffers.length; i++){
      		  if($scope.myOffers[i].id==id){
      			  regI = i;
      		      $scope.myOffers[regI].showDone = true;
      		      $scope.myOffers[regI].hasOffered = true;
      		      $scope.myOffers[regI].suma = suma;
      		      $timeout(function() {
      		    	  $scope.myOffers[regI].showDone = false;
      		      }, 3000);        			  
      		  }
      	  }
		 }
		 mopc.showSthWentWrong = function(id) {
     	  var regI; 
     	  for(var i=0; i<$scope.myOffers.length; i++){
     		  if($scope.myOffers[i].id==id){
     			  regI = i;
     		      $scope.myOffers[regI].showSthWentWrong = true;
     		      $timeout(function() {
     		    	  $scope.myOffers[regI].showSthWentWrong = false;
     		      }, 3000);        			  
     		  }
     	  }
		 }
    }

})();