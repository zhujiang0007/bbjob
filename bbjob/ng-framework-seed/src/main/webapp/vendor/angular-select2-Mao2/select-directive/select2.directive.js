/*
 * 		当前文件在router时，请放在controller前方。,,,请使用自带的msOptions存放数据，不支持repeat
 * 
 * 
 * 
 * 
 * 
 * 
 * */


(function() {
	'use strict';
	angular.module('mao.select2', []);
})();

(function() {
	'use strict';

angular
.module('mao.select2')
.directive('mSelect2', select2d);
select2d.$inject = ['$timeout'];
function select2d( $timeout) {
  var directive = {
			restrict: 'AE',
			scope: {
				msModel: '=?',
				msOptions: '=?',
//				msKeyToTheValue: '@?',						 //键对值x,y or y,x
//				msX:'@?',
//				msY: '@?',
//				msStyle: '@?',
//				msAllowclear: '@?',							
//				msMaximumselectionlength: '@?',				//限制长度
//				msMinimumresultsforsearch: '@?',			//空时显示search，赋值Infinity不显示search
//				msTokenseparators: '@?',					//自动创建标签时的分隔符 例 [',', ' ', ';']
//				required: '@?',
//				ms-show-all: '@?'
				msOnchange: '&?',				//也可以直接使用select的onchange
				msOnopen: '&?',
				msOnclose: '&?',
				isPass: '=?'
//				msOnselect: '&?',
//				msOnunselect: '&?'
			},
		    link: link
		  };
  return directive;  
  function link (scope, element, attrs){
	var currentClassName = randomClass();
	element.addClass(currentClassName);
	var el = $("." + currentClassName);
	var styleList = ['normal', 'shadow', 'red', 'blue', 'green'];

	scope.$watchCollection('msOptions', init, true);
	scope.$watch('msModel', msModelReset, true);
	
	var opt = {
			data: "",
			placeholder: "",
			allowClear: "",
			maximumSelectionLength: "",
			minimumResultsForSearch: "",
			tags: false,
			tokenSeparators: ""
		};
	
	if(!attrs.msModel) console.log('select无msModel属性');
	if(!attrs.msOptions) console.log('select无msOptions属性');
	
  	init();
  	
  	//main obj start
  	function init(){
  			
  		//验证 placeholder
  		if(attrs.msPlaceholder){
  			opt.placeholder = attrs.msPlaceholder;
  		}else{opt.placeholder = "";};
  		//验证 allowClear
  		if(attrs.msAllowclear){
  			opt.allowClear = attrs.msAllowclear;
  		}else{opt.allowClear = "";};
  		//验证 maximumSelectionLength
  		if(attrs.msMaximumselectionlength){
  			opt.maximumSelectionLength = attrs.msMaximumselectionlength;
  		}else{opt.maximumSelectionLength = "";};
  		//验证 minimumResultsForSearch
  		if(attrs.msMinimumresultsforsearch){
  			opt.minimumResultsForSearch = attrs.msMinimumresultsforsearch;
  		}else{opt.minimumResultsForSearch = "";};
  		//验证 tokenSeparators
  		if(attrs.msTokenseparators){
  			opt.tags = true; opt.tokenSeparators = attrs.msTokenseparators;
  		}else{opt.tags = "";opt.tokenSeparators="";};
  		
  		if(attrs.isPass){
  			if(scope.msModel==null || scope.msModel==""){
  				scope.isPass = false;
  			}else{
  				scope.isPass = true;
  			}
		}
  			
		//验证  msOptions msKeyToTheValue msX msY
  		if(attrs.msOptions && scope.msOptions!=null && scope.msOptions!=""){
  			if(attrs.msKeyToTheValue){
  				//键对值时的执行
  				var kv = attrs.msKeyToTheValue.replace(/\s+/g,"");
  				switch(kv){
  					case 'x,y': opt.data = xToYFn(scope.msOptions, true); break;
  					case 'y,x': opt.data = xToYFn(scope.msOptions, false); break;
  					default: console.log('错误：select msKeyToTheValue格式不正确，请使用 x,y or y,x 格式');break;
  				}
  				runSelect2(opt);
  			}else if(attrs.msX && attrs.msY){
  				//x和y赋值
  				opt.data = standard(scope.msOptions, attrs.msX, attrs.msY);
  				runSelect2(opt);
  			}else{
  				console.log('错误：select 缺少MsKey或MsValue属性值');
  			}
  		}else{
  			//没有MsOptions的情况
  			opt.data="";
  			runSelect2(opt);
  		}
  		
  		
  		
  		
  		
  		//验证select style
  		if(!attrs.msStyle){
  			if(attrs.multiple){
  				
  			}else{
  	  			styleSet('normal');
  			}
  		}else{
  			if(!attrs.multiple){
	  			if(styleList.indexOf(attrs.msStyle) != -1){
	  				styleSet(attrs.msStyle);
	  			}else{
	  	  			styleSet('normal');
	  	  			console.log('错误：msStyle请使用 normal、shadow、red、green、blue;')
	  			}
  			}
  		}
  		
  	};
  	//main obj end
  		
  		
  		
	//select open
	el.on("select2:open", function (e) { 
		if(attrs.msOnopen){
			$timeout(function(){ scope.msOnopen(); })
		}
	});
	
	//select close
	el.on("select2:close", function (e) { 
		if(attrs.msOnclose){
			$timeout(function(){ scope.msOnclose(); })
		}
		if(attrs.required){
			$timeout(function(){ 
				var cont = el.siblings('span').children('.selection').children('.select2-selection--single');
				if(scope.msModel == undefined || scope.msModel == null || scope.msModel == ""){
					cont.removeClass("select2-selection--single-success");
					cont.addClass("select2-selection--single-error");
					if(attrs.isPass){
						scope.isPass = false;
					}
				}else{
					cont.removeClass("select2-selection--single-error");
					cont.addClass("select2-selection--single-success");
					if(attrs.isPass){
						scope.isPass = true;
					}
				}
				},50);
		}
	});
	
	//select select
	el.on("select2:select", function (e) { 
		if(!attrs.multiple){
				//单选模式
				var is = el.prop('value');
				scope.msModel = is;
				if(attrs.msOnchange){
					$timeout(function(){
					scope.msOnchange({selected:is}); 
					})
				}
		}else{
			//多选模式
			var its = e.currentTarget, itsCurrent = [];
		
			for(var i = 0; i < its.length; i ++ ){
				if(its[i].selected){
					itsCurrent.push(its[i].value);
				};
			};
			scope.msModel = itsCurrent;
			$timeout(function(){
				if(attrs.msOnchange){
					scope.msOnchange({itsCurrent:is}); 
				}
			});
		
			
		}
	});
	
	//select unselect
	el.on("select2:unselect", function (e) { 
		if(!attrs.multiple){
				//单选模式
			$timeout(function(){
				var is = el.val();
				scope.msModel = is;
				if(attrs.msOnchange){
					$timeout(function(){
						scope.msOnchange({selected:is}); 
					})
				}
			},50)
		}else{
			//多选模式
			var its = e.currentTarget, itsCurrent = [];
		
			for(var i = 0; i < its.length; i ++ ){
				if(its[i].selected){
					itsCurrent.push(its[i].value);
				};
			};
			scope.msModel = itsCurrent;
			$timeout(function(){
				if(attrs.msOnchange){
					scope.msOnchange({selected:itsCurrent}); 
				}
			});
		}
		
		
	});
	
	//select change
	el.on("change", function (e) { 
		if(!attrs.multiple){
			$timeout(function(){
				//单选模式
				var is = el.prop('value');
//				scope.msModel = is;
//				if(attrs.msOnchange){
//					scope.msOnchange({selected:is}); 
//				}
			})
		}else{
			//多选模式
			var its = e.currentTarget, itsCurrent = [];
		
			$timeout(function(){
			for(var i = 0; i < its.length; i ++ ){
				if(its[i].selected){
					itsCurrent.push(its[i].value);
				};
			};
				scope.msModel = itsCurrent;
//				if(attrs.msOnchange){
//					scope.msOnchange({selected:itsCurrent}); 
//				}
			});
		}
	});
  		
  	
	//select 样式 设置
	function styleSet(pay){
		switch(pay){
		case 'normal': break;
		case 'shadow': shadowFn();break;
		case 'red': colorFn('red');break;
		case 'green': colorFn('green');break;
		case 'blue': colorFn('blue');break;
		}
	}
	//阴影设置
	function shadowFn(){
		var cont = el.siblings('span').children('.selection').children('.select2-selection--single');
		cont.addClass('select2-selection--single-shadow');
		el.on("select2:open", function (e) {
			cont.addClass('select2-selection--single-shadow-open');
			});
		el.on("select2:close", function (e) {
			cont.removeClass('select2-selection--single-shadow-open');
			});
	}
	//RGBset
	function colorFn(color){
		var cont = el.siblings('span').children('.selection').children('.select2-selection--single');
		cont.addClass('select2-selection--single-' + color);
		el.on("select2:open", function (e) {
			cont.addClass('select2-selection--single-' + color + '-open');
			$('.select2-dropdown').addClass(color + '-border')
			});
		el.on("select2:close", function (e) {
			cont.removeClass('select2-selection--single-' + color + '-open');
			$('.select2-dropdown').removeClass(color + '-border')
			});
	}
	
	
	//MsModel赋值
	function msModelFn(){
			var its = e.currentTarget,
		itsCurrent = [],
		itCurrent = "";
		
	if(attrs.multiple){
		//多选赋值model
		if(attrs.msModel){
			for(var i = 0; i < its.length; i ++ ){
				if(its[i].selected){
					itsCurrent.push(its[i].value);
				};
			};
			$timeout(function(){
				scope.msModel = itsCurrent;
			});
		};
		
	}else{
		//单选赋值model
		if(attrs.msModel){
			for(var i = 0; i < its.length; i ++ ){
				if(its[i].selected){
					itCurrent = its[i].value;
				};
			};
			$timeout(function(){
				scope.msModel = itCurrent;
			});
		};
	};
	}
	
  	
  	//standardChange  重置键对值  id and text
	function standard(data, x , y){
		
		var newArrayData = $.map(data, function (obj) {
					  obj.id = obj[x]; 
					  obj.text = obj[y];
					  return obj;
					});
		return newArrayData;
	}
	
	//针对 key to value的重置
	function xToYFn(data, b){
			var newArrayData = [];
		if(b){
			for(var i in data){
				var it = {id: i, text: data[i]};
				newArrayData.push(it);
		    }
		}else{
			for(var i in data){
				var it = {id: data[i], text: i};
				newArrayData.push(it);
		    }
		}
			return newArrayData;
	}

	//随机ClassName
	function randomClass(){
		var className = "";
		for( var i = 0; i < 15; i ++ ) {
			className += String.fromCharCode(Math.floor(Math.random()*26)+"A".charCodeAt(0));
		}
		return className;
	}


	//如果不是多选， 则在开头追加一个className为msPlaceholder的option元素，以显示placeholder
	function placeh(data){
		if(data==null || data==""){
			el.html("");
		}
		if(!attrs.multiple){
			if(attrs.msPlaceholder){
				if(element.children().eq(0).prop('class') !== 'msPlaceholder')
				element.prepend('<option value="" class="msPlaceholder"></option>');
			}
		}
	}
	
	//是否显示全部按钮,只有单选才有全选按钮
	function showAllFn(data){
		
		if(attrs.msShowAll){
			
			if(!attrs.multiple){
				
				if(data==null || data==""){
					
					data = [{id: " ", text: "全部"}];
				}else{
					
					data.unshift({id: " ", text: "全部"});
					
				}
				
			}
			
		}
		
		return data;
		
	};


	
	//执行select2
	function runSelect2(opt){
		placeh(opt.data);
		opt.data = showAllFn(opt.data);
		el.html("");
		el.select2(opt);
		$timeout(function(){
			if(attrs.msModel){
				el.val(scope.msModel).trigger("change");
			}
		})
	}
	

	//msModel 初始化赋值
	function msModelReset(){
		$timeout(function(){
		if(attrs.msModel){
			el.val(scope.msModel).trigger("change");
		};
		})
	};
	
	
	
  };
}




		
})();