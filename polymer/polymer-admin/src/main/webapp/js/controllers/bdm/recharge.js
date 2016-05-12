'use strict';

/* Controllers */

  // controller
app.controller('RechargeCtrl', ['$scope','$rootScope', '$http','$state', function($scope,$rootScope,$http,$state) {
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
	}//$scope.switchPages = [{label:'1',num:1},{label:'2',num:2},{label:'3',num:3},{label:'4',num:4},{label:'5',num:5},{label:'>>',num:6}];
	
	
	$scope.query = function(pageNum) {
		if (pageNum==undefined)$scope.currentPage = 1;
		else $scope.currentPage = pageNum;
		$scope.list = [];
		
		$scope.condition.currentPage = $scope.currentPage;
		var param = $.param($scope.condition);
		$http.post('recharge/list', param,{headers: { "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8"}})
	      .then(function(response) {
	    	  if (response.data.status=='000000'){//操作成功
	    		  $scope.list = response.data.list;
	    		  $scope.currentPage = response.data.currentPage;
	    		  $scope.totalPage = response.data.totalPage;
	    		  $scope.totalRecord = response.data.totalRecord;
	    		  
	    		  var start = 0;
				  var end = 0;
				  if ($scope.currentPage<=3)start = 1;
				  else start = $scope.currentPage-3;
					
				  if (($scope.totalPage-$scope.currentPage)<=3) end = $scope.totalPage
				  else end = $scope.currentPage +3;
					
				  $scope.switchPages=[];
				  for (var i=start;i<=end ;i++){
					  $scope.switchPages.push({label:i+'',num:i});
				  }
	    			
	    	  }else{//操作失败
	    		  $rootScope.authError = response.data.message;
	    		  $state.go("login");
	    	  }
	      },function(x) {
	        $scope.authError = x;
	      });
    };
    
    $scope.edit = function(id){
    	if (id==undefined){
    		console.log("create a new entity");
    		
    	}else{
    		console.log("edit a new entity");
    		//load entity from server
    		var param = $.param({merchantCode:id});
    		$http.post('recharge/load', param,{headers: { "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8"}})
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
    	}
    	$state.go("papp.account.recharge.edit");
    };
    $scope.change=function(){
    	alert($scope.condition.money);
    }
    $scope.save = function(){
    	if (editform.$invalid)return;
    	var money = $scope.condition.money;
    	$scope.entity.balance=money;
    	var param = $.param($scope.entity);
    	$http.post('recharge/save',param,{headers: { "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8"}})
		.then(function(response) {
	    	  if (response.data.status=='000000'){//保存成功
	    		  $scope.entity = response.data.entity;
	    		  
	    		  $scope.isNeedRefreshList = true;
	    	  }else{//登录失败
	    		  $rootScope.authError = response.data.message;
	    		  $state.go("login");
	    	  }
	      },function(x) {
	        $scope.authError = x;
	      });
    }
    
    $scope.goBack = function(){
    	if ($scope.isNeedRefreshList){
    		$state.go("papp.account.recharge.enquiry");
    		$scope.query($scope.currentPage);
    		$scope.isNeedRefreshList = false;
    	}else{
    		$state.go("papp.account.recharge.enquiry");
    	}
    	
    }

    
}]);
