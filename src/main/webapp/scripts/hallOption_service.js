angular.module('services.hallOption', []).factory('HallOption', function($resource,cfg) {
return $resource('http://localhost:'+cfg.port+'/hh/rest/v1/halloption/:id/:sessionid', { id: '@_id',sessionid : '@sessionid' }, {
	    update: {
	      method: 'PUT'
	    },
	    "delete": {
		      method: 'DELETE'
		    }
	  });
});