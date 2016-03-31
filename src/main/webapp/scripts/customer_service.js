angular.module('services.customer', []).factory('Customer', function($resource,cfg) {
return $resource('http://localhost:'+cfg.port+'/hh/rest/v1/customer/:id/:sessionid', { id: '@_id',sessionid : '@sessionid' }, {
	    update: {
	      method: 'PUT'
	    },
	    "delete": {
		      method: 'DELETE'
		    }
	  });
}).factory('LoginRequest', function(){
	function LoginRequest(email,password){
		this.email = email;
		this.password = password;
	};
	LoginRequest.build = function(data){
		return new LoginRequest(data.email, data.password);
	};
	return LoginRequest;
});