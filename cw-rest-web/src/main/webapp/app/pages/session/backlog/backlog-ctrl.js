angular.module('backlog-module').controller('BacklogCtrl', ['$scope', '$http', '$window', '$rootScope', function ($scope, $http) {
    const HTTP_GET_OPEN_STORIES_URL = window.location.origin + '/cw-rest/session/rest/story/fetch/all';
    const HTTP_GET_FILES_STORIES_URL = window.location.origin + '/cw-rest/session/rest/story/files';
    const HTTP_GET_TASKS_STORIES_URL = window.location.origin + '/cw-rest/session/rest/story/tasks';

    $scope.isReadOnly = true;
    $scope.showToolbar = false;
    $scope.isLoading = true;

    $http.get(HTTP_GET_OPEN_STORIES_URL).then(function (response) {
        $scope.stories = response.data;
        $scope.isLoading = false;
    });

    $scope.showToolbarElements = function ($event) {
        if ($($event.currentTarget.children[0]).hasClass('hide')) {
            $($event.currentTarget.children[0]).removeClass('hide')

        } else {
            $($event.currentTarget.children[0]).addClass('hide')
        }
    }

    $scope.loadFiles = function (story) {
        if (!story.files) {
            $http.get(HTTP_GET_FILES_STORIES_URL, {
                params: {'storyCode': story.code}

            }).then(function (response) {
                story.files = response.data;
            });
        }
    }

    $scope.loadTasks = function (story) {
        if (!story.tasks) {
            $http.get(HTTP_GET_TASKS_STORIES_URL, {
                params: {'storyCode': story.code}

            }).then(function (response) {
                story.tasks = response.data;
            });
        }
    }

    $scope.colorRepeat = function (index) {
        if (!(index % 2)) {
            return 'list-group-item-info';
        }
    }

    $scope.showResume = function (story) {
    }

    $scope.showDetails = function (story) {
    }

    $scope.toolbarShow = function () {

    }

    $scope.filterStories = function (story){
        var filter = $scope.storyFilter;
        var filterResult = true;

        angular.forEach(filter, function (value, key) {
            if(story.currentStatusLog.storyStatus.toLowerCase() == key.toLowerCase()){
                 filterResult = value;
            }
        });

        // TODO BUG AQUI
        return filterResult;
    }
}]).filter('unsafe', function ($sce) {
    return $sce.trustAsHtml;
});
