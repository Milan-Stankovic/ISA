(function () {
    'use strict';

    angular
		.module('app')
		.controller('sysAdminController', sysAdminController);

    sysAdminController.$inject = ['$location', '$scope', '$rootScope', '$cookies', '$window', '$http'];
    function sysAdminController($location, $scope, $rootScope, $cookies, $window, $http) {
    	var sac = this;
    	
        var init = function (){
        	$scope.adding=true;
        	$scope.type_options = ["theatre", "cinema"];
        	$http({
                method: 'GET',
                url: 'http://localhost:8096/rekviziti/zvanicni'
              }).then(function successCallback(response) {
                }
              );

        };
        init();
       
        sac.add = function(){
        	$scope.adding=false;
        }
        sac.showDone = function(id) {
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