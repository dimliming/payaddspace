'use strict';

/* Controllers */

  // controller
app.controller('MerchantUserCtrl', ['$scope','$rootScope', '$http','$state', function($scope,$rootScope,$http,$state) {
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
	//$scope.switchPages = [{label:'1',num:1},{label:'2',num:2},{label:'3',num:3},{label:'4',num:4},{label:'5',num:5},{label:'>>',num:6}];
    
    $scope.edit = function(id){
    		$http.post('merchantUser/load',{headers: { "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8"}})
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
    $scope.save = function(){
    	if (editform.$invalid)return;
    	
    	var param = $.param($scope.entity);
    	$http.post('merchantUser/save', param,{headers: { "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8"}})
		.then(function(response) {
	    	  if (response.data.status=='000000'){//保存成功
	    		  $scope.entity = response.data.entity;	    		  
	    		  $scope.isNeedRefreshList = true;
	    		  alert("保存成功");
	    	  }else{//登录失败
	    		  $rootScope.authError = response.data.message;
	    		  $state.go("login");
	    	  }
	      },function(x) {
	        $scope.authError = x;
	      });
    }
    $(document).ready($scope.edit);
}]);
