angular.module('iteration-module').controller('IterationCtrl', ['$scope', '$http', '$window', '$rootScope', function ($scope, $http, $window, $rootScope) {
    var HTTP_GET_ITERATIONS_URL = window.location.origin + '/cw-rest/session/rest/iteration/fetch/basicList';
    var HTTP_GET_FULL_DATA_ITERATION_URL = window.location.origin + '/cw-rest/session/rest/iteration/fetch';

    loadIterationBasicData();

    function loadIterationBasicData() {
        $scope.fetchIterations = $http.get(HTTP_GET_ITERATIONS_URL).then(function (response) {
            return $scope.fetchIterations = response.data;
        });
    }

    $scope.onSelectIteration = function (iteration) {
        $http.get(HTTP_GET_FULL_DATA_ITERATION_URL, {
            params: {
                iterationName: iteration.name
            }
        }).then(function (response) {
            $scope.selectedIteration.data = response.data;
        });
    };
}]);

