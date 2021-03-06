angular.module('bestellenApp.controllers', [])
	.controller('MainController', function($scope, $rootScope, $state, $window, Hall,i18n, Customer, LoginRequest,SessionValidator) {

		
		// Logout method
		$scope.logout = function(){
			if(SessionValidator.logout()){
				$state.go('login');
			}
		}
		
	}).controller('LoginController', function($scope,$rootScope,$state, $stateParams, Customer, LoginRequest,SessionValidator) {
       
        $scope.login = function() {
        	if(SessionValidator.login($scope.loginRequest) != "login_fail"){
        		SessionValidator.setCookie("loginData",JSON.stringify(SessionValidator.login($scope.loginRequest)),15);
        		SessionValidator.setLayoutForUser($rootScope.role);
        		$state.go('hallReservations');
        		
        	}else{
        		var loginMessage = $.i18n.prop("label_login_fail");
        		jQuery(".message").text(loginMessage).fadeIn();
        	}
        };

    }).controller('HallListController', function($scope,$rootScope, $state, $window, Hall) {
        var response = Hall.get({sessionid: $rootScope.sessionID});
        response.$promise.then(function(data) {
        	if(data.success){
        		$scope.halls = data.data;
        	}else{
        		alert("niet correct geautoriseerd");
        	}
        });

        $scope.deleteHall = function(hall) {
            Hall.delete({
                id: hall.id,
                sessionid: $rootScope.sessionID
            }, function() {
                $window.location.reload();
            });
        };
    }).controller('HallViewController', function($scope,$rootScope, $stateParams, Hall) {
        var response = Hall.get({
            id: $stateParams.id,
            sessionid: $rootScope.sessionID
        });
        response.$promise.then(function(data) {
            $scope.hall = data.data;
        });

    }).controller('HallCreateController', function($scope,$rootScope, $state, $stateParams, Hall) {
        $scope.hall = new Hall();

        $scope.addHall = function() {
            $scope.hall.$save({sessionid: $rootScope.sessionID},function() {
                $state.go('halls');
            });
        };
    }).controller('HallEditController', function($scope,$rootScope, $state, $stateParams, Hall) {
        $scope.updateHall = function() {
            Hall.update({
                id: $scope.hall.id,
                sessionid: $rootScope.sessionID
            }, $scope.hall, function() {
                $state.go('halls');
            });
        };

        $scope.loadHall = function() {
            var response = Hall.get({
                id: $stateParams.id,
                sessionid: $rootScope.sessionID
            });
            response.$promise.then(function(data) {
                $scope.hall = data.data;
            });
        };

        $scope.loadHall();
    }).controller('HallOptionListController', function($scope,$rootScope, $state, $window, HallOption) {
        var response = HallOption.get({sessionid: $rootScope.sessionID});
        response.$promise.then(function(data) {
            $scope.hallOptions = data.data;
        });

        $scope.deleteHallOption = function(hallOption) {
            HallOption.delete({
                id: hallOption.id,
                sessionid: $rootScope.sessionID
            }, function() {
                $window.location.reload();
            });
        };

    }).controller('HallOptionEditController', function($scope,$rootScope, $state, $stateParams, HallOption) {
        $scope.updateHallOption = function() {
            HallOption.update({
                id: $scope.hallOption.id,
                sessionid: $rootScope.sessionID
            }, $scope.hallOption, function() {
                $state.go('hallOptions');
            });
        };

        $scope.loadHallOption = function() {
            var response = HallOption.get({
                id: $stateParams.id,
                sessionid: $rootScope.sessionID
            });
            response.$promise.then(function(data) {
                $scope.hallOption = data.data;
            });
        };

        $scope.loadHallOption();

    }).controller('HallOptionCreateController', function($scope,$rootScope, $state, $stateParams, HallOption) {
        $scope.hallOption = new HallOption();

        $scope.addHallOption = function() {
            $scope.hallOption.$save({sessionid: $rootScope.sessionID},function() {
                $state.go('hallOptions');
            });
        };
    }).controller('HallOptionViewController', function($scope,$rootScope, $stateParams, HallOption) {
        var response = HallOption.get({
            id: $stateParams.id,
            sessionid: $rootScope.sessionID
        });
        response.$promise.then(function(data) {
            $scope.hallOption = data.data;
        });

    }).controller('HallReservationListController', function($scope,$rootScope, $state, $window, HallReservation, HallOption) {
        var responseHallOption = HallOption.get({sessionid: $rootScope.sessionID});
        responseHallOption.$promise.then(function(data) {
            $scope.hallOptions = data.data;
        });

        var responseHallReservation = HallReservation.get({sessionid: $rootScope.sessionID});
        responseHallReservation.$promise.then(function(data) {
        	if(data.success){
	            $scope.hallReservations = data.data;
	            // Only a manager may directly remove a HallReservation
	            setTimeout(function(){
	            	if($rootScope.role != "manager"){
		            	jQuery(".btn.btn-danger.btn-verwijderen").attr("style","display:none");
		            }
	            },10);
        	}

        });
        
       

        $scope.deleteHallReservation = function(hallReservation) {
            HallReservation.delete({
                id: hallReservation.id,
                sessionid: $rootScope.sessionID
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
        };
    }).controller('HallReservationEditController', function($scope,$rootScope, $stateParams, $state, $window, HallReservation, HallOption, Hall,PartOfDays, CustomPartOfDay,i18n) {
        var responseHallOption = HallOption.get({sessionid: $rootScope.sessionID});
        responseHallOption.$promise.then(function(data) {
            $scope.hallOptions = data.data;
        });
        
        $scope.selectedHallOptions 	= [];
        $scope.selectedHall = 0;
        $scope.selectedCustomer = 0;
        
        var responseHall = Hall.get({sessionid: $rootScope.sessionID});
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
    		 
    		 // selectedCustomer has been set by toggleCustomer so
             if($scope.selectedCustomer != 0){
             	// Current sessionID is a manager, get customerDropdownValue
             	$scope.hallReservation.customer = $scope.selectedCustomer;
             }else{
             	// Current sessionID is a customer, get the ID
             	$scope.hallReservation.customer = $rootScope.userID;
             }
    		 
             // Remove @id
    		 delete $scope.hallReservation["@id"];
   		     		 
    		 $scope.hallReservation.hallOptions = $scope.selectedHallOptions;
    		 $scope.hallReservation.partOfDays 	= $scope.selectedPartOfDays;
    		 
             HallReservation.update({
            	 id: $scope.hallReservation.id,
            	 sessionid: $rootScope.sessionID
             }, $scope.hallReservation, function(data) {
	        	 if(data.success){
	        		 $state.go('hallReservations');
	        	 }else{
	        		 var errorMessage = $.i18n.prop("label_"+data.data);
	        		 jQuery(".message").text(errorMessage).fadeIn();
	        	 }
             });
         };

         $scope.loadHallReservation = function() {
             var response = HallReservation.get({
                 id: $stateParams.id,
                 sessionid: $rootScope.sessionID
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
         
         
         $scope.currentDate = moment(new Date()).add(-1,'weeks');
         $scope.currentWeek = moment($scope.currentDate).week();
         $scope.days = [];
         $scope.selectedPartOfDays = [];
         
         $scope.getPartOfDays = function(){
         	$scope.currentWeek = moment($scope.currentDate).week();
         	
         	// Get partOfDays for hall.id and week
         	var response = PartOfDays.get({
         		hallid: $scope.selectedHall,
 	            weeknmr: moment($scope.currentDate).week(),
 	            sessionid: $rootScope.sessionID
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
 	        		jQuery('*[data-day="'+_partOfDay.date+'"]').find('*[data-daypart="'+_partOfDay.partOfDay+'"] span').addClass("oldDate selected");
 	        		jQuery('*[data-day="'+_partOfDay.date+'"]').find('*[data-daypart="'+_partOfDay.partOfDay+'"] span').text("oude data");
		       	 });
 	        },500);
         }
         
         $scope.partOfDayClick = function(datum,partOfDay,$event){
        	if(jQuery($event.target).hasClass("vrij") || jQuery($event.target).hasClass("oldDate")){
         	$scope.partOfDay 			= new CustomPartOfDay();
         	$scope.partOfDay.date 		= datum;
         	$scope.partOfDay.partOfDay 	= partOfDay;
         	
         	var partOfDayAlreadyExists = false;
         	// If already exists, remove it!
         	angular.forEach($scope.selectedPartOfDays, function(selectedPartOfDay) {
    			if(selectedPartOfDay.date == $scope.partOfDay.date && selectedPartOfDay.partOfDay == $scope.partOfDay.partOfDay ){
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
         
         
         
         $scope.toggleHallOption = function toggleHallOption(hallOption) {
             var idx = $scope.selectedHallOptions.indexOf(hallOption);
             if (idx > -1) {
                 $scope.selectedHallOptions.splice(idx, 1);
             } else {
                 $scope.selectedHallOptions.push(hallOption);
             }
         };
         
         $scope.toggleHall = function toggleHall() {
        	 var response = JSON.parse($scope.hallReservation.hall);
        	 $scope.selectedHall = response.id ;
        	 $scope.getPartOfDays();
         };
         
         $scope.toggleCustomer = function toggleCustomer() {
        	 	var customer = JSON.parse($scope.hallReservation.customer);
        	 	$scope.selectedCustomer = customer;
         };

         $scope.loadHallReservation();
         
    }).controller('HallReservationCreateController', function($scope,$rootScope,SessionValidator, $state, $stateParams, HallReservation,HallOption, Hall, PartOfDays,Customer, CustomPartOfDay,i18n) {
        var responseHallOption = HallOption.get({sessionid: $rootScope.sessionID});
        responseHallOption.$promise.then(function(data) {
            $scope.hallOptions = data.data;

            // Remove @id
            angular.forEach($scope.hallOptions, function(option) {
                delete option["@id"];
            });
        });
        
        var responseCustomer = Customer.get({sessionid: $rootScope.sessionID});
        responseCustomer.$promise.then(function(data) {
            $scope.allCustomers = data.data;
        });

        
        if($rootScope.role =="manager"){
        	setTimeout(function(){
        		jQuery("#hiddenCustomer").fadeIn(10);
        	},200)
        	 
        }

        var responseHall = Hall.get({sessionid: $rootScope.sessionID});
        responseHall.$promise.then(function(data) {
            $scope.halls = data.data;
            // Remove @id
            angular.forEach($scope.halls, function(hall) {
                delete hall["@id"];
            });
        });
        
        $scope.selectedHall = 0;
        $scope.selectedCustomer = 0;
        $scope.currentDate = moment(new Date()).add(-1,'weeks');
        $scope.currentWeek = moment($scope.currentDate).week();
        $scope.days = [];
        $scope.selectedPartOfDays = [];
        
        $scope.toggleCustomer = function toggleCustomer() {
       	 	var customer = JSON.parse($scope.hallReservation.customer);
       	 	$scope.selectedCustomer = customer;
        };
        
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
	            weeknmr: moment($scope.currentDate).week(),
	            sessionid: $rootScope.sessionID
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
            $scope.hallReservation.hall = $scope.selectedHall;
            $scope.hallReservation.partOfDays = $scope.selectedPartOfDays;
            $scope.hallReservation.hallOptions = [];
            
            // selectedCustomer has been set by toggleCustomer so
            if($scope.selectedCustomer != 0){
            	// Current sessionID is a manager, get customerDropdownValue
            	$scope.hallReservation.customer = $scope.selectedCustomer;
            }else{
            	// Current sessionID is a customer, get the ID
            	$scope.hallReservation.customer = $rootScope.userID;
            }
            
            jQuery("#hallReservationOptions input:checked").each(function() {
                $scope.hallReservation.hallOptions.push(JSON.parse(jQuery(this).val()).id);
            });

            $scope.hallReservation.$save({sessionid: $rootScope.sessionID},function(data) {
            	if(data.success){
            		$state.go('hallReservations');
            	}else{
   	        		 var errorMessage = $.i18n.prop("label_"+data.data);
   	        		 jQuery(".message").text(errorMessage).fadeIn();
            	}

            });
        };
    }).controller('HallReservationViewController', function($scope,$rootScope,$state, $stateParams, HallReservation, HallOption, Hall,i18n,cfg) {
    	$scope.language = function () {
            return i18n.language;
        };
        $scope.setLanguage = function (lang) {
            i18n.setLanguage(lang);
        };
        
        $scope.performAction = function(action){
        	
        	jQuery.ajax({
        	    headers: { 
        	        'Accept': 'application/json',
        	        'Content-Type': 'application/json' 
        	    },
        	    'type': 'PUT',
        	    async: false,
        	    'url': "http://localhost:"+cfg.port+"/hh/rest/v1/hallReservation/"+$scope.hallReservation.id+"/"+action+"/"+ $rootScope.sessionID,
        	    'dataType': 'json',
        	    'success': function(data){
        	    	if(data.success){
        	    		var errorMessage = "Wijziging doorgevoerd.";
        	    		jQuery(".message").removeClass("error").addClass("success").text(errorMessage).fadeIn();
    	    			$scope.hallReservation = data.data;
    	    			$scope.replaceActionLabels();
    	    			setTimeout(function(){
    	    				jQuery(".message").fadeOut();
    	    				if(($scope.hallReservation.state == "CANCELLED" && action == "confirm") || ($scope.hallReservation.state == "PAID" && action == "pay") ){
    	    					$state.go("hallReservations");
    	    				}
        	    		},2500);
        	    	}else{
        	    		var errorMessage = $.i18n.prop("label_"+data.data);
        	    		jQuery(".message").removeClass("success").addClass("error").text(errorMessage).fadeIn();
       	        		setTimeout(function(){
    	    				jQuery(".message").fadeOut();
        	    		},2500);
        	    	}
    	    	}
    	    });
        }
        
        $scope.replaceActionLabels = function(){
        	var actionCount = 0;
        	angular.forEach($scope.hallReservation.actions, function(action) {
        		var btnLabel = $.i18n.prop("label_"+$scope.hallReservation.state + "_"+action);
        		jQuery("#actionContainer msg:eq("+actionCount+")").text(btnLabel);
        		actionCount++;
        	});
        }
        
        
        var response = HallReservation.get({
            id: $stateParams.id,
            sessionid: $rootScope.sessionID
        });
        response.$promise.then(function(data) {
            $scope.hallReservation = data.data;
            // Only if a hallReservation isn't final & isn't paid customers may edit it
            setTimeout(function(){
            	if(!$scope.hallReservation.canbemodified){
	            	jQuery(".btn-wijzigen").attr("style","display:none");
	            }
            },10);
        });

    }).controller('CustomerCreateController', function($scope,$rootScope,$state, $stateParams, Customer) {
    	$scope.customer = new Customer();
        $scope.addCustomer = function() {
            $scope.customer.$save(function() {
            	$state.go('login');
            });
        };

    }).controller('CustomerViewController', function($scope,$rootScope,$state, $stateParams, Customer) {
        var response = Customer.get({
            id: $rootScope.userID,
            sessionid: $rootScope.sessionID
        });
        response.$promise.then(function(data) {
            $scope.customer = data.data;
        });

    }).controller('AgendaController', function($scope,$rootScope, $compile, uiCalendarConfig, Agenda) {
        $scope.events = [];
        
        /* event source that calls a function on every view switch */
        $scope.eventsF = function (start, end, timezone, callback) {
        	var momentStart = moment(start);
        	var momentEnd = moment(end);
        	
        	// Get the agendaItems
    	    var response = Agenda.get({
	            start: momentStart.format('YYYY-MM-DD'),
	            end: momentEnd.format('YYYY-MM-DD'),
	            sessionid: $scope.sessionID
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
