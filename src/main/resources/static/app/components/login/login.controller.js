(function () {
    'use strict';

    angular
		.module('app')
		.controller('loginController', loginController);

    loginController.$inject = ['$location', '$scope', '$rootScope','$http', '$window', '$cookies'];
    function loginController($location, $scope, $rootScope, $http, $window, $cookies) {
        var lc = this;
        var user;
        $scope.message="";
        var init = function (){
            if($cookies.get('user'))
                $location.path("home")
        };
        init();
        
        $scope.indexFunc = function(){
        	$location.path("home");
        }
        
        $scope.prijavaFunc = function(username, pass){
        	if(username===undefined || pass===undefined || username==="" || pass===""){
        		$scope.message="Enter both username and password.";
        		return;
        	}
        	var data = {
        	    "userName" : username,
        	    "password" :pass
        	}
            $http({
              method: 'POST',
              url: 'http://localhost:8096/api/login/',
              data: data
            }).then(function successCallback(response) {
                var user = response.data;
               
                if(user===null || user===undefined || user===""){
                $scope.message="Login error. Please check your credentials.";
                	return;
                }
                
               
                else{
     				
     				if(user.status=="NERESEN" && user.hasOwnProperty('tip')){

                		
                		$cookies.remove('user');
                		$cookies.remove('id');
                		$window.location.href = 'http://localhost:8096/#!/adminLogin';
                		return;
                		
                	}else if(user.status=="NERESEN" && !user.hasOwnProperty('tip')){
                		$scope.message="Before login you must confirm your registration via email.";
                		
                		$cookies.remove('user');
                        $cookies.remove('id');
                		return;
                	}
                		
     	
	     			$cookies.put("user", user.userName, {
	     			   path: 'core'
	     			});
	     			$cookies.put("id", user.id, {
                    	     			   path: 'core'
                    	     			});
	     			console.log("Uspesno logovanje: " + $cookies.get('user') + ", id: " +  $cookies.get('id'))
	            	$window.location.href = 'http://localhost:8096/';
                }
                
                }, function errorCallback(response) {
                 $scope.message="Error.";

                });

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