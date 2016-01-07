angular.module('backlog-module').controller('BacklogCtrl', ['$scope', '$http', '$window', '$rootScope', function ($scope, $http) {
    const HTTP_GET_OPEN_STORIES_URL = window.location.origin + '/cw-rest/session/rest/story/fetch/all';
    const HTTP_GET_FILES_STORIES_URL = window.location.origin + '/cw-rest/session/rest/story/files';
    const HTTP_GET_TASKS_STORIES_URL = window.location.origin + '/cw-rest/session/rest/story/tasks';
    const REMOVE_FILE_URL = window.location.origin + '';
    const DOWNLOAD_FILE_URL = window.location.origin + '';

    $scope.isReadOnly = true;
    $scope.showToolbar = false;
    $scope.isLoading = true;

    $scope.storyFilter = {
        status: {
            removed: false,
            waiting: false,
            available: false,
            in_analysis: false,
            analyzed: false,
            finalized: false,
        },

        text: {
            value: ''
        }
    };

    $scope.statusClass = function (story) {
        return 'color_' + story.currentStatusLog.storyStatus;
    };

    $http.get(HTTP_GET_OPEN_STORIES_URL).then(function (response) {
        $scope.stories = response.data;
        $scope.isLoading = false;
    });

    $scope.loadFiles = function (story) {
        if (!story.files) {
            $http.get(HTTP_GET_FILES_STORIES_URL, {
                params: {'storyCode': story.code}

            }).then(function (response) {
                story.files = response.data;
            });
        }
    };

    $scope.loadTasks = function (story) {
        if (!story.tasks) {
            $http.get(HTTP_GET_TASKS_STORIES_URL, {
                params: {'storyCode': story.code}

            }).then(function (response) {
                story.tasks = response.data;
            });
        }
    };

    $scope.colorRepeat = function (index) {
        if (!(index % 2)) {
            return 'list-group-item-info';
        }
    };

    $scope.filterStories = function (story) {
        if (filterStatus(story) && filterOpenTextArea(story)) {
            return true;
        }
    };

    $scope.formatTime = function (time) {
        return new Date(time);
    };

    $scope.iterationName = function (story){
        var iteration = story.iteration;

        if(iteration){
            return 'Iteração : ' + iteration.name;
        }
    };

    $scope.removeFile = function removeFile(story, file){
        $http.post(REMOVE_FILE_URL, {
            params: {'story': story, 'file' : file}

        }).then(function (response) {
            // REMOVER ARQUIVO DO JSON
        });
    };

    $scope.downloadFile = function downloadFile(story, file){
        $http.get(DOWNLOAD_FILE_URL, {
            params: {'story': story, 'file' : file}

        }).then(function (response) {
            // BAIXAR ARQUIVO ENCONTRADO
        });
    };

    function filterStatus(story) {
        for (property in $scope.storyFilter.status) {
            if ($scope.storyFilter.status[property]) {
                return $scope.storyFilter.status[story.currentStatusLog.storyStatus.toLowerCase()];
            } else {
                continue;
            }
        }

        return true;
    };

    function filterOpenTextArea(story) {
        var value = $scope.storyFilter.text.value;

        if (value) {
            return story.code.includes(value) || story.branch.includes(value) || story.subject.includes(value);

        } else {
            return true;
        }
    };

}]).filter('unsafe', function ($sce) {
    return $sce.trustAsHtml;
});
