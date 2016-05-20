'use strict';

/* Controllers */

  // controller
app.controller('AccountCtrl', ['$scope','$rootScope', '$http','$state', function($scope,$rootScope,$http,$state) {
    if ($scope.condition==undefined){
    	$scope.condition = {};
    }
    if ($scope.entity==undefined){
    	$scope.entity = {};
    }
	if ($scope.currentPage==undefined){
		$scope.currentPage = 0;
	}
	if ($scope.totalPage==undefined){
		$scope.totalPage = -1;
	}
	if ($scope.totalRecord==undefined){
		$scope.totalRecord = -1;
	}
	if ($scope.isNeedRefreshList==undefined){
		$scope.isNeedRefreshList = false;
	}
	if ($scope.list==undefined){
		$scope.list = [];
	}
	
	
    
    $scope.edit = function(){
    		$http.post('account/load',{headers: { "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8"}})
    		.then(function(response) {
  	    	  if (response.data.status=='000000'){//登录成功
  	    		  $scope.entity = response.data.entity;     			
  	    	  }else{//登录失败
  	    		  $rootScope.authError = response.data.message;
  	    		  $state.go("login");
  	    	  }
  	      },function(x) {
  	        $scope.authError = x;
  	      });
    		//$scope.entity = {};
    };
    $(document).ready($scope.edit);
    
}]);
