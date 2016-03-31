angular.module('sessionvalidator', []).factory('SessionValidator', function() {
	
	return{
		getCookie: function(cname){
			var name = cname + "=";
		    var ca = document.cookie.split(';');
		    for(var i=0; i<ca.length; i++) {
		        var c = ca[i];
		        while (c.charAt(0)==' ') c = c.substring(1);
		        if (c.indexOf(name) == 0) return c.substring(name.length,c.length);
		    }
		    return "";
		},
		setCookie: function(cname, cvalue, exdays) {
		    var d = new Date();
		    d.setTime(d.getTime() + (exdays*24*60*60*1000));
		    var expires = "expires="+d.toUTCString();
		    document.cookie = cname + "=" + cvalue + "; " + expires;
		},
		isValidSession: function(){
			var sessionID 	= JSON.parse(this.getCookie("loginData")).sessionID;
			var requestData = "";
			jQuery.ajax({
        	    headers: { 
        	        'Accept': 'application/json',
        	        'Content-Type': 'application/json' 
        	    },
        	    async: false,
        	    'type': 'GET',
        	    'url': "http://localhost:8082/hh/rest/v1/login/"+sessionID,
        	    'dataType': 'json',
        	    'success': function(data){
        	    	requestData =  data.data;
    	    	}
    	    });
			return requestData;
		},
		logout: function(){
			this.setCookie("loginData","",-1);
			return true;
		},
		login: function(LoginRequest){
			var requestData = "";
			jQuery.ajax({
        	    headers: { 
        	        'Accept': 'application/json',
        	        'Content-Type': 'application/json' 
        	    },
        	    'type': 'POST',
        	    async: false,
        	    'url': "http://localhost:8082/hh/rest/v1/login",
        	    'data': JSON.stringify(LoginRequest),
        	    'dataType': 'json',
        	    'success': function(data){
        	    	requestData = data.data;
    	    	}
    	    });
			return requestData;
		},
		setLayoutForUser: function(role){
			// Wait till DOM-ready
			setTimeout(function(){
				// hide menu-items which not may be accessed
				console.log("MIJN ROL "+ role);
				
				//if(role == ""){
				//	jQuery("nav a[data-role]").parent().hide();
				//}else if(role == "customer"){
				//	jQuery("nav a[data-role='manager']").parent().hide();
				//}
				
				if(role == ""){
					jQuery("nav a[data-role]").parent().hide();
				}else{
					jQuery("nav a").each(function(){
						if(jQuery(this).data("role") != undefined && jQuery(this).data("role").toString().indexOf(role)<0){
							console.log("button role "+ jQuery(this).data("role"));
							jQuery(this).parent().hide();
						}else{
							jQuery(this).parent().show();
						}
					})
				}
				console.log(jQuery("ins[data-role]").data("role"));
				// check if user may access curent page
				if(jQuery("ins[data-role]").data("role") != null && jQuery("ins[data-role]").data("role").indexOf(role) <=0){
					//window.location = 'http://localhost:8082/hh';
				}
			},200);
		}
	}
});