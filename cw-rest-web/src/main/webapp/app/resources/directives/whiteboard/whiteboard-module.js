angular.module('whiteboard-module', [])
.directive('cwWhiteboard', function() {
    return {
        restrict: 'E',
        templateUrl: 'app/resources/directives/whiteboard/whiteboard-template.html',
        controller : 'WhiteboardCtrl',
        scope: {
            cwUrlSource: '@'
        }
    }
})
.directive('cwWhiteboardStages', function() {
    return {
        restrict: 'E',
        templateUrl: 'app/resources/directives/whiteboard/stages-template.html',
        controller : 'WhiteboardStagesCtrl',
        scope: {
            cwStages: '='
        }
    };
})
.directive('cwWhiteboardTickets', function() {
    return {
        restrict: 'E',
        templateUrl: 'app/resources/directives/whiteboard/tickets-template.html',
        controller : 'WhiteboardTicketCtrl',
        scope: {
            cwStories: '=',
            lastStage: '=',
            firstStage: '='
        }
    };
});
