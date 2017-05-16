/*
 * 
 * <text-over text-content="texts" text-row="1"></text-over>
 * 
 * text-content: 文字内容。也可以把文字放在标签内部。（内容尽可能不要带有HTML标签，内容中有html标签，会影响计算，导致行数计算、溢出计算易出错。）
 * 
 * text-row: 行数，所限制的行数；
 * 
 * 此指令会给 text-over标签内的span 赋id属性；span标签会清除padding属性和定位属性；
 * 
 * 
 * 若计算不正确，请删除内容中的html标签，并且给标签赋值行高在测试；
 * 
 * 不支持响应改变，需刷新页面
 * 
 * 
 * */


(function() {
	'use strict';
	angular.module('text.over', []);
})();

(function() {
	'use strict';

angular
.module('text.over')
.directive('textOver', textOver);
textOver.$inject = ['$timeout'];
function textOver( $timeout) {
  var directive = {
			restrict: 'AE',
			scope: {
				textContent: '=?',
				textRow: '@?'
			},
			template: '<div><span ng-transclude></span></div>',
			transclude: true,
			replace: true,
		    link: link
		  };
  return directive;  
  function link (scope, element, attrs){
	
	var currentClassName = randomClass();
	element.children('span').prop('id', currentClassName);
	var el = document.getElementById(currentClassName);
	el.style.padding = 0;
	el.style.position = 'initial';
	
	init();
	
	function init(){
		
		if(!scope.textRow) return;
		
		if(!scope.textContent) {
			
			var str = el.innerHTML;
			
		}else{
			
			var str = scope.textContent;
			
		};
		
  		var style = getComputedStyle(el);
  		
  		var lineHeight = style["line-height"];  
  		

  		var at = scope.textRow*parseInt(lineHeight);		
  		
  		var tempstr = str;                       
  		
  		el.innerHTML = tempstr;                
  		
  		var len = tempstr.length;
  		
  		var i = 0;
  		
		if(el.offsetHeight <= at){             
			
		    el.innerHTML = tempstr;
		    
		}else{      
			
    		var temp = "";						
    		
    		el.innerHTML = temp;
    		
			while(el.offsetHeight <= at){
				
			         temp = tempstr.substring(0, i+1);
			         
			         i++;
			         
			         el.innerHTML = temp;
			         
			     }    		
			     
    		 var slen = temp.length;
    		 
		     tempstr = temp.substring(0, slen-1);
		     
		     len = tempstr.length
		     
		     el.innerHTML = tempstr.substring(0, len-3) +"...";     
		     
		     el.height = at +"px";                                
		     
		}
  		
	}		
	scope.$watchCollection('textContent', init, true);
	
	//随机ClassName
	function randomClass(){
		
		var className = "";
		
		for( var i = 0; i < 6; i ++ ) {
			
			className += String.fromCharCode(Math.floor(Math.random()*26)+"A".charCodeAt(0));
			
		}
		
		return 'textOver-'+className;
		
	}
	
  };
};




		
})();