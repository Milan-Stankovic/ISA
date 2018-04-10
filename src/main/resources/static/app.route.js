(function () {
    'use strict';

    angular
		.module('app')
        .config(['$stateProvider', '$urlRouterProvider', '$locationProvider', function($stateProvider, $urlRouterProvider, $locationProvider) {

      $urlRouterProvider.otherwise("/home");

      $stateProvider
		 .state('core', {
			 url: '/',
			 templateUrl: 'app/components/core/core.html',
			 controller: 'coreController',
			 controllerAs: 'cc'
		 })
		.state('core.home', {
			url: 'home',
			templateUrl: 'app/components/home/home.html',
			controller: 'homeController',
			controllerAs: 'hc'
		})
		.state('core.login', {
			url: 'home',
			templateUrl: 'app/components/login/login.html',
			controller: 'loginController',
			controllerAs: 'lc'
		})
		.state('core.fanZone', {
			url: 'fanZone',
			templateUrl: 'app/components/fanZone/fanZone.html',
			controller: 'fanZoneController',
			controllerAs: 'fzc'
		});
      $locationProvider.html5Mode({
    	  enabled: true,
    	  requireBase: false
    	});
    }]);


})();
