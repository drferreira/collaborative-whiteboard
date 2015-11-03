angular.module('whiteboard-module').controller('WhiteboardTicketCtrl', ['$scope', '$http', '$window', '$rootScope', function ($scope, $http, $window, $rootScope) {


    $scope.formatTime = function (time) {
        return new Date(time);
    }

}]).filter('unsafe', function ($sce) {
    return $sce.trustAsHtml;
});
