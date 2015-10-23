angular.module('cw-app', ['ui.bootstrap'])
.directive('cwNavbar', function() {
    return {
        restrict: 'E',
        templateUrl: '../../resources/js/cw/directives/navbar/navbar-template.html'
    };
})
.directive('cwWhiteboard', function() {
    return {
        restrict: 'E',
        templateUrl: '../../resources/js/cw/directives/whiteboard/whiteboard-template.html'
    };
})
.directive('cwWhiteboardStages', function() {
    return {
        restrict: 'E',
        templateUrl: '../../resources/js/cw/directives/whiteboard/stages-template.html'
    };
})
.directive('cwWhiteboardTickets', function() {
    return {
        restrict: 'E',
        templateUrl: '../../resources/js/cw/directives/whiteboard/tickets-template.html'
    };
});
