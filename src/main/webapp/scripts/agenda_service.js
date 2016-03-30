angular.module('services.agenda', []).factory('Agenda', function($resource) {
	
	return $resource('http://localhost:8082/hh/rest/v1/agenda/:start/:end/:sessionid', { 
		start: '@start', end: '@end', sessionid : '@sessionid'
	});
});