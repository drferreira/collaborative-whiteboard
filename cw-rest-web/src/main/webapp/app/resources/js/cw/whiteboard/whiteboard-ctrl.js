angular.module('whiteboard-module').controller('WhiteboardCtrl', ['$scope', '$http', '$window', '$rootScope', function ($scope, $http, $window, $rootScope) {
    var websocket;

    $scope.connection = {};
    $scope.whiteboard = {};
    $scope.connection.available = false;

    websocket = new WebSocket($scope.cwUrlSource);

    websocket.onclose = function (evt) {
        $scope.$apply(function() {
            $scope.connection.available = false;
        })
    };

    websocket.onerror = function (evt) {
        $scope.$apply(function() {
            $scope.connection.available = false;
        })
    };

    websocket.onmessage = function (evt) {
        $scope.$apply(function() {
            $scope.connection.available = true;
            $scope.whiteboard.data = angular.fromJson(evt.data);
        })
    };

}]);
