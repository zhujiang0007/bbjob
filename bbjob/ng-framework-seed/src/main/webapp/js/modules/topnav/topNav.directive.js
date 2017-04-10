

(function() {
	'use strict';

angular
.module('app.topnav')
.directive('topNav', topNav);
topNav.$inject = ['$timeout', '$compile'];
function topNav( $timeout, $compile) {
  var directive = {
			restrict: 'A',
			scope: {
				topNavMenu: "=?"
			},
		    link: link
		  };
  return directive;  
  function link (scope, element, attrs){
  	
	var currentClassName = randomClass();
	element.addClass(currentClassName);
	var el = $("." + currentClassName);
	var parentEl = el.parent();
	
	var menuList = 1;
	
	scope.topDis = true;
	scope.btmDis = true;
	
	var arrowHTML = '<div class="topNavArrowGroup">' +
						'<button id="topNavArrowTop" type="button" disabled class="btn btn-info btn-xs"><i class="icon-arrow-up"></i></button>' +
						'<button id="topNavArrowBtm" type="button" class="btn btn-info btn-xs"><i class="icon-arrow-down"></i></button>' +
					'</div>';
	
	parentEl.append(arrowHTML);
	
    var arrow = $('.topNavArrowGroup');
    var arrowTop = $('#topNavArrowTop');
    var arrowBtm = $('#topNavArrowBtm');
  	var n = 0;
  	
	//menu变化监视
	scope.$watch('topNavMenu', init, true);
	//window宽度监视
	$(window).resize(function(){
		init();
	});
	
  	init();
  	
  	//main obj start
  	function init(){
  		var menu = {}, cont = {};
  		if(!attrs.topNavMenu) return;
  		//获取menu的object和length
  		 menu = {
			items: scope.topNavMenu,
			counts: scope.topNavMenu.length
			};
		el.attr('top-nav-menu-length', menu.counts);
		//获取宽高
		$timeout(function(){
			cont = {
					width: el.width(),
					height: el.height()
				};
			//判断菜单行
			if(cont.height>el.parent('#mainNavContainer').height()){
				if($(window).width()<=768){
					arrow.hide();
				}else{
					arrow.show();
				}
				n = 0;
				menuList = cont.height/el.parent('#mainNavContainer').height();
				el.animate({marginTop:0}, 200);
	    		arrowTop.attr("disabled", 'disabled');
	    		arrowBtm.removeAttr("disabled");
			}else{
				arrow.hide();
				el.animate({marginTop:0}, 200);
			}
		});
		
		
		
		
  	};
  	//main obj end
  		
    arrowTop.click(function(){
    	n--;
    	console.log(n)
    	arrowAnimate(n);
		arrowBtm.removeAttr("disabled");
    	if(n==0){
    		arrowTop.attr("disabled", 'disabled');
    		}
    });
    arrowBtm.click(function(){
    	n++;
    	arrowAnimate(n);
		arrowTop.removeAttr("disabled");
    	if(n==menuList-1){
    		arrowBtm.attr("disabled", 'disabled');
    		}
    });
	
    function arrowAnimate (num){
    	el.css({borderLeftColor:"rgba(255,255,255,0.1)"});
    	el.animate({marginTop:-num*55},200,function(){
        	el.css({borderLeftColor:"rgba(255,255,255,0)"});
    	});
    }
	
	
  };
}




//随机ClassName
function randomClass(){
	var className = "";
	for( var i = 0; i < 15; i ++ ) {
		className += String.fromCharCode(Math.floor(Math.random()*10)+"A".charCodeAt(0));
	}
	return 'topnav-'+className;
}

		
})();