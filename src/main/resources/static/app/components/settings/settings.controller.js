(function () {
    'use strict';

    angular
		.module('app')
		.controller('settingsController', settingsController);

    settingsController.$inject = ['$location', '$scope', '$rootScope','$http', '$window', '$cookies', '$timeout'];
    function settingsController($location, $scope, $rootScope, $http, $window, $cookies, $timeout) {
        var sc = this;
        $scope.user = undefined;
        $scope.message="";

        var init = function (){ 	
			$scope.adm = [];
			$scope.isAdmin=false;
        	console.log("trazim admira, path: " + 'http://localhost:8096/admin/' + $cookies.get('user'));
        	$http({
  			  method: 'GET',
  			  url: 'http://localhost:8096/admin/' + $cookies.get('user')
  				 
  			}).then(function successCallback(response) {
  				$scope.user = response.data;
  				$scope.adm = response.data;

  				 if($scope.user!=undefined && $scope.user.hasOwnProperty('tip')){
                    $scope.isAdmin=true;
  				}
  				
  			  }, function errorCallback(response) {
  				  console.log("Greska kod GET user");
  			  });

        };
        init();

        if($scope.user==undefined){
      		console.log("user undefined trazim usra, path: " + 'http://localhost:8096/api/user/' + $cookies.get('user'));
;
      		$http({
      			  method: 'GET',
      			  url: 'http://localhost:8096/api/user/' + $cookies.get('user')
      				 
      			}).then(function successCallback(response) {
      				$scope.user = response.data;
                    $scope.isAdmin=false;
                    $scope.adm = undefined;
      			  }, function errorCallback(response) {
      				  console.log("Greska kod GET user");
      			  });
      	}

        function validateEmail(email) {
            var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
            return re.test(String(email).toLowerCase());
        }

        $scope.indexFunc = function(){
        	$location.path("home");
        }

        
        $scope.saveFunc = function(fname, lname, email, city, phone, pass, pass2){

        	if(!(pass===pass2) && pass!='' && pass2!='' && pass!=undefined && pass2!=undefined){
        		$scope.message = "Passwords don't match."
        		return;
        	}
        	if(document.getElementById('phone').value != ''){
        		if(isNaN(phone)){
            		$scope.message = "Enter valid phone number."
            		return;
            	}
        	}

        	if(pass!=$scope.user.password && pass!=undefined && pass!="" )
        		$scope.user.password = pass;

        	if(fname!=$scope.user.ime && fname!=undefined && fname!="")
        		$scope.user.ime = fname;
        	
        	if(lname!=$scope.user.prezime && lname!=undefined && lname!="")
        		$scope.user.prezime = lname;
        	
        	if(email!=$scope.user.email && email!=undefined && email!="")
        	    if(validateEmail(email)){
        	            $scope.user.email = email;
        	        }else{
        	           $scope.message="Enter valid email address." ;
        	           return;
        	        }

        	
        	if(city!=$scope.user.grad && city!=undefined)
        		$scope.user.grad = city;
        	
        	if(phone!=$scope.user.brojTelefona && phone!=undefined)
        		$scope.user.brojTelefona = phone;
        	
        	
        	console.log("userName " +  $scope.user.userName +
        			" password "+ $scope.user.password +
        			" ime "+ $scope.user.ime +
        			" prezime "+ $scope.user.prezime +
        			" email "+ $scope.user.email +
        			" grad "+ $scope.user.grad +
        			" brojTelefona "+ $scope.user.brojTelefona);
        			
        	if($scope.user.hasOwnProperty('tip')){
        	 $http({
                  method: 'POST',
                  url: 'http://localhost:8096/api/settings/admin',
                  data: $scope.user
                }).then(function successCallback(response) {
                    if(response.data==""){
                        $scope.message = "Changes successfully saved.";
                        console.log("userName " +  $scope.user.userName +
                                " password "+ $scope.user.password +
                                " ime "+ $scope.user.ime +
                                " prezime "+ $scope.user.prezime +
                                " email "+ $scope.user.email +
                                " grad "+ $scope.user.grad +
                                " brojTelefona "+ $scope.user.brojTelefona);
                                $timeout(function() {
                                     $window.location.reload();
                                                  }, 3000)
                    }

                    else{
                     $scope.message = response.data;
                    $timeout(function() {
                           $window.location.reload();
                                      }, 3000)
                    }


                    }, function errorCallback(response) {
                     $scope.message="Error saving changes."

                    });
        	}

        	else{
        		 $http({
                     method: 'POST',
                     url: 'http://localhost:8096/api/settings/reg',
                     data: $scope.user
                   }).then(function successCallback(response) {
                   	if(response.data==""){
                   		$scope.message ="Changes successfully saved.";
                   		console.log("userName " +  $scope.user.userName +
                       			" password "+ $scope.user.password +
                       			" ime "+ $scope.user.ime +
                       			" prezime "+ $scope.user.prezime +
                       			" email "+ $scope.user.email +
                       			" grad "+ $scope.user.grad +
                       			" brojTelefona "+ $scope.user.brojTelefona);

                       			 $timeout(function() {
                                      $window.location.reload();
                                      }, 3000)
                   	}
                   		
                   	else{
                   	$scope.message = response.data;
                   	 $timeout(function() {
                           $window.location.reload();
                          }, 3000)
                   	}

                       }, function errorCallback(response) {
                        console.log("greska kod reg settings")

                       });
        	}
        }

        sc.saveAdmin = function(){
        	var data = {
        			"userName": $scope.adm.ime,
        			"prezime": $scope.adm.prezime,
        			"userName": $scope.adm.userName,
        			"password": $scope.adm.pass,
        			"grad": $scope.adm.grad,
        			"brojTelefona": $scope.adm.brojTelefona        			
        	};
        	if($scope.adm.pass!=$scope.admpass2){
        		alert("Password is not matching");
        		return;
        	}
        	if($scope.adm.pass=="" || $scope.adm.pass==undefined){
        		data.pass = "";
        	}
        	$http({
    			  method: 'PUT',
    			  url: 'http://localhost:8096/admininfo/' + $scope.adm.id,
    			  data: data    				 
    			}).then(function successCallback(response) {
    				$scope.adm = response.data;
    			});
        }
    }


})();