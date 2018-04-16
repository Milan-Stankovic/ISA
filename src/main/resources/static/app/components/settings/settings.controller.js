(function () {
    'use strict';

    angular
		.module('app')
		.controller('settingsController', settingsController);

    settingsController.$inject = ['$location', '$scope', '$rootScope','$http', '$window', '$cookies'];
    function settingsController($location, $scope, $rootScope, $http, $window, $cookies) {
        var sc = this;
        var user = undefined;


        var init = function (){ 	
        	console.log("trazim admira, path: " + 'http://localhost:8096/admin/' + $cookies.get('user'));
        	$http({
  			  method: 'GET',
  			  url: 'http://localhost:8096/admin/' + $cookies.get('user')
  				 
  			}).then(function successCallback(response) {
  				user = response.data;
  				 if(user!=undefined && user.hasOwnProperty('tip')){

  				}
  				
  			  }, function errorCallback(response) {
  				  console.log("Greska kod GET user");
  			  });

        };
        init();
        
        if(user===undefined || user===null){
      		console.log("user undefined trazim usra, path: " + 'http://localhost:8096/api/user/' + $cookies.get('user'));
;
      		$http({
      			  method: 'GET',
      			  url: 'http://localhost:8096/api/user/' + $cookies.get('user')
      				 
      			}).then(function successCallback(response) {
      				user = response.data;

      			  }, function errorCallback(response) {
      				  console.log("Greska kod GET user");
      			  });
      	}
      
        $scope.indexFunc = function(){
        	$location.path("home");
        }

        
        $scope.saveFunc = function(fname, lname, email, city, phone, pass, pass2){
        	
        	if(!(pass===pass2) && pass!='' && pass2!=''){
        		alert("Passwords don't match.")
        		return;
        	}
        	if(document.getElementById('phone').value != ''){
        		if(isNaN(phone)){
            		alert("Enter valid phone number.")
            		return;
            	}
        	}
     
        	if(pass!=user.password && pass!=undefined)
        		user.password = pass;

        	if(fname!=user.ime && fname!=undefined)
        		user.ime = fname;
        	
        	if(lname!=user.prezime && lname!=undefined)
        		user.prezime = lname;
        	
        	if(email!=user.email && email!=undefined)
        		user.email = email;
        	
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
                        alert("Changes successfully saved.")
                        console.log("userName " +  user.userName +
                                " password "+ user.password +
                                " ime "+ user.ime +
                                " prezime "+ user.prezime +
                                " email "+ user.email +
                                " grad "+ user.grad +
                                " brojTelefona "+ user.brojTelefona);
                    }

                    else
                        alert(response.data)

                    }, function errorCallback(response) {
                     alert("greska u saveFunc")

                    });
        	}

        	else{
        		 $http({
                     method: 'POST',
                     url: 'http://localhost:8096/api/settings/reg',
                     data: user
                   }).then(function successCallback(response) {
                   	if(response.data==""){
                   		alert("Changes successfully saved.")
                   		console.log("userName " +  user.userName +
                       			" password "+ user.password +
                       			" ime "+ user.ime +
                       			" prezime "+ user.prezime +
                       			" email "+ user.email +
                       			" grad "+ user.grad +
                       			" brojTelefona "+ user.brojTelefona);
                   	}
                   		
                   	else
                   		alert(response.data)

                       }, function errorCallback(response) {
                        alert("greska u saveFunc")

                       });
        	}
        }

    }


})();