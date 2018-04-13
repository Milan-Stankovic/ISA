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
        $scope.resList = [];
        $scope.reqList = [];
        $scope.sentList = [];
        $scope.searched = false;
        $scope.isDisabled = false;
        $scope.addButton = "Add";
        $scope.isAdmin = false;
        var init = function (){ 	
        	console.log("trazim admira, path: " + 'http://localhost:8096/admin/' + $cookies.get('user'));
        	$http({
  			  method: 'GET',
  			  url: 'http://localhost:8096/admin/' + $cookies.get('user')
  				 
  			}).then(function successCallback(response) {
  				user = response.data;
  				$scope.isAdmin = true;
  				
  			  }, function errorCallback(response) {
  				  console.log("Greska kod GET user");
  			  });

        };
        init();
        
        if(user===undefined || user===null){
      		console.log("user undefined trazim usra, path: " + 'http://localhost:8096/api/user/' + $cookies.get('user'));
      		$scope.isAdmin = false;
      		$http({
      			  method: 'GET',
      			  url: 'http://localhost:8096/api/user/' + $cookies.get('user')
      				 
      			}).then(function successCallback(response) {
      				user = response.data;
      				if(user!=undefined && ($scope.isAdmin==false)){
      		        	console.log("frnessssss")

      		        	$http({
      		    			  method: 'GET',
      		    			  url: 'http://localhost:8096/api/user/friends/' + $cookies.get('user')
      		    				 
      		    			}).then(function successCallback(response) {
      		    				$scope.friendsList = response.data;
      		    				console.log("prijatelja: " + $scope.friendsList.length);
      		    				//alert(user.userName)
      		    			  }, function errorCallback(response) {
      		    				  console.log("Greska kod GET user frineds");
      		    			  });

      		    		$http({
                              method: 'GET',
                              url: 'http://localhost:8096/api/user/req/' + $cookies.get('user')

                            }).then(function successCallback(response) {
                                $scope.reqList = response.data;
                                console.log("requestova: " + $scope.reqList.length);
                                //alert(user.userName)
                              }, function errorCallback(response) {
                                  console.log("Greska kod GET user frineds");
                              });


                        $http({
                              method: 'GET',
                              url: 'http://localhost:8096/api/user/sent/' + $cookies.get('user')

                            }).then(function successCallback(response) {
                                $scope.sentList = response.data;
                                console.log("poslatih: " + $scope.sentList.length);
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
        
        $scope.searchFunc = function(searchfName, searchlName){
        	if((searchfName==undefined || searchfName==="") && (searchlName==undefined|| searchlName==="")){
        		alert("Both search fields are empty.")
        		return;
        	}
        	$scope.addButton = "Add";
        	$scope.isDisabled = false;
        	var data = searchfName + "." + searchlName;
        	$scope.results=[];
        	$http({
    			  method: 'POST',
    			  url: 'http://localhost:8096/api/settings/search',
    			  data: data
    			
    			}).then(function successCallback(response) {
    			    $scope.searched=true;
    				$scope.resList = response.data;
    				console.log("pre filtera: " + $scope.resList.length);
    				var i,j;
    				var temp=[];
    				var found = false;
    				console.log(user.email);
    				for(i=0; i < $scope.resList.length; i++){
    				    for(j=0; j < $scope.friendsList.length; j++){
                            if($scope.resList[i].email===$scope.friendsList[j].email || $scope.resList[i].email===user.email){
                                found=true;
                            }
                        }
                        if(found){
                            found = false;
                        }else{
                            temp.push($scope.resList[i]);
                            found = false;
                        }


    				}
    				$scope.resList=temp;

    				for(i=0; i < $scope.resList.length; i++){
                        for(j=0; j < $scope.sentList.length; j++){
                            if($scope.resList[i].email===$scope.sentList[j].email){
                                for(var k = 0; k < temp.length; k++) {

                                    if($scope.resList[i].email===temp[k].email) {
                                        break;
                                    }
                                }
                                temp.splice(k, 1);
                            }
                        }

                    }
                    $scope.resList=temp;

                    for(i=0; i < $scope.resList.length; i++){
                        for(j=0; j < $scope.reqList.length; j++){
                            if($scope.resList[i].email===$scope.reqList[j].email){
                                for(var k = 0; k < temp.length; k++) {

                                    if($scope.resList[i].email===temp[k].email) {
                                        break;
                                    }
                                }
                                temp.splice(k, 1);
                            }
                         }

                     }
                    $scope.resList=temp;
    			  }, function errorCallback(response) {
    				  console.log("Greska kod search");
    			  });
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


                $http({
                    method: 'GET',
                    url: 'http://localhost:8096/api/user/req/' + $cookies.get('user')

                  }).then(function successCallback(response) {
                      $scope.reqList = response.data;
                      console.log("requestova: " + $scope.reqList.length);
                      //alert(user.userName)
                    }, function errorCallback(response) {
                        console.log("Greska kod GET user frineds");
                    });


              $http({
                    method: 'GET',
                    url: 'http://localhost:8096/api/user/sent/' + $cookies.get('user')

                  }).then(function successCallback(response) {
                      $scope.sentList = response.data;
                      console.log("poslatih: " + $scope.sentList.length);
                      //alert(user.userName)
                    }, function errorCallback(response) {
                        console.log("Greska kod GET user frineds");
                    });

        }

        $scope.declineFriend = function(email){
            console.log("odbijam: " + email)
            $http({
                  method: 'DELETE',
                  url: 'http://localhost:8096/api/user/decline/' + $cookies.get('user'),
                  data: email
                }).then(function successCallback(response) {
                    $scope.reqList = response.data;
                    console.log("requestova: " + $scope.reqList.length);
                    //alert(user.userName)
                  }, function errorCallback(response) {
                      console.log("Greska kod GET user frineds");
                  });

        }

        $scope.acceptFriend = function(email){
                    console.log("prihvatam: " + email)
                    $http({
                          method: 'POST',
                          url: 'http://localhost:8096/api/user/accept/' + $cookies.get('user'),
                          data: email
                        }).then(function successCallback(response) {
                            $scope.reqList = response.data;
                            console.log("requestova: " + $scope.reqList.length);

                            $http({
                                      method: 'GET',
                                      url: 'http://localhost:8096/api/user/friends/' + $cookies.get('user')

                                    }).then(function successCallback(response) {
                                        $scope.friendsList = response.data;
                                        console.log("prijatelja: " + $scope.friendsList.length);
                                        //alert(user.userName)
                                      }, function errorCallback(response) {
                                          console.log("Greska kod GET user frineds");
                                      });

                            //alert(user.userName)
                          }, function errorCallback(response) {
                              console.log("Greska kod GET user frineds");
                          });


                }



        $scope.addFriend = function(email){
            console.log("dodajem: " + email)
            $http({
                  method: 'POST',
                  url: 'http://localhost:8096/api/user/friends/' + $cookies.get('user'),
                  data: email
                }).then(function successCallback(response) {

                    $scope.isDisabled = true;
                    $scope.addButton = "Sent";
                    $http({
                      method: 'GET',
                      url: 'http://localhost:8096/api/user/sent/' + $cookies.get('user')

                    }).then(function successCallback(response) {
                        $scope.sentList = response.data;
                        console.log("poslatih: " + $scope.sentList.length);
                        //alert(user.userName)
                      }, function errorCallback(response) {
                          console.log("Greska kod GET user frineds");
                      });
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