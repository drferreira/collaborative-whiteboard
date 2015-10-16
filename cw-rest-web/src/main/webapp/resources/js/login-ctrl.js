angular.module('cw-app').controller('login-ctrl', ['$scope', '$http', '$window', function ($scope, $http, $window) {
	$scope.login = function(){
		console.log($scope.email);
		console.log($scope.password);
	}
}]);
