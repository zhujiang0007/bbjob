/*
 * 
 * table-nothing 将属性放入table中；
 * 
 * table-data="demo"  table的循环数据，放过来；
 * 
 * nothing-img=""    可选，提示图片
 * 
 * nothing-text=""    提示文字
 * 
 * 
 * */


(function() {
	'use strict';
	angular.module('table.nothing', []);
})();

(function() {
	'use strict';

angular
.module('table.nothing')
.directive('msTableNothing', tableNothing);
tableNothing.$inject = ['$timeout'];
function tableNothing( $timeout) {
  var directive = {
			restrict: 'AE',
			scope:{
			    msTableData: '=?',
			    msNothingImg: '@?',
			    msNothingText: '@?',
		    	msTableLength: '=?'
			  },
		    link: link,
		  };
  return directive;  
  
  function link (scope, element, attrs){
//  	var thlength = element.find('th').length;
  	var thlength = scope.msTableLength;
  	var imgTe = "";
  	var textTe = "没有数据啊~~";
  	if(attrs.msNothingImg){
  		imgTe = '<img src="' + attrs.msNothingImg + '" />';
  	}else{
  		imgTe = "";
  	}
  	if(attrs.msNothingText){
  		textTe = attrs.msNothingText;
  	}
  	
  	var template = '<tr class="tableNothingMsg">'+
						'<td colspan="' + thlength + '" style="text-align: center !important;">'+
							textTe + imgTe +
						'</td>'+
					'</tr>';
  	
	init ();
	
	function init () {
		
		if(!attrs.msTableData) return;
		if($('.tableNothingMsg').length>0){
			$('.tableNothingMsg').remove();
		}
		if(scope.msTableData == "" || scope.msTableData == null || !scope.msTableData.length>0){
			element.append(template);
		}
	};
	
	scope.$watchCollection('msTableData', init, true);
	
	
  };
};




		
})();