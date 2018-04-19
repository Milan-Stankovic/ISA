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
		.state('core.friends', {
            url: 'friends',
            templateUrl: 'app/components/friends/friends.html',
            controller: 'friendsController',
            controllerAs: 'fc'
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
        .state('core.allReservations', {
          url: 'allReservations',
          templateUrl: 'app/components/allReservations/allReservations.html',
          controller: 'allReservationsController',
          controllerAs: 'arc'
      })
        .state('core.invitation', {
          url: 'invitation/{id1}/event/{id2}',
          templateUrl: 'app/components/invitation/invitation.html',
          controller: 'invitationController',
          controllerAs: 'ic'
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

          .state('core.adminIzvestaj', {
              url: 'adminIzvestaj',
              templateUrl: 'app/components/adminIzvestaj/izvestaj.html',
              controller: 'adminIzvestajController',
              controllerAs: 'ic',
			  params: {id :null}
          })
		.state('core.fanZone', {
			url: 'fanZone',
			templateUrl: 'app/components/fanZone/fanZone.html',
			controller: 'fanZoneController',
			controllerAs: 'fzc'
		})
		  .state('core.adminOneClick', {
              url: 'adminOneClick',
              templateUrl: 'app/components/adminOneClick/oneClick.html',
              controller: 'adminOneClickController',
              controllerAs: 'aoc',
              params: {id :null,
				  dName : null,
				  pTime : null,
				  pId : null,
				  dId : null,
				  bpId: null,
				  pPrice: null
			  }
          })
          .state('core.userOneClick', {
              url: 'userOneClick',
              templateUrl: 'app/components/userOneClick/userOneClick.html',
              controller: 'userOneClickController',
              controllerAs: 'uoc',
              params: {id :null}
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
		})
		.state('core.oglasi', {
			url: 'oglasi',
			templateUrl: 'app/components/oglasi/oglasi.html',
			controller: 'oglasiController',
			controllerAs: 'oc'
		})
		.state('core.mojiOglasiPonude', {
			url: 'mojiOglasiPonude',
			templateUrl: 'app/components/mojiOglasiPonude/mojiOglasiPonude.html',
			controller: 'mojiOPController',
			controllerAs: 'mopc'
		});
     /* $locationProvider.html5Mode({
    	  enabled: true,
    	  requireBase: false
    	});*/
    }]);


})();
