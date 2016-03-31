angular.module('services.hall', []).factory('Hall', function($resource,cfg) {
return $resource('http://localhost:'+cfg.port+'/hh/rest/v1/hall/:id/:sessionid', { id: '@_id',sessionid : '@sessionid' }, {
	    update: {
	      method: 'PUT'
	    },
	    "delete": {
		      method: 'DELETE'
		    }
	  });
});