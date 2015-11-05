angular.module('whiteboard-module').controller('WhiteboardTicketCtrl', ['$scope', '$http', '$window', '$rootScope', function ($scope, $http, $window, $rootScope) {

    var $HTTP_POST_URL = window.location.origin + '/cw-rest/session/rest/whiteboard/task/';

    $scope.formatTime = function (time) {
        return new Date(time);
    }

    $scope.isDontLastStage = function () {
        return !$scope.lastStage;
    }

    $scope.isLastStage = function () {
        return $scope.lastStage;
    }

    $scope.inUse = function (task) {
        return angular.equals(task.taskStatus.value, 'BUSY');
    }

    $scope.available = function (task) {
        return angular.equals(task.taskStatus.value, 'AVAILABLE');
    }

    $scope.isFinalized = function (task) {
        return angular.equals(task.taskStatus.value, 'FINALIZED');
    }

    $scope.availableToFinalize = function (task) {
        return (!$scope.isFinalized(task)) && ($scope.isLastStage(task)) && ($scope.inUse(task));
    }


    $scope.isNotFirstStage = function () {
        return !$scope.firstStage;
    }

    $scope.play = function (task) {
        $http.post($HTTP_POST_URL + 'play', task).then(function (response) {

        }, function (response) {
        });
    }

    $scope.stop = function (task) {
        $http.post($HTTP_POST_URL + 'stop', task).then(function (response) {

        }, function (response) {
        });
    }

    $scope.goForward = function (task) {
        $http.post($HTTP_POST_URL + 'goForward', task).then(function (response) {

        }, function (response) {
        });
    }

    $scope.goBack = function (task) {
        $http.post($HTTP_POST_URL + 'goBack', task).then(function (response) {

        }, function (response) {
        });
    }

    $scope.finalize = function (task) {
        $http.post($HTTP_POST_URL + 'finalize', task).then(function (response) {

        }, function (response) {
        });
    }
}]).filter('unsafe', function ($sce) {
    return $sce.trustAsHtml;
});
