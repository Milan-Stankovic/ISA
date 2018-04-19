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
        	$scope.showNotifications=false;
        	$scope.showOffers=false;
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
              		if(response.data==""){
              			$location.path('/home');
              			return;
              		}
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
                    			$scope.mySales[i].nnaziv = $scope.mySales[i].naziv;
                    			$scope.mySales[i].nopis = $scope.mySales[i].opis;
                    			$scope.mySales[i].ashowDone = false;
                    			$scope.mySales[i].ashowSthWentWrong= false;
                    			$scope.mySales[i].ablankField= false;
                    			$scope.mySales[i].aemptyField= "";
                    			$scope.mySales[i].showAccept= mopc.checkIfDone(all[i].licitacija);       
                    			$scope.mySales[i].showStatus = mopc.setStatus(all[i].status);
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
	                  			$scope.myOffers[i].active = false;	
	                  			if($scope.myOffers[i].active=='UTOKU')
		                  			$scope.myOffers[i].active = true;
	                  			$scope.myOffers[i].prihvaceno = mopc.checkIfAccepted(all[i]);	
	                  			$scope.myOffers[i].odbijeno = mopc.checkIfDenied(all[i]);	
	                  		}
	                  });
	              	$http({
	                    method: 'GET',
	                    url: 'http://localhost:8096/obavestenja/'+$scope.regUser,
	                  }).then(function successCallback(response) {
	                  		var all = response.data;
	                  		$scope.myNotifications = response.data;
	                  		for(var i=0; i<all.length; i++){
	                  			
	                  		}
	                  });
              });
        };
        init();
        
        mopc.setStatus = function(status){
        	if(status=='UTOKU')
        		return "active";
        	if(status=='POSTAVLJENO')
        		return "waiting for approval";
        	if(status=='ZAVRSENA')
        		return "done";
        	return "denied";
        	
        }
        mopc.checkIfDone = function(licitacija){
        	for(var i=0; i<licitacija.length; i++){
        		if(licitacija[i].prihvaceno)
        			return false;
        	}
        	return true;
        }
        mopc.findSuma  = function(item){
    	  for(var i=0; i<item.licitacija.length; i++){
    		  if(item.licitacija[i].ponudio==$scope.regUser){
    			  return item.licitacija[i].suma;
    		  }
    	  }
    	  return '?';
      }
        mopc.checkIfAccepted = function(item){
        	for(var i=0; i<item.licitacija.length; i++){
        		if(item.licitacija[i].prihvaceno && item.licitacija[i].ponudio==$scope.regUser)
        			return true;
        	}
        	return false;
        }
        mopc.checkIfDenied = function(item){
        	if(item.stauts=='ZAVRSENO'){
	        	for(var i=0; i<item.licitacija.length; i++){
	        		if(!item.licitacija[i].prihvaceno && item.licitacija[i].ponudio==$scope.regUser)
	        			return true;
	        	}
        	}
        	return false;
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
        	$http({
        		method: 'PUT',
        		url: 'http://localhost:8096/rekviziti/gotovo/'+item.id+'/'+offer.id
              }).then(function successCallback(response) {
            	  if(response.data!=""){
            		  item.showAccept = false;
            		  for(var i=0; i<item.licitacija.length; i++){
            			  if(item.licitacija[i].id==id)
            				  item.licitacija[i].prihvaceno=true;
            		  }
            	  }
              });	 
        }
        mopc.deleteNotif = function(notif){
        	$http({
        		method: 'DELETE',
        		url: 'http://localhost:8096/obavestenja/'+notif.id
              }).then(function successCallback(response) {
            		 var index = $scope.myNotifications.indexOf(notif);
            		 $scope.myNotifications.splice(index, 1);            		 
              });	
        }
        mopc.editItem = function(item){
        	item.showEdit=true;
        }

        mopc.goBack = function(item){
        	item.showEdit=false;        	
        }
        mopc.checkOffers = function(item){
        	if(item.showOffers){
        		item.showOffers = false;
        		return;
        	}
        	item.showOffers = true;        	
        }
        mopc.saveChanges = function(item){
        	if(item.nnaziv==""){
        		oc.ablankField("Title", item);
        		return;
        	}
        	if(item.nopis==""){
        		oc.ablankField("Description", item);
        		return;
        	}
        	if(item.krajDate=="" ){
        		oc.ablankField("Date", item);
        		return;
        	}
        	var data = {
            		"slika":"",
            		"naziv":item.nnaziv,
            		"opis": item.nopis,
            		"cena": "",
            		"datum": new Date(item.krajDate),
            		"username": $cookies.get('user')
        		};
        	if(item.nslika==""){
        		$http({
            		method: 'PUT',
            		url: 'http://localhost:8096/rekviziti/polovni/'+item.id,
            		data: data
	              }).then(function successCallback(response) {
	            	  oc.ashowDone(item);
	            	  item.slika = response.data.slika;
	            	  item.naziv = item.nnaziv;
	            	  item.opis = item.nopis;
	            	  item.kraj = mopc.parseDate(item.krajDate);
	              });
        		return;
        	}
        	var file = item.nslika;
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
            		method: 'PUT',
            		url: 'http://localhost:8096/rekviziti/polovni/'+item.id,
            		data: data
	              }).then(function successCallback(response) {
	            	  mopc.ashowDone(item);
	            	  item.slika = response.data.slika;
	            	  item.naziv = item.nnaziv;
	            	  item.opis = item.nopis;
	            	  item.kraj = mopc.parseDate(item.krajDate);
	              });	              
            });
        }
        mopc.toggleSales = function(){
        	$scope.showSales=true;
        	$scope.showOffers=false;
        	$scope.showNotifications=false;
        }
        mopc.toggleOffers = function(){
        	$scope.showSales=false;
        	$scope.showOffers=true;
        	$scope.showNotifications=false;
        }
        mopc.toggleNotif = function(){
        	$scope.showSales=false;
        	$scope.showOffers=false;
        	$scope.showNotifications=true;
        }
        mopc.ashowSthWentWrong = function(item) {
		      item.ashowSthWentWrong = true;
		      $timeout(function() {
		    	  item.ashowSthWentWrong = false;
		      }, 3000);    
		 }
        mopc.ashowDone = function(item) {
	      item.ashowDone = true;
	      $timeout(function() {
	    	  item.ashowDone = false;
	    	item.ashowEdit=false;
	      }, 2000);        
      } 
    	    
        
        mopc.ablankField = function(field, item){
		 item.ablankField = true;
		 item.aemptyField = field;
	      $timeout(function() {
	    	  item.ablankField = false;
				 item.aemptyField = "";
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