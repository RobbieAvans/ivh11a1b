angular.module('services.partofdays', []).factory('PartOfDays', function($resource,cfg) {
	return $resource('http://localhost:'+cfg.port+'/hh/rest/v1/partofday/:hallid/:weeknmr/:sessionid', { 
		hallid: '@hallid',
		weeknmr: '@weeknmr',
		sessionid : '@sessionid'
	});
});