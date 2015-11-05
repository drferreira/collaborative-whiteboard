angular.module('whiteboard-module', ['ui.bootstrap'])
.directive('cwWhiteboard', function() {
    return {
        restrict: 'E',
        templateUrl: 'app/resources/js/cw/whiteboard/whiteboard-template.html',
        controller : 'WhiteboardCtrl',
        scope: {
            cwUrlSource: '@'
        }
    }
})
.directive('cwWhiteboardStages', function() {
    return {
        restrict: 'E',
        templateUrl: 'app/resources/js/cw/whiteboard/stages-template.html',
        controller : 'WhiteboardStagesCtrl',
        scope: {
            cwStages: '='
        }
    };
})
.directive('cwWhiteboardTickets', function() {
    return {
        restrict: 'E',
        templateUrl: 'app/resources/js/cw/whiteboard/tickets-template.html',
        controller : 'WhiteboardTicketCtrl',
        scope: {
            cwStories: '=',
            lastStage: '=',
            firstStage: '='
        }
    };
});
