(function () {
    'use strict';

    angular
		.module('app')
		.controller('adminModeController', adminModeController);

    adminModeController.$inject = ['$location', '$scope', '$rootScope', '$cookies', '$window', '$http', '$timeout', '$sce'];
    function adminModeController($location, $scope, $rootScope, $cookies, $window, $http, $timeout, $sce) {
    	var amc = this;
    	
        var init = function (){
        	var regUser={};
        	regUser = $cookies.get('user');
        	$http({
                method: 'GET',
                url: 'http://localhost:8096/admin/'+regUser,
              }).then(function successCallback(response) {
              		if(response.data.tip=="FAN" || response.data.tip=="POZBI"){
              			$location.path('/home');
              			return;
              		}
              });
//ADD theatre/cinema
        	$scope.tcshowDone=false;
            $scope.tcshowSthWentWrong=false;
            $scope.tcblankField=false;
            $scope.tcemptyField="";
        	$scope.type_options = ["theatre", "cinema"];
        	$scope.tc={"naziv":"",
        				"opis":"",
        				"adresa":"",
        				"tipUstanove":"theatre",
          				"mapaDone": "",
          				"mapa": ""};
//treshold
            $scope.tshowDone=false;
            $scope.tshowSthWentWrong=false;
            $scope.tblankField=false;
            $scope.temptyField="";
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
            
//admin	    	
            $scope.admPozBio = [];
            $scope.ashowDone=false;
            $scope.ashowSthWentWrong=false;
            $scope.ablankField=false;
            $scope.aemptyField="";
            $scope.admType_options = ["fanZone", "for Theatre/Cinema"];
            $scope.adm={"resp": [],
            			"username": "",
            			"email":"",
            			"pass":"default",
            			"tipAdmina":"fanZone"};
            
            
        	$http({
                method: 'GET',
                url: 'http://localhost:8096/pb'
              }).then(function successCallback(response) {
            	  $scope.pb = response.data;
                }
              );

        };
        init();
        
        $scope.trustSrc = function(src) {
            return $sce.trustAsResourceUrl(src);
        }
       
        amc.editTreshold = function(){
        	if($scope.bs.goldTreshold=="" || $scope.bs.silverTreshold=="" || $scope.bs.bronzeTreshold=="" ||
        			!parseInt($scope.bs.goldTreshold) || !parseInt($scope.bs.silverTreshold) || !parseInt($scope.bs.bronzeTreshold)){
        		aac.tblankField("Some Treshold");
        		return;
        	}
        	if($scope.bs.bronzePopust=="" || $scope.bs.silverPopust=="" || $scope.bs.goldPopust=="" ||
        		!parseInt($scope.bs.goldPopust) || !parseInt($scope.bs.silverPopust) || !parseInt($scope.bs.bronzePopust)){
        		aac.tblankField("Some Discount");
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
            	  amc.tshowDone();  
              });
        	
        	
        }
        amc.addTC = function(){
        	if($scope.tc.naziv==""){
        		amc.tcblankField("Name");
        		return;
        	}
        	if($scope.tc.opis==""){
        		amc.tcblankField("Description");
        		return;
        	}
        	if($scope.tc.adresa==""){
        		amc.tcblankField("Adress");
        		return;
        	}
        	if($scope.tc.tipUstanove==""){
        		amc.tcblankField("Type");
        		return;
        	}
        	var data = {"id": 0,
        				"naziv":$scope.tc.naziv,
        				"opis":$scope.tc.opis,
        				"adresa":$scope.tc.adresa,
        				"urlMape":$scope.tc.mapa,
        				"tip":"BIOSKOP",
    				};
        	if($scope.tc.tipUstanove=="theatre")
        		data.tip="POZORISTE";
        	$http({
                method: 'POST',
                url: 'http://localhost:8096/pbDTO',
                data: data
              }).then(function successCallback(response) {
            	  if(response.data!=""){
            		  amc.tcshowDone();
                	  $scope.tc={"naziv":"",
              				"opis":"",
              				"adresa":"",
              				"tipUstanove":"theatre",
              				"mapaDone": "",
              				"mapa": ""};
                      $scope.tc.mapaDone="";
                      $scope.pb.push(response.data);
            	  }
            	  else{
            		amc.tcshowSthWentWrong();
                  	
            	  }
              });
        }
        
        amc.addAdmin = function(){
        	if($scope.admPozBio.length==0){
        		amc.ablankField("Responsible for");
        		return;
        	}
        	if($scope.adm.username==""){
        		amc.ablankField("Username");
        		return;
        	}
        	if($scope.adm.email==""){
        		amc.ablankField("Email");
        		return;
        	}
        	var data = {"pozBio": $scope.admPozBio,
	        			"username": $scope.adm.username,
	        			"email":$scope.adm.email,
	        			"pass":"default"
    				};
        	if($scope.adm.tipAdmina=='fanZone')
        		data.tipAdmina='FAN';
        	else
        		data.tipAdmina='POZBI'
        	$http({
                method: 'POST',
                url: 'http://localhost:8096/admins',
                data: data
              }).then(function successCallback(response) {
            	  if(response.data!=""){
            		  amc.ashowDone(); 
	            	  $scope.adm={
	              			"username": "",
	              			"email":"",
	              			"pass":"default",
	              			"tipAdmina":"fanZone"};
            	  }
            	  else
            		  amc.ashowSthWentWrong();
              });
        }
        
        amc.addToList = function(id){
        	var index = $scope.admPozBio.indexOf(id);
        	if(index!=-1){
        		$scope.admPozBio.splice(index, 1);
        	}else{
            	$scope.admPozBio.push(id);        		
        	}
        }
        amc.addSufix = function(){
        	$scope.tc.mapaDone = $scope.tc.mapa;
        }
        
        amc.tcshowDone = function() {
		      $scope.tcshowDone = true;
		      $timeout(function() {
		    	  $scope.tcshowDone = false;
		      }, 3000);    
		 }
		 amc.tcblankField = function(field){
			 $scope.tcblankField = true;
			 $scope.tcemptyField = field;
		      $timeout(function() {
		    	  $scope.tcblankField = false;
					 $scope.tcemptyField = "";
		      }, 3000);    
		  }
		 amc.tcshowSthWentWrong = function() {
		      $scope.tcshowSthWentWrong = true;
		      $timeout(function() {
		    	  $scope.tcshowSthWentWrong = false;
		      }, 3000);    
		 }		
        amc.tshowDone = function() {
		      $scope.tshowDone = true;
		      $timeout(function() {
		    	  $scope.tshowDone = false;
		      }, 3000);    
		 }
		 amc.tblankField = function(field){
			 $scope.tblankField = true;
			 $scope.temptyField = field;
		      $timeout(function() {
		    	  $scope.tblankField = false;
					 $scope.temptyField = "";
		      }, 3000);    
		  }
		 amc.tshowSthWentWrong = function() {
		      $scope.tshowSthWentWrong = true;
		      $timeout(function() {
		    	  $scope.tshowSthWentWrong = false;
		      }, 3000);    
		 }		
		 amc.ashowDone = function() {
		      $scope.ashowDone = true;
		      $timeout(function() {
		    	  $scope.ashowDone = false;
		      }, 3000);    
		 }
		 amc.ablankField = function(field){
			 $scope.ablankField = true;
			 $scope.aemptyField = field;
		      $timeout(function() {
		    	  $scope.ablankField = false;
					 $scope.aemptyField = "";
		      }, 3000);    
		  }
		 amc.ashowSthWentWrong = function() {
		      $scope.ashowSthWentWrong = true;
		      $timeout(function() {
		    	  $scope.ashowSthWentWrong = false;
		      }, 3000);    
		 }
    }

})();