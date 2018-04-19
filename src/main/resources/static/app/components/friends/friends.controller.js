(function () {
    'use strict';

    angular
		.module('app')
		.controller('friendsController', friendsController);

    friendsController.$inject = ['$location', '$scope', '$rootScope','$http', '$window', '$cookies'];
    function friendsController($location, $scope, $rootScope, $http, $window, $cookies) {
        var fc = this;
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
            if(!$cookies.get('user'))
                $location.path("login")
        	console.log("trazim admira, path: " + 'http://localhost:8096/admin/' + $cookies.get('user'));
        	$http({
  			  method: 'GET',
  			  url: 'http://localhost:8096/admin/' + $cookies.get('user')

  			}).then(function successCallback(response) {
  				user = response.data;
  				 if(user!=undefined && user.hasOwnProperty('tip')){
  				    $scope.isAdmin = true;
  				}

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


    }


})();