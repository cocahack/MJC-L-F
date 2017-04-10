$(document).ready(function(){
    $('#notice>a').on('click keypress',function(){
        $(this).addClass('tab_active');
        $($(this).parent()).next().css('display','block');
        $('#qna>a').removeClass('tab_active');
        $($('#qna>a').parent()).next().css('display','none');
    });
    $('#qna>a').on('click keypress',function(){
        $(this).addClass('tab_active');
        $($(this).parent()).next().css('display','block');
        $('#notice>a').removeClass('tab_active');
        $($('#notice>a').parent()).next().css('display','none');
    });
    $('#loss>a').on('click keypress',function(){
        $(this).addClass('tab_active');
        $($(this).parent()).next().css('display','block');
        $('#found>a').removeClass('tab_active');
        $($('#found>a').parent()).next().css('display','none');
    });
    $('#found>a').on('click keypress',function(){
        $(this).addClass('tab_active');
        $($(this).parent()).next().css('display','block');
        $('#loss>a').removeClass('tab_active');
        $($('#loss>a').parent()).next().css('display','none');
    });
});