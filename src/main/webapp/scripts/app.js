angular.module('bestellenApp', ['ui.router','datatables', 'ngResource', 'bestellenApp.controllers', 'services.hall','services.hallOption','autoActive']);
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
  });
}).run(function($state) {
 // $state.go('halls');
});

