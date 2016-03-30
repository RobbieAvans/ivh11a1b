(function () {
    angular.module('autoActive', [])
        .directive('autoActive', ['$location','$state', function ($location,$state) {
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
                setActive();
                scope.$on('$locationChangeSuccess', setActive);
            }
        }
    }]);
}());