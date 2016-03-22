angular.module('services.agenda', []).factory('Agenda', function($resource) {
	
	return $resource('http://localhost:8080/hh/rest/v1/agenda/:start/:end', { 
		start: '@start', end: '@end'
	});
});