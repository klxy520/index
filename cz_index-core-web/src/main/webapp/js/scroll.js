// JavaScript Document

/*myScroll使用方法
$(function(){
	$("div.list_lh").myScroll({
		speed:100, //数值越大，速度越慢
		rowHeight:20 //li的高度
	});

*/

/*easysroll使用方法
	$(".list_lh").easysroll({
		direction: "top", //滚动方向 left（向左）right（向右） top(向上) bottom(向下) 默认left
		numberr: "2", //每一次滚动数量 默认是1
		delays:"1000",//完成一次动画所需时间 默认是1000等于1秒
		scrolling: "2000",//每一次动画的时间间隔 默认是1000等于1秒
		fadein:false,  //是否支持淡入或淡出 默认false
		enterStop:true//鼠标移入是否暂停滚动 默认是true
		})
}); */

(function($) {
	$.fn.myScroll = function(options) {
		// 默认配置
		var defaults = {
			speed : 40, // 滚动速度,值越大速度越慢
			rowHeight : 24
		// 每行的高度
		};

		var opts = $.extend({}, defaults, options), intId = [];

		function marquee(obj, step) {

			obj.find("ul").animate({
				marginTop : '-=1'
			}, 0, function() {
				var s = Math.abs(parseInt($(this).css("margin-top")));
				if (s >= step) {
					$(this).find("li").slice(0, 1).appendTo($(this));
					$(this).css("margin-top", 0);
				}
			});
		}

		this.each(function(i) {
			var sh = opts["rowHeight"], speed = opts["speed"], _this = $(this);
			intId[i] = setInterval(function() {
				if (_this.find("ul").height() <= _this.height()) {
					clearInterval(intId[i]);
				} else {
					marquee(_this, sh);
				}
			}, speed);

			_this.hover(function() {
				clearInterval(intId[i]);
			}, function() {
				intId[i] = setInterval(function() {
					if (_this.find("ul").height() <= _this.height()) {
						clearInterval(intId[i]);
					} else {
						marquee(_this, sh);
					}
				}, speed);
			});

		});

	}

})(jQuery);

(function($) {
	$.fn.easysroll = function(options) {
		var parameter = {
			direction : "left",
			numberr : "1",
			delays : "1000",
			scrolling : "1000",
			fadein : false,
			enterStop : true
		};
		var ops = $.extend(parameter, options);
		var $this = $(this);
		var _this = this;
		var _time = null;
		var obj = _this.find("ul");
		var items = obj.find("li");
		var itemsleg = items.length;
		var itemsW = items.outerWidth(true);
		var itemsH = items.outerHeight(true);
		var _direction = ops.direction;
		var _numberr = ops.numberr;
		var _delays = ops.delays;
		var _scrolling = ops.scrolling;
		var _fadein = ops.fadein;
		var _enterStop = ops.enterStop;
		if (_direction == "top" || _direction == "bottom") {
			items.css({
				"float" : "none"
			});
			obj.width(itemsW * itemsleg);
			if (_direction == "bottom") {
				obj.css("margin-top", -_numberr * itemsH);
			}
		} else if (_direction == "left" || _direction == "right") {
			items.css({
				"float" : "left"
			});
			obj.width(itemsW * itemsleg);
			if (_direction == "right") {
				obj.css("margin-left", -_numberr * itemsW);
			}
		} else {
			alert("您配置的滚动方向有误，请重新配置");
			return true;
		}
		function scroll() {
			if (_direction == "left") {
				obj.animate({
					"margin-left" : -_numberr * itemsW
				}, Number(_delays), function() {
					for (var i = 0; i < _numberr; i++) {
						obj.find("li").eq(0).appendTo(obj);
					}
					obj.css({
						"margin-left" : 0
					})
					if (_fadein) {
						obj.find("li").eq(0).animate({
							"opacity" : 0
						}, Number(_delays));
						obj.find("li").eq(itemsleg - 1).css({
							"opacity" : 1
						});
					}
				});
			} else if (_direction == "right") {
				obj.animate({
					"margin-left" : 0
				}, Number(_delays), function() {
					for (var i = 0; i < _numberr; i++) {
						obj.find("li").eq(itemsleg - 1).prependTo(obj);
					}
					;
					obj.css("margin-left", -_numberr * itemsW);
					if (_fadein) {
						obj.find("li").eq(0).animate({
							"opacity" : 1
						}, Number(_delays));
						obj.find("li").eq(itemsleg - 1).css({
							"opacity" : 0
						});
					}
				});
			} else if (_direction == "top") {
				obj.animate({
					"margin-top" : -_numberr * itemsH
				}, Number(_delays), function() {
					for (var i = 0; i < _numberr; i++) {
						obj.find("li").eq(0).appendTo(obj);
					}
					obj.css({
						"margin-top" : 0
					});
					if (_fadein) {
						obj.find("li").eq(0).animate({
							"opacity" : 0
						}, Number(_delays));
						obj.find("li").eq(itemsleg - 1).css({
							"opacity" : 1
						});
					}
				});
			} else if (_direction == "bottom") {
				obj.animate({
					"margin-top" : 0
				}, Number(_delays), function() {
					for (var i = 0; i < _numberr; i++) {
						obj.find("li").eq(itemsleg - 1).prependTo(obj);
					}
					obj.css("margin-top", -_numberr * itemsH);
					if (_fadein) {
						obj.find("li").eq(0).animate({
							"opacity" : 1
						}, Number(_delays));
						obj.find("li").eq(itemsleg - 1).css({
							"opacity" : 0
						});
					}
				});
			}
		}
		$this.hover(function() {
			if (_enterStop) {
				clearInterval(_time);
			}
		}, function() {
			_time = setInterval(scroll, _scrolling);
		}).trigger('mouseleave');
	}
})(jQuery);