(function () {
    'use strict';

    angular
		.module('app')
		.controller('oglasiController', oglasiController);

    oglasiController.$inject = ['$location', '$scope', '$rootScope', '$cookies', '$window', '$http', '$timeout'];
    function oglasiController($location, $scope, $rootScope, $cookies, $window, $http, $timeout) {
    	var oc = this;
    	
        var init = function (){
        	$scope.ashowDone=false;
        	$scope.ashowSthWentWrong=false;
        	$scope.ablankField=false;
        	$scope.aemptyField="";  
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
        	$scope.it={
        		"slika":"",
        		"naziv":"",
        		"opis": "",
        		"cena": 0,
        		"datum": "",
        		"username": $cookies.get('user')
        	};
        	var regUser={};
        	regUser = $cookies.get('user');
        	$scope.items = [];
        	$http({
                method: 'GET',
                url: 'http://localhost:8096/api/user/'+regUser,
              }).then(function successCallback(response) {
              		if(response.data==""){
              			$location.path('/home');
              			return;
              		}
            	  $scope.regUserID = response.data.id;

              	  $http({
                      method: 'GET',
                      url: 'http://localhost:8096/rekviziti/polovni/'+$scope.regUserID,
                    }).then(function successCallback(response) {
                    		var all = response.data;
                    		$scope.items = response.data;
                    		for(var i=0; i<all.length; i++){
                    			$scope.items[i].showDone=false;
                    			$scope.items[i].showSthWentWrong=false;
                    			$scope.items[i].ponuda = 0;
                    			$scope.items[i].hasOffered = oc.showBTN($scope.items[i]);
	                  			$scope.items[i].kraj = oc.parseDate(all[i].kraj);
                    		}
                    });
              });
        	$scope.preview=[];
        };
        init();
        
        oc.parseDate = function(d){
        	var date = new Date(d);
			var day = date.getDate();
			day = day = (day < 10) ? ("0" + day) : day;
			var month = date.getMonth() + 1;
			month = (month < 10) ? ("0" + month) : month;
			return (day + "-" + month + "-" + date.getFullYear());
        }
        
      oc.showBTN = function(item){
    	  for(var i=0; i<item.licitacija.length; i++){
    		  if(item.licitacija[i].ponudio==$scope.regUser){
    			  item.suma = item.licitacija[i].suma;
    			  return true;
    		  }
    	  }
    	  return false;
      }
        oc.add = function(){
        	if($scope.it.naziv==""){
        		oc.ablankField("Title");
        		return;
        	}
        	if($scope.it.opis==""){
        		oc.ablankField("Description");
        		return;
        	}
        	if($scope.it.datum=="" ){
        		oc.ablankField("Date");
        		return;
        	}
        	var data = {
            		"slika":"",
            		"naziv":$scope.it.naziv,
            		"opis": $scope.it.opis,
            		"cena": "",
            		"datum": new Date($scope.it.datum),
            		"username": $cookies.get('user')
        		};
        	if($scope.it.slika==""){
        		$http({
            		method: 'POST',
            		url: 'http://localhost:8096/rekviziti/polovni',
            		data: data
	              }).then(function successCallback(response) {
	            	  oc.ashowDone();
	            	  $scope.it = {
	              		"slika":"",
	              		"naziv":"",
	              		"opis": "",
	              		"cena": 0,
	              		"datum": "",
	              		"username": $cookies.get('user')
	              	  };
	            	  $scope.preview.push(response.data);
	              });
        		return;
        	}
        	var file = $scope.it.slika;
        	var fileFormData = new FormData();
            fileFormData.append('file', file);
        	$http({
        		method: 'POST',
                url: 'http://localhost:8096/rekviziti/upload',
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined},
                data: fileFormData
            }).then(function successCallback(response) {
            	data.slika = response.data.slika;
            	$http({
            		method: 'POST',
            		url: 'http://localhost:8096/rekviziti/polovni',
            		data: data
	              }).then(function successCallback(response) {
	            	  oc.ashowDone();
	            	  $scope.it = {
	              		"slika":"",
	              		"naziv":"",
	              		"opis": "",
	              		"cena": 0,
	              		"datum": "",
	              		"username": $cookies.get('user')
	              	  };
	            	  $scope.preview.push(response.data);
	              });	              
            });
        }	 
        oc.makeOffer = function(item){
        	if(!parseFloat(item.cena)){
        		return;
        	}
        	var data = {"idRekvizita": item.id,
        				"username": $cookies.get('user'),
        				"cena": parseFloat(item.cena)
        		};
        	$http({
        		method: 'POST',
        		url: 'http://localhost:8096/rekviziti/polovni/ponuda',
        		data: data
              }).then(function successCallback(response) {
            	  if(response.data==""){
            		  oc.showDone(item.id, parseFloat(item.cena));
            		  item.cena="";
            	  }
            	  else{
            		  oc.showSthWentWrong(item.id);
            	  }
              });	 
        }
        oc.makeNewOffer = function(item){
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
            		  oc.showDone(item.id);
            		  item.suma=item.novaPonuda;
            		  item.novaPonuda="";
            	  }
            	  else{
            		  oc.showSthWentWrong(item.id);
            	  }
              });	 
        }
        oc.ashowSthWentWrong = function() {
		      $scope.ashowSthWentWrong = true;
		      $timeout(function() {
		    	  $scope.ashowSthWentWrong = false;
		      }, 3000);    
		 }
        oc.ashowDone = function() {
		      $scope.ashowDone = true;
		      $timeout(function() {
		    	  $scope.ashowDone = false;
		      }, 3000);    
		 }
        oc.ablankField = function(field){
			 $scope.ablankField = true;
			 $scope.aemptyField = field;
		      $timeout(function() {
		    	  $scope.ablankField = false;
					 $scope.aemptyField = "";
		      }, 3000);    
		 }
        oc.showDone = function(id, suma) {
      	  var regI; 
      	  for(var i=0; i<$scope.items.length; i++){
      		  if($scope.items[i].id==id){
      			  regI = i;
      		      $scope.items[regI].showDone = true;
      		      $scope.items[regI].hasOffered = true;
      		      $scope.items[regI].suma = suma;
      		      $timeout(function() {
      		    	  $scope.items[regI].showDone = false;
      		      }, 3000);        			  
      		  }
      	  }
		 };
		 oc.showSthWentWrong = function(id) {
     	  var regI; 
     	  for(var i=0; i<$scope.items.length; i++){
     		  if($scope.items[i].id==id){
     			  regI = i;
     		      $scope.items[regI].showSthWentWrong = true;
     		      $timeout(function() {
     		    	  $scope.items[regI].showSthWentWrong = false;
     		      }, 3000);        			  
     		  }
     	  }
		 };
    }

})();