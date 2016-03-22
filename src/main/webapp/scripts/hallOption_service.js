angular.module('services.hallOption', []).factory('HallOption', function($resource) {
return $resource('http://localhost:8080/hh/rest/v1/halloption/:id', { id: '@_id' }, {
	    update: {
	      method: 'PUT'
	    },
	    "delete": {
		      method: 'DELETE'
		    }
	  });
});