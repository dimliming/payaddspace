angular.module('app')
  .directive('myTable', ['$rootScope', function($rootScope) {
     return {
      restrict: 'E',
      template:' <table class="table table-striped m-b-none responsive"></table>',
      replace: true,
      link: function(scope, element, attrs) {   
    	  var data = [
    	              [
    	                  "Tiger Nixon",
    	                  "System Architect",
    	                  "Edinburgh",
    	                  "5421",
    	                  "2011/04/25",
    	                  "$3,120"
    	              ],
    	              [
    	                  "Garrett Winters",
    	                  "Director",
    	                  "Edinburgh",
    	                  "8422",
    	                  "2011/07/25",
    	                  "$5,300"
    	              ]
    	          ]
    	  element.dataTable({
    		  'bSort': false,
    		  'aoColumns': [ 
    		                { sWidth: "45%", bSearchable: false, bSortable: false }, 
    		                { sWidth: "45%", bSearchable: false, bSortable: false }, 
    		                { sWidth: "10%", bSearchable: false, bSortable: false } 
    		          ],
    		  data:data
    	  });
      }
     };
  }]);