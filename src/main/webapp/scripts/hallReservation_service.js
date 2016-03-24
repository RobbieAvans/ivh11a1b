angular.module('services.hallReservation', []).factory('HallReservation', function($resource) {
return $resource('http://localhost:8082/hh/rest/v1/hallReservation/:id', { id: '@_id' }, {
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