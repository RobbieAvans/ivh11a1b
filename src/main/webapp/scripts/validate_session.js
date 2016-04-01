angular.module('sessionvalidator', []).factory('SessionValidator', function(cfg) {
	
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
        	    'url': "http://localhost:"+cfg.port+"/hh/rest/v1/login/"+sessionID,
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
        	    'url': "http://localhost:"+cfg.port+"/hh/rest/v1/login",
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
				
				if(role == ""){
					jQuery("nav a[data-role]").parent().hide();
				}else{
					jQuery("nav a").each(function(){
						if(jQuery(this).data("role") != undefined && jQuery(this).data("role").toString().indexOf(role)<0){
							jQuery(this).parent().hide();
						}else{
							jQuery(this).parent().show();
						}
					})
				}
			},200);
		}
	}
});