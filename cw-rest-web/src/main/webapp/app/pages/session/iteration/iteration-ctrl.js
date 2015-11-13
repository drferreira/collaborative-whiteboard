angular.module('iteration-module').controller('IterationCtrl', ['$scope', '$http', '$window', '$rootScope', function ($scope, $http, $window, $rootScope) {
    var HTTP_GET_ITERATIONS_URL = window.location.origin + '/cw-rest/session/rest/iteration/list';
    var HTTP_GET_OPEN_STORIES_URL = window.location.origin + '/cw-rest/session/rest/backlog/fetch/open';
    var HTTP_GET_STORIES_BY_ITERATION_URL = window.location.origin + '/cw-rest/session/rest/backlog/fetch';

    loadIterationList();

    function loadIterationList() {
        $scope.fetchIterations = $http.get(HTTP_GET_ITERATIONS_URL).then(function (response) {
            return $scope.fetchIterations = response.data;
        });
    }

    $scope.onSelectIteration = function (iteration) {
        $scope.selectedIteration.available = true

        loadStoriesToSelectedIteration(iteration);
        loadOpenStories();

    };

    function loadStoriesToSelectedIteration(iteration) {
        $http.get(HTTP_GET_STORIES_BY_ITERATION_URL, {
            params: {
                iterationName: iteration.name
            }
        }).then(function (response) {
            $scope.selectedIteration.stories = response.data;
        });

    }

    function loadOpenStories() {
        $http.get(HTTP_GET_OPEN_STORIES_URL).then(function (response) {
            $scope.selectedIteration.stories.available = response.data;
        });
    }

    $scope.formatDate = function (date) {
        return new Date(date);
    }
}]);

