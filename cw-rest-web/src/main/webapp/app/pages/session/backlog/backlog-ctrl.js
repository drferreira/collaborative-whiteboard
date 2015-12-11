angular.module('backlog-module').controller('BacklogCtrl', ['$scope', '$http', '$window', '$rootScope', function ($scope, $http) {
    const HTTP_GET_OPEN_STORIES_URL = window.location.origin + '/cw-rest/session/rest/story/fetch/all';

    $scope.isReadOnly = true;
    $scope.showToolbar = false;
    $scope.isLoading = true;

    $http.get(HTTP_GET_OPEN_STORIES_URL).then(function (response) {
        $scope.stories = response.data;
        $scope.isLoading = false;
    });

    $scope.showToolbarElements = function ($event) {
        if($($event.currentTarget.children[0]).hasClass('hide')){
            $($event.currentTarget.children[0]).removeClass('hide')

        }else{
            $($event.currentTarget.children[0]).addClass('hide')
        }
    }

    $scope.showResume = function (story) {
    }

    $scope.showDetails = function (story) {
    }

    $scope.showTasks = function (story) {
    }

    $scope.showFiles = function (story) {
    }

    $scope.toolbarShow = function () {

    }
}]).filter('unsafe', function ($sce) {
    return $sce.trustAsHtml;
});
