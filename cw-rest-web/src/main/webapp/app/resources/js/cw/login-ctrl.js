angular.module('cw-app',['ui.bootstrap']).controller('login-ctrl', ['$scope', '$http', '$window', function ($scope, $http, $window) {
    $scope.alerts = [];
    $authentication_error_message = 'Login Invalido. Verifique os dados informados.'

    $scope.login = function(){

        $http.post(window.location.origin + '/cw-rest/session/rest/authentication/login', {
            email: $scope.email,
            password: $scope.password

        }).then(function (response){
            if(response.data.status == 'SUCCESS'){
                $window.location.href = window.location.origin + '/cw-rest/app/pages/session/home.html'

            }else{
                $scope.alerts.pop();
                $scope.alerts.push({type:'danger', msg:$authentication_error_message});
            }

        }, function (response){
            console.log(response);

        });
    }
}]);
