(function (angular) {
//stari angular
    var app = angular.module("app", []);
    
    app.controller("headerCtrl", ['$scope', '$http', '$window',
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
            $http({
              method: 'POST',
              url: 'http://localhost:8096/api/login/username='+$scope.username + "&pass=" + $scope.pass
            }).then(function successCallback(response) {
                $scope.current = response.data;
                if($scope.current){
                    alert($scope.current.userName)
                }

                else
                    alert("Greška u prijavi")
                }, function errorCallback(response) {
                 alert("greska u prijavaFunc")
                });

        }


        }]);
})(angular);