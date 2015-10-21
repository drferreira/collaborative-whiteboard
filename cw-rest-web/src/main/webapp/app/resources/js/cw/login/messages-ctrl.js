angular.module('cw-app').controller('MessagesCtrl', ['$scope', '$http', '$window', function ($scope, $http, $window) {
    $scope.alerts = [];

    function addLoginErrorMessage(){
        $scope.alerts.pop();
        $scope.alerts.push({type:'warning', msg:$authentication_error_message});
    }

    $scope.$on("loginErrorEvent", function () {
        addLoginErrorMessage();
    });
}]);
