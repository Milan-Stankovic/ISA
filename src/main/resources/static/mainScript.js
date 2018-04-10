(function (angular) {
//stari angular
    var app = angular.module("app", ['ngCookies']);
    
    app.controller("headerCtrl", ['$scope', '$http', '$window', '$cookies',
        function loginCtrl($scope, $http, $localStorage, $window){

        $scope.loginFunc = function(){
            $http({
              method: 'GET',
              url: 'http://localhost:8096/api/login'
            }).then(function successCallback(response) {
                window.location = 'http://localhost:8096/login.html';
                }, function errorCallback(response) {
                 alert("greska u loginFunc")
                });

        }

        $scope.indexFunc = function(){
            window.location = "http://localhost:8096/index.html";
        }

        $scope.prijavaFunc = function(username, pass){
        	var data = {
        			"userName": $scope.username,
        			"password": $scope.password
        	}
            $http({
              method: 'POST',
              url: 'http://localhost:8096/api/login/',
              data: data
            }).then(function successCallback(response) {
                $scope.current = response.data;
                if($scope.current){
                	if($scope.current.status=="NERESEN"){
                		alert("Lozinka mora biti promenjena. Neuspesno logovanje.")
                		
                		$cookies.remove('username');
                		$scope.loggedIn = false;
                	}
                    $scope.loggedIn = true;
                    bakeCookie();
                    alert("Uspesno logovanje.")
                }

                else{
                    $scope.loggedIn = false;
                    alert("Greška u prijavi")
                }

                }, function errorCallback(response) {
                 alert("greska u prijavaFunc")
                $scope.loggedIn = false;
                });

        }

        window.fbAsyncInit = function() {
            FB.init({
              appId      : '330212657486853',
              cookie     : true,
              xfbml      : true,
              version    : 'v2.12'
            });


            FB.AppEvents.logPageView();

            $scope.fbLogoutUser = function() {
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
            		  $cookies.put('username', $scope.current.userName);
            	  } else {
            		  $cookies.put('username', $scope.current.userName);
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
                                 $scope.current = response.data;

                                 if($scope.current){
                                     console.log("dobio: " + $scope.current.userName)
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
//            fbLogoutUser();
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


        }]);
})(angular);