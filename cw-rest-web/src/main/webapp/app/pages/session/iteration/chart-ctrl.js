angular.module('iteration-module').controller('ChartCtrl', ['$scope', '$http', function ($scope, $http, $rootScope) {
    var HTTP_GET_ITERATIONS_URL = window.location.origin + '/cw-rest/session/rest/iteration/list';
    var HTTP_GET_STORIES_BY_ITERATION_URL = window.location.origin + '/cw-rest/session/rest/story/fetch';

    initChartData();

    $http.get(HTTP_GET_ITERATIONS_URL).then(function (response) {
        $scope.iterations = response.data;
    });

    $scope.$on("updateIterationChartData", function () {
        $http.get(HTTP_GET_ITERATIONS_URL).then(function (response) {
            $scope.iterations = response.data;
        });
    });

    $scope.$watch('iterations', function () {
        if($scope.iterations){
            initChartData();

            processIterations($scope.iterations);
            $scope.data = [$scope.days, $scope.points, $scope.stories];
        }
    });

    function initChartData(){
        $scope.series = ['Dias', 'Pontos', 'Estorias'];
        $scope.labels = [];
        $scope.data = [];
        $scope.days = [];
        $scope.points = [];
        $scope.stories = [];
    };


    function calcIterationDays(iteration) {
        var init = moment(new Date(iteration.init));
        var end = moment(new Date(iteration.end));
        $scope.days.push(moment.duration(end.diff(init)).asDays());
    };

    function processIterations(iterations) {
        var iteration = iterations.shift();

        calcStoryData(iteration, function () {
            $scope.labels.push(iteration.name);
            calcIterationDays(iteration);

            if (iterations.length > 0)
                processIterations(iterations);
        });

    }

    function calcStoryData(iteration, callback) {
        $http.get(HTTP_GET_STORIES_BY_ITERATION_URL, {
            params: {
                iterationName: iteration.name
            }
        }).then(function (response) {
            var storyPoints = 0;

            angular.forEach(response.data, function (story, key) {
                storyPoints += story.storyPoints;

            }, storyPoints);

            $scope.stories.push(response.data.length);
            $scope.points.push(storyPoints);

            if (callback)
                callback();
        });
    };
}]);
