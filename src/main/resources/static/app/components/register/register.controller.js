(function () {
    'use strict';

    angular
		.module('app')
		.controller('registerController', registerController);

    registerController.$inject = ['$location', '$scope', '$rootScope','$http', '$window', '$cookies'];
    function registerController($location, $scope, $rootScope, $http, $window, $cookies) {
        var rc = this;
        var user;
        var init = function (){
        $scope.message="";
        };
        init();
        
        $scope.indexFunc = function(){
        	$location.path("home");
        }

        function validateEmail(email) {
            var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
            return re.test(String(email).toLowerCase());
        }

        $scope.regFunc = function(username, pass, pass2, fname, lname, email, hometown, phone){
            if(username==undefined || username=="" || pass==undefined || pass=="" || pass2==undefined || pass2==""
            || fname==undefined || fname=="" || lname==undefined || lname=="" || email=="" || email==undefined){
                $scope.message="Missing informations.";
                return;
            }
        	if(!(pass===pass2)){
        		$scope.message="Passwords don't match.";
        		return;
        	}
        	if(isNaN(phone)){
        		$scope.message="Enter valid phone number.";
        		return;
        	}
        	if(!validateEmail(email)){
        	    $scope.message="Enter valid email.";
        	    return;
        	}
        	var data = {
        			"userName": username,
        			"password": pass,
        			"ime": fname,
        			"prezime": lname,
        			"email": email,
        			"grad": hometown,
        			"brojTelefona": phone
        	}
        	/*alert("userName" +  username +
        			"password"+ pass +
        			"ime"+ fname +
        			"prezime"+ lname +
        			"email"+ email +
        			"grad"+ hometown +
        			"brojTelefona"+ phone)*/
            $http({
              method: 'POST',
              url: 'http://localhost:8096/api/register/',
              data: data
            }).then(function successCallback(response) {
            	if(response.data=="")
            		$scope.message="We sent you an email to confirm your registration.";
            	else
            		alert(response.data)

                }, function errorCallback(response) {
                 $scope.message="Username or email is already taken.";

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