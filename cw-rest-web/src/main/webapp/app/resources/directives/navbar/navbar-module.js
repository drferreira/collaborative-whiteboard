angular.module('navbar-module', ['ui.bootstrap'])
.directive('cwNavbar', function() {
    return {
        restrict: 'E',
        templateUrl: 'app/resources/directives/navbar/navbar-template.html',
        controller: 'NavbarCtrl',
        scope: {
            active: '@'
        }
    };
});
