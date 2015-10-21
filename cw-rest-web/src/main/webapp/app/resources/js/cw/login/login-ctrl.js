angular.module('cw-app').controller('LoginCtrl', ['$scope', '$http', '$window','$rootScope' , function ($scope, $http, $window, $rootScope) {

    $scope.alerts = [];
    $authentication_error_message = 'Login Invalido. Verifique os dados informados.';

    $HTTP_POST_URL = window.location.origin + '/cw-rest/session/rest/authentication/login';

    $scope.login = function(){
       $http.post($HTTP_POST_URL, {
            email: $scope.email,
            password: $scope.password

        }).then(function (response){
            if(response.data.status == 'SUCCESS'){
                $window.location.href = window.location.origin + '/cw-rest/app/pages/session/home.html'

            }else{
                $rootScope.$broadcast("loginErrorEvent");
            }

        }, function (response){
            console.log(response);
        });
    }
}]);
