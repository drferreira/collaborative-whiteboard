angular.module('navbar-module').controller('NavbarCtrl', ['$scope', '$http', '$window', '$rootScope', function ($scope, $http, $window, $rootScope) {
    $HTTP_POST_URL = window.location.origin + '/cw-rest/session/rest/authentication/logout';

    $scope.logout = function () {

        $http.post($HTTP_POST_URL).then(function (response) {
            if (response.data) {
                $window.location.href = window.location.origin + '/cw-rest/app/pages/index.html'

            }
        });
    }
}]);