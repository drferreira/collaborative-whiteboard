angular.module('cw-app').controller('IterationDataProgressCtrl', ['$scope', function ($scope) {
    $scope.iteration = {};

    $scope.iteration.daysRemaining = 10;

    $scope.iteration.finalizedTasks = 40;
    $scope.iteration.finalizedStories = 10;
    $scope.iteration.name = 'Iteracao 80';
    $scope.iteration.initDate = '10/10/19856';
    $scope.iteration.endDate = '30/10/19856';
}]);
