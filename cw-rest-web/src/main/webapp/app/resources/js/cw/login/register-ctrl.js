angular.module('cw-app').controller('register-ctrl', ['$scope', '$http', '$window', function ($scope, $http, $window) {
	$scope.register = function(){
		console.log($scope.email_register);
		console.log($scope.first_name_register);
        console.log($scope.surname_register);
        console.log($scope.birthdate_register);
        console.log($scope.password_register);
	}
}]);

