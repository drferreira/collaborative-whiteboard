angular.module('cw-home').controller('IterationDataProgressCtrl', ['$scope', '$http', function ($scope, $http) {
    var HTTP_POST_URL = window.location.origin + '/cw-rest/session/rest/iteration/current/progress';


    $scope.formatTime = function (time){
        return new Date(time);
    }

    $http.get(HTTP_POST_URL).then(function (response) {
        if (response.data) {
            $scope.iteration = response.data;
        }
    });

}]);
