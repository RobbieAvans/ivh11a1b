angular.module('services.hall', []).factory('Hall', function($resource) {
return $resource('http://localhost:8080/hh/rest/v1/hall/:id', { id: '@_id' }, {
	    update: {
	      method: 'PUT'
	    },
	    "delete": {
		      method: 'DELETE'
		    }
	  });
});