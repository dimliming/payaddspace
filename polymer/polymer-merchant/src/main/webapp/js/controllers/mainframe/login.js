'use strict';

/* Controllers */
  // signin controller
app.controller('LoginFormController', ['$scope','$rootScope', '$http', '$state', function($scope,$rootScope, $http, $state) {
    $scope.user = {};
    $scope.authError = null;
    
    $scope.login = function() {
      $scope.authError = null;
      // Try to login
      var param = $.param($scope.user);
      $http.post('system/login', param,{headers: { "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8"}})
      .then(function(response) {
    	  if (response.data.status=='000000'){//登录成功
    		  $rootScope.user = response.data.user;
    		  $state.go('papp.index');
    	  }else{//登录失败
    		  $scope.authError = response.data.message;
    	  }
      },function(x) {
        $scope.authError = x;
      });
    };
  }])
;