(function () {
    'use strict';

    angular
		.module('app')
		.controller('settingsController', settingsController);

    settingsController.$inject = ['$location', '$scope', '$rootScope','$http', '$window', '$cookies', '$timeout'];
    function settingsController($location, $scope, $rootScope, $http, $window, $cookies, $timeout) {
        var sc = this;
        var user = undefined;
        $scope.message="";

        var init = function (){ 	
			$scope.adm = [];
			$scope.isAdmin=false;
        	console.log("trazim admira, path: " + 'http://localhost:8096/admin/' + $cookies.get('user'));
        	$http({
  			  method: 'GET',
  			  url: 'http://localhost:8096/admin/' + $cookies.get('user')
  				 
  			}).then(function successCallback(response) {
  				user = response.data;
  				$scope.adm = response.data;
  				$scope.isAdmin=true;
  				 if(user!=undefined && user.hasOwnProperty('tip')){

  				}
  				
  			  }, function errorCallback(response) {
  				  console.log("Greska kod GET user");
  			  });

        };
        init();
        sc.saveAdmin = function(){}
        if(user===undefined || user===null){
      		console.log("user undefined trazim usra, path: " + 'http://localhost:8096/api/user/' + $cookies.get('user'));
;
      		$http({
      			  method: 'GET',
      			  url: 'http://localhost:8096/api/user/' + $cookies.get('user')
      				 
      			}).then(function successCallback(response) {
      				user = response.data;
                    $scope.user = user;
                    $scope.isAdmin=false;
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

        	if(pass!=user.password && pass!=undefined && pass!="" )
        		user.password = pass;

        	if(fname!=user.ime && fname!=undefined && fname!="")
        		user.ime = fname;
        	
        	if(lname!=user.prezime && lname!=undefined && lname!="")
        		user.prezime = lname;
        	
        	if(email!=user.email && email!=undefined && email!="")
        	    if(validateEmail(email)){
        	            user.email = email;
        	        }else{
        	           $scope.message="Enter valid email address." ;
        	           return;
        	        }

        	
        	if(city!=user.grad && city!=undefined)
        		user.grad = city;
        	
        	if(phone!=user.brojTelefona && phone!=undefined)
        		user.brojTelefona = phone;
        	
        	
        	console.log("userName " +  user.userName +
        			" password "+ user.password +
        			" ime "+ user.ime +
        			" prezime "+ user.prezime +
        			" email "+ user.email +
        			" grad "+ user.grad +
        			" brojTelefona "+ user.brojTelefona);
        			
        	if(user.hasOwnProperty('tip')){
        	 $http({
                  method: 'POST',
                  url: 'http://localhost:8096/api/settings/admin',
                  data: user
                }).then(function successCallback(response) {
                    if(response.data==""){
                        $scope.message = "Changes successfully saved.";
                        console.log("userName " +  user.userName +
                                " password "+ user.password +
                                " ime "+ user.ime +
                                " prezime "+ user.prezime +
                                " email "+ user.email +
                                " grad "+ user.grad +
                                " brojTelefona "+ user.brojTelefona);
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
                     console.log("nije prihvatio izmene admira")

                    });
        	}

        	else{
        		 $http({
                     method: 'POST',
                     url: 'http://localhost:8096/api/settings/reg',
                     data: user
                   }).then(function successCallback(response) {
                   	if(response.data==""){
                   		$scope.message ="Changes successfully saved.";
                   		console.log("userName " +  user.userName +
                       			" password "+ user.password +
                       			" ime "+ user.ime +
                       			" prezime "+ user.prezime +
                       			" email "+ user.email +
                       			" grad "+ user.grad +
                       			" brojTelefona "+ user.brojTelefona);

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