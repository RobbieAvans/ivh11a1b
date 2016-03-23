angular.module('services.customer', []).factory('Customer', function($resource) {
return $resource('http://localhost:8082/hh/rest/v1/customer/:id', { id: '@_id' }, {
	    update: {
	      method: 'PUT'
	    },
	    "delete": {
		      method: 'DELETE'
		    }
	  });
});