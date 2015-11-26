angular.module('iteration-module').controller('ChartCtrl', ['$scope', '$http', function ($scope, $http, $rootScope) {
    var HTTP_GET_ITERATIONS_URL = window.location.origin + '/cw-rest/session/rest/iteration/list';
    var HTTP_GET_STORIES_BY_ITERATION_URL = window.location.origin + '/cw-rest/session/rest/story/fetch';


    $scope.$on("updateIterationChartData", function () {
        populateChart();
    });

    function populateChart(){
        $scope.days = [];
        $scope.points = [];
        $scope.stories = [];
        $scope.labels = [];
        $scope.data = [];
        $scope.series = ['Dias', 'Pontos', 'Estorias'];

        $http.get(HTTP_GET_ITERATIONS_URL).then(function (response) {
            angular.forEach(response.data, function(iteration, key) {
                $scope.labels.push(iteration.name);
                calcIterationDays(iteration);
                calcStoryData(iteration);
            });

            $scope.data = [$scope.days, $scope.points, $scope.stories];
        });
    }


    function calcIterationDays(iteration){
        var init = moment(new Date(iteration.init));
        var end = moment(new Date(iteration.end));
        $scope.days.push(moment.duration(end.diff(init)).asDays());
    };

    function calcStoryData(iteration){
        var storyPoints = 0;

        $http.get(HTTP_GET_STORIES_BY_ITERATION_URL, {
            params: {
                iterationName: iteration.name
            }
        }).then(function (response) {
            var stories = response.data;
            $scope.stories.push(stories.length);

            angular.forEach(stories, function (value, key) {
                storyPoints = storyPoints + value.storyPoints;

            }, storyPoints);

            $scope.points.push(storyPoints);
        });
    };

    populateChart();
}]);
