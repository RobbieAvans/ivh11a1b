angular.module('services.hallReservation', []).factory('HallReservation', function($resource,cfg) {
return $resource('http://localhost:'+cfg.port+'/hh/rest/v1/hallReservation/:id/:sessionid', { id: '@_id',sessionid : '@sessionid' }, {
	    update: {
	      method: 'PUT'
	    },
	    "delete": {
		      method: 'DELETE'
		    }
	  });
}).factory('CustomPartOfDay', function(){
	function CustomPartOfDay(date,partOfDay){
		this.date = date;
		this.partOfDay = partOfDay;
	};
	CustomPartOfDay.build = function(data){
		return new CustomPartOfDay(data.date, data.partOfDay);
	};
	return CustomPartOfDay;
});