$(document).ready(function(){
    var pathName = $(location).attr('search');

    if(pathName.match('type=lost')){
        $('.side>ul>li').first('li').find('a').addClass('select');
    }
    else if(pathName.match('type=found')){
        $('.side>ul>li').last('li').find('a').addClass('select');
    }
    $('#slidedown').on('click keypress',function(e){
        e.preventDefault();
        $('.detail_search').slideToggle();
    });
    $('.date').datepicker({
        datepicker: true,
        dateFormat: "yy-mm-dd"
    });
    $('.current').on('click keypress',function(e){
    	e.preventDefault();
    });
});