angular.module('navbar-module', ['ui.bootstrap'])
.directive('cwNavbar', function() {
    return {
        restrict: 'E',
        templateUrl: 'app/resources/js/cw/navbar/navbar-template.html'
    };
});
