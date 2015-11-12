angular.module('cw-home').controller('HomeCtrl', ['$scope', '$http', '$window', '$rootScope', function ($scope, $filter) {

    $scope.urlSourceWhiteboard = 'ws://'+ document.location.host + '/cw-rest/whiteboard';

}]);
