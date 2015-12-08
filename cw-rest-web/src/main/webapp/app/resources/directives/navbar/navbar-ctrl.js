angular.module('navbar-module').controller('NavbarCtrl', ['$scope', '$http', '$window', '$rootScope', function ($scope, $http, $window, $rootScope) {
    $HTTP_POST_URL = window.location.origin + '/cw-rest/session/rest/authentication/logout';


    angular.element('.active').removeClass('active');
    angular.element('#' + $scope.active).addClass('active');


    $scope.logout = function () {
        $http.post($HTTP_POST_URL).then(function (response) {
            if (response.data) {
                $window.location.href = window.location.origin + '/cw-rest/app/pages/index.html'

            }
        });
    }

    $scope.navigateToWhiteboard = function (){
        $window.location.href = window.location.origin + '/cw-rest/app/pages/session/home/index.html'
    }

    $scope.navigateToIterations = function (){
        $window.location.href = window.location.origin + '/cw-rest/app/pages/session/iteration/index.html'
    }

    $scope.navigateToBacklog = function (){
        $window.location.href = window.location.origin + '/cw-rest/app/pages/session/backlog/index.html'
    }
}]);