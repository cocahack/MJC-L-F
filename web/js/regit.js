$(document).ready(function(){
	$("#phonenum0").keydown(function(e) {
        if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 ||
            (e.keyCode === 65 && (e.ctrlKey === true || e.metaKey === true)) || 
            (e.keyCode >= 35 && e.keyCode <= 40)) {
                 return;
        }
        if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
            e.preventDefault();
        }
    });
	$("#phonenum1").keydown(function(e) {
        if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 ||
            (e.keyCode === 65 && (e.ctrlKey === true || e.metaKey === true)) || 
            (e.keyCode >= 35 && e.keyCode <= 40)) {
                 return;
        }
        if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
            e.preventDefault();
        }
    });
	$("#phonenum2").keydown(function(e) {
        if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 ||
            (e.keyCode === 65 && (e.ctrlKey === true || e.metaKey === true)) || 
            (e.keyCode >= 35 && e.keyCode <= 40)) {
                 return;
        }
        if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
            e.preventDefault();
        }
    });
	$("#phonenum0").change(function(e){
		var phonenum = $('#phonenum0').val()+$('#phonenum1').val()+$('#phonenum2').val();
	    $('#fullphonenum').val(phonenum);
	});
	$("#phonenum1").change(function(e){
		var phonenum = $('#phonenum0').val()+$('#phonenum1').val()+$('#phonenum2').val();
	    $('#fullphonenum').val(phonenum);
	});
	$("#phonenum2").change(function(e){
		var phonenum = $('#phonenum0').val()+$('#phonenum1').val()+$('#phonenum2').val();
	    $('#fullphonenum').val(phonenum);
	});
	
    $('#imgupload').change(function(e){
        $in=$(this);
        $in.next().html($in.val());
    });
    var now = new Date();
    var year= now.getFullYear();
    var $day = $('#day');
    $('#year').val(year);
    
    $('#month').on('change', function(){
        var inputMonth = $('#month').val();
        var lastDay = ( new Date( year, inputMonth, 0) ).getDate();
        $day.empty();
        $day.append('<option value="">'+'-'+'</option>');
        for(var i=1; i<=lastDay; i++){
        	if(i<10){
        		$day.append('<option value="0'+i+'">'+i+'</option>');
        	}else{
            $day.append('<option value="'+i+'">'+i+'</option>');
        	}
        }
    });
    $('#day').on('change', function(){
    	var fulldate = $('#year').val()+'-'+$('#month').val()+'-'+$('#day').val();
        $('#fullDate').val(fulldate);
    });
    
    
    
    var imgTarget = $('.preview-image .upload-hidden');
    var fileTarget = $('.filebox .upload-hidden');
      fileTarget.on('change', function(){  // 값이 변경되면
    if(window.FileReader){  // modern browser
      var filename = $(this).val().split('/').pop().split('\\').pop();
    } 
    else {  // old IE
      var filename = $(this).val().split('/').pop().split('\\').pop();  // 파일명만 추출
    }
    // 추출한 파일명 삽입
    $(this).siblings('.upload-name').val(filename);
  });
      if(fileTarget.val()!=null){
    	  fileTarget.trigger('change');
      }
    imgTarget.on('change', function(){
        var parent = $(this).parent();
        parent.children('.upload-display').remove();

        if(window.FileReader){
            //image 파일만
            if (!$(this)[0].files[0].type.match(/image\//)) return;
            
            var reader = new FileReader();
            
            reader.onload = function(e){
                var src = e.target.result;
                parent.prepend('<div class="upload-display"><div class="upload-thumb-wrap"><img src="'+src+'" class="upload-thumb"></div></div>');
            }
            reader.readAsDataURL($(this)[0].files[0]);
        }

        else {
            $(this)[0].select();
            $(this)[0].blur();
            var imgSrc = document.selection.createRange().text;
            parent.prepend('<div class="upload-display"><div class="upload-thumb-wrap"><img class="upload-thumb"></div></div>');

            var img = $(this).siblings('.upload-display').find('img');
            img[0].style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(enable='true',sizingMethod='scale',src=\""+imgSrc+"\")";        
        }
    });
    
    $('.ver1').on('submit',function(e){
        var fileNm = $("#input-file").val();
        var phonenum = $('#phonenum0').val()+$('#phonenum1').val()+$('#phonenum2').val();
	    $('#fullphonenum').val(phonenum);
        if(fileNm.trim()!=''){
             var ext = fileNm.slice(fileNm.lastIndexOf(".") + 1).toLowerCase();
 
            if (($.inArray(ext, ['gif','png','jpg','jpeg']) == -1)) {
                alert('gif,png,jpg,jpeg 파일만 업로드 할수 있습니다.');
                return false;
            }
        }
        if(!$.isNumeric($('#stnum').val())||$('#stnum').val().length<10){
            $('#stnum').val('').focus();
            return false;
        }
        else if(($('#phonenum0').val()==''||$('#phonenum1').val()==''||$('#phonenum2').val()=='')&&$('#kakao').val()==''){
        	alert("전화번호 또는 카카오톡 ID를 입력하세요.");
           $('#phonenum1').focus();
          return false;
        } 
        else if($('#month').val()==''||$('#day').val()==''){
        	alert('날짜를 정확하게 입력하세요.');
        	return false;
        }
    });
    
});