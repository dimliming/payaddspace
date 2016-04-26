'use strict';

/* Controllers */
  // index controller
app.controller('IndexController', ['$scope', '$http', '$state', function($scope, $http, $state) {
    console.log("check has login");
	/*$http.get('api/login').then(function(response) {
        if ( !response.data.user ) {
          $scope.authError = 'Email or Password not right';
          $state.go('papp.login');
        }
      }, function(x) {
        $scope.authError = x;
        $state.go('papp.login');
      });*/
  }])
;