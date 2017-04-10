$(document).ready(function(){
	var pathName = $(location).attr('pathname');

    if(pathName.match('notice')){
        $('.side>ul>li').first('li').find('a').addClass('select');
    }
    else if(pathName.match('faq')){
        $('.side>ul>li').eq(1).find('a').addClass('select');
    }
    else if(pathName.match('loc')){
        $('.side>ul>li').eq(2).find('a').addClass('select');
    }
    else if(pathName.match('other')){
        $('.side>ul>li').last('li').find('a').addClass('select');
    }
	
});