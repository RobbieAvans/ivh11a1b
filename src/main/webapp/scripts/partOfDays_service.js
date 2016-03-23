angular.module('services.partofdays', []).factory('PartOfDays', function($resource) {
	return $resource('http://localhost:8082/hh/rest/v1/partofday/:hallid/:weeknmr', { 
		hallid: '@hallid',
		weeknmr: '@weeknmr'
	});
});