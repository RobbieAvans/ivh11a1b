angular.module('services.customer', []).factory('Customer', function($resource) {
return $resource('http://localhost:8080/hh/rest/v1/customer/:id', { id: '@_id' }, {
	    update: {
	      method: 'PUT'
	    },
	    "delete": {
		      method: 'DELETE'
		    }
	  });
});