angular.module('bestellenApp', ['ui.router','datatables','sessionvalidator', 'ngResource', 'bestellenApp.controllers', 'services.hall','services.hallOption','services.customer','services.hallReservation','autoActive','ui.calendar','services.agenda','services.partofdays']);

angular.module('bestellenApp').config(function($stateProvider) {
  $stateProvider.state('halls', { 
    url: '/hall/',
    templateUrl: 'zaal/zalen/index.html',
    controller: 'HallListController'
  }).state('viewHall', { 
    url: '/hall/:id',
    templateUrl: 'zaal/index.html',
    controller: 'HallViewController'
  }).state('newHall', { 
    url: '/hall/',
    templateUrl: 'zaal/toevoegen/index.html',
    controller: 'HallCreateController'
  }).state('editHall', { 
    url: '/hall/:id',
    templateUrl: 'zaal/wijzigen/index.html',
    controller: 'HallEditController'
  }).state('hallOptions', { 
	    url: '/halloption/:id',
	    templateUrl: 'halloption/halloptions/index.html',
	    controller: 'HallOptionListController'
  }).state('viewHallOption', { 
	    url: '/halloption/:id',
	    templateUrl: 'halloption/index.html',
	    controller: 'HallOptionViewController'
  }).state('newHallOption', { 
	    url: '/halloption/:id',
	    templateUrl: 'halloption/toevoegen/index.html',
	    controller: 'HallOptionCreateController'
  }).state('editHallOption', { 
	    url: '/halloption/:id',
	    templateUrl: 'halloption/wijzigen/index.html',
	    controller: 'HallOptionEditController'
  }).state('hallReservations', { 
	    url: '/hallreservation/:id',
	    templateUrl: 'hallreservation/hallreservations/index.html',
	    controller: 'HallReservationListController'
  }).state('newHallReservation', { 
	    url: '/hallreservation/:id',
	    templateUrl: 'hallreservation/toevoegen/index.html',
	    controller: 'HallReservationCreateController'
  }).state('viewHallReservation', { 
	    url: '/hallreservation/:id',
	    templateUrl: 'hallreservation/index.html',
	    controller: 'HallReservationViewController'
  }).state('editHallReservation', { 
	    url: '/hallreservation/:id',
	    templateUrl: 'hallreservation/wijzigen/index.html',
	    controller: 'HallReservationEditController'
  }).state('registerCustomer', { 
	    url: '/customer/registrer',
	    templateUrl: 'customer/toevoegen/index.html',
	    controller: 'CustomerCreateController'
  }).state('viewCustomer', { 
	    url: '/customer/:id',
	    templateUrl: 'customer/index.html',
	    controller: 'CustomerViewController'
  }).state('agenda', {
	  url: '/agenda',
	  templateUrl: 'agenda/agenda.html',
	  controller: 'AgendaController'
  }).state('login', { 
	    url: '/login/',
	    templateUrl: 'login/index.html',
	    controller: 'LoginController'
  });
}).run(function($state,$rootScope,LoginRequest,SessionValidator) {
	$rootScope.$on('$stateChangeStart', 
	function(event, toState, toParams, fromState, fromParams){ 
		console.log("KOMT IE NU HIER ");
		if(SessionValidator.getCookie("loginData") != ""){
			if(SessionValidator.isValidSession() != null){
				SessionValidator.setCookie("loginData",JSON.stringify(SessionValidator.isValidSession()),15);
				
				$rootScope.userID		= JSON.parse(SessionValidator.getCookie("loginData")).id;
				$rootScope.sessionID	= JSON.parse(SessionValidator.getCookie("loginData")).sessionID;
				$rootScope.role 		= JSON.parse(SessionValidator.getCookie("loginData")).role;
				
				// Alter lay-out depending on user-role
				//console.log("Moet nu hier komen want is correct ingelogd, rol: " + $rootScope.role);
				SessionValidator.setLayoutForUser($rootScope.role);
			}else{
				console.log("Komt ie hier?");
				//SessionValidator.logout();
				SessionValidator.setLayoutForUser("");
				// Redirect naar LOGIN
			}
		}else{
			console.log("Hij moet hier komen");
			SessionValidator.setLayoutForUser("");
			// Redirect naar LOGIN
		}
	})
	
  $state.go('registerCustomer');
});



