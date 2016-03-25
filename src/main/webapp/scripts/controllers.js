angular.module('bestellenApp.controllers', [])
	.controller('MainController', function($scope, $state, $window, Hall,i18n, Customer) {
	    
	})
    .controller('HallListController', function($scope, $state, $window, Hall) {
        var response = Hall.get();
        response.$promise.then(function(data) {
            $scope.halls = data.data;
        });

        $scope.deleteHall = function(hall) {
            Hall.delete({
                id: hall.id
            }, function() {
                $window.location.reload();
            });
        };
    }).controller('HallViewController', function($scope, $stateParams, Hall) {
        var response = Hall.get({
            id: $stateParams.id
        });
        response.$promise.then(function(data) {
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
            Hall.update({
                id: $scope.hall.id
            }, $scope.hall, function() {
                $state.go('halls');
            });
        };

        $scope.loadHall = function() {
            var response = Hall.get({
                id: $stateParams.id
            });
            response.$promise.then(function(data) {
                $scope.hall = data.data;
            });
        };

        $scope.loadHall();
    }).controller('HallOptionListController', function($scope, $state, $window, HallOption) {
        var response = HallOption.get();
        response.$promise.then(function(data) {
            $scope.hallOptions = data.data;
        });

        $scope.deleteHallOption = function(hallOption) {
            HallOption.delete({
                id: hallOption.id
            }, function() {
                $window.location.reload();
            });
        };

    }).controller('HallOptionEditController', function($scope, $state, $stateParams, HallOption) {
        $scope.updateHallOption = function() {
            HallOption.update({
                id: $scope.hallOption.id
            }, $scope.hallOption, function() {
                $state.go('hallOptions');
            });
        };

        $scope.loadHallOption = function() {
            var response = HallOption.get({
                id: $stateParams.id
            });
            response.$promise.then(function(data) {
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
        var response = HallOption.get({
            id: $stateParams.id
        });
        response.$promise.then(function(data) {
            $scope.hallOption = data.data;
        });

    }).controller('HallReservationListController', function($scope, $state, $window, HallReservation, HallOption) {
        var responseHallOption = HallOption.get();
        responseHallOption.$promise.then(function(data) {
            $scope.hallOptions = data.data;
        });

        var responseHallReservation = HallReservation.get();
        responseHallReservation.$promise.then(function(data) {
            $scope.hallReservations = data.data;
            
            // Calculate totalPrice of hallReservation (with hallOptions)
            angular.forEach($scope.hallReservations, function(reservation) {
                // Make sure Angular sees totalPrice as an int
                reservation.totalPrice = 0;
                reservation.totalPrice = reservation.hall.price;

                $scope.selection = [];
                angular.forEach(reservation.hallOptions, function(hallOption) {
                    console.log("De prijs" + hallOption.price);
                    // Create selection of HallOption
                    $scope.selection.push(hallOption.description);
                    reservation.totalPrice = parseInt(reservation.totalPrice) + parseInt(hallOption.price);
                })
            })

        });

        $scope.deleteHallReservation = function(hallReservation) {
            HallReservation.delete({
                id: hallReservation.id
            }, function() {
                $window.location.reload();
            });
        };



        // toggle selection for a given fruit by name
        $scope.toggleSelection = function toggleSelection(hallOption) {
            var idx = $scope.selection.indexOf(hallOption);
            if (idx > -1) {
                $scope.selection.splice(idx, 1);
            } else {
                $scope.selection.push(hallOption);
            }

            console.log($scope.selection);
        };
    }).controller('HallReservationEditController', function($scope, $stateParams, $state, $window, HallReservation, HallOption, Hall,PartOfDays, CustomPartOfDay) {
        var responseHallOption = HallOption.get();
        responseHallOption.$promise.then(function(data) {
            $scope.hallOptions = data.data;
        });
        
        $scope.selectedHallOptions 	= [];
        $scope.selectedHall			= 0;
        
        var responseHall = Hall.get();
        responseHall.$promise.then(function(data) {
            $scope.halls = data.data;
            // Remove @id
            angular.forEach($scope.halls, function(hall) {
                delete hall["@id"];
            });
        });

    	
    	 $scope.updateHallReservation = function() {
        	 // Change object for API
    		 $scope.hallReservation.hall 		= $scope.selectedHall;
    		 $scope.hallReservation.customer 	= 1;
    		 
             // Remove @id
    		 delete $scope.hallReservation["@id"];
   		     		 
    		 $scope.hallReservation.hallOptions = $scope.selectedHallOptions;
    		 $scope.hallReservation.partOfDays 	= $scope.selectedPartOfDays;
    		 
             HallReservation.update({
            	 id: $scope.hallReservation.id
             }, $scope.hallReservation, function(data) {
	        	 if(data.success){
	        		 $state.go('hallReservations');
	        	 }
             });
         };

         $scope.loadHallReservation = function() {
             var response = HallReservation.get({
                 id: $stateParams.id
             });
             response.$promise.then(function(data) {
                 $scope.hallReservation = data.data;               
                 
                 // Set selected hallOptions
                 $scope.checkedHallOptions = function(option){
                	 var returnValue = false;
                	 angular.forEach($scope.hallReservation.hallOptions, function(hallOption) {
                		 if(hallOption.description == option.description){
                			 returnValue = true;
                		 }
                     });
                	 return returnValue;
                 }
                 
                 // Set $scope.selectedHallOption
                 angular.forEach($scope.hallReservation.hallOptions, function(hallOption) {
        			 var idx = $scope.selectedHallOptions.indexOf(hallOption.id);
                     if (idx > -1) {
                         $scope.selectedHallOptions.splice(idx, 1);
                     } else {
                        $scope.selectedHallOptions.push(hallOption.id);
                     }
                 });
                 
                 // Set selected hall
                 $scope.checkedHall = function(hall){
                	 var returnValue = false;
                	 if($scope.hallReservation.hall.description == hall.description){
            			 returnValue = true;
            		 }
                	 return returnValue;
                 }
                 
	              // Set $scope.selectedHall
	        	 $scope.selectedHall = $scope.hallReservation.hall.id;
	        	 
	        	 $scope.getPartOfDays();
                
             });
             
         };
         
         $scope.selectedHall = 0;
         $scope.currentDate = moment(new Date()).add(-1,'weeks');
         $scope.currentWeek = moment($scope.currentDate).week();
         $scope.days = [];
         $scope.selectedPartOfDays = [];
         
         $scope.getPartOfDays = function(){
        	 
        	 // Set dummy PartOfDays
        	 $scope.hallReservation.partOfDays = [{"date":"23-03-2016","partOfDay":"Morning"},{"date":"23-03-2016","partOfDay":"Afternoon"},{"date":"23-03-2016","partOfDay":"Evening"}];

         	$scope.currentWeek = moment($scope.currentDate).week();
         	
         	// Get partOfDays for hall.id and week
         	var response = PartOfDays.get({
         		hallid: $scope.selectedHall,
 	            weeknmr: moment($scope.currentDate).week()
 	        });
 	        
 	        response.$promise.then(function(data) {
 	        	if (data.success) {
 	        		$scope.days = [];
 	        		angular.forEach(data.data, function(days) {
 	        			$scope.days.push(days);
 			        });	
 	        	}
 	        });
 	        
	       	 
	 	        
 	        jQuery("#cntrPartOfDay").fadeIn(200);
 	        setTimeout(function(){
	 	        // Make each partOfDay in current HallReservatin selected
 	        	angular.forEach($scope.hallReservation.partOfDays, function(_partOfDay) {
 	        		$scope.partOfDay 			= new CustomPartOfDay();
 	            	$scope.partOfDay.date 		= _partOfDay.date;
 	            	$scope.partOfDay.partOfDay 	= _partOfDay.partOfDay;
 	        		
 	        		$scope.selectedPartOfDays.push($scope.partOfDay);
 	        		jQuery('*[data-day="'+_partOfDay.date+'"]').find('*[data-daypart="'+_partOfDay.partOfDay+'"] span').addClass("selected");
		       	 });
 	        },500);
         }
         
         $scope.partOfDayClick = function(datum,partOfDay,$event){
         	$scope.partOfDay 			= new CustomPartOfDay();
         	$scope.partOfDay.date 		= datum;
         	$scope.partOfDay.partOfDay 	= partOfDay;
         	
         	var partOfDayAlreadyExists = false;
         	// If already exists, remove it!
         	angular.forEach($scope.selectedPartOfDays, function(selectedPartOfDay) {
    			if(selectedPartOfDay.date == $scope.partOfDay.date && selectedPartOfDay.partOfDay == $scope.partOfDay.partOfDay ){
    				console.log("VERWIJDEREN");
    				$scope.selectedPartOfDays.splice($scope.partOfDay,1);
    				jQuery($event.target).removeClass("selected");
    				partOfDayAlreadyExists = true;
    			}
	        });	
         	
         	// Doesn't exists, add it to the selected list
         	if(!partOfDayAlreadyExists){
         		$scope.selectedPartOfDays.push($scope.partOfDay);
             	jQuery($event.target).addClass("selected");
         	}
         	
         	console.log($scope.selectedPartOfDays);
         };
         
         
         $scope.getNextWeek = function(){
         	$scope.currentDate = moment($scope.currentDate).add(1,'weeks');
         	$scope.getPartOfDays();
         };
         
         $scope.getPreviousWeek = function(){
         	$scope.currentDate = moment($scope.currentDate).add(-1,'weeks');
         	$scope.getPartOfDays();
         }
         
         
         
         $scope.toggleHallOption = function toggleHallOption(hallOption) {
             var idx = $scope.selectedHallOptions.indexOf(hallOption);
             if (idx > -1) {
                 $scope.selectedHallOptions.splice(idx, 1);
             } else {
                 $scope.selectedHallOptions.push(hallOption);
             }
             console.log($scope.selectedHallOptions);
         };
         
         $scope.toggleHall = function toggleHall() {
        	 var response = JSON.parse($scope.hallReservation.hall);
        	 $scope.selectedHall = response.id ;
         };

         $scope.loadHallReservation();
         
    }).controller('HallReservationCreateController', function($scope, $state, $stateParams, HallReservation, HallOption, Hall, PartOfDays, CustomPartOfDay) {
        var responseHallOption = HallOption.get();
        responseHallOption.$promise.then(function(data) {
            $scope.hallOptions = data.data;

            // Remove @id
            angular.forEach($scope.hallOptions, function(option) {
                delete option["@id"];
            });
        });

        var responseHall = Hall.get();
        responseHall.$promise.then(function(data) {
            $scope.halls = data.data;
            // Remove @id
            angular.forEach($scope.halls, function(hall) {
                delete hall["@id"];
            });
        });
        
        $scope.selectedHall = 0;
        $scope.currentDate = moment(new Date()).add(-1,'weeks');
        $scope.currentWeek = moment($scope.currentDate).week();
        $scope.days = [];
        $scope.selectedPartOfDays = [];
        
        $scope.toggleHall = function toggleHall() {
       	 	var hall = JSON.parse($scope.hallReservation.hall);
       	 	$scope.selectedHall = hall.id;
       	 	
       	 	$scope.getPartOfDays();
        };
        
        $scope.partOfDayClick = function(datum,partOfDay,$event){
        	$scope.partOfDay 			= new CustomPartOfDay();
        	$scope.partOfDay.date 		= datum;
        	$scope.partOfDay.partOfDay 	= partOfDay;
        	
        	if($scope.selectedPartOfDays.length == 0){
        		$scope.selectedPartOfDays.push($scope.partOfDay);
            	jQuery($event.target).addClass("selected");
        	}else{
	        	angular.forEach($scope.selectedPartOfDays, function(selectedPartOfDay) {
	    			if(selectedPartOfDay.date == $scope.partOfDay.date && selectedPartOfDay.partOfDay == $scope.partOfDay.partOfDay ){
	    				$scope.selectedPartOfDays.splice(selectedPartOfDay,1);
	    				jQuery($event.target).removeClass("selected");
	    			}else{
	    				$scope.selectedPartOfDays.push($scope.partOfDay);
	                	jQuery($event.target).addClass("selected");
	    			}
		        });	
        	}
        };
        
        
        $scope.getNextWeek = function(){
        	$scope.currentDate = moment($scope.currentDate).add(1,'weeks');
        	$scope.getPartOfDays();
        };
        
        $scope.getPreviousWeek = function(){
        	$scope.currentDate = moment($scope.currentDate).add(-1,'weeks');
        	$scope.getPartOfDays();
        }
        
        $scope.getPartOfDays = function(){
        	$scope.currentWeek = moment($scope.currentDate).week();
        	
        	// Get partOfDays for hall.id and week
        	var response = PartOfDays.get({
        		hallid: $scope.selectedHall,
	            weeknmr: moment($scope.currentDate).week()
	        });
	        
	        response.$promise.then(function(data) {
	        	if (data.success) {
	        		$scope.days = [];
	        		angular.forEach(data.data, function(days) {
	        			$scope.days.push(days);
			        });	
	        	}
	        });
	        
	        jQuery("#cntrPartOfDay").fadeIn(200);
        }
        
        
        

        $scope.hallReservation = new HallReservation();

        $scope.addHallReservation = function() {
            $scope.hallReservation.hall = 1;
            $scope.hallReservation.partOfDays = $scope.selectedPartOfDays;
            $scope.hallReservation.hallOptions = [];
            jQuery("#hallReservationOptions input:checked").each(function() {
                $scope.hallReservation.hallOptions.push(JSON.parse(jQuery(this).val()).id);
                //console.log("HallOPTION id "+JSON.parse(jQuery(this).val()).id);
            });
            
            alert(JSON.stringify($scope.hallReservation));

            $scope.hallReservation.$save(function() {
            	 // Change object for API
	       		 //$scope.hallReservation.hall 		= $scope.selectedHall.id;
	       		 $scope.hallReservation.customer 	= 1;
            	
	       		 console.log($scope.hallReservation);
	       		 
                $state.go('hallReservations');
            });
        };
    }).controller('HallReservationViewController', function($scope, $stateParams, HallReservation, HallOption, Hall) {
    	$scope.language = function () {
            return i18n.language;
        };
        $scope.setLanguage = function (lang) {
            i18n.setLanguage(lang);
        };
        
        var response = HallReservation.get({
            id: $stateParams.id
        });
        response.$promise.then(function(data) {
            $scope.hallReservation = data.data;

            $scope.hallReservation.totalPrice = 0;
            $scope.hallReservation.totalPrice = $scope.hallReservation.hall.price;
            // Calculate totalPrice of hallReservation (with hallOptions)
            angular.forEach($scope.hallReservation.hallOptions, function(hallOption) {
                $scope.hallReservation.totalPrice = parseInt($scope.hallReservation.totalPrice) + parseInt(hallOption.price);
            })

        });

    }).controller('CustomerCreateController', function($scope,$state, $stateParams, Customer) {
    	$scope.customer = new Customer();

    	
        $scope.addCustomer = function() {
        	console.log($scope.customer);
        	
            $scope.customer.$save(function() {
                $state.go('viewCustomer');
            });
        };

    }).controller('CustomerViewController', function($scope,$state, $stateParams, Customer) {
        var response = Customer.get({
            id: $stateParams.id
        });
        response.$promise.then(function(data) {
            $scope.customer = data.data;
        });

    }).controller('AgendaController', function($scope, $compile, uiCalendarConfig, Agenda) {
        $scope.events = [];
        
        /* event source that calls a function on every view switch */
        $scope.eventsF = function (start, end, timezone, callback) {
        	var momentStart = moment(start);
        	var momentEnd = moment(end);
        	
        	// Get the agendaItems
    	    var response = Agenda.get({
	            start: momentStart.format('YYYY-MM-DD'),
	            end: momentEnd.format('YYYY-MM-DD')
	        });
	        
	        response.$promise.then(function(data) {
	        	if (data.success) {
	        		angular.forEach(data.data, function(item) {
	        			$scope.events.push({
				            title: item.description,
				            start: moment(item.startDate),
				            end: moment(item.endDate),
				            className: []
				        });	
	        		});
	        	}
	        });
        };
        
        /* Change View */
        $scope.changeView = function(view,calendar) {
          uiCalendarConfig.calendars[calendar].fullCalendar('changeView',view);
        };
        /* Change View */
        $scope.renderCalender = function(calendar) {
          if(uiCalendarConfig.calendars[calendar]){
            uiCalendarConfig.calendars[calendar].fullCalendar('render');
          }
        };
        /* config object */
        $scope.uiConfig = {
          calendar:{
            height: 300,
            defaultView: 'agendaWeek',
            editable: false,
            firstDay: 1,
            lang: 'nl',
			header: {
				left: 'prev,next today',
				center: 'title',
				right: 'month,agendaWeek,agendaDay'
			},
            eventClick: $scope.alertOnEventClick,
            eventDrop: $scope.alertOnDrop,
            eventResize: $scope.alertOnResize,
            eventRender: $scope.eventRender
          }
        };

        /* event sources array*/
        $scope.eventSources = [$scope.events, $scope.eventsF];
    });
