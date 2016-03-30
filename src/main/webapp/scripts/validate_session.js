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
			if(this.getCookie("loginData") != null){
				return true;
			}else{
				return false;
			}
		},
		setLayoutForUser: function(){
			// hide menu-items which not may be accessed
			if(JSON.parse(this.getCookie("loginData")).data.role == "customer"){
				jQuery("nav a[data-role='manager']").parent().hide();
			}
			
			console.log(jQuery("ins[data-role]").data("role"));
			// check if user may access curent page
			if(jQuery("ins[data-role]").data("role") != null && jQuery("ins[data-role]").data("role").indexOf(JSON.parse(this.getCookie("loginData")).data.role) <=0){
				window.location = 'http://localhost:8082/hh';
			}
		}
	}
});