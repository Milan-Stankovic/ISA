(function () {
    'use strict';

    angular
		.module('app')
		.controller('fanZoneController', fanZoneController);

    fanZoneController.$inject = ['$location', '$scope', '$rootScope', '$cookies', '$window', '$http', '$timeout'];
    function fanZoneController($location, $scope, $rootScope, $cookies, $window, $http, $timeout) {
    	var fzc = this;
    	
        var init = function (){

        	$http({
                method: 'GET',
                url: 'http://localhost:8096/api/user/'+$cookies.get('user'),
              }).then(function successCallback(response) {
            	  if(response.data!=""){
            		  if(response.data==""){
            			  $location.path('/home');
            			  return;
            		  }
            		  $scope.userID=response.data.id;
            	  }
            	  });
        	
        	$http({
                method: 'GET',
                url: 'http://localhost:8096/rekviziti/zvanicni'
              }).then(function successCallback(response) {
                   var items = response.data;
                   if (items!=""){
                	   $scope.items = items;
                	   for(var i=0; i<$scope.items.length; i++){
                		   $scope.items[i].showDone= false;
                		   $scope.items[i].showSthWentWrong = false;
                	   }
                   }else{
                	   $scope.items = [];
                   }
                }
              );

        };
        init();
       
        fzc.rezervisi = function(id){
      		$http({
                method: 'PUT',
                url: 'http://localhost:8096/rekviziti/'+id+'/rezervisi/'+$scope.userID
              }).then(function successCallback(response) {
	            	 var result = response.data;
	         		 if(result!=""){
	         			 fzc.showDone(id);
	         		 }
	         		 else
	         			 fzc.showSthWentWrong(id);	         			 
              });      	
        }

        fzc.showDone = function(id) {
        	  var regI; 
        	  for(var i=0; i<$scope.items.length; i++){
        		  if($scope.items[i].id==id){
        			  regI = i;
        		      $scope.items[regI].showDone = true;
        		      $timeout(function() {
        		    	  $scope.items[regI].showDone = false;
        		      }, 3000);        			  
        		  }
        	  }
		 };
		 fzc.showSthWentWrong = function(id) {
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