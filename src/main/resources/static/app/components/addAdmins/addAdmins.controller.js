(function () {
    'use strict';

    angular
		.module('app')
		.controller('addAdminsController', addAdminsController);

    addAdminsController.$inject = ['$location', '$scope', '$rootScope', '$cookies', '$window', '$http'];
    function addAdminsController($location, $scope, $rootScope, $cookies, $window, $http) {
    	var aac = this;
    	
        var init = function (){
        	$scope.type_options = ['FanZone', 'Regular'];        	
        	$http({
                method: 'GET',
                url: 'http://localhost:8096/rekviziti/zvanicni'
              }).then(function successCallback(response) {
                }
              );

        };
        init();
       
        aac.add = function(){
        	$scope.adding=false;
        }
        aac.showDone = function(id) {
        	  for(var i=0; i<$scope.items.length; i++){
        		  if($scope.items[i].id==id){
        		      $scope.items[i].showDone = true;
        		      $timeout(function() {
        		    	  $scope.items[i].showDone = false;
        		      }, 3000);        			  
        		  }
        	  }
		 };
    }

})();