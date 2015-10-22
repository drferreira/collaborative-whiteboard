angular.module('cw-app').controller('RegisterCtrl', ['$scope', '$http', '$window', '$rootScope', function ($scope, $http, $window, $rootScope) {
    $HTTP_POST_URL_VALIDATE = window.location.origin + '/cw-rest/session/rest/user/validate';
    $HTTP_POST_URL_CREATE = window.location.origin + '/cw-rest/session/rest/user/create';

    $scope.register = function(){
        $http.post($HTTP_POST_URL_CREATE, $scope.user).then(function (response){
            if(response.data){
                $window.location.href = window.location.origin + '/cw-rest/app/pages/session/home.html'
            }
        });
    }

    $scope.validateEmail = function(){
        $rootScope.$broadcast("clearMessagesEvent");

        $http.post($HTTP_POST_URL_VALIDATE, $scope.user).then(function (response){
            $scope.emailInUse = !response.data;
            $scope.register_form.emailRegister.$setValidity('emailInUse', response.data);
            $scope.register_form.$setValidity('emailInUse', response.data);
        });
    }

    $scope.matchPassword = function(){
        if($scope.user.passwordRegister != $scope.passwordConfirmationRegister){
            if($scope.passwordRegister && $scope.passwordConfirmationRegister){
                $scope.register_form.$setValidity('passwordMatch', false);
            }
        }
    }
}]);

