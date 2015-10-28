angular.module('whiteboard-module').controller('WhiteboardTicketCtrl', ['$scope', '$http', '$window', '$rootScope', function ($scope, $http, $window, $rootScope) {


}]).filter('unsafe', function($sce) {
    return $sce.trustAsHtml;
});
