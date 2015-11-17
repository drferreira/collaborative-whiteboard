angular.module('iteration-module').controller('IterationCtrl', ['$scope', '$http', '$window', '$rootScope', function ($scope, $http, $window, $rootScope) {
    var HTTP_GET_ITERATIONS_URL = window.location.origin + '/cw-rest/session/rest/iteration/list';
    var HTTP_GET_OPEN_STORIES_URL = window.location.origin + '/cw-rest/session/rest/story/fetch/open';
    var HTTP_GET_STORIES_BY_ITERATION_URL = window.location.origin + '/cw-rest/session/rest/story/fetch';

    loadIterationList();

    function loadIterationList() {
        $scope.fetchIterations = $http.get(HTTP_GET_ITERATIONS_URL).then(function (response) {
            return $scope.fetchIterations = response.data;
        });
    }

    $scope.onSelectIteration = function (iteration) {
        $scope.selectedIteration.available = true

        loadStoriesIntoIteration(iteration);
        loadOpenStories();

    };

    function loadStoriesIntoIteration(iteration) {
        $http.get(HTTP_GET_STORIES_BY_ITERATION_URL, {
            params: {
                iterationName: iteration.name
            }
        }).then(function (response) {
            $scope.selectedIteration.intoIteration = response.data;
            console.log(response.data);
        });

    }

    function loadOpenStories() {
        $http.get(HTTP_GET_OPEN_STORIES_URL).then(function (response) {
            $scope.selectedIteration.available = response.data;
            console.log(response.data);
        });
    }

    $scope.formatDate = function (date) {
        return new Date(date);
    }
}]).filter('unsafe', function ($sce) {
    return $sce.trustAsHtml;
});
