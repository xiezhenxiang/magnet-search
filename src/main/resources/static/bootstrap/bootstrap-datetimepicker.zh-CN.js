/**
 * Simplified Chinese translation for bootstrap-datetimepicker
 * Yuan Cheung <advanimal@gmail.com>
 */
;(function($){
	$.fn.datetimepicker.dates['zh-CN'] = {
			days: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"],
			daysShort: ["周日", "周一", "周二", "周三", "周四", "周五", "周六", "周日"],
			daysMin:  ["日", "一", "二", "三", "四", "五", "六", "日"],
			months: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
			monthsShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
			today: "今天",
			suffix: [],
			meridiem: ["上午", "下午"]
	};
}(jQuery));

$(function() {  
    $('.year').datetimepicker({  
       language: 'zh-CN',  
       format:'yyyy-mm-dd',//选择完日期后，input框里的时间值的格式  
       startDate:"2017-01-01",//开始日期时间，在此之前的都不可选，同理也有endDate  
       weekStart: 1,  
		todayBtn:  1,  
		autoclose: 1,  
		todayHighlight: 1,  
		startView: 2,  
		forceParse: 0,
		minView: "month",
		showMeridian: 1  
   });  
});  