angular.module('cw-app').controller('MessagesCtrl', ['$scope', '$http', '$window', function ($scope, $http, $window) {
    $scope.alerts = [];
    $authentication_error_message = 'Login Invalido. Verifique os dados informados.';

    function addLoginErrorMessage(){
        $scope.alerts.pop();
        $scope.alerts.push({type:'warning', msg:$authentication_error_message});
    }

    $scope.$on("loginErrorEvent", function () {
        addLoginErrorMessage();
    });

    $scope.$on("clearMessagesEvent", function () {
        $scope.alerts = [];
    });
}]);
