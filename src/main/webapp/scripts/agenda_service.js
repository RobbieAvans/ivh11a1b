angular.module('services.agenda', []).factory('Agenda', function($resource,cfg) {
	
	return $resource('http://localhost:'+cfg.port+'/hh/rest/v1/agenda/:start/:end/:sessionid', { 
		start: '@start', end: '@end', sessionid : '@sessionid'
	});
});