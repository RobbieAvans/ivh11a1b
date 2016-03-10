angular.module('services.hallReservation', []).factory('HallReservation', function($resource) {
return $resource('http://localhost:8082/hh/rest/v1/hallReservation/:id', { id: '@_id' }, {
	    update: {
	      method: 'PUT'
	    },
	    "delete": {
		      method: 'DELETE'
		    }
	  });
});