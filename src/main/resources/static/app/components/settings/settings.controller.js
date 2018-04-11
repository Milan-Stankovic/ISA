(function () {
    'use strict';

    angular
		.module('app')
		.controller('settingsController', settingsController);

    settingsController.$inject = ['$location', '$scope', '$rootScope','$http', '$window', '$cookies'];
    function settingsController($location, $scope, $rootScope, $http, $window, $cookies) {
        var sc = this;
        var user = undefined;
        $scope.friendsList = [];
        
        var init = function (){ 	
        	console.log("trazim admira, path: " + 'http://localhost:8096/api/admin/' + $cookies.get('user'));
        	$http({
  			  method: 'GET',
  			  url: 'http://localhost:8096/api/admin/' + $cookies.get('user')
  				 
  			}).then(function successCallback(response) {
  				user = response.data;
  				//alert(user.userName)
  				
  			  }, function errorCallback(response) {
  				  console.log("Greska kod GET user");
  			  });

        };
        init();
        
        if(user===undefined || user===null){
      		console.log("user undefined trazim usra, path: " + 'http://localhost:8096/api/user/' + $cookies.get('user'));
      		$http({
      			  method: 'GET',
      			  url: 'http://localhost:8096/api/user/' + $cookies.get('user')
      				 
      			}).then(function successCallback(response) {
      				user = response.data;
      				/*console.log("userName " +  user.userName +
      	        			" password "+ user.password +
      	        			" ime "+ user.ime +
      	        			" prezime "+ user.prezime +
      	        			" email "+ user.email +
      	        			" grad "+ user.grad +
      	        			" brojTelefona "+ user.brojTelefona + 
      	        			"prijateji" + user.prijatelji);*/
      				
      				if(user!=undefined){
      		        	console.log("frnessssss")
      		        	var tempLista;
      		        	$http({
      		    			  method: 'GET',
      		    			  url: 'http://localhost:8096/api/user/friends/' + $cookies.get('user')
      		    				 
      		    			}).then(function successCallback(response) {
      		    				$scope.friendsList = response.data;
      		    				console.log($scope.friendsList.length);
      		    				//alert(user.userName)
      		    			  }, function errorCallback(response) {
      		    				  console.log("Greska kod GET user frineds");
      		    			  });
      		        	
      		        
      		        }
      			  }, function errorCallback(response) {
      				  console.log("Greska kod GET user");
      			  });
      	}
      
        $scope.indexFunc = function(){
        	$location.path("home");
        }
        
        $scope.deleteFriend = function(email){
        	console.log("brisem: " + email)
        	$http({
    			  method: 'DELETE',
    			  url: 'http://localhost:8096/api/user/friends/' + $cookies.get('user'),
    			  data: email
    			}).then(function successCallback(response) {
    				$scope.friendsList = response.data;
    				console.log($scope.friendsList.length);
    				//alert(user.userName)
    			  }, function errorCallback(response) {
    				  console.log("Greska kod GET user frineds");
    			  });
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
        			
        	if(user.hasOwnProperty('tip'))
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
        /*
        window.fbAsyncInit = function() {
            FB.init({
              appId      : '330212657486853',
              cookie     : true,
              xfbml      : true,
              version    : 'v2.12'
            });


            FB.AppEvents.logPageView();

           function fbLogoutUser() {
                FB.getLoginStatus(function(response) {
                    if (response && response.status === 'connected') {
                        FB.logout(function(response) {
                            document.location.reload();
                            $scope.loggedIn = false;
                        });
                    }
                });
            }


            function bakeCookie(){
            	var cookie = $cookies.get('username');

            	  if ( cookie ) {
            		  $cookies.remove("username");
            		  $cookies.put('username', user.userName);
            	  } else {
            		  $cookies.put('username', user.userName);
            	  }
            }

            function GetFBLoginStatus() {
                FB.getLoginStatus( function(response) {
                    console.log(response);
                    if (response.status === 'connected') {
                        var accessToken = response.authResponse.accessToken;
                        console.log("Connected to Facebook, token: " + accessToken);
                         $scope.loggedIn = true;

                //SUCCESS
                        FB.api('/me', { locale: 'rs_RS', fields: 'id,first_name,last_name, email,birthday, hometown,education,gender,website,work' },
                          function(response) {

                            console.log(response.id);
                            console.log(pass);
                            console.log(response.first_name);
                            console.log(response.last_name);

                            console.log("salje post req na fblogin")
                            var data={
                            	"userName": response.id,
                            	"password": pass,
                            	"ime": response.first_name,
                            	"prezime": response.last_name
                            }
                            $http({
                              method: 'POST',
                              url: 'http://localhost:8096/api/fblogin/',
                              data: data
                            }).then(function successCallback(response) {
                                 user = response.data;

                                 if(user){
                                     console.log("dobio: " + user.userName)
                                     $scope.loggedIn=true;
                                 }
                                 else{
                                    $scope.loggedIn = false;
                                    alert("Greška u prijavi")
                                 }


                              }
                            );
                        })
                    } else if (response.status === 'not_authorized') {
                        //login function
                        console.log('login first');
                        $scope.loggedIn = false;
                    } else {
                        //login function
                        console.log('login first');
                        $scope.loggedIn = false;
                    }
                }, true);
            }
           //fbLogoutUser();
            GetFBLoginStatus();

          };

          (function(d, s, id){
             var js, fjs = d.getElementsByTagName(s)[0];
             if (d.getElementById(id)) {return;}
             js = d.createElement(s); js.id = id;
             js.src = "https://connect.facebook.net/en_US/sdk.js";
             fjs.parentNode.insertBefore(js, fjs);
           }(document, 'script', 'facebook-jssdk'));


            (function(d, s, id) {
             var js, fjs = d.getElementsByTagName(s)[0];
             if (d.getElementById(id)) return;
             js = d.createElement(s); js.id = id;
             js.src = 'https://connect.facebook.net/en_US/sdk.js#xfbml=1&version=v2.12&appId=330212657486853&autoLogAppEvents=1';
             fjs.parentNode.insertBefore(js, fjs);
           }(document, 'script', 'facebook-jssdk'));
*/
    }


})();