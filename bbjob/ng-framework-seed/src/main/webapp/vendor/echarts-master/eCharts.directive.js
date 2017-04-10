(function() {
	'use strict';
	angular
		.module('m.echarts', [])
})();
	
(function() {
	'use strict';

	angular
		.module('m.echarts')
		.directive('eCharts', eCharts);
	function eCharts() {
		var directive = {
			restrict: 'EA',
			scope: {
				edata: '=?',
				eheight: '@?'	//eid 和  eheight 输入必须为字符=必须含有单引号;
			},
			link: link
		};
		return directive;
		function link(scope, element, attrs) {
			if(!scope.eheight) return;
			
			var currentClassName = randomClass();
			element.prop('id', currentClassName);
			var thisIt =document.getElementById(currentClassName);
			
			thisIt.style.height = function(){
				if(scope.eheight.substr(-2) == 'px'){
					return scope.eheight;
				}else{
					return scope.eheight + 'px';
				}
			}();
			var myChart = echarts.init(thisIt);
			init(scope.edata);
			function init(data){
				if(!data) return;
				myChart.setOption(data);
			}
			scope.$watchCollection('edata', init, true);
		}
		
	//随机ClassName
	function randomClass(){
		var className = "";
		for( var i = 0; i < 15; i ++ ) {
			className += String.fromCharCode(Math.floor(Math.random()*26)+"A".charCodeAt(0));
		}
		return className;
	}
	}
		
})();