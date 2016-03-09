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
	  
}).controller('HallReservationListController', function($scope, $state, $window, HallReservation, HallOption) {
	var responseHallOption = HallOption.get();
	responseHallOption.$promise.then(function(data){
		$scope.hallOptions = data.data;
	});
	
	  var responseHallReservation = HallReservation.get();
	  responseHallReservation.$promise.then(function(data){
	      $scope.hallReservations = data.data;
	      
	      // Calculate totalPrice of hallReservation (with hallOptions)
	      angular.forEach($scope.hallReservations,function(reservation){
	    	  // Make sure Angular sees totalPrice as an int
	    	  reservation.totalPrice = 0;
	    	  $scope.selection = [];
	    	  angular.forEach(reservation.hallOptions,function(hallOption){
	    		  // Create selection of HallOption
	    		  $scope.selection.push(hallOption.description);
	    		  reservation.totalPrice = parseInt(reservation.totalPrice) + parseInt(hallOption.price);
	    	  })
          })
	      
	  });
	  
	  

	  // toggle selection for a given fruit by name
	  $scope.toggleSelection = function toggleSelection(hallOption) {
	    var idx = $scope.selection.indexOf(hallOption);
	    if (idx > -1) {
	      $scope.selection.splice(idx, 1);
	    }
	    else {
	      $scope.selection.push(hallOption);
	    }
	    
	    console.log($scope.selection);
	  };
}).controller('HallReservationCreateController', function($scope, $state, $stateParams,HallReservation, HallOption) {
		var responseHallOption = HallOption.get();
		responseHallOption.$promise.then(function(data){
			$scope.hallOptions = data.data;
		});
	
	  $scope.hallReservation = new HallReservation();  

	  $scope.addHallReservation = function() {
		  $scope.hallReservation.hallOptions = []
		 jQuery("#hallReservationOptions input:checked").each(function(){
			 $scope.hallReservation.hallOptions.push(JSON.parse(jQuery(this).val()));
		 }); 
		  
		console.log($scope.hallReservation);
	    $scope.hallReservation.$save(function() {
	      $state.go('hallOptions'); 
	    });
	  };
	});