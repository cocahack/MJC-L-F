$(document).ready(function(){
    $('#onSub').on('mouseover focus', function(){
        $('.list').css('display','none'); 
        $('.subNav').css('display','block'); 
    });
    $('#onList').on('mouseover focus', function(){
         $('.subNav').css('display','none'); 
        $('.list').css('display','block'); 
    })
    $('#onRegit').on('mouseover focus', function(){
         $('.subNav').css('display','none'); 
        $('.list').css('display','none'); 
    })
    $('#mainNav').on('mouseleave blur',function(){
        $('.list').css('display','none'); 
        $('.subNav').css('display','none'); 
    });
});