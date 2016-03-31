angular.module('services.hall', []).factory('Hall', function($resource) {
return $resource('http://localhost:8082/hh/rest/v1/hall/:id/:sessionid', { id: '@_id',sessionid : '@sessionid' }, {
	    update: {
	      method: 'PUT'
	    },
	    "delete": {
		      method: 'DELETE'
		    }
	  });
});