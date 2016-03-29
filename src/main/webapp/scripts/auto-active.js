(function () {
    angular.module('autoActive', [])
        .directive('autoActive', ['$location', function ($location) {
        return {
            restrict: 'A',
            scope: false,
            link: function (scope, element) {
                function setActive() {
                    var path = $location.path();
                    if (path) {
                        angular.forEach(element.find('aside.item a'), function (li) {
                            var anchor = li;
                            if (anchor.href.indexOf(path) > -1) {
                                angular.element(li).parent().addClass('active');
                            } else {
                                angular.element(li).parent().removeClass('active');
                            }
                        });
                    }
                }
                function validateROLE() {
                	setTimeout(function(){
                		// Validate logindata
                		if(getCookie("loginData") !=""){
                			var loginData = JSON.parse(getCookie("loginData"));
                			if(jQuery(".pageROLE").text() != "" && jQuery(".pageROLE").text() != loginData.data.role ){
                				alert("Deze pagina mag je niet bekijken");
                				window.location = "/hh/static/";
                			}
                		}
                	},150);
                }
                
                function getCookie(cname) {
        		    var name = cname + "=";
        		    var ca = document.cookie.split(';');
        		    for(var i=0; i<ca.length; i++) {
        		        var c = ca[i];
        		        while (c.charAt(0)==' ') c = c.substring(1);
        		        if (c.indexOf(name) == 0) return c.substring(name.length,c.length);
        		    }
        		    return "";
        		}

                setActive();
                validateROLE();

                scope.$on('$locationChangeSuccess', setActive);
                scope.$on('$locationChangeSuccess', validateROLE);
            }
        }
    }]);
}());