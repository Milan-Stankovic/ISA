(function (angular) {
    var app = angular.module("app", []);
    
    app.controller("headerCtrl", ['$scope', '$http', '$window',
        function loginCtrl($scope, $http, $localStorage, $window){
            
    	$scope.sviBioskopi = null;
    	
    	
    	var getBioskopi = function(){
    		$http({
    			  method: 'GET',
    			  url: 'http://localhost/ISA/getData/Bioskopi'
    				 
    			}).then(function successCallback(response) {
    				$scope.sviBioskopi = response.data;
    				alert($scope.sviBioskopi.size());
    			  }, function errorCallback(response) {
    				  console.log("Greska kod GET bioskopi");
    			  });
    		
    	}
    	getBioskopi();
    	
            $scope.showBioskopi = function(){
            	$http({
				  method: 'GET',
				  url: 'http://localhost/ISA/service/' 
			}).then(function successCallback(response) {
				window.location.replace('/ISA/bioskopi.html');
			  }, function errorCallback(response) {
				 alert("feeeeeeeeeeeeejl")
			  });
		
            }
            
            $scope.showPozorista = function(){
			
            }
            
            
        }]);
})(angular);