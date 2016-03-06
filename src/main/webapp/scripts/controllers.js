angular.module('bestellenApp.controllers', [])
.controller('HallListController', function($scope, $state, $window, Hall) {
  var response = Hall.get();
  response.$promise.then(function(data){
      $scope.halls = data.data;
  });

  $scope.deleteHall = function(hall) { 
	  Hall.delete({id: hall.id},function(){
		  $window.location.href = '';
	});
  };
}).controller('HallViewController', function($scope, $stateParams, Hall) {
  var response = Hall.get({ id: $stateParams.id });
  response.$promise.then(function(data){
      $scope.hall = data.data;
  });
  
}).controller('HallCreateController', function($scope, $state, $stateParams, Hall) {
  $scope.hall = new Hall();  

  $scope.addHall = function() { 
    $scope.hall.$save(function() {
      $state.go('halls'); 
    });
  };
}).controller('HallEditController', function($scope, $state, $stateParams, Hall) {
	
	
  $scope.updateHall = function() { 
	Hall.update({id: $scope.hall.id},$scope.hall,function(){
	  $state.go('halls'); 
	});
  };

  $scope.loadHall = function() { 
	  var response = Hall.get({ id: $stateParams.id });
	  response.$promise.then(function(data){
	      $scope.hall = data.data;
	  });
  };
  
  $scope.loadHall(); 
}).controller('HallOptionListController', function($scope, $state, $window, HallOption) {
	  var response = HallOption.get();
	  response.$promise.then(function(data){
	      $scope.hallOptions = data.data;
	  });
	
	  $scope.deleteHallOption = function(hallOption) { 
		  HallOption.delete({id: hallOption.id},function(){
			  $window.location.href = '';
		});
	  };
	  
}).controller('HallOptionEditController', function($scope, $state, $stateParams, HallOption) {
	  $scope.updateHallOption = function() { 
		  	HallOption.update({id: $scope.hallOption.id},$scope.hallOption,function(){
			  $state.go('hallOptions'); 
			});
		  };

		  $scope.loadHallOption = function() { 
			  var response = HallOption.get({ id: $stateParams.id });
			  response.$promise.then(function(data){
			      $scope.hallOption = data.data;
			  });
		  };
		  
		  $scope.loadHallOption(); 
	 
}).controller('HallOptionCreateController', function($scope, $state, $stateParams, HallOption) {
  $scope.hallOption = new HallOption();  

  $scope.addHallOption = function() { 
    $scope.hallOption.$save(function() {
      $state.go('hallOptions'); 
    });
  };
}).controller('HallOptionViewController', function($scope, $stateParams, HallOption) {
	  var response = HallOption.get({ id: $stateParams.id });
	  response.$promise.then(function(data){
	      $scope.hallOption = data.data;
	  });
	  
});