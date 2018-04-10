(function () {
    'use strict';

    angular
		.module('app')
		.controller('loginController', loginController);

    loginController.$inject = ['$location', '$scope', '$rootScope','$http', '$window', '$cookies'];
    function loginController($location, $scope, $rootScope, $http, $window, $cookies) {
        var lc = this;
        var user;
        var init = function (){
        	
        };
        init();
        
        $scope.indexFunc = function(){
        	$location.path("home");
        }
        
        $scope.prijavaFunc = function(username, pass){
        	var data = {
        			"userName": username,
        			"password": pass
        	}
            $http({
              method: 'POST',
              url: 'http://localhost:8096/api/login/',
              data: data
            }).then(function successCallback(response) {
                var user = response.data;
                
                //var rrr = JSON.parse($cookies.get('userName'));
               
     			if(user!=null && user!=undefined){
     				if(user.status=="NERESEN"){
                		alert("Lozinka mora biti promenjena. Neuspesno logovanje.")
                		
                		$cookies.remove('user');
              
                		return;
                	}
     	
	     			$cookies.put("user", user.userName, {
	     			   path: 'core'
	     			});
	     			alert("Uspesno logovanje: " + $cookies.get('user'))
	            	$window.location.href = 'http://localhost:8096/';
     			}
                
                else{
   
                    alert("Greška u prijavi, null user")
                }

                }, function errorCallback(response) {
                 alert("greska u prijavaFunc")

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