angular.module('whiteboard-module').controller('WhiteboardCtrl', ['$scope', '$http', '$window', '$rootScope', function ($scope, $filter) {
    var websocket;

    $scope.connection = {};
    $scope.whiteboard = {};
    $scope.whiteboard.error = {};

    websocket = new WebSocket($scope.cwUrlSourceBind);

    websocket.onclose = function (evt) {
        $scope.$apply(function () {
            $scope.whiteboard.error.connection = true;
        })
    };

    websocket.onerror = function (evt) {
        $scope.$apply(function () {
            $scope.whiteboard.error.connection = true;
        })
    };

    websocket.onmessage = function (evt) {
        $scope.$apply(function () {
            $scope.whiteboard.data = angular.fromJson(evt.data);
            checkEmptyWhiteboardFlag($scope.whiteboard.data.stages);
        })
    };


    function checkEmptyWhiteboardFlag(stages){
        if(!stages.length){
            $scope.whiteboard.error.initialized = true;
        }else{
            $scope.whiteboard.error.initialized = false;
        }
    }
}]);
