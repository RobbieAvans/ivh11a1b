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
  });
}).run(function($state,$rootScope,LoginRequest,SessionValidator) {
	$rootScope.$on('$stateChangeStart', 
	function(event, toState, toParams, fromState, fromParams){ 
	    // get user data
		validateSessionID();
		
		function validateSessionID(){
			console.log(SessionValidator.isValidSession());
			SessionValidator.setLayoutForUser();
			//if($rootScope.sessionID != "asdfasdf"){
			//	$rootScope.setCookie("loginData","",-1);
			//}
			//console.log($rootScope.sessionID);
		}
		
		
		
	})
	
  $state.go('registerCustomer');
});



