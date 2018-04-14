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
		.state('core.adminLogin', {
			url: 'adminLogin',
			templateUrl: 'app/components/adminLogin/adminLogin.html',
			controller: 'adminLoginController',
			controllerAs: 'alc'
		})
		.state('core.settings', {
			url: 'settings',
			templateUrl: 'app/components/settings/settings.html',
			controller: 'settingsController',
			controllerAs: 'sc'
		})
		.state('core.register', {
			url: 'register',
			templateUrl: 'app/components/register/register.html',
			controller: 'registerController',
			controllerAs: 'rc'
		})
		.state('core.login', {
			url: 'login',
			templateUrl: 'app/components/login/login.html',
			controller: 'loginController',
			controllerAs: 'lc'
		})
		.state('core.pozoristeBioskop', {
            url: 'pozoristeBioskop/{id}',
            templateUrl: 'app/components/pozoristeBioskop/pozoristeBioskop.html',
            controller: 'pozoristeBioskopController',
            controllerAs: 'pbc'
        })
        .state('core.reservation', {
            url: 'pozoristeBioskop/{id1}/projekcije/{id2}/reservation',
            templateUrl: 'app/components/reservation/reservation.html',
            controller: 'reservationController',
            controllerAs: 'resc'
        })
        .state('core.projekcija', {
            url: 'pozoristeBioskop/{id1}/projekcije/{id2}',
            templateUrl: 'app/components/projekcija/projekcija.html',
            controller: 'projekcijaController',
            controllerAs: 'prc'
        })
		.state('core.regKorHome', {
            url: 'regKorHome',
            templateUrl: 'app/components/regKorHome/regKorHome.html',
            controller: 'regKorHomeController',
            controllerAs: 'rhc'
        })
		  .state('core.admin', {
          url: 'admin',
          templateUrl: 'app/components/admin/admin.html',
          controller: 'adminController',
          controllerAs: 'ac'
      })
		.state('core.fanZone', {
			url: 'fanZone',
			templateUrl: 'app/components/fanZone/fanZone.html',
			controller: 'fanZoneController',
			controllerAs: 'fzc'
		})
		.state('core.addEditItem', {
			url: 'addEditItem',
			templateUrl: 'app/components/addEditItem/addEditItem.html',
			controller: 'addEditItemController',
			controllerAs: 'aeic'
		})
		.state('core.adminMode', {
			url: 'adminMode',
			templateUrl: 'app/components/adminMode/adminMode.html',
			controller: 'adminModeController',
			controllerAs: 'amc'
		})
		.state('core.addAdmins', {
			url: 'addAdmins',
			templateUrl: 'app/components/addAdmins/addAdmins.html',
			controller: 'addAdminsController',
			controllerAs: 'aac'
		});
     /* $locationProvider.html5Mode({
    	  enabled: true,
    	  requireBase: false
    	});*/
    }]);


})();
