angular.module('services.hallOption', []).factory('HallOption', function($resource) {
return $resource('http://localhost:8082/hh/rest/v1/halloption/:id/:sessionid', { id: '@_id',sessionid : '@sessionid' }, {
	    update: {
	      method: 'PUT'
	    },
	    "delete": {
		      method: 'DELETE'
		    }
	  });
});